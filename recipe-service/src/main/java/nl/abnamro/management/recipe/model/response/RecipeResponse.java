package nl.abnamro.management.recipe.model.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

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
