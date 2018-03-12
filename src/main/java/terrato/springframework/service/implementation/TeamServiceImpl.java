package terrato.springframework.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.LeagueRepository;
import terrato.springframework.repository.NationalityRepository;
import terrato.springframework.repository.TeamRepository;
import terrato.springframework.service.TeamService;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final NationalityRepository nationalityRepository;

    public TeamServiceImpl(LeagueRepository leagueRepository, TeamRepository teamRepository, NationalityRepository nationalityRepository) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.nationalityRepository = nationalityRepository;
    }



    @Override
    public Set<Team> findTeamByLeagueId(Long idLeague) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(idLeague));

        if (!leagueOptional.isPresent()) {
            log.error("League is not found!");
        }

        League league = leagueOptional.get();

        return  league.getTeams();
    }

    @Override
    public Team findTeamById(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));

        if (!teamOptional.isPresent()){
            throw new RuntimeException("No team");
        } else {
            return teamOptional.get();
        }
    }

    @Override
    @Transactional
    public Team saveTeam(Team source, Long idLeague) {
//        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(source.getId()));

        Optional<League> leagueOptional = Optional.ofNullable((leagueRepository.findOne(idLeague)));

        if (!leagueOptional.isPresent()) {
            log.error("League with id " + source.getId() + " doesn't exist");
            throw new RuntimeException("brak ligi");
        } else {

            League league = leagueOptional.get();

            Optional<Team> teamOptional = league.getTeams().stream()
                    .filter(team -> team.getId().equals(source.getId()))
                    .findFirst();

            if (teamOptional.isPresent()) {

                teamOptional.get().setName(source.getName());
                teamOptional.get().setBalanceOfMatches(source.getBalanceOfMatches());
                teamOptional.get().setLeague(league);
                teamOptional.get().setPlayers(source.getPlayers());
                teamOptional.get().setPoints(source.getPoints());
                teamOptional.get().setNationality(nationalityRepository.findOne(source.getNationality().getId()));

                return teamOptional.get();

            } else {

                source.setLeague(league);
//                teamOptional.get().setBalanceOfMatches(new BalanceOfMatches());
                teamRepository.save(source);

                return source;
            }
        }
    }


    @Override
    @Transactional
    public void deleteTeamFromLeague(Long idLeague, Long idTeam) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(idLeague));

        if (leagueOptional.isPresent()) {
            League league = leagueOptional.get();

            Optional<Team> teamOptional = league.getTeams()
                    .stream()
                    .filter(team -> team.getId().equals(idTeam))
                    .findFirst();

            if (teamOptional.isPresent()) {
                Team teamToDelete = teamOptional.get();
                league.getTeams().remove(teamToDelete);
                teamToDelete.setLeague(null);
                teamRepository.delete(teamToDelete);
                leagueRepository.save(league);
            }
        }

    }

    @Override
    public void deleteTeam(Long id) {
        Optional<Team> teamToDelete = Optional.ofNullable(teamRepository.findOne(id));

        if (!teamToDelete.isPresent()) {
            log.error("Team with id: " + id + " doesn't exist");
        } else
            teamRepository.delete(teamToDelete.get());
    }

    @Override
    public Set<Team> setTeamByPoints(Long idLeague) {
        Optional<League> league = Optional.ofNullable(leagueRepository.findOne(idLeague));

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
