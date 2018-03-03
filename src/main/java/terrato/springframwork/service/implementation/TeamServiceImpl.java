package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.service.TeamService;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    LeagueRepository leagueRepository;


    @Override
    public Collection<Team> sortTeamsByPoints(Long idLeague) {
        Optional<League> league = leagueRepository.findById(idLeague);

        if (league.isPresent()) {
            Set<Team> teamSet = league.get().getTeams();

            TreeSet<Team> listTeamSort = new TreeSet<>(teamSet);
            league.get().setTeams(listTeamSort);
            leagueRepository.save(league.get());

            return listTeamSort;
        } else {
            log.error("I can't find league with id: " + idLeague);
            throw new RuntimeException("League doesn't exist");
        }
    }
}
