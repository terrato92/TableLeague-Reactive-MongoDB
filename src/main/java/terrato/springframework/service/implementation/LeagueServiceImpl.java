package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import terrato.springframework.converter.LeagueDtoToLeague;
import terrato.springframework.converter.LeagueToLeagueDto;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.LeagueDto;
import terrato.springframework.repository.reactiveRepository.LeagueReactiveRepository;
import terrato.springframework.service.LeagueService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
public class LeagueServiceImpl implements LeagueService {


    private  final LeagueReactiveRepository leagueRepository;
    private final LeagueToLeagueDto leagueToLeagueDto;
    private final LeagueDtoToLeague leagueDtoToLeague;

    public LeagueServiceImpl(LeagueReactiveRepository leagueRepository, LeagueToLeagueDto leagueToLeagueDto, LeagueDtoToLeague leagueDtoToLeague) {
        this.leagueRepository = leagueRepository;
        this.leagueToLeagueDto = leagueToLeagueDto;
        this.leagueDtoToLeague = leagueDtoToLeague;
    }

    @Override
    public Flux<League> getLeagues() {
        return leagueRepository.findAll();
    }

    @Override
    public Mono<Set<Team>> showLeagueTeams(String idLeague) {
        League league = leagueRepository.findById(idLeague).block();

        Set<Team> teams = league.getTeams();

        return Mono.just(new HashSet<>(teams));
    }

    @Override
    public Mono<League> getLeagueById(String idLeague) {
        League league = leagueRepository.findById(idLeague).block();

        return Mono.just(league);

    }

    @Override
    public Mono<LeagueDto> getLeagueDtoById(String idLegue) {

        return leagueRepository.findById(idLegue)
                .map(league -> {
                    LeagueDto leagueDto = leagueToLeagueDto.convert(league);

                    leagueDto.getTeams().forEach(teamDto -> teamDto.setLeagueId(leagueDto.getId()));

                    return leagueDto;
                });


    }


    @Override
    public Mono<League> saveLeague(League league) {
        return leagueRepository.save(league);
    }

    @Override
    public Mono<LeagueDto> saveLeagueDto(LeagueDto leagueDto) {
        return leagueRepository
                .save(leagueDtoToLeague.convert(leagueDto))
                .map(source -> leagueToLeagueDto.convert(source));
    }


    @Override
    public Mono<Void> deleteLeagueById(String idLeague) {

        leagueRepository.deleteById(idLeague).block();

        return Mono.empty();
    }


}
