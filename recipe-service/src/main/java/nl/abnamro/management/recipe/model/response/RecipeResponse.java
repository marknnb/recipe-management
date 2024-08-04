package nl.abnamro.management.recipe.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public class RecipeResponse {
    public String recipeId;
    public String name;
    public String type;
    public int numberOfServings;
    public List<String> ingredients;
    public List<String> instructions;
}
