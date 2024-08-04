package nl.abnamro.management.recipe.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.entity.IngredientEntity;
import nl.abnamro.management.recipe.entity.InstructionEntity;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.PagedResult;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.RecipeSearch;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.model.response.RecipeResponse;
import nl.abnamro.management.recipe.repository.IngredientEntityRepository;
import nl.abnamro.management.recipe.repository.InstructionEntityRepository;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.RecipeService;
import nl.abnamro.management.recipe.service.SearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static nl.abnamro.management.recipe.entity.QIngredientEntity.ingredientEntity;
import static nl.abnamro.management.recipe.entity.QInstructionEntity.instructionEntity;
import static nl.abnamro.management.recipe.entity.QRecipeEntity.recipeEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientEntityRepository ingredientEntityRepository;
    private final InstructionEntityRepository instructionEntityRepository;
    private final ErrorMessagePropertyConfig messageProvider;
    private final SearchService searchService;

    @Override
    public PagedResult<RecipeResponse> getRecipeList(int pageNo) {
        var sort = Sort.by(Sort.Direction.ASC, "name");
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        var pageable = PageRequest.of(pageNo, 5, sort);
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
    public CreateRecipeResponse createRecipe(RecipeRequest request) {
        var save = recipeRepository.save(RecipeMapper.mapToRecipeEntity(request));
        return CreateRecipeResponse.builder().recipeId(save.getId().toString()).build();
    }

    @Override
    public void updateRecipe(Long id, RecipeRequest updatedRecipeRequest) {
        RecipeEntity existingRecipe = recipeRepository.findById(id).orElseThrow(this::throwRecipeNotFoundException);
        // Convert the updated instructions and ingredients from String to Entity
        RecipeEntity requestedRecipe = RecipeMapper.mapToRecipeEntity(updatedRecipeRequest);

        existingRecipe.setName(updatedRecipeRequest.getName());
        existingRecipe.setRecipeType(updatedRecipeRequest.getType().toString());
        existingRecipe.setServings(updatedRecipeRequest.getNumberOfServings());
        existingRecipe.setInstructions(getUpdatedInstructions(requestedRecipe, existingRecipe));
        existingRecipe.setIngredients(getUpdatedIngredients(requestedRecipe, existingRecipe));
        recipeRepository.save(existingRecipe);
    }

    private Set<IngredientEntity> getUpdatedIngredients(RecipeEntity requestedRecipe, RecipeEntity existingRecipe) {
        // Handle Instructions
        Set<IngredientEntity> a =  existingRecipe.getIngredients();;
        Set<IngredientEntity> b =  requestedRecipe.getIngredients();

        // Update existing items
        List<IngredientEntity> currentIngredientsList = new ArrayList<>(a);
        List<IngredientEntity> updatedIngredientsList = new ArrayList<>(b);

        // Delete extra items in original List
        while (currentIngredientsList.size() > updatedIngredientsList.size()) {
            final int index = currentIngredientsList.size() - 1;
            final IngredientEntity entity = currentIngredientsList.get(index);
            ingredientEntityRepository.delete(entity);
            currentIngredientsList.remove(index);
        }

        for(int i=0;i<currentIngredientsList.size();i++){
            var currentIngredient = currentIngredientsList.get(i);
            var updateIngredient =  updatedIngredientsList.get(i);
            currentIngredient.setName(updateIngredient.getName());
        }

        // Add New items
        if (updatedIngredientsList.size() > currentIngredientsList.size()) {
            int startIndex = currentIngredientsList.size();
            final List<IngredientEntity> newIngredients = updatedIngredientsList.subList(startIndex, updatedIngredientsList.size());
            currentIngredientsList.addAll(newIngredients);
        }
        currentIngredientsList.forEach(instruction -> instruction.setRecipe(existingRecipe));
        a.clear();
        a.addAll(currentIngredientsList);
        return a;
    }

    private  Set<InstructionEntity> getUpdatedInstructions(RecipeEntity updateRecipe, RecipeEntity existingRecipe) {
        // Handle Instructions
        Set<InstructionEntity> a = existingRecipe.getInstructions();
        Set<InstructionEntity> b = updateRecipe.getInstructions();

        // Update existing items
        List<InstructionEntity> currentInstructionsList = new ArrayList<>(a);
        List<InstructionEntity> updatedInstructionsList = new ArrayList<>(b);


        // Delete extra items in original List
        while (currentInstructionsList.size() > updatedInstructionsList.size()) {
            final int index = currentInstructionsList.size() - 1;
            final InstructionEntity entity = currentInstructionsList.get(index);
            instructionEntityRepository.delete(entity);
            currentInstructionsList.remove(index);
        }

        for(int i=0;i<currentInstructionsList.size();i++){
            var currentInstruction = currentInstructionsList.get(i);
            var updateInstruction =  updatedInstructionsList.get(i);

            currentInstruction.setDescription(updateInstruction.getDescription());
            currentInstruction.setStep(updateInstruction.getStep());
        }

        // Add New items
        if (updatedInstructionsList.size() > currentInstructionsList.size()) {
            int startIndex = currentInstructionsList.size();
            final List<InstructionEntity> newInstructions = updatedInstructionsList.subList(startIndex, updatedInstructionsList.size());
            currentInstructionsList.addAll(newInstructions);
        }
        currentInstructionsList.forEach(instruction -> instruction.setRecipe(existingRecipe));

        a.clear();
        a.addAll(currentInstructionsList);
        return a;
    }

    @Override
    public void deleteRecipeById(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId).orElseThrow(this::throwRecipeNotFoundException);
        recipeRepository.delete(recipe);
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
