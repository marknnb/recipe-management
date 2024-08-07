package nl.abnamro.management.recipe.repository;

import nl.abnamro.management.recipe.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {}
