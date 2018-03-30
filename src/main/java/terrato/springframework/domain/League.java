package terrato.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by onenight on 2018-03-02.
 */
@Getter
@Setter
@Document
public class League {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private Difficulty difficulty;

    private Set<Team> teams = new HashSet<>();

    private Nationality nationality;

    public League addTeam(Team team){
        this.teams.add(team);
        return this;
    }


}
