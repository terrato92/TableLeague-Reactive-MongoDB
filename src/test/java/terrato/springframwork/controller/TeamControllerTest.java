package terrato.springframwork.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import terrato.springframwork.service.LeagueService;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.TeamService;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamControllerTest {

    @Mock
    LeagueService leagueService;

    @Mock
    NationalityService nationalityService;

    @Mock
    TeamService teamService;

    TeamController teamController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamController = new TeamController(teamService, leagueService, nationalityService);

        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }


    @Test
    public void getTeams() throws Exception {

    }

    @Test
    public void getTeamsFromLeagues() throws Exception {
    }

    @Test
    public void findTeamById() throws Exception {
    }

    @Test
    public void saveOrUpdateTeam() throws Exception {
    }

    @Test
    public void addTeamToLeague() throws Exception {
    }

    @Test
    public void deleteTeamFromLeague() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void sortLeague() throws Exception {
    }

}