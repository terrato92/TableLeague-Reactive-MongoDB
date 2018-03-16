package terrato.springframework.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import terrato.springframework.domain.League;
import terrato.springframework.exception.NotFoundException;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.TeamService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by onenight on 2018-03-03.
 */
public class LeagueControllerTest {

    @Mock
    LeagueService leagueService;

    @Mock
    TeamService teamService;

    @Mock
    NationalityService nationalityService;

    LeagueController leagueController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        leagueController = new LeagueController(leagueService, teamService, nationalityService);
        mockMvc = MockMvcBuilders.standaloneSetup(leagueController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void showLeaguesTest() throws Exception {

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("leagues"))
                .andExpect(view().name("index"));
    }

    @Test
    public void showLeagueById() throws Exception {
        League league = new League();
        league.setId(1L);


        when(leagueService.getLeagueById(anyLong())).thenReturn(league);
        when(teamService.findTeamByLeagueId(anyLong())).thenReturn(league.getTeams());

        mockMvc.perform(get("/league/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league"))
                .andExpect(model().attributeExists("teams"))
                .andExpect(view().name("league/show"));
    }

    @Test
    public void testGetNewLeagueForm() throws Exception {

        mockMvc.perform(get("/league/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/leagueform"))
                .andExpect(model().attributeExists("league"))
                .andExpect(model().attributeExists("nationalities"));
    }

    @Test
    public void deleteLeague() throws Exception {

        mockMvc.perform(get("/league/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(leagueService, times(1)).deleteLeagueById(anyLong());
    }

    @Test
    public void saveOrUpdateLeague() throws Exception {
        League league = new League();
        league.setId(1L);

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);

        mockMvc.perform(post("/league").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("name", "pr"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }


    @Test
    public void testGetLeagueByIdNotFound() throws Exception {

        when(leagueService.getLeagueById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/league/8/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test()
    public void testGetLeagueByIdNumberFormatException() throws Exception {

        mockMvc.perform(get("/league/asd/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

}