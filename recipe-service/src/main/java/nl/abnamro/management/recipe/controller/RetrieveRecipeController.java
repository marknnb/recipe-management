package nl.abnamro.management.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.management.recipe.controller.annotation.RecipeRestController;
import nl.abnamro.management.recipe.model.GenericResponse;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RecipeRestController
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "security_auth")
public class RetrieveRecipeController {
    private final RetrieveRecipeService retrieveRecipeService;

    @Operation(
            description =
                    "List all recipes. Response of this API is paginated. Default page size is 5 and it is configurable.If there are no recipes API will give empty response")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful request",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PagedResult.class))
                        })
            })
    @GetMapping
    public ResponseEntity<PagedResult<RecipeResponse>> getRecipeList(
            @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Getting all the recipes");
        return ResponseEntity.ok(retrieveRecipeService.getRecipeList(pageNo));
    }

    @Operation(description = "List one recipe by its ID")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Found the recipe",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeResponse.class))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "Recipe not found",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericResponse.class))
                        })
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(
            @Parameter(description = "Recipe ID", required = true) @PathVariable(name = "id") Integer id) {
        log.info("Getting the recipe by its id. Id: {}", id);
        return ResponseEntity.ok(retrieveRecipeService.getRecipeById(id));
    }

    @Operation(summary = "Search recipes", description = "Searches for recipes based on the provided criteria.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Successful request",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = RecipeResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No recipes found matching the criteria")
    })
    @PostMapping(value = "/search")
    public ResponseEntity<List<RecipeResponse>> filterRecipe(@RequestBody @Valid RecipeSearch recipeSearch) {
        log.info("Filtering the recipe by given properties");
        return ResponseEntity.ok(retrieveRecipeService.filterRecipe(recipeSearch));
    }
}
