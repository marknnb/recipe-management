package nl.abnamro.management.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.management.recipe.service.RetrieveRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(RetrieveRecipeController.class)
class RetrieveRecipeControllerTest {
    @MockBean
    private RetrieveRecipeService retrieveRecipeService;

    @Autowired
    private ObjectMapper objectMapper;
}