package nl.abnamro.management.recipe.service.impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import nl.abnamro.management.recipe.config.ErrorMessagePropertyConfig;
import nl.abnamro.management.recipe.entity.IngredientEntity;
import nl.abnamro.management.recipe.entity.InstructionEntity;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.exception.RecipeNotFoundException;
import nl.abnamro.management.recipe.mapper.RecipeMapper;
import nl.abnamro.management.recipe.model.RecipeRequest;
import nl.abnamro.management.recipe.model.response.CreateRecipeResponse;
import nl.abnamro.management.recipe.repository.IngredientEntityRepository;
import nl.abnamro.management.recipe.repository.InstructionEntityRepository;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyRecipeServiceImpl implements ModifyRecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientEntityRepository ingredientEntityRepository;
    private final InstructionEntityRepository instructionEntityRepository;
    private final ErrorMessagePropertyConfig messageProvider;
    private final RecipeMapper recipeMapper;

    @Override
    public CreateRecipeResponse createRecipe(RecipeRequest request) {
        var save = recipeRepository.save(recipeMapper.mapToRecipeEntity(request));
        return CreateRecipeResponse.builder().recipeId(save.getId().toString()).build();
    }

    @Override
    public void updateRecipe(Long id, RecipeRequest updatedRecipeRequest) {
        RecipeEntity existingRecipe = recipeRepository.findById(id).orElseThrow(this::throwRecipeNotFoundException);
        // Convert the updated instructions and ingredients from String to Entity
        RecipeEntity requestedRecipe = recipeMapper.mapToRecipeEntity(updatedRecipeRequest);
        existingRecipe.setName(updatedRecipeRequest.getName());
        existingRecipe.setRecipeType(updatedRecipeRequest.getType().toString());
        existingRecipe.setServings(updatedRecipeRequest.getNumberOfServings());
        existingRecipe.setInstructions(getUpdatedInstructions(requestedRecipe, existingRecipe));
        existingRecipe.setIngredients(getUpdatedIngredients(requestedRecipe, existingRecipe));
        recipeRepository.save(existingRecipe);
    }

    @Override
    public void deleteRecipeById(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId).orElseThrow(this::throwRecipeNotFoundException);
        recipeRepository.delete(recipe);
    }

    private Set<IngredientEntity> getUpdatedIngredients(RecipeEntity requestedRecipe, RecipeEntity existingRecipe) {
        // Handle Instructions
        Set<IngredientEntity> existingIngredientsSet = existingRecipe.getIngredients();
        Set<IngredientEntity> requestedIngredientsSet = requestedRecipe.getIngredients();

        // Update existing items
        List<IngredientEntity> currentIngredientsList = new ArrayList<>(existingIngredientsSet);
        List<IngredientEntity> updatedIngredientsList = new ArrayList<>(requestedIngredientsSet);

        // Delete extra items in original List
        while (currentIngredientsList.size() > updatedIngredientsList.size()) {
            final int index = currentIngredientsList.size() - 1;
            final IngredientEntity entity = currentIngredientsList.get(index);
            ingredientEntityRepository.delete(entity);
            currentIngredientsList.remove(index);
        }

        for (int i = 0; i < currentIngredientsList.size(); i++) {
            var currentIngredient = currentIngredientsList.get(i);
            var updateIngredient = updatedIngredientsList.get(i);
            currentIngredient.setName(updateIngredient.getName());
        }

        // Add New items
        if (updatedIngredientsList.size() > currentIngredientsList.size()) {
            int startIndex = currentIngredientsList.size();
            final List<IngredientEntity> newIngredients =
                    updatedIngredientsList.subList(startIndex, updatedIngredientsList.size());
            currentIngredientsList.addAll(newIngredients);
        }
        currentIngredientsList.forEach(instruction -> instruction.setRecipe(existingRecipe));
        existingIngredientsSet.clear();
        existingIngredientsSet.addAll(currentIngredientsList);
        return existingIngredientsSet;
    }

    private Set<InstructionEntity> getUpdatedInstructions(RecipeEntity updateRecipe, RecipeEntity existingRecipe) {
        // Handle Instructions
        Set<InstructionEntity> existingInstructionsSet = existingRecipe.getInstructions();
        Set<InstructionEntity> updatedInstructionsSet = updateRecipe.getInstructions();

        // Update existing items
        List<InstructionEntity> currentInstructionsList = new ArrayList<>(existingInstructionsSet);
        List<InstructionEntity> updatedInstructionsList = new ArrayList<>(updatedInstructionsSet);

        // Delete extra items in original List
        while (currentInstructionsList.size() > updatedInstructionsList.size()) {
            final int index = currentInstructionsList.size() - 1;
            final InstructionEntity entity = currentInstructionsList.get(index);
            instructionEntityRepository.delete(entity);
            currentInstructionsList.remove(index);
        }

        for (int i = 0; i < currentInstructionsList.size(); i++) {
            var currentInstruction = currentInstructionsList.get(i);
            var updateInstruction = updatedInstructionsList.get(i);

            currentInstruction.setDescription(updateInstruction.getDescription());
            currentInstruction.setStep(updateInstruction.getStep());
        }

        // Add New items
        if (updatedInstructionsList.size() > currentInstructionsList.size()) {
            int startIndex = currentInstructionsList.size();
            final List<InstructionEntity> newInstructions =
                    updatedInstructionsList.subList(startIndex, updatedInstructionsList.size());
            currentInstructionsList.addAll(newInstructions);
        }
        currentInstructionsList.forEach(instruction -> instruction.setRecipe(existingRecipe));

        existingInstructionsSet.clear();
        existingInstructionsSet.addAll(currentInstructionsList);
        return existingInstructionsSet;
    }

    private RecipeNotFoundException throwRecipeNotFoundException() {
        return new RecipeNotFoundException(messageProvider.getMessage("recipe.notFound"));
    }
}
