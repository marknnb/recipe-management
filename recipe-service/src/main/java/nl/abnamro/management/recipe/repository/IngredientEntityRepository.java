package nl.abnamro.management.recipe.repository;

import nl.abnamro.management.recipe.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientEntityRepository extends JpaRepository<IngredientEntity, Long> {
}