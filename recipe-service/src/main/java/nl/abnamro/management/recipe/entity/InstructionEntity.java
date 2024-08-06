package nl.abnamro.management.recipe.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instruction")
public class InstructionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instruction_id_gen")
    @SequenceGenerator(name = "instruction_id_gen", sequenceName = "instruction_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @Column(name = "step")
    private Integer step;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructionEntity that = (InstructionEntity) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
