package nl.abnamro.management.recipe.integration_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import java.util.List;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Sql(value = "/test-data.sql", executionPhase = BEFORE_TEST_CLASS)
public class RetrieveRecipeServiceImplT {

    @Autowired
    private RetrieveRecipeService retrieveRecipeService;

    @Test
    public void testGetRecipeList() {
        // given
        int pageNo = 1;

        // call
        PagedResult<RecipeResponse> pagedResult = retrieveRecipeService.getRecipeList(pageNo);

        // assert
        assertNotNull(pagedResult);
        assertFalse(pagedResult.data().isEmpty());
    }

    @Test
    public void testGetRecipeById() {
        // given
        Integer recipeId = 1;

        // call
        RecipeResponse recipeResponse = retrieveRecipeService.getRecipeById(recipeId);

        // assert
        assertNotNull(recipeResponse);
        assertEquals("Spaghetti Bolognese", recipeResponse.name);
    }

    @Test
    public void testFilterRecipe() {
        //// given
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);

        // call
        List<RecipeResponse> filteredRecipes = retrieveRecipeService.filterRecipe(recipeSearch);

        // assert
        assertNotNull(filteredRecipes);
        assertFalse(filteredRecipes.isEmpty());
    }

    @Test
    public void shouldGetNonExistentRecipeById() {
        // given
        Integer nonExistentRecipeId = 999;

        // call and assert
        assertThrows(RecipeNotFoundException.class, () -> {
            retrieveRecipeService.getRecipeById(nonExistentRecipeId);
        });
    }

    @Test
    public void shouldFilterRecipeWithNoResults() {
        // given
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);
        recipeSearch.setMinServings(100);
        recipeSearch.setMaxServings(200);

        // call
        List<RecipeResponse> filteredRecipes = retrieveRecipeService.filterRecipe(recipeSearch);

        // assert
        assertNotNull(filteredRecipes);
        assertTrue(filteredRecipes.isEmpty());
    }
}
