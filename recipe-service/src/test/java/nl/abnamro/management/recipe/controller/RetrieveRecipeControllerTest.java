package nl.abnamro.management.recipe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.impl.RetrieveRecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RetrieveRecipeControllerTest {

    @Mock
    private RetrieveRecipeServiceImpl retrieveRecipeService;

    @InjectMocks
    private RetrieveRecipeController retrieveRecipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetRecipeByIdSuccess() {
        // given
        int recipeId = 1;
        RecipeResponse expectedResponse = RecipeResponse.builder()
                .recipeId("1")
                .name("test")
                .instructions(List.of("cut onions"))
                .instructions(List.of("onions"))
                .type("VEGETARIAN")
                .numberOfServings(5)
                .build();
        when(retrieveRecipeService.getRecipeById(recipeId)).thenReturn(expectedResponse);

        // call
        ResponseEntity<RecipeResponse> response = retrieveRecipeController.getRecipe(recipeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(retrieveRecipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    void shouldGetRecipeByIdNotFound() {
        // given
        int recipeId = 999;
        when(retrieveRecipeService.getRecipeById(recipeId)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        // call

        var recipeNotFoundException = assertThrows(RecipeNotFoundException.class, () -> {
            retrieveRecipeController.getRecipe(recipeId);
        });
        assertEquals("Recipe not found", recipeNotFoundException.getMessage());
    }

    @Test
    void shouldSearchRecipesSuccess() {
        // given
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);
        List<RecipeResponse> expectedResponses = Arrays.asList(
                new RecipeResponse("1", "Test Recipe 1", "VEGETARIAN", 5, List.of("Ingredient1"), List.of("Step 1")),
                new RecipeResponse("2", "Test Recipe 2", "VEGETARIAN", 10, List.of("Ingredient2"), List.of("Step 2")));

        when(retrieveRecipeService.filterRecipe(recipeSearch)).thenReturn(expectedResponses);

        // call
        ResponseEntity<List<RecipeResponse>> response = retrieveRecipeController.filterRecipe(recipeSearch);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(retrieveRecipeService, times(1)).filterRecipe(recipeSearch);
    }

    @Test
    void shouldSearchRecipesNoResults() {
        // given
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setMinServings(10000);

        when(retrieveRecipeService.filterRecipe(recipeSearch)).thenReturn(List.of());

        // call
        ResponseEntity<List<RecipeResponse>> response = retrieveRecipeController.filterRecipe(recipeSearch);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(retrieveRecipeService, times(1)).filterRecipe(recipeSearch);
    }

    @Test
    void shouldGetAllRecipesSuccess() {
        // given
        int pageNumber = 1;
        List<RecipeResponse> expectedResponses = Arrays.asList(
                new RecipeResponse("1", "Test Recipe 1", "VEGETARIAN", 5, List.of("Ingredient1"), List.of("Step 1")),
                new RecipeResponse("2", "Test Recipe 2", "VEGETARIAN", 10, List.of("Ingredient2"), List.of("Step 2")));
        PagedResult<RecipeResponse> recipeResponsePagedResult =
                new PagedResult<>(expectedResponses, 1, 1, 1, true, true, false, false);

        when(retrieveRecipeService.getRecipeList(pageNumber)).thenReturn(recipeResponsePagedResult);

        // call
        ResponseEntity<PagedResult<RecipeResponse>> recipeList = retrieveRecipeController.getRecipeList(pageNumber);

        // Assert
        assertEquals(HttpStatus.OK, recipeList.getStatusCode());
        assertNotNull(recipeList.getBody());
        verify(retrieveRecipeService, times(1)).getRecipeList(pageNumber);
    }
}
