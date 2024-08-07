package nl.abnamro.management.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.management.recipe.controller.annotation.RecipeRestController;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RecipeRestController
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "security_auth")
public class ModifyRecipeController {
    private final ModifyRecipeService modifyRecipeService;

    @Operation(summary = "Create a recipe", description = "Creates a new recipe with the provided details.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Recipe created successfully",
                content = @Content(schema = @Schema(implementation = CreateRecipeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CreateRecipeResponse> createRecipe(
            @Parameter(description = "Properties of the recipe", required = true) @RequestBody @Valid
                    RecipeRequest request) {
        log.info("Creating the recipe with properties");
        return ResponseEntity.ok(modifyRecipeService.createRecipe(request));
    }

    @Operation(
            summary = "Update a recipe",
            description = "Updates an existing recipe with the provided details."
                    + "This endpoints requires current picture of the recipe.Partial update request is not allowed")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recipe updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateRecipe(
            @PathVariable("id") Long recipeId, @RequestBody @Valid RecipeRequest request) {
        modifyRecipeService.updateRecipe(recipeId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a recipe", description = "Deletes a recipe by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable("id") Long recipeId) {
        modifyRecipeService.deleteRecipeById(recipeId);
        return ResponseEntity.noContent().build();
    }
}
