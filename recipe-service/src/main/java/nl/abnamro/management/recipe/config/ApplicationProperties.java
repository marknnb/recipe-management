package nl.abnamro.management.recipe.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "recipe")
public record ApplicationProperties(@DefaultValue("5") @Min(1) int pageSize) {}
