package nl.abnamro.management.recipe.config;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig extends IntegrationTestContainerConfig {
    static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:24.0.2";
    static String realmImportFile = "abn-amro-recipe-management-realm.json";
    static String realmName = "abn-amro-recipe-management";

    @Bean
    KeycloakContainer keycloak(DynamicPropertyRegistry registry) {
        var keycloak = new KeycloakContainer(KEYCLOAK_IMAGE).withRealmImportFile(realmImportFile);
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + realmName);
        return keycloak;
    }
}
