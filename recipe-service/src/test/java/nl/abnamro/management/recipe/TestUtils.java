package nl.abnamro.management.recipe;

import lombok.experimental.UtilityClass;
import nl.abnamro.management.recipe.entity.IngredientEntity;
import nl.abnamro.management.recipe.entity.InstructionEntity;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.List;

@UtilityClass
public class TestUtils {
    public static @NotNull RecipeRequest getRecipeRequest() {
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("New Recipe");
        recipeRequest.setType(RecipeType.VEGETARIAN);
        recipeRequest.setNumberOfServings(5);
        recipeRequest.setIngredients(List.of("Ingredient1", "Ingredient2"));
        recipeRequest.setInstructions(List.of("Step 1", "Step 2"));
        return recipeRequest;
    }

    public static @NotNull RecipeEntity getTestRecipeEntity() {
        var recipeEntity = new RecipeEntity();
        recipeEntity.setName("test");
        recipeEntity.setRecipeType(RecipeType.VEGETARIAN.toString());
        recipeEntity.setServings(5);
        recipeEntity.setIngredients(new LinkedHashSet<>() {{
            var ingredientEntity = new IngredientEntity();
            ingredientEntity.setName("Onions");
            ingredientEntity.setId(1L);
            add(ingredientEntity);
        }});
        recipeEntity.setInstructions(new LinkedHashSet<>() {{
            var instructionEntity = new InstructionEntity();
            instructionEntity.setStep(1);
            instructionEntity.setDescription("cut onions");
            instructionEntity.setId(1L);
            add(instructionEntity);
        }});
        recipeEntity.setId(1L);
        return recipeEntity;
    }

    public static RecipeResponse getTestRecipeResponse() {
        return RecipeResponse.builder()
                .recipeId("1")
                .name("test")
                .instructions(List.of("cut onions"))
                .instructions(List.of("onions"))
                .type("VEGETARIAN")
                .numberOfServings(5)
                .build();

    }

    public static @NotNull RecipeRequest getRecipeUpdateRequest() {
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setNumberOfServings(5);
        recipeRequest.setType(RecipeType.VEGETARIAN);
        recipeRequest.setName("Updated Recipe Name");
        recipeRequest.setIngredients(List.of("Updated Ingredient1", "Updated Ingredient2"));
        recipeRequest.setInstructions(List.of("Updated Step 1", "Updated Step 2"));
        return recipeRequest;
    }
}
