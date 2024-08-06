package nl.abnamro.management.recipe.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import nl.abnamro.management.recipe.model.response.RecipeResponse;

public interface SearchService {
    List<RecipeResponse> query(List<BooleanExpression> expressionList);
}
