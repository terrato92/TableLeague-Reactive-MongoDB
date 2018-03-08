package terrato.springframwork.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by onenight on 2018-03-02.
 */
@Data
@Table
@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "league_name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "league")
    private Set<Team> teams = new HashSet<>();

    @Lob
    private Byte[] image;

    @OneToOne
    public Nationality state;

    public League addTeam(Team team){
        team.setLeague(this);
        teams.add(team);
        return this;
    }


}
