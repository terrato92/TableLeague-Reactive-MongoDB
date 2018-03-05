package terrato.springframwork.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.repository.NationalityRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.TeamService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamServiceImplTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    NationalityRepository nationalityRepository;

    @Mock
    LeagueRepository leagueRepository;

    TeamService teamService;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamService = new TeamServiceImpl(leagueRepository, teamRepository, nationalityRepository);
    }


    @Test
    public void getTeamsFromLeague() throws Exception {
        League league = new League();
        league.setId(3L);

        Team team1 = new Team();
        team1.setId(1L);
        team1.setLeague(league);
        Team team2 = new Team();
        team2.setId(2L);
        team2.setLeague(league);

        Set<Team> teams = new HashSet<>();
        teams.add(team1);
        teams.add(team2);

        league.setTeams(teams);

       assertNotNull("NUll", league.getTeams());
       assertEquals(2, league.getTeams().stream().filter(team -> team.getLeague().equals(league)).count());
    }

    @Test
    public void findLeagueTeamById() throws Exception {
        Team team = new Team();
        team.setId(2L);

        League league = new League();
        league.setId(1L);
        team.setLeague(league);
        league.getTeams().add(team);

        Optional<League> leagueOptional = Optional.of(league);

        when(leagueRepository.findOne(anyLong())).thenReturn(leagueOptional.get());

        Team team1 = teamService.findTeamById(1L, 2L);

        assertEquals(Long.valueOf(2L), team1.getId());
        assertNotNull(teamService.findTeamById(1L, 2L));
    }


    @Test
    public void addTeamToLeague() throws Exception {
        League league = new League();
        league.setId(2L);

        Team team = new Team();
        team.setId(1L);

        when(teamRepository.findOne(anyLong())).thenReturn(team);
        when(leagueRepository.findOne(anyLong())).thenReturn(league);

        teamService.addTeamToLeague(2L, team.getId());

        assertEquals(league, team.getLeague());
        assertNotNull("null", team.getLeague());

    }

    @Test
    public void testSaveTeam() throws Exception {
        Team team = new Team();
        team.setId(1L);
        League league = new League();
        league.setId(2L);
        team.setLeague(league);

        Optional<League> leagueOptional = Optional.of(new League());

        League saveLeague = new League();
        saveLeague.setId(3L);
        saveLeague.addTeam(team);
        saveLeague.getTeams().iterator().next().setId(1L);

        when(leagueRepository.findOne(anyLong())).thenReturn(leagueOptional.get());
        when(leagueRepository.save((League) any())).thenReturn(saveLeague);

        Team team1 = teamService.saveTeam(team);

        assertEquals(Long.valueOf(1L), team1.getId());
    }

    @Test
    public void deleteTeamFromLeague() throws Exception {
        League league = new League();
        league.setId(3L);

        Team team = new Team();
        team.setId(2L);
        team.setLeague(league);

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        teamService.deleteTeamFromLeague(league.getId(), team.getId());

        assertTrue(league.getTeams().isEmpty());
    }


    @Test
    public void setTeamByPoints() throws Exception {
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Chelsea");
        team1.setPoints(6);
        Team team2 = new Team();
        team1.setId(2L);
        team2.setPoints(9);
        team2.setName("Manchester");

        Set<Team> teams = new HashSet<>();
        teams.add(team1);
        teams.add(team2);

        League league = new League();
        league.setId(3L);
        league.setTeams(teams);

        team1.setLeague(league);
        team2.setLeague(league);

        when(leagueRepository.findOne(anyLong())).thenReturn(league);

        teamService.setTeamByPoints(league.getId());

        assertEquals("Manchester", league.getTeams().iterator().next().getName());

    }

}