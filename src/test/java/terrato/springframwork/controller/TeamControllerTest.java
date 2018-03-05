package terrato.springframwork.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import terrato.springframwork.domain.League;
import terrato.springframwork.service.LeagueService;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.TeamService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        League league = new League();

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);

        mockMvc.perform(get("/league/1/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/list"))
                .andExpect(model().attributeExists("teams"));

        verify(leagueService, times(1)).getLeagueById(anyLong());

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