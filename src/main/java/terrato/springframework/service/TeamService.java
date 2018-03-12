package terrato.springframework.service;

import terrato.springframework.domain.Team;

import java.util.Collection;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamService {



    Set<Team> findTeamByLeagueId(Long idLeague);

    Team saveTeam(Team teamSource);

    Team addTeamToLeague(Long leagueId, Long teamId);

    void deleteTeamFromLeague(Long idLeague, Long idTeam);

    void deleteTeam(Long id);

    Collection<Team> setTeamByPoints(Long idLeague);

}
