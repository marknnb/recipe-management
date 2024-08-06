package nl.abnamro.management.recipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    // @ColumnDefault("nextval('ingredient_id_seq'::regclass)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_gen")
    @SequenceGenerator(name = "ingredient_id_gen", sequenceName = "ingredient_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientEntity that = (IngredientEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
