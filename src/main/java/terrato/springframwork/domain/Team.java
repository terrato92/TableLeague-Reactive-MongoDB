package terrato.springframwork.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by onenight on 2018-03-02.
 */

@Getter
@EqualsAndHashCode(exclude = "league")
@Setter
@Table
@Entity
public class Team implements Comparable<Team>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "team_name")
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", fetch = FetchType.LAZY)
    private Set<Player> players;

    @OneToOne
    @JoinColumn(name = "balance_id")
    private BalanceOfMatches balanceOfMatches;


    @Transient
    private int points;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Lob
    private Byte[] image;

    @OneToOne
    private Nationality state;


    @Override
    public int compareTo(Team o) {
        return this.getPoints() < o.getPoints() ? 1 : (this.getPoints() > o.getPoints() ? -1 : 0);
    }

    public Team addPlayer(Player player){
        player.setTeam(this);
        players.add(player);
        return this;
    }
}
