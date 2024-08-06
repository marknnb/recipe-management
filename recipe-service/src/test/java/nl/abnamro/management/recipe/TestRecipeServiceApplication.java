package nl.abnamro.management.recipe;

import nl.abnamro.management.recipe.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class TestRecipeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(RecipeServiceApplication::main)
                .with(TestcontainersConfig.class)
                .run(args);
    }
}
