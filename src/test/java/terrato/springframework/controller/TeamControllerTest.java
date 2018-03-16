package terrato.springframework.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

import java.util.HashSet;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Mock
    PlayerService playerService;

    TeamController teamController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamController = new TeamController(teamService, leagueService, nationalityService, playerService);

        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }


    @Test
    public void newTeamTest() throws Exception {
        League league = new League();
        league.setId(1L);

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);
        when(nationalityService.listAllNationalities()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/league/1/team/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/teamform"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    public void testShowTeamById() throws Exception {

        Team team = new Team();

        when(teamService.findTeamById(anyLong())).thenReturn(team);

        mockMvc.perform(get("/team/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/show"))
                .andExpect(model().attributeExists("players"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    public void testListAllTeams() throws Exception {

        mockMvc.perform(get("/league/1/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/list"))
                .andExpect(model().attributeExists("teams"));


    }

    @Test
    public void testUpdateTeamForm() throws Exception {
        Team team = new Team();

        when(teamService.findTeamById(anyLong())).thenReturn(team);

        mockMvc.perform(get("/team/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/teamform"))
                .andExpect(model().attributeExists("team"));


    }

    @Test
    public void saveOrUpdate() throws Exception {
        Team team = new Team();
        team.setId(3L);

        when(teamService.findTeamById(anyLong())).thenReturn(team);

        mockMvc.perform(post("/league/3/team").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("leagueId","")
                .param("name","Chelsea"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/3/show"));
    }


    @Test
    public void deleteTeamFromLeagueTest() throws Exception {
        Team team = new Team();
        team.setId(1L);

        when(teamService.findTeamById(anyLong())).thenReturn(team);

        mockMvc.perform(post("/team/1/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/1/show"));

    }

    @Test
    public void sortLeague() throws Exception {
        mockMvc.perform(get("/league/1/sort"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league_list"))
                .andExpect(view().name("league/show"));
    }

}