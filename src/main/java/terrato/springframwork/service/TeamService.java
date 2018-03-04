package terrato.springframwork.service;

import terrato.springframwork.domain.Team;

import java.util.Collection;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamService {


    Team createTeam(Team team);

    Set<Team> getTeams();

    Collection<Team> getTeamsFromLeague(Long idLeague);

    Team findTeamById(Long idTeam);

    Team updateTeam(Team teamSource, Long idTeam);

    Team addTeamToLeague(Long leagueId, Long teamId);

    void deleteTeamFromLeague(Long idTeam);

    void deleteTeam(Long id);

    Collection<Team> setTeamByPoints(Long idLeague);

}
