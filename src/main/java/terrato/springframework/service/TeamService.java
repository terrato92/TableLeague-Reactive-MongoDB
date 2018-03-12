package terrato.springframework.service;

import org.springframework.transaction.annotation.Transactional;
import terrato.springframework.domain.Team;

import java.util.Collection;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamService {



    Set<Team> findTeamByLeagueId(Long idLeague);


    Team findTeamById(Long idTeam);

    @Transactional
    Team saveTeam(Team source, Long idLeague);

    void deleteTeamFromLeague(Long idLeague, Long idTeam);

    void deleteTeam(Long id);

    Collection<Team> setTeamByPoints(Long idLeague);

}
