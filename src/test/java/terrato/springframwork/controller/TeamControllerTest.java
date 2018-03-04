package terrato.springframwork.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import terrato.springframwork.service.TeamService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamControllerTest {

    @Mock
    Model modell;

    @Mock
    TeamService teamService;

    TeamController teamController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamController = new TeamController(teamService);

        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    public void newTeam() throws Exception {

        mockMvc.perform(get("/team/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/list"));
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