package nl.abnamro.management.recipe.integration_test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import nl.abnamro.management.recipe.entity.RecipeEntity;
import nl.abnamro.management.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

@DataJpaTest(
        properties = {
            "spring.test.database.replace=none",
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db",
        })
@Import(TestcontainersConfiguration.class)
class RecipeRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void shouldGetAllProducts() {
        List<RecipeEntity> recipes = recipeRepository.findAll();
        assertThat(recipes).hasSize(10);
    }

    @Test
    void shouldGetRecipeById() {
        RecipeEntity recipe = recipeRepository.findById(1L).orElseThrow();
        assertThat(recipe.getName()).isEqualTo("Spaghetti Bolognese");
    }

    @Test
    void shouldDeleteRecipeById() {
        assertDoesNotThrow(() -> recipeRepository.deleteById(1L));
    }
}
