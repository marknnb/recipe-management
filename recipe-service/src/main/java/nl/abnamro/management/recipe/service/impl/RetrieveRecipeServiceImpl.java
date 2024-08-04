package nl.abnamro.management.recipe.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import jakarta.transaction.Transactional;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.abnamro.management.recipe.entity.QIngredientEntity.ingredientEntity;
import static nl.abnamro.management.recipe.entity.QInstructionEntity.instructionEntity;
import static nl.abnamro.management.recipe.entity.QRecipeEntity.recipeEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class RetrieveRecipeServiceImpl implements RetrieveRecipeService {
    private final RecipeRepository recipeRepository;
    private final ErrorMessagePropertyConfig messageProvider;
    private final SearchService searchService;
    private final ApplicationProperties properties;

    @Override
    public PagedResult<RecipeResponse> getRecipeList(int pageNo) {
        var sort = Sort.by(Sort.Direction.ASC, "name");
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        var pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        var productsPage = recipeRepository.findAll(pageable).map(RecipeMapper::mapToRecipeResponse);
        return RecipeMapper.getRecipeResponsePagedResult(productsPage);
    }

    @Override
    public RecipeResponse getRecipeById(Integer id) {
        return recipeRepository
                .findById(Long.valueOf(id))
                .map(RecipeMapper::mapToRecipeResponse)
                .orElseThrow(this::throwRecipeNotFoundException);
    }

    @Override
    public List<RecipeResponse> filterRecipe(RecipeSearch recipeSearch) {
        List<BooleanExpression> expressionList = new ArrayList<>();
        // Filter Vegetarian
        Optional.ofNullable(recipeSearch.getRecipeType())
                .ifPresent(recipeType -> expressionList.add(recipeEntity.recipeType.eq(recipeSearch.getRecipeType().toString())));


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
                expressionList.add(recipeEntity.in(JPAExpressions.select(ingredientEntity.recipe).from(ingredientEntity).where(ingredientEntity.name.containsIgnoreCase(includeIngredient))));
            }
        }

        // Ingredients, exclude
        if (recipeSearch.getExcludeIngredientName() != null) {
            var excludedIngredients = recipeSearch.getExcludeIngredientName().split(",");
            for (String excludeIngredient : excludedIngredients) {
                expressionList.add(recipeEntity.notIn(JPAExpressions.select(ingredientEntity.recipe).from(ingredientEntity).where(ingredientEntity.name.containsIgnoreCase(excludeIngredient))));
            }
        }

        // Text Search in instructions
        var instructionText = recipeSearch.getInstructionText();
        if (instructionText != null) {
            expressionList.add(recipeEntity.in(JPAExpressions.select(instructionEntity.recipe).from(instructionEntity).where(instructionEntity.description.containsIgnoreCase(instructionText))));
        }
        return searchService.query(expressionList);
    }

    private RecipeNotFoundException throwRecipeNotFoundException() {
        return new RecipeNotFoundException(messageProvider.getMessage("recipe.notFound"));
    }
}
