package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.LeagueRepository;
import terrato.springframework.repository.NationalityRepository;
import terrato.springframework.repository.TeamRepository;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.TeamService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamServiceImplTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    LeagueService leagueService;

    @Mock
    NationalityRepository nationalityRepository;

    @Mock
    LeagueRepository leagueRepository;

    @Mock
    TeamService teamService;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamService = new TeamServiceImpl(leagueRepository, teamRepository, nationalityRepository);

    }


    @Test
    public void findLeagueTeamById() throws Exception {
//        Team team = new Team();
//        team.setId(2L);
//
//        League league = new League();
//        league.setId(1L);
//        team.setLeague(league);
//        league.getTeams().add(team);
//
//        Optional<League> leagueOptional = Optional.of(league);
//
//        when(leagueRepository.findOne(anyLong())).thenReturn(leagueOptional.get());
//
//        Team team1 = teamService.findTeamByLeagueId(1L, 2L);
//
//        assertEquals(Long.valueOf(2L), team1.getId());
//        assertNotNull(teamService.findTeamByLeagueId(1L, 2L));
    }


    @Test
    public void addTeamToLeague() throws Exception {
//        League league = new League();
//        league.setId(2L);
//
//        Team team = new Team();
//        team.setId(1L);
//
//        when(teamRepository.findOne(anyLong())).thenReturn(team);
//        when(leagueRepository.findOne(anyLong())).thenReturn(league);
//
//        teamService.addTeamToLeague(2L, team.getId());
//
//        assertEquals(league, team.getLeague());
//        assertNotNull("null", team.getLeague());

    }

    @Test
    public void testSaveTeam() throws Exception {
//        Team team = new Team();
//        team.setId(1L);
//        League league = new League();
//        league.setId(2L);
//        team.setLeague(league);
//
//        Optional<League> leagueOptional = Optional.of(new League());
//
//        League saveLeague = new League();
//        saveLeague.setId(3L);
//        saveLeague.addTeam(team);
//        saveLeague.getTeams().iterator().next().setId(1L);
//
//        when(leagueRepository.findOne(anyLong())).thenReturn(leagueOptional.get());
//        when(leagueRepository.save((League) any())).thenReturn(saveLeague);
//
//        Team team1 = teamService.saveTeam(team);
//
//        assertEquals(Long.valueOf(1L), team1.getId());
    }

    @Test
    public void deleteTeamFromLeague() throws Exception {
        League league = new League();
        league.setId(1L);
        Team team = new Team();
        team.setId(1L);
        team.setLeague(league);
        league.addTeam(team);

        when(leagueService.getLeagueById(team.getLeague().getId())).thenReturn(team.getLeague());

        assertEquals(1, league.getTeams().size());

        teamService.deleteTeamFromLeague(team.getLeague().getId(), team.getId());

        assertTrue(leagueService.showLeagueTeams(team.getLeague().getId()).isEmpty());
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