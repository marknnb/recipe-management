package nl.abnamro.management.recipe.mapper;

import lombok.extern.slf4j.Slf4j;
import nl.abnamro.management.recipe.entity.IngredientEntity;
import nl.abnamro.management.recipe.entity.InstructionEntity;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RecipeMapper {

    private LinkedHashSet<InstructionEntity> mapToInstructionEntity(
            List<String> instructions, AtomicInteger count, RecipeEntity recipeEntity) {
        return instructions.stream()
                .map(instruction -> {
                    InstructionEntity instructionEntity = new InstructionEntity();
                    instructionEntity.setDescription(instruction);
                    instructionEntity.setStep(count.incrementAndGet());
                    instructionEntity.setRecipe(recipeEntity);
                    return instructionEntity;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private LinkedHashSet<IngredientEntity> mapToIngredientEntity(List<String> ingredientIds, RecipeEntity recipeEntity) {
        return ingredientIds.stream()
                .map(ingredientName -> {
                    IngredientEntity ingredientEntity = new IngredientEntity();
                    ingredientEntity.setName(ingredientName);
                    ingredientEntity.setRecipe(recipeEntity);
                    return ingredientEntity;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public RecipeEntity mapToRecipeEntity(RecipeRequest request) {
        List<String> instructions = request.getInstructions();
        List<String> ingredientIds = request.getIngredients();
        AtomicInteger count = new AtomicInteger(0);
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(request.getName());
        recipeEntity.setRecipeType(request.getType().toString());
        recipeEntity.setServings(request.getNumberOfServings());
        recipeEntity.setName(request.getName());
        recipeEntity.setInstructions(mapToInstructionEntity(instructions, count, recipeEntity));
        recipeEntity.setIngredients(mapToIngredientEntity(ingredientIds, recipeEntity));
        return recipeEntity;
    }

    public List<RecipeResponse> mapToRecipeResponse(List<RecipeEntity> content) {
        log.info(content.toString());
        return content.stream().map(this::mapToRecipeResponse).toList();
    }

    public RecipeResponse mapToRecipeResponse(RecipeEntity recipe) {
        return Optional.of(recipe)
                .map(item -> {
                    Set<InstructionEntity> instructions = item.getInstructions();
                    Set<IngredientEntity> ingredients = item.getIngredients();
                    return RecipeResponse.builder()
                            .recipeId(item.getId().toString())
                            .name(item.getName())
                            .type(item.getRecipeType())
                            .numberOfServings(item.getServings())
                            .ingredients(ingredients.stream()
                                    .map(IngredientEntity::getName)
                                    .toList())
                            .instructions(instructions.stream()
                                    .map(InstructionEntity::getDescription)
                                    .toList())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Error in mapping RecipeEntity"));
    }

    public PagedResult<RecipeResponse> getRecipeResponsePagedResult(Page<RecipeResponse> productsPage) {
        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }
}
