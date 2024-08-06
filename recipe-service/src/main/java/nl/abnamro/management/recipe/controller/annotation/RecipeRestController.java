package nl.abnamro.management.recipe.controller.annotation;

import java.lang.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping(value = "api/v1/recipe")
@Validated
public @interface RecipeRestController {}
