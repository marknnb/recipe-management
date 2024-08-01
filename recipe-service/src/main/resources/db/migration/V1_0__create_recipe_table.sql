CREATE SEQUENCE IF NOT EXISTS recipe_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE RECIPE
(
    id          BIGINT default nextval('recipe_id_seq') NOT NULL,
    name        VARCHAR(255),
    recipe_type VARCHAR(255)                            NOT NULL,
    servings    INT                                     NOT NULL,
    CONSTRAINT pk_recipe PRIMARY KEY (id)
);
