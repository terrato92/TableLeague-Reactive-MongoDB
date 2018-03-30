package terrato.springframework.service;

import reactor.core.publisher.Mono;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.TeamDto;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamService {


    Mono<TeamDto> saveTeam(TeamDto sourceTeam);

    Mono<Void> deleteTeam(String id);

    Mono<TeamDto> findTeamDtoByLeagueIdAndTeamId(String idLeague, String idTeam);

    Mono<Team> findTeamById(String idTeam);
}
