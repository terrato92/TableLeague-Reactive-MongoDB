package terrato.springframework.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.LeagueDto;

import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface LeagueService {


    Flux<League> getLeagues();

    Mono<League> saveLeague(League league);

    Mono<LeagueDto> saveLeagueDto(LeagueDto leagueDto);

    Mono<Set<Team>> showLeagueTeams(String idLeague);

    Mono<League> getLeagueById(String idLeague);

    Mono<LeagueDto> getLeagueDtoById(String idLegue);

    Mono<Void> deleteLeagueById(String id);

}
