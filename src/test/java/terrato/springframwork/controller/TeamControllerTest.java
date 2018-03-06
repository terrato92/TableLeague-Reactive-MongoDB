package terrato.springframwork.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Team;
import terrato.springframwork.service.LeagueService;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.TeamService;

import java.util.HashSet;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void newTeamTest() throws Exception {
        League league = new League();
        league.setId(1L);

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);
        when(nationalityService.listAllStates()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/league/1/team/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/teamform"))
                .andExpect(model().attributeExists("team"))
                .andExpect(model().attributeExists("nationalList"));
    }

    @Test
    public void showTeamTest() throws Exception {
        Team team = new Team();

        when(teamService.findTeamById(anyLong(), anyLong())).thenReturn(team);

        mockMvc.perform(get("/league/1/team/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/show"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    public void getTeamsFromLeaguesTest() throws Exception {

        mockMvc.perform(get("/league/1/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/list"))
                .andExpect(model().attributeExists("teams"));


    }

    @Test
    public void updateTeamFormTest() throws Exception {

//        Team team = new Team();
//
//        when(teamService.findTeamById(anyLong(), anyLong())).thenReturn(team);
//        when(nationalityService.listAllStates()).thenReturn(new HashSet<>());
//
//        mockMvc.perform(get("/league/1/team/2/update"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("league/team/teamform"))
//                .andExpect(model().attributeExists("team"))
//                .andExpect(model().attributeExists("states"));
//

    }

    @Test
    public void saveOrUpdate() throws Exception {
//
//        mockMvc.perform(get("/league/3/team").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "")
//                .param("league", ""))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/league/3/team/1/show"));

    }


    @Test
    public void deleteTeamFromLeagueTest() throws Exception {

        mockMvc.perform(get("/league/2/team/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/2/teams"));

    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(get("/team/1/remove"))
                .andExpect(status().isOk())
                .andExpect(view().name("teams/list"));
    }

    @Test
    public void sortLeague() throws Exception {
        mockMvc.perform(get("/league/1/sort"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league_list"))
                .andExpect(view().name("league/show"));
    }

}