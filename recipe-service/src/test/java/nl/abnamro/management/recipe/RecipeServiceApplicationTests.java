package nl.abnamro.management.recipe;

import nl.abnamro.management.recipe.config.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfig.class)
@SpringBootTest
class RecipeServiceApplicationTests {

    @Test
    void contextLoads() {}
}
