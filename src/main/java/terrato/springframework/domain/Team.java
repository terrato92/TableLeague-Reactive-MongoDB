package terrato.springframework.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by onenight on 2018-03-02.
 */

@Getter
@Setter
public class Team implements Comparable<Team> {

    private String id = UUID.randomUUID().toString();

    private String name;

    private Set<Player> players = new HashSet<>();

    private BalanceOfMatches balanceOfMatches = new BalanceOfMatches();

    private Power power;

    private int points;

    private League league;

    private Nationality nationality;


    @Override
    public int compareTo(Team team) {
        return this.getPoints() < team.getPoints() ? 1 : -1;
    }

    public Team addPlayer(Player player) {
        this.players.add(player);
        return this;
    }

}
