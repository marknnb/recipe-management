package nl.abnamro.management.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.management.recipe.service.ModifyRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ModifyRecipeController.class)
class ModifyRecipeControllerTest {

    @MockBean
    private  ModifyRecipeService modifyRecipeService;

    @Autowired
    private ObjectMapper objectMapper;
}