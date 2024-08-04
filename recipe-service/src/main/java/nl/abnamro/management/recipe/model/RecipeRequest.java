package nl.abnamro.management.recipe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class RecipeRequest {
    @NotBlank(message = "{recipeName.notBlank}")
    @Schema(description = "The name of the recipe", example = "Pasta")
    private String name;

    @Schema(description = "The type of the recipe", example = "VEGETARIAN")
    private RecipeType type;

    @NotNull(message = "{numberOfServings.notNull}")
    @Positive(message = "{numberOfServings.positive}")
    @Schema(description = "The number of servings per recipe", example = "4")
    private int numberOfServings;

    @NotEmpty(message = "{ingredients.notEmpty}")
    @Schema(description = "The ids of the ingredients needed to make the recipe", example = "[1,2]")
    private List<String> ingredients;

    @NotEmpty(message = "{instructions.notEmpty}")
    @Schema(description = "Instructions to prepare recipe", example = "[cut vegetables,wash rice]")
    private List<String> instructions;
}
