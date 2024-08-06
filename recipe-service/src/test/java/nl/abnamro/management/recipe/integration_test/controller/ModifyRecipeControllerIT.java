package nl.abnamro.management.recipe.integration_test.controller;

import static nl.abnamro.management.recipe.TestUtils.getRecipeRequest;
import static nl.abnamro.management.recipe.TestUtils.getRecipeUpdateRequest;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import nl.abnamro.management.recipe.config.IntegrationTestContainerConfig;
import nl.abnamro.management.recipe.config.WithMockOAuth2User;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@Import(IntegrationTestContainerConfig.class)
public class ModifyRecipeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyRecipeService modifyRecipeService;

    @Test
    @Order(1)
    @WithMockUser
    void shouldCreateRecipeSuccess() throws Exception {
        // when
        var recipeRequest = getRecipeRequest();
        CreateRecipeResponse expectedResponse =
                CreateRecipeResponse.builder().recipeId("1").build();
        when(modifyRecipeService.createRecipe(any(RecipeRequest.class))).thenReturn(expectedResponse);
        // call and assert
        mockMvc.perform(post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeRequest)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(modifyRecipeService, times(1)).createRecipe(any(RecipeRequest.class));
    }

    @Test
    @Order(2)
    @WithMockOAuth2User(username = "user")
    void shouldCreateRecipeInvalidData() throws Exception {
        // when
        RecipeRequest invalidRecipeRequest = new RecipeRequest(); // Empty request to simulate invalid data

        // call and assert
        mockMvc.perform(post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRecipeRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(3)
    @WithMockOAuth2User(username = "user")
    void shouldUpdateRecipeSuccess() throws Exception {
        // when
        long recipeId = 1L;
        var recipeRequest = getRecipeUpdateRequest();
        doNothing().when(modifyRecipeService).updateRecipe(eq(recipeId), any(RecipeRequest.class));
        // call and assert
        mockMvc.perform(put("/api/v1/recipe/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeRequest)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(modifyRecipeService, times(1)).updateRecipe(eq(recipeId), any(RecipeRequest.class));
    }

    @Test
    @Order(4)
    @WithMockOAuth2User(username = "user")
    void shouldUpdateRecipeNotFound() throws Exception {
        // when
        long recipeId = 999L;
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setType(RecipeType.VEGETARIAN);
        recipeRequest.setName("Nonexistent Recipe");
        recipeRequest.setNumberOfServings(5);
        recipeRequest.setIngredients(List.of("Ingredient1", "Ingredient2"));
        recipeRequest.setInstructions(List.of("Step 1", "Step 2"));

        doThrow(new RecipeNotFoundException("Recipe not found"))
                .when(modifyRecipeService)
                .updateRecipe(eq(recipeId), any(RecipeRequest.class));

        // call and assert
        mockMvc.perform(put("/api/v1/recipe/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Recipe not found"))
                .andDo(print());

        verify(modifyRecipeService, times(1)).updateRecipe(eq(recipeId), any(RecipeRequest.class));
    }

    @Test
    @Order(5)
    @WithMockOAuth2User(username = "user")
    void shouldDeleteRecipeSuccess() throws Exception {
        // when
        long recipeId = 1L;

        doNothing().when(modifyRecipeService).deleteRecipeById(recipeId);

        // call and assert
        mockMvc.perform(delete("/api/v1/recipe/{id}", recipeId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(modifyRecipeService, times(1)).deleteRecipeById(recipeId);
    }

    @Test
    @Order(6)
    @WithMockOAuth2User(username = "user")
    void shouldDeleteRecipeNotFound() throws Exception {
        // when
        long recipeId = 999L;

        doThrow(new RecipeNotFoundException("Recipe not found"))
                .when(modifyRecipeService)
                .deleteRecipeById(recipeId);

        // call and assert
        mockMvc.perform(delete("/api/v1/recipe/{id}", recipeId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Recipe not found"))
                .andDo(print());

        verify(modifyRecipeService, times(1)).deleteRecipeById(recipeId);
    }
}
