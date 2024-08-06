package nl.abnamro.management.recipe.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
public class RecipeResponse {
    public String recipeId;
    public String name;
    public String type;
    public int numberOfServings;
    public List<String> ingredients;
    public List<String> instructions;
}
