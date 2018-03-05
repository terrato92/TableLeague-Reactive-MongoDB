package terrato.springframwork.service;

import terrato.springframwork.domain.Team;

import java.util.Collection;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamService {


    Team findTeamById(Long idLeague, Long idTeam);

    Team saveTeam(Team teamSource);

    Team addTeamToLeague(Long leagueId, Long teamId);

    void deleteTeamFromLeague(Long idLeague, Long idTeam);

    void deleteTeam(Long id);

    Collection<Team> setTeamByPoints(Long idLeague);

}
