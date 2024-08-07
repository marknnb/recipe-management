package nl.abnamro.management.recipe.service.impl;

import static nl.abnamro.management.recipe.entity.QRecipeEntity.recipeEntity;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.SearchService;
import org.springframework.stereotype.Service;

/**
 * Service implementation for executing dynamic query based on filter criteria
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final RecipeMapper recipeMapper;
    private final ErrorMessagePropertyConfig messageProvider;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Executes query based on expressionList and returns result.
     * If result is empty then throws RecipeNotFoundException
     * @return RecipeResponse
     */
    @Override
    public List<RecipeResponse> query(List<BooleanExpression> expressionList) {
        JPQLQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        JPQLQuery<RecipeEntity> recipeJPQLQuery = queryFactory.selectFrom(recipeEntity);

        final Predicate[] expressions = expressionList.toArray(new Predicate[0]);
        List<RecipeEntity> list = recipeJPQLQuery.where(expressions).stream().toList();
        if (list.isEmpty()) {
            throw new RecipeNotFoundException(messageProvider.getMessage("filter.recipe"));
        }
        return recipeMapper.mapToRecipeResponse(list);
    }
}
