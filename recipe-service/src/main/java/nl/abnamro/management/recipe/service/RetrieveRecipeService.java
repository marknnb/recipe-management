package nl.abnamro.management.recipe.service;

import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.RecipeResponse;

import java.util.List;

public interface RetrieveRecipeService {
    PagedResult<RecipeResponse> getRecipeList(int pageNo);

    RecipeResponse getRecipeById(Integer id);

    List<RecipeResponse> filterRecipe(RecipeSearch recipeSearch);
}
