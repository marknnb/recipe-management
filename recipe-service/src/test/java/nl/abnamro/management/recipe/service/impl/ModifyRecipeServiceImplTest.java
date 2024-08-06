package nl.abnamro.management.recipe.service.impl;

import static nl.abnamro.management.recipe.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.repository.IngredientEntityRepository;
import nl.abnamro.management.recipe.repository.InstructionEntityRepository;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ModifyRecipeServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientEntityRepository ingredientEntityRepository;

    @Mock
    private InstructionEntityRepository instructionEntityRepository;

    @Mock
    private ErrorMessagePropertyConfig messageProvider;

    @Mock
    private RecipeMapper recipeMapper;

    ModifyRecipeService modifyRecipeService;

    @BeforeEach
    void setUp() {
        modifyRecipeService = new ModifyRecipeServiceImpl(
                recipeRepository,
                ingredientEntityRepository,
                instructionEntityRepository,
                messageProvider,
                recipeMapper);
    }

    @Test
    void shouldCreateTheRecipe() {
        // given
        RecipeRequest recipeRequest = getRecipeRequest();
        when(recipeMapper.mapToRecipeEntity(any(RecipeRequest.class))).thenReturn(getTestRecipeEntity());
        when(recipeRepository.save(any())).thenReturn(getTestRecipeEntity());

        // call
        CreateRecipeResponse recipe = modifyRecipeService.createRecipe(recipeRequest);

        // assert
        assertAll("Assert response", () -> assertNotNull(recipe), () -> assertEquals(recipe.getRecipeId(), "1"));
    }

    @Test
    void shouldUpdateTheRecipe() {
        // given
        var recipeUpdateRequest = getRecipeUpdateRequest();
        var recipeId = 1L;

        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(getTestRecipeEntity()));
        when(recipeMapper.mapToRecipeEntity(any(RecipeRequest.class))).thenReturn(getTestRecipeEntity());
        // doNothing().when(recipeRepository).save( any(RecipeEntity.class));
        when(recipeRepository.save(any())).thenReturn(getTestRecipeEntity());

        // call
        assertDoesNotThrow(() -> modifyRecipeService.updateRecipe(recipeId, recipeUpdateRequest));
    }

    @Test
    void shouldThrowRecipeNotFoundException() {
        // given
        var recipeUpdateRequest = getRecipeUpdateRequest();
        var recipeId = 1L;

        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // call
        assertThrows(
                RecipeNotFoundException.class, () -> modifyRecipeService.updateRecipe(recipeId, recipeUpdateRequest));
    }

    @Test
    void shouldDeleteRecipe() {
        // given
        var recipeId = 1L;

        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(getTestRecipeEntity()));

        // call
        assertDoesNotThrow(() -> modifyRecipeService.deleteRecipeById(recipeId));
    }

    @Test
    void shouldRaiseRecipeNotFoundWhileDeletingEntity() {
        // given
        var recipeId = 1L;

        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // call
        assertThrows(RecipeNotFoundException.class, () -> modifyRecipeService.deleteRecipeById(recipeId));
    }
}
