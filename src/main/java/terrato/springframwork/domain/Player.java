package terrato.springframwork.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by onenight on 2018-03-02.
 */
@Data
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
}
