package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.repository.NationalityRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.TeamService;

import java.util.*;

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
    public Set<Team> getTeams() {
        Set<Team> teams = new HashSet<>();
        teamRepository.findAll().forEach(teams::add);

        return teams;
    }

    @Override
    public Set<Team> getTeamsFromLeague(Long idLeague) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(idLeague));

        if (leagueOptional.isPresent()) {
            League league = leagueOptional.get();

            return league.getTeams();
        } else {
            log.error("League doesn't exist");
            throw new RuntimeException("League doesn't exist");
        }
    }

    @Override
    public Team findTeamById(Long idLeague, Long idTeam) {
        Optional<League> leagueOptional = Optional.of(leagueRepository.findOne(idLeague));

        if (!leagueOptional.isPresent()) {
            log.error("League is not found!");
        }

        League league = leagueOptional.get();

        Optional<Team> teamOptional = league.getTeams()
                .stream()
                .filter(team -> team.getId().equals(idTeam))
                .findFirst();

        return teamOptional.get();
    }

    @Override
    @Transactional
    public Team saveTeam(Team source) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(source.getLeague().getId()));

        if (!leagueOptional.isPresent()) {
            log.error("League with id " + source.getId() + " doesn't exist");
            return new Team();
        } else {

            League league = leagueOptional.get();

            Optional<Team> teamOptional = league.getTeams().stream()
                    .filter(team -> team.getId().equals(source.getId()))
                    .findFirst();

            if (teamOptional.isPresent()) {

                teamOptional.get().setName(source.getName());
                teamOptional.get().setBalanceOfMatches(source.getBalanceOfMatches());
                teamOptional.get().setLeague(source.getLeague());
                teamOptional.get().setPlayers(source.getPlayers());
                teamOptional.get().setPoints(source.getPoints());
                teamOptional.get().setState(nationalityRepository.findOne(source.getState().getId()));

            } else {
                Team team = new Team();
                team.setLeague(league);
                league.getTeams().add(team);
            }

            League savedLeague = leagueRepository.save(league);

            Optional<Team> saveTeamOptional = savedLeague.getTeams().stream()
                    .filter(leagueTeam -> leagueTeam.getId().equals(source.getId()))
                    .findFirst();

            return saveTeamOptional.get();
        }
    }
    @Override
    @Transactional
    public Team addTeamToLeague(Long leagueId, Long teamId) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(leagueId));

        if (leagueOptional.isPresent()) {
            Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(teamId));
            Team team = teamOptional.get();
            team.setLeague(leagueOptional.get());

            teamRepository.save(team);
            return team;
        } else {
            throw new RuntimeException("League id doesn't exist");
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
                leagueRepository.save(league);
            }
        }

    }

    @Override
    @Transactional
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
