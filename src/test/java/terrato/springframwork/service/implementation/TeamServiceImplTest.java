package terrato.springframwork.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.repository.TeamRepositroy;
import terrato.springframwork.service.TeamService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamServiceImplTest {

    @Mock
    TeamRepositroy teamRepositroy;

    TeamService teamService;

    @Mock
    LeagueRepository leagueRepository;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamService = new TeamServiceImpl(leagueRepository, teamRepositroy);
    }

    @Test
    public void createTeam() throws Exception {
        Team team = new Team();
        team.setId(1L);

        when(teamRepositroy.findOne(anyLong())).thenReturn(team);

    }

    @Test
    public void getTeams() throws Exception {
        Set<Team> league = new HashSet<>();
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(2L);
        league.add(team1);
        league.add(team2);

        when(teamRepositroy.findAll()).thenReturn(league);

        assertEquals(2, league.size());
        verify(teamRepositroy, never()).findOne(anyLong());
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
    public void findTeamById() throws Exception {
        Team team = new Team();
        team.setId(2L);

        when(teamRepositroy.findOne(anyLong())).thenReturn(team);

        Team team1 = teamService.findTeamById(2L);

        assertNotNull(team1);
    }

    @Test
    public void updateTeam() throws Exception {
        Team team = new Team();
        team.setId(1L);
        Team team1 = new Team();
        team1.setId(2L);
        team.setName("lol");

        when(teamRepositroy.findOne(anyLong())).thenReturn(team);

        teamService.updateTeam(team1, 1L);

        assertEquals(team.getName(), team1.getName());


    }

    @Test
    public void addTeamToLeague() throws Exception {
        League league = new League();
        league.setId(2L);

        Team team = new Team();
        team.setId(1L);

        when(teamRepositroy.findOne(anyLong())).thenReturn(team);
        when(leagueRepository.findOne(anyLong())).thenReturn(league);

        teamService.addTeamToLeague(2L, team.getId());

        assertEquals(league, team.getLeague());
        assertNotNull("null", team.getLeague());

    }

    @Test
    public void deleteTeamFromLeague() throws Exception {
        League league = new League();
        league.setId(3L);

        Team team = new Team();
        team.setId(2L);
        team.setLeague(league);

        when(teamRepositroy.findOne(anyLong())).thenReturn(team);

        teamService.deleteTeamFromLeague(league.getId(), team.getId());

        assertTrue(league.getTeams().isEmpty());
    }

    @Test
    public void deleteTeam() throws Exception {
        Team team = new Team();
        team.setId(5L);

        teamRepositroy.delete(team.getId());

        verify(teamRepositroy, times(1)).delete(anyLong());
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