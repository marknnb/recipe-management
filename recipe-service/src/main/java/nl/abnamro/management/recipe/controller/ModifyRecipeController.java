package nl.abnamro.management.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.management.recipe.controller.annotation.RecipeRestController;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RecipeRestController
@RequiredArgsConstructor
@Slf4j
public class ModifyRecipeController {
    private final ModifyRecipeService modifyRecipeService;

    @PostMapping
    @Operation(description = "Create a recipe")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Recipe created"),
                    @ApiResponse(responseCode = "400", description = "Bad input")
            })
    @ResponseStatus(CREATED)
    public ResponseEntity<CreateRecipeResponse> createRecipe(
            @Parameter(description = "Properties of the recipe", required = true)
            @RequestBody @Valid RecipeRequest request) {
        log.info("Creating the recipe with properties");
        return ResponseEntity.ok(modifyRecipeService.createRecipe(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateRecipe(
            @PathVariable("id") Long recipeId,
            @RequestBody @Valid RecipeRequest request) {
        modifyRecipeService.updateRecipe(recipeId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete the recipe")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Recipe not found by the given ID")
            })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecipeById(
            @PathVariable("id") Long recipeId) {
        modifyRecipeService.deleteRecipeById(recipeId);
        return ResponseEntity.noContent().build();
    }
}
