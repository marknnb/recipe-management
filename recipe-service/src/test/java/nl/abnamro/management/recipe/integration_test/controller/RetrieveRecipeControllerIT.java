package nl.abnamro.management.recipe.integration_test.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import nl.abnamro.management.recipe.config.IntegrationTestContainerConfig;
import nl.abnamro.management.recipe.config.WithMockOAuth2User;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.impl.RetrieveRecipeServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@Import(IntegrationTestContainerConfig.class)
public class RetrieveRecipeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveRecipeServiceImpl retrieveRecipeService;

    @Test
    @Order(1)
    @WithMockOAuth2User(username = "user")
    void shouldGetRecipeByIdSuccess() throws Exception {
        // Arrange
        int recipeId = 1;
        RecipeResponse recipeResponse = new RecipeResponse(
                "1",
                "Test Recipe",
                "VEGETARIAN",
                5,
                List.of("Ingredient 1", "2 cups", "Ingredient 2", "1 tbsp"),
                List.of("Step 1 instruction", "Step 2 instruction"));

        when(retrieveRecipeService.getRecipeById(recipeId)).thenReturn(recipeResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipe/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(recipeId))
                .andExpect(jsonPath("$.name").value("Test Recipe"))
                .andExpect(jsonPath("$.ingredients[0]").value("Ingredient 1"))
                .andExpect(jsonPath("$.instructions[0]").value("Step 1 instruction"))
                .andExpect(jsonPath("$.type").value("VEGETARIAN"))
                .andDo(print());

        verify(retrieveRecipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    @Order(2)
    @WithMockOAuth2User(username = "user")
    void shouldGetRecipeByIdNotFound() throws Exception {
        // Arrange
        int recipeId = 999;

        when(retrieveRecipeService.getRecipeById(recipeId)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipe/{id}", recipeId))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(retrieveRecipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    @Order(3)
    @WithMockOAuth2User(username = "user")
    void shouldSearchRecipesWithFilterSuccess() throws Exception {
        // Arrange
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);

        List<RecipeResponse> expectedResponses = Arrays.asList(
                new RecipeResponse("1", "Test Recipe 1", "VEGETARIAN", 5, List.of("Ingredient1"), List.of("Step 1")),
                new RecipeResponse("2", "Test Recipe 2", "VEGETARIAN", 10, List.of("Ingredient2"), List.of("Step 2")));

        when(retrieveRecipeService.filterRecipe(any(RecipeSearch.class))).thenReturn(expectedResponses);

        // Act & Assert
        mockMvc.perform(post("/api/v1/recipe/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeSearch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Recipe 1"))
                .andExpect(jsonPath("$[1].name").value("Test Recipe 2"))
                .andDo(print());

        verify(retrieveRecipeService, times(1)).filterRecipe(any(RecipeSearch.class));
    }

    @Test
    @Order(4)
    @WithMockOAuth2User(username = "user")
    void shouldSearchRecipesWithFilterNoResults() throws Exception {
        // Arrange
        RecipeSearch recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);
        recipeSearch.setIngredientName("Unknown Ingredient");

        when(retrieveRecipeService.filterRecipe(any(RecipeSearch.class))).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(post("/api/v1/recipe/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeSearch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andDo(print());

        verify(retrieveRecipeService, times(1)).filterRecipe(any(RecipeSearch.class));
    }

    @Test
    @Order(5)
    @WithMockOAuth2User(username = "user")
    void shouldGetAllRecipesWithPaginationSuccess() throws Exception {
        // Arrange
        int pageNumber = 1;

        List<RecipeResponse> expectedResponses = Arrays.asList(
                new RecipeResponse("1", "Test Recipe 1", "VEGETARIAN", 5, List.of("Ingredient1"), List.of("Step 1")),
                new RecipeResponse("2", "Test Recipe 2", "VEGETARIAN", 10, List.of("Ingredient2"), List.of("Step 2")));

        PagedResult<RecipeResponse> recipeResponsePagedResult =
                new PagedResult<>(expectedResponses, 1, 1, 1, true, true, false, false);

        when(retrieveRecipeService.getRecipeList(pageNumber)).thenReturn(recipeResponsePagedResult);

        // Act & Assert
        mockMvc.perform(get("/api/v1/recipe").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].recipeId").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("Test Recipe 1"))
                .andExpect(jsonPath("$.data[1].name").value("Test Recipe 2"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andDo(print());

        verify(retrieveRecipeService, times(1)).getRecipeList(pageNumber);
    }
}
