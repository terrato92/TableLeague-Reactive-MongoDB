package terrato.springframwork.service;

import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;

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
