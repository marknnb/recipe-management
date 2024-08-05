package nl.abnamro.management.recipe.service.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.abnamro.management.recipe.entity.QRecipeEntity.recipeEntity;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final RecipeMapper recipeMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RecipeResponse> query(List<BooleanExpression> expressionList) {
        JPQLQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        JPQLQuery<RecipeEntity> recipeJPQLQuery = queryFactory.selectFrom(recipeEntity);

        final Predicate[] expressions = expressionList.toArray(new Predicate[0]);
        List<RecipeEntity> list = recipeJPQLQuery.where(expressions).stream().toList();
        return recipeMapper.mapToRecipeResponse(list);
    }
}
