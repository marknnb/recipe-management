package nl.abnamro.management.recipe.service;

import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.model.response.RecipeResponse;

import java.util.List;

public interface RecipeService {
    PagedResult<RecipeResponse> getRecipeList(int  pageNo);

    RecipeResponse getRecipeById(Integer id);

    CreateRecipeResponse createRecipe(RecipeRequest request);

    void updateRecipe(Long recipeId, RecipeRequest request);

    void deleteRecipeById(Long recipeId);

    List<RecipeResponse> filterRecipe(RecipeSearch recipeSearch);
}
