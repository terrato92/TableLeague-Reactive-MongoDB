package terrato.springframework.service.implementation;

import terrato.springframework.domain.BalanceOfMatches;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.BalanceOfMatchesRepository;
import terrato.springframework.repository.TeamRepository;
import terrato.springframework.service.DefeatService;
import terrato.springframework.service.DrawService;
import terrato.springframework.service.WinService;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-04.
 */
public class BalanceOfMatchesImpl implements DefeatService, DrawService, WinService {

    private final TeamRepository teamRepository;
    private final BalanceOfMatchesRepository balanceOfMatchesRepository;


    public BalanceOfMatchesImpl(TeamRepository teamRepository, BalanceOfMatchesRepository balanceOfMatchesRepository) {
        this.teamRepository = teamRepository;
        this.balanceOfMatchesRepository = balanceOfMatchesRepository;
    }

    @Override
    public BalanceOfMatches winMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));


        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setWins(team.getBalanceOfMatches().getWins() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);
            return balanceOfMatches;

        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }


    }

    @Override
    public BalanceOfMatches drawMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setDraws(team.getBalanceOfMatches().getDraws() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);

            return balanceOfMatches;
        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }

    }

    @Override
    public BalanceOfMatches defeatMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));


        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setDefeats(team.getBalanceOfMatches().getDefeats() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);

            return balanceOfMatches;

        } else {
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }


    }
}
