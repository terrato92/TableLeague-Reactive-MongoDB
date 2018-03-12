package terrato.springframework.service;

import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;

import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface LeagueService {


    Set<League> getLeagues();

    League saveLeague(League league);

    Set<Team> showLeagueTeams(Long idLeague);

    League getLeagueById(Long aLong);

    void deleteLeagueById(Long id);

}
