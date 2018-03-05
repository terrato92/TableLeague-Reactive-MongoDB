package terrato.springframwork.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by onenight on 2018-03-05.
 */
@Data
@Entity
public class Nationality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    @OneToOne
    private Team team;
}
