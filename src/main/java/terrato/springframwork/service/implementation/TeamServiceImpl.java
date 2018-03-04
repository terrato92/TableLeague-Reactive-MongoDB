package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.repository.TeamRepositroy;
import terrato.springframwork.service.TeamService;

import java.util.*;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    LeagueRepository leagueRepository;

    TeamRepositroy teamRepositroy;

    public TeamServiceImpl(LeagueRepository leagueRepository, TeamRepositroy teamRepositroy) {
        this.leagueRepository = leagueRepository;
        this.teamRepositroy = teamRepositroy;
    }

    @Override
    public Team createTeam(Team team) {
        Team team1 = teamRepositroy.findOne(team.getId());

        if (team1 == null) {
            Team newTeam = new Team();

            newTeam.setId(team.getId());
            newTeam.setName(team.getName());
            newTeam.setLeague(null);
            newTeam.setBalanceOfMatches(team.getBalanceOfMatches());
            newTeam.setPlayers(team.getPlayers());

            teamRepositroy.save(newTeam);
            return newTeam;

        } else {
            log.error("Team with id: " + team.getId() + " is in League");
            throw new RuntimeException("Team exist.");
        }
    }

    @Override
    public Set<Team> getTeams() {
        Set<Team> teams = new HashSet<>();
        teamRepositroy.findAll().forEach(teams::add);

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
    public Team findTeamById(Long idTeam) {
        return teamRepositroy.findOne(idTeam);
    }

    @Override
    @Transactional
    public Team updateTeam(Team source, Long id) {
        Optional<Team> teamUpdate = Optional.ofNullable(teamRepositroy.findOne(id));

        if (teamUpdate.isPresent()) {

            teamUpdate.get().setName(source.getName());
            teamUpdate.get().setBalanceOfMatches(source.getBalanceOfMatches());
            teamUpdate.get().setLeague(source.getLeague());
            teamUpdate.get().setPlayers(source.getPlayers());
            teamUpdate.get().setPoints(source.getPoints());

            return teamUpdate.get();

        } else {
            log.error("Team with id: " + source.getId() + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + source.getId());
        }
    }

    @Override
    @Transactional
    public Team addTeamToLeague(Long leagueId, Long teamId) {
        Optional<League> leagueOptional = Optional.ofNullable(leagueRepository.findOne(leagueId));

        if (leagueOptional.isPresent()) {
            Optional<Team> teamOptional = Optional.ofNullable(teamRepositroy.findOne(teamId));
            Team team = teamOptional.get();
            team.setLeague(leagueOptional.get());

            teamRepositroy.save(team);
            return team;
        } else {
            throw new RuntimeException("League id doesn't exist");
        }
    }

    @Override
    @Transactional
    public void deleteTeamFromLeague(Long idTeam) {
        Optional<Team> teamToDelete = Optional.ofNullable(teamRepositroy.findOne(idTeam));

        if (!teamToDelete.isPresent()) {
            log.error("Team with id: " + idTeam + " doesn't exist");
        } else {
            Team team = teamToDelete.get();
            team.setLeague(null);
            teamRepositroy.save(team);
        }
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        Optional<Team> teamToDelete = Optional.ofNullable(teamRepositroy.findOne(id));

        if (!teamToDelete.isPresent()) {
            log.error("Team with id: " + id + " doesn't exist");
        } else
            teamRepositroy.delete(teamToDelete.get());
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
