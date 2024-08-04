package nl.abnamro.management.recipe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSearch {
    @Schema(description = "The Type of the recipe", example = "VEGETARIAN")
    RecipeType recipeType;

    @Schema(description = "No. of service for Recipe", example = "4")
    Integer minServings;

    @Schema(description = "No. of service for Recipe", example = "6")
    Integer maxServings;

    @Schema(description = "Ingredients included  in Recipe", example = "Onions")
    String ingredientName;

    @Schema(description = "Ingredients must not be  in Recipe", example = "Pasta")
    String excludeIngredientName;

    @Schema(description = "Instruction text  must not be  in Recipe", example = "Pasta")
    String instructionText;
}
