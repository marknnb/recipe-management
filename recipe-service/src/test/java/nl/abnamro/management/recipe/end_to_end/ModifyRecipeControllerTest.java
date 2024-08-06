package nl.abnamro.management.recipe.end_to_end;

import static io.restassured.RestAssured.given;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = "/test-data.sql", executionPhase = BEFORE_TEST_CLASS)
class ModifyRecipeControllerTest extends AbstractIT {

    @Nested
    class CreateRecipeClass {
        @Test
        void shouldCreateRecipeInDb() {
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + getToken())
                    .body(ControllerTestConstants.VALID_CREATE_RECIPE_REQUEST)
                    .when()
                    .post("/api/v1/recipe")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()
                    .body()
                    .prettyPrint();
        }

        @Test
        void shouldReturnErrorResponse() {
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + getToken())
                    .body(ControllerTestConstants.INVALID_CREATE_RECIPE_REQUEST)
                    .when()
                    .post("/api/v1/recipe")
                    .then()
                    .statusCode(400);
        }
    }

    @Nested
    class UpdateRecipeClass {
        @Test
        void shouldCreateRecipeInDb() {
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + getToken())
                    .body(ControllerTestConstants.VALID_UPDATE_RECIPE_REQUEST)
                    .when()
                    .put("/api/v1/recipe/{id}", 1)
                    .then()
                    .statusCode(204);
        }
    }

    @Nested
    class DeleteRecipeClass {
        @Test
        void shouldDeleteRecipeInDb() {
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + getToken())
                    .when()
                    .delete("/api/v1/recipe/{id}", 1)
                    .then()
                    .statusCode(204);
        }
    }
}
