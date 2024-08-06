package nl.abnamro.management.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.management.recipe.controller.annotation.RecipeRestController;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RecipeRestController
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "security_auth")
public class RetrieveRecipeController {
    private final RetrieveRecipeService retrieveRecipeService;

    @Operation(description = "List all recipes")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful request")
            })
    @GetMapping
    public ResponseEntity<PagedResult<RecipeResponse>> getRecipeList(
            @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Getting the recipes");
        return ResponseEntity.ok(retrieveRecipeService.getRecipeList(pageNo));
    }

    @Operation(description = "List one recipe by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful request"),
                    @ApiResponse(responseCode = "404", description = "Recipe not found by the given ID")
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(
            @Parameter(description = "Recipe ID", required = true) @PathVariable(name = "id") Integer id) {
        log.info("Getting the recipe by its id. Id: {}", id);
        return ResponseEntity.ok(retrieveRecipeService.getRecipeById(id));
    }

    @Operation(description = "Search recipes by given parameters")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful request"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Different error messages related to criteria and recipe")
            })
    @PostMapping(value = "/search")
    public ResponseEntity<List<RecipeResponse>> filterRecipe(
            @RequestBody @Valid RecipeSearch recipeSearch) {
        log.info("Updating the recipe by given properties");
        return ResponseEntity.ok(retrieveRecipeService.filterRecipe(recipeSearch));
    }
}
