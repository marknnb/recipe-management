package nl.abnamro.management.recipe.service.impl;

import static nl.abnamro.management.recipe.TestUtils.getTestRecipeEntity;
import static nl.abnamro.management.recipe.TestUtils.getTestRecipeResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.abnamro.management.recipe.config.ApplicationProperties;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.RecipeType;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import nl.abnamro.management.recipe.service.SearchService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class RetrieveRecipeServiceImplTest {

    @MockBean
    RetrieveRecipeService retrieveRecipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ErrorMessagePropertyConfig messageProvider;

    @Mock
    private SearchService searchService;

    @Mock
    private ApplicationProperties properties;

    @Mock
    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        retrieveRecipeService = new RetrieveRecipeServiceImpl(
                recipeRepository, messageProvider, searchService, properties, recipeMapper);
    }

    @Test
    void shouldReturnValidRecipeRecords() {
        // given
        var recipeId = 1;
        var recipeResponsePage = getRecipeEntities();
        var recipeResponsePagedResult = getRecipeResponsePagedResult();
        // when
        when(properties.pageSize()).thenReturn(5);
        when(recipeRepository.findAll(any(PageRequest.class))).thenReturn(recipeResponsePage);
        when(recipeMapper.getRecipeResponsePagedResult(any())).thenReturn(recipeResponsePagedResult);

        // then
        PagedResult<RecipeResponse> responsePagedResult = retrieveRecipeService.getRecipeList(recipeId);

        // assert

        assertAll(
                "Asserting response",
                () -> assertNotNull(responsePagedResult),
                () -> assertEquals(responsePagedResult.pageNumber(), 1),
                () -> assertEquals(responsePagedResult.totalPages(), 2),
                () -> assertTrue(responsePagedResult.isFirst()),
                () -> assertFalse(responsePagedResult.isLast()),
                () -> assertTrue(responsePagedResult.hasNext()),
                () -> assertFalse(responsePagedResult.hasPrevious()),
                () -> assertEquals(responsePagedResult.data().size(), 1));
    }

    @Test
    void shouldReturnValidRecipeForGivenId() {
        // given
        var recipeId = 1;

        // when
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(getTestRecipeEntity()));
        when(recipeMapper.mapToRecipeResponse(any(RecipeEntity.class))).thenReturn(getTestRecipeResponse());

        // call
        RecipeResponse recipeById = retrieveRecipeService.getRecipeById(recipeId);

        // assert
        assertAll("Asserting response", () -> assertNotNull(recipeById));
    }

    @Test
    void shouldReturnNotFoundError() {
        // given
        var recipeId = -1;

        // when
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(messageProvider.getMessage(any(String.class))).thenReturn("Provide valid Recipe Id");

        // call
        assertThrows(RecipeNotFoundException.class, () -> retrieveRecipeService.getRecipeById(recipeId));
    }

    @Test
    void shouldReturnValidRecipeForFilterCriteria() {
        // given
        var recipeSearch = new RecipeSearch();
        recipeSearch.setRecipeType(RecipeType.VEGETARIAN);
        recipeSearch.setIngredientName("onion");
        recipeSearch.setMinServings(4);
        recipeSearch.setMaxServings(10);
        recipeSearch.setInstructionText("oven");
        recipeSearch.setExcludeIngredientName("wood");

        List<RecipeResponse> recipeResponses = new ArrayList<>() {
            {
                add(getTestRecipeResponse());
            }
        };

        // when
        when(searchService.query(any())).thenReturn(recipeResponses);

        // call
        var actualRecipeResponse = retrieveRecipeService.filterRecipe(recipeSearch);

        // assert
        assertAll("Asserting response", () -> assertNotNull(actualRecipeResponse));
    }

    private static @NotNull PagedResult<RecipeResponse> getRecipeResponsePagedResult() {
        List<RecipeResponse> recipeResponseList = List.of(getTestRecipeResponse());
        return new PagedResult<>(recipeResponseList, 10, 1, 2, true, false, true, false);
    }

    private static @NotNull Page<RecipeEntity> getRecipeEntities() {
        List<RecipeEntity> recipeEntities = List.of(getTestRecipeEntity());
        return new PageImpl<>(recipeEntities);
    }
}
