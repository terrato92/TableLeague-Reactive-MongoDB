package terrato.springframework.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by onenight on 2018-03-02.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "team")
@Table
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(18)
    @Max(45)
    private int age;

    @ManyToOne
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private Position position;

    @OneToOne
    private Nationality nationality;
}
