package terrato.springframwork.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private final String NAME = "Premier League";

    @Size(min = 19, max = 22)
    @OneToMany(mappedBy = "league")
    private Set<Team> teams = new HashSet<>();

    @Lob
    private Byte[] image;

    public League() {
    }
}
