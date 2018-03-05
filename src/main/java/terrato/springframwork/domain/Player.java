package terrato.springframwork.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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


    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Position position;

    @OneToOne
    private Nationality state;
}
