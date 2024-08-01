CREATE SEQUENCE IF NOT EXISTS ingredient_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE ingredient
(
    id        BIGINT default nextval('ingredient_id_seq') NOT NULL,
    recipe_id BIGINT,
    name      VARCHAR(255),
    CONSTRAINT pk_ingredient PRIMARY KEY (id)
);

ALTER TABLE ingredient
    ADD CONSTRAINT FK_INGREDIENT_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES RECIPE (id);