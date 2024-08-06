package nl.abnamro.management.recipe.integration_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import java.util.List;
import java.util.Optional;
import nl.abnamro.management.recipe.config.IntegrationTestContainerConfig;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.impl.ModifyRecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(IntegrationTestContainerConfig.class)
@Sql(value = "/test-data.sql", executionPhase = BEFORE_TEST_CLASS)
public class ModifyRecipeServiceImplT {

    @Autowired
    private ModifyRecipeServiceImpl modifyRecipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void shouldCreateRecipe() {
        // given
        RecipeRequest request = new RecipeRequest();
        request.setName("Test Recipe");
        request.setType(RecipeType.VEGETARIAN);
        request.setNumberOfServings(4);
        request.setIngredients(List.of("Ingredient1", "Ingredient2"));
        request.setInstructions(List.of("Step1", "Step2"));

        // call
        CreateRecipeResponse response = modifyRecipeService.createRecipe(request);

        // assert
        assertNotNull(response);
        assertNotNull(response.getRecipeId());

        Optional<RecipeEntity> createdRecipe = recipeRepository.findById(Long.valueOf(response.getRecipeId()));
        assertTrue(createdRecipe.isPresent());
        assertEquals("Test Recipe", createdRecipe.get().getName());
    }

    @Test
    public void shouldUpdateRecipe() {
        // given
        Long recipeId = 1L;
        RecipeRequest updatedRequest = new RecipeRequest();
        updatedRequest.setName("Updated Recipe");
        updatedRequest.setType(RecipeType.OTHER);
        updatedRequest.setNumberOfServings(5);
        updatedRequest.setIngredients(List.of("UpdatedIngredient1", "UpdatedIngredient2"));
        updatedRequest.setInstructions(List.of("UpdatedStep1", "UpdatedStep2"));

        // call
        modifyRecipeService.updateRecipe(recipeId, updatedRequest);

        // assert
        Optional<RecipeEntity> updatedRecipe = recipeRepository.findById(recipeId);
        assertTrue(updatedRecipe.isPresent());
        assertEquals("Updated Recipe", updatedRecipe.get().getName());
    }

    @Test
    public void shouldDeleteRecipe() {
        // given
        Long recipeId = 1L;

        // call
        modifyRecipeService.deleteRecipeById(recipeId);

        // assert
        Optional<RecipeEntity> deletedRecipe = recipeRepository.findById(recipeId);
        assertFalse(deletedRecipe.isPresent());
    }

    @Test
    public void shouldUpdateNonExistentRecipe() {
        // given
        Long nonExistentRecipeId = 999L;
        RecipeRequest updatedRequest = new RecipeRequest();
        updatedRequest.setName("Non-Existent Recipe");
        updatedRequest.setType(RecipeType.OTHER);
        updatedRequest.setNumberOfServings(5);
        updatedRequest.setIngredients(List.of("Ingredient1", "Ingredient2"));
        updatedRequest.setInstructions(List.of("Step1", "Step2"));

        // call and assert
        assertThrows(RecipeNotFoundException.class, () -> {
            modifyRecipeService.updateRecipe(nonExistentRecipeId, updatedRequest);
        });
    }

    @Test
    public void shouldDeleteNonExistentRecipe() {
        // given
        Long nonExistentRecipeId = 999L;

        // call and assert
        assertThrows(RecipeNotFoundException.class, () -> {
            modifyRecipeService.deleteRecipeById(nonExistentRecipeId);
        });
    }
}
