package nl.abnamro.management.recipe.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import nl.abnamro.management.recipe.model.response.RecipeResponse;

import java.util.List;

public interface SearchService {
    List<RecipeResponse> query(List<BooleanExpression> expressionList);
}
