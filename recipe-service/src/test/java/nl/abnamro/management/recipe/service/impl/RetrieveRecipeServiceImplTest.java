package nl.abnamro.management.recipe.service.impl;

import nl.abnamro.management.recipe.config.ApplicationProperties;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.entity.IngredientEntity;
import nl.abnamro.management.recipe.entity.InstructionEntity;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.PagedResult;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
        retrieveRecipeService = new RetrieveRecipeServiceImpl(recipeRepository, messageProvider, searchService, properties, recipeMapper);
    }

    @Test
    void shouldReturnValidRecipeRecords() {
        //given
        var recipeId = 1;
        var recipeResponsePage = getRecipeEntities();
        var recipeResponsePagedResult = getRecipeResponsePagedResult();
        //when
        when(properties.pageSize()).thenReturn(5);
        when(recipeRepository.findAll(any(PageRequest.class))).thenReturn(recipeResponsePage);
        when(recipeMapper.getRecipeResponsePagedResult(any())).thenReturn(recipeResponsePagedResult);

        //then
        PagedResult<RecipeResponse> responsePagedResult = retrieveRecipeService.getRecipeList(recipeId);

        //assert

        assertAll("Asserting response",
                () -> assertNotNull(responsePagedResult),
                () -> assertEquals(responsePagedResult.pageNumber(), 1),
                () -> assertEquals(responsePagedResult.totalPages(), 2),
                () -> assertTrue(responsePagedResult.isFirst()),
                () -> assertTrue(responsePagedResult.isLast()),
                () -> assertTrue(responsePagedResult.hasNext()),
                () -> assertTrue(responsePagedResult.hasPrevious()));
    }

    private static @NotNull PagedResult<RecipeResponse> getRecipeResponsePagedResult() {
        List<RecipeResponse> recipeResponseList = new ArrayList<>() {{
            var recipeResponse = RecipeResponse.builder()
                    .recipeId("1")
                    .name("test")
                    .instructions(new ArrayList<>() {{
                        add("cut onions");
                    }})
                    .instructions(new ArrayList<>() {{
                        add("Onions");
                    }})
                    .type("VEGETARIAN")
                    .numberOfServings(5)
                    .build();
            add(recipeResponse);
        }};
        return new PagedResult<>(recipeResponseList, 10, 1, 2, true, false, true, false);
    }

    private static @NotNull Page<RecipeEntity> getRecipeEntities() {
        List<RecipeEntity> recipeEntities = new ArrayList<>() {{
            var recipeEntity = new RecipeEntity();
            recipeEntity.setName("test");
            recipeEntity.setRecipeType(RecipeType.VEGETARIAN.toString());
            recipeEntity.setServings(5);
            recipeEntity.setIngredients(new LinkedHashSet<>() {{
                var ingredientEntity = new IngredientEntity();
                ingredientEntity.setName("Onions");
                ingredientEntity.setId(1L);
                add(ingredientEntity);
            }});
            recipeEntity.setInstructions(new LinkedHashSet<>() {{
                var instructionEntity = new InstructionEntity();
                instructionEntity.setStep(1);
                instructionEntity.setDescription("cut onions");
                instructionEntity.setId(1L);
                add(instructionEntity);
            }});
            recipeEntity.setId(1L);
            add(recipeEntity);
        }};

        return new PageImpl<>(recipeEntities);
    }
}