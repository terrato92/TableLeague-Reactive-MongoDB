package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.service.LeagueService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Slf4j
@Service
public class LeagueServiceImpl implements LeagueService {


    LeagueRepository leagueRepository;

    public LeagueServiceImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public Set<League> getLeagues() {
        Set<League> leagues = new HashSet<>();
        leagueRepository.findAll().iterator().forEachRemaining(leagues::add);
        return leagues;
    }

    @Override
    public Set<Team> showLeagueTeams(Long idLeague) {
        Set<Team> teamSet = leagueRepository.findOne(idLeague).getTeams();
        return teamSet;
    }

    @Override
    @Transactional
    public League getLeagueById(Long idLeague) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(idLeague));
        League league = leagueOptional.get();
        return league;
    }



    @Override
    @Transactional
    public League saveLeague(League league) {
        League league1 = leagueRepository.save(league);

        return league1;
    }


    @Override
    public void deleteLeagueById(Long idLeague) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(idLeague));
        League league1 = leagueOptional.get();
        leagueRepository.delete(league1);
    }



}
