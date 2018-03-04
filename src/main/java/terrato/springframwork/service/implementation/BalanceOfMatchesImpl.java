package terrato.springframwork.service.implementation;

import terrato.springframwork.domain.BalanceOfMatches;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.BalanceOfMatchesRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.DefeatService;
import terrato.springframwork.service.DrawService;
import terrato.springframwork.service.WinService;

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
    public void winMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));


        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setWins(team.getBalanceOfMatches().getWins() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);
        }


    }

    @Override
    public void drawMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));


        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setWins(team.getBalanceOfMatches().getDraws() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);
        }

    }

    @Override
    public void defeatMatch(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));


        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<BalanceOfMatches> balanceOfMatchesOptional = Optional.ofNullable(team.getBalanceOfMatches());

            BalanceOfMatches balanceOfMatches = balanceOfMatchesOptional.get();

            balanceOfMatches.setWins(team.getBalanceOfMatches().getDefeats() + 1);

            team.setBalanceOfMatches(balanceOfMatches);

            teamRepository.save(team);
        }


    }
}
