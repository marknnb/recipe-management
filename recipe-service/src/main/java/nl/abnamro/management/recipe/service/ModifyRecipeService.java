package nl.abnamro.management.recipe.service;

import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;

public interface ModifyRecipeService {

    CreateRecipeResponse createRecipe(RecipeRequest request);

    void updateRecipe(Long recipeId, RecipeRequest request);

    void deleteRecipeById(Long recipeId);
}
