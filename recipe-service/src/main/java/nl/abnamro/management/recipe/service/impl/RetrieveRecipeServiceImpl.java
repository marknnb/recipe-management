package nl.abnamro.management.recipe.service.impl;

import static nl.abnamro.management.recipe.entity.QIngredientEntity.ingredientEntity;
import static nl.abnamro.management.recipe.entity.QInstructionEntity.instructionEntity;
import static nl.abnamro.management.recipe.entity.QRecipeEntity.recipeEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nl.abnamro.management.recipe.config.ApplicationProperties;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import nl.abnamro.management.recipe.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service implementation for retrieving recipes.
 * This service provides methods to get a list of recipes, get a recipe by ID, and filter recipes based on search criteria.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RetrieveRecipeServiceImpl implements RetrieveRecipeService {
    private final RecipeRepository recipeRepository;
    private final ErrorMessagePropertyConfig messageProvider;
    private final SearchService searchService;
    private final ApplicationProperties properties;
    private final RecipeMapper recipeMapper;

    /**
     * Retrieves a paginated list of recipes.
     *
     * @param pageNo the page number to retrieve
     * @return the paginated result containing the list of recipes
     */
    @Override
    public PagedResult<RecipeResponse> getRecipeList(int pageNo) {
        var sort = Sort.by(Sort.Direction.ASC, "id");
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        var pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<RecipeResponse> productsPage = recipeRepository.findAll(pageable).map(recipeMapper::mapToRecipeResponse);
        return recipeMapper.getRecipeResponsePagedResult(productsPage);
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id the ID of the recipe to retrieve
     * @return the response containing the details of the recipe
     */
    @Override
    public RecipeResponse getRecipeById(Integer id) {
        return recipeRepository
                .findById(Long.valueOf(id))
                .map(recipeMapper::mapToRecipeResponse)
                .orElseThrow(this::throwRecipeNotFoundException);
    }

    /**
     * Filters recipes based on the provided search criteria.
     *
     * @param recipeSearch the search criteria for filtering recipes
     * @return the list of recipes that match the search criteria
     */
    @Override
    public List<RecipeResponse> filterRecipe(RecipeSearch recipeSearch) {
        List<BooleanExpression> expressionList = new ArrayList<>();
        // Filter Vegetarian
        Optional.ofNullable(recipeSearch.getRecipeType())
                .ifPresent(recipeType -> expressionList.add(
                        recipeEntity.recipeType.eq(recipeSearch.getRecipeType().toString())));

        // Filter Servings
        var minServings = recipeSearch.getMinServings();
        var maxServings = recipeSearch.getMaxServings();
        if (minServings != null && recipeSearch.getMaxServings() != null) {
            expressionList.add(recipeEntity.servings.between(minServings, maxServings));
        } else if (minServings != null) {
            expressionList.add(recipeEntity.servings.gt(minServings - 1));
        } else if (maxServings != null) {
            expressionList.add(recipeEntity.servings.lt(maxServings + 1));
        }

        // Ingredients, include
        if (recipeSearch.getIngredientName() != null) {
            var includedIngredients = recipeSearch.getIngredientName().split(",");

            for (String includeIngredient : includedIngredients) {
                expressionList.add(recipeEntity.in(JPAExpressions.select(ingredientEntity.recipe)
                        .from(ingredientEntity)
                        .where(ingredientEntity.name.containsIgnoreCase(includeIngredient))));
            }
        }

        // Ingredients, exclude
        if (recipeSearch.getExcludeIngredientName() != null) {
            var excludedIngredients = recipeSearch.getExcludeIngredientName().split(",");
            for (String excludeIngredient : excludedIngredients) {
                expressionList.add(recipeEntity.notIn(JPAExpressions.select(ingredientEntity.recipe)
                        .from(ingredientEntity)
                        .where(ingredientEntity.name.containsIgnoreCase(excludeIngredient))));
            }
        }

        // Text Search in instructions
        var instructionText = recipeSearch.getInstructionText();
        if (instructionText != null) {
            expressionList.add(recipeEntity.in(JPAExpressions.select(instructionEntity.recipe)
                    .from(instructionEntity)
                    .where(instructionEntity.description.containsIgnoreCase(instructionText))));
        }
        return searchService.query(expressionList);
    }

    /**
     * Throws a RecipeNotFoundException with a custom error message.
     *
     * @return the RecipeNotFoundException
     */
    private RecipeNotFoundException throwRecipeNotFoundException() {
        return new RecipeNotFoundException(messageProvider.getMessage("recipe.notFound"));
    }
}
