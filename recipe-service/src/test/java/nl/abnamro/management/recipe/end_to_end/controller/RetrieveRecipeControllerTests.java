package nl.abnamro.management.recipe.end_to_end.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(value = "/test-data.sql",executionPhase = BEFORE_TEST_CLASS)
public class RetrieveRecipeControllerTests extends AbstractIT {

    @Nested
    class GetALLRecipeTests {
        @Test
        void shouldReturnRecipe() {
            given().contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/recipe?page=1")
                    .then()
                    .statusCode(200)
                    .body("data", hasSize(5))
                    .body("totalElements", is(10))
                    .body("pageNumber", is(1))
                    .body("totalPages", is(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }

        @Test
        void shouldReturnEmptyData() {
            given().contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/recipe?page=10")
                    .then()
                    .statusCode(200)
                    .body("data", hasSize(0));
        }
    }

    @Nested
    class GetRecipeByIdTests {
        @Test
        void shouldReturnRecipeById() {
            given().contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/recipe/{id}", 1)
                    .then()
                    .statusCode(200)
                    .body("recipeId", is("1"))
                    .body("name", is("Spaghetti Bolognese"))
                    .body("type", is("OTHER"))
                    .body("numberOfServings", is(4))
                    .body("ingredients", hasSize(5))
                    .body("instructions", hasSize(5));
        }

        @Test
        void shouldReturnNotFoundWhenRecipeNotExists() {
            given().contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/recipe/{id}", 112333)
                    .then()
                    .statusCode(404);
        }
    }

    @Nested
    class GetRecipeByFilterTests {
        @Test
        void shouldReturnAllVegetarianRecipes() {
            String filterRequest = """
                    {
                       "recipeType": "VEGETARIAN"
                    }
                    """;
            given()
                    .contentType(ContentType.JSON)
                    .body(filterRequest)
                    .when()
                    .post("/api/v1/recipe/search")
                    .then()
                    .statusCode(200)
                    .body("size()", is(5));
        }

        @Test
        void shouldReturnAllOtherTypeRecipes() {
            String filterRequest = """
                    {
                        "recipeType": "OTHER"
                    }
                    """;
            given()
                    .contentType(ContentType.JSON)
                    .body(filterRequest)
                    .when()
                    .post("/api/v1/recipe/search")
                    .then()
                    .statusCode(200)
                    .body("size()", is(5));
        }

        @Test
        void shouldReturnAllVegRecipeIncludesOnionsRecipes() {
            String filterRequest = """
                    {
                      "recipeType": "VEGETARIAN",
                      "minServings": 4,
                      "maxServings": 6,
                      "ingredientName": "onions"
                    }
                    """;
            given()
                    .contentType(ContentType.JSON)
                    .body(filterRequest)
                    .when()
                    .post("/api/v1/recipe/search")
                    .then()
                    .statusCode(200)
                    .body("size()", is(1))
                    .extract()
                    .response()
                    .body()
                    .prettyPrint();
        }
    }

}
