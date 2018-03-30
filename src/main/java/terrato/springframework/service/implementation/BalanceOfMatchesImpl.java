package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.BalanceOfMatches;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.reactiveRepository.BalanceOfMatchesReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.DefeatService;
import terrato.springframework.service.DrawService;
import terrato.springframework.service.WinService;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-04.
 */
@Service
public class BalanceOfMatchesImpl implements DefeatService, DrawService, WinService {

    private final TeamReactiveRepository teamRepository;
    private final BalanceOfMatchesReactiveRepository balanceOfMatchesRepository;


    public BalanceOfMatchesImpl(TeamReactiveRepository teamRepository,
                                BalanceOfMatchesReactiveRepository balanceOfMatchesRepository) {

        this.teamRepository = teamRepository;
        this.balanceOfMatchesRepository = balanceOfMatchesRepository;
    }

    @Override
    public Mono<BalanceOfMatches> winMatch(String idTeam) {
        Team team = teamRepository.findById(idTeam).block();


        if (team != null) {

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setWins(team.getBalanceOfMatches().getWins() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);

            return Mono.just(balanceOfMatches);

        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }


    }

    @Override
    public Mono<BalanceOfMatches> drawMatch(String idTeam) {
        Team team = teamRepository.findById(idTeam).block();

        if (team != null) {

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setDraws(team.getBalanceOfMatches().getDraws() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);

            return Mono.just(balanceOfMatches);
        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }

    }

    @Override
    public Mono<BalanceOfMatches> defeatMatch(String idTeam) {

        Team team = teamRepository.findById(idTeam).block();


        if (team != null) {

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setDefeats(team.getBalanceOfMatches().getDefeats() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);

            return Mono.just(balanceOfMatches);

        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }


    }
}
