package nl.abnamro.management.recipe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.service.impl.ModifyRecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ModifyRecipeControllerTest {

    @Mock
    private ModifyRecipeServiceImpl modifyRecipeService;

    private ModifyRecipeController modifyRecipeController;

    @BeforeEach
    void setUp() {
        modifyRecipeController = new ModifyRecipeController(modifyRecipeService);
    }

    @Test
    void shouldUpdateRecipeSuccess() {
        // given
        long recipeId = 1;
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("Updated Recipe Name");

        doNothing().when(modifyRecipeService).updateRecipe(eq(recipeId), any(RecipeRequest.class));

        // call
        ResponseEntity<Void> response = modifyRecipeController.updateRecipe(recipeId, recipeRequest);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(modifyRecipeService, times(1)).updateRecipe(eq(recipeId), any(RecipeRequest.class));
    }

    @Test
    void shouldUpdateRecipeNotFound() {
        // given
        long recipeId = 999;
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("Nonexistent Recipe");

        doThrow(new RecipeNotFoundException("Recipe not found"))
                .when(modifyRecipeService)
                .updateRecipe(eq(recipeId), any(RecipeRequest.class));

        // call
        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            modifyRecipeController.updateRecipe(recipeId, recipeRequest);
        });

        // Assert
        assertEquals("Recipe not found", exception.getMessage());
        verify(modifyRecipeService, times(1)).updateRecipe(eq(recipeId), any(RecipeRequest.class));
    }

    @Test
    void shouldCreateRecipeSuccess() {
        // given
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("New Recipe");

        CreateRecipeResponse expectedResponse =
                CreateRecipeResponse.builder().recipeId("1").build();

        when(modifyRecipeService.createRecipe(any(RecipeRequest.class))).thenReturn(expectedResponse);

        // call
        ResponseEntity<CreateRecipeResponse> response = modifyRecipeController.createRecipe(recipeRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getRecipeId());
        verify(modifyRecipeService, times(1)).createRecipe(any(RecipeRequest.class));
    }

    @Test
    void shouldCreateRecipeInvalidData() {
        // given
        RecipeRequest invalidRecipeRequest = new RecipeRequest();

        when(modifyRecipeService.createRecipe(any(RecipeRequest.class)))
                .thenThrow(new IllegalArgumentException("Invalid recipe data"));

        // call
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            modifyRecipeController.createRecipe(invalidRecipeRequest);
        });

        // Assert
        assertEquals("Invalid recipe data", exception.getMessage());
        verify(modifyRecipeService, times(1)).createRecipe(any(RecipeRequest.class));
    }

    @Test
    void shouldDeleteRecipeSuccess() {
        // given
        long recipeId = 1;
        doNothing().when(modifyRecipeService).deleteRecipeById(recipeId);

        // call
        ResponseEntity<Void> response = modifyRecipeController.deleteRecipeById(recipeId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(modifyRecipeService, times(1)).deleteRecipeById(recipeId);
    }

    @Test
    void shouldDeleteRecipeNotFound() {
        // given
        long recipeId = 999;
        doThrow(new RecipeNotFoundException("Recipe not found"))
                .when(modifyRecipeService)
                .deleteRecipeById(recipeId);

        // call
        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            modifyRecipeController.deleteRecipeById(recipeId);
        });

        // Assert
        assertEquals("Recipe not found", exception.getMessage());
        verify(modifyRecipeService, times(1)).deleteRecipeById(recipeId);
    }
}
