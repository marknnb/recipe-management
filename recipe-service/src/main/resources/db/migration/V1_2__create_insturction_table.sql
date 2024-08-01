CREATE SEQUENCE IF NOT EXISTS instruction_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE instruction
(
    id          BIGINT default nextval('instruction_id_seq') NOT NULL,
    recipe_id   BIGINT,
    step        INT,
    description text,
    CONSTRAINT pk_instruction PRIMARY KEY (id)
);

ALTER TABLE instruction
    ADD CONSTRAINT FK_INSTRUCTION_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES RECIPE (id);