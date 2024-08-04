package nl.abnamro.management.recipe.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateRecipeResponse {
    @Schema(description = "Id of created Recipe", example = "1234")
    String recipeId;
}
