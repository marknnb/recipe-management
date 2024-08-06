package nl.abnamro.management.recipe.repository;

import nl.abnamro.management.recipe.entity.InstructionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionEntityRepository extends JpaRepository<InstructionEntity, Long> {}
