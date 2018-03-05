package terrato.springframwork.service;

import terrato.springframwork.domain.League;

import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface LeagueService {


    Set<League> getLeagues();

    League saveLeague(League league);

    League getLeagueById(Long aLong);

    void deleteLeagueById(Long id);

}
