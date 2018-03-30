package terrato.springframework.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Nationality;
import terrato.springframework.domain.Team;
import terrato.springframework.exception.NotFoundException;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.TeamService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by onenight on 2018-03-03.
 */
public class LeagueControllerTest {

    private static final String ID = "Serie A";

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
        mockMvc = MockMvcBuilders.standaloneSetup(leagueController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showLeaguesTest() throws Exception {
        League league = new League();
        league.setId(ID);

        when(leagueService.getLeagues()).thenReturn(Flux.just(league));

        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("leagues"))
                .andExpect(view().name("index"));
    }

    @Test
    public void showLeagueById() throws Exception {
        League league = new League();
        league.setId(ID);

        when(leagueService.getLeagueById(anyString())).thenReturn(Mono.just(league));

        mockMvc.perform(get("/league/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league"))
                .andExpect(view().name("league/show"));
    }

    @Test
    public void testGetNewLeagueForm() throws Exception {
        Team team = new Team();
        team.setId(ID);

        when(nationalityService.listAllNationalities()).thenReturn(Flux.just(new Nationality()));

        mockMvc.perform(get("/league/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/leagueform"))
                .andExpect(model().attributeExists("league"))
                .andExpect(model().attributeExists("nationalities"));
    }

    @Test
    public void testGetPostNewLeagueForm() throws Exception {
        League league = new League();
        league.setId(ID);

        when(leagueService.saveLeague(any())).thenReturn(Mono.just(league));

        when(nationalityService.listAllNationalities()).thenReturn(Flux.just(new Nationality()));

        mockMvc.perform(post("/league")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("name", "some string")
                .param("nationalities", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/Serie A/show"));

    }

    @Test
    public void testPostValidationFail() throws Exception {
        League league = new League();
        league.setId(ID);

        when(leagueService.saveLeague(any())).thenReturn(Mono.just(league));

        when(nationalityService.listAllNationalities()).thenReturn(Flux.just(new Nationality()));

        mockMvc.perform(post("/league")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("name", "")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league"))
                .andExpect(view().name("league/leagueform"));

    }

    @Test
    public void testGetUpdateView() throws Exception {
        League league = new League();
        league.setId(ID);

        when(leagueService.getLeagueById(anyString())).thenReturn(Mono.just(league));
        when(nationalityService.listAllNationalities()).thenReturn(Flux.just(new Nationality()));

        mockMvc.perform(get("/league/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("league"))
                .andExpect(view().name("league/leagueform"));
    }

    @Test
    public void deleteLeague() throws Exception {

        mockMvc.perform(get("/league/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(leagueService, times(1)).deleteLeagueById(anyString());
    }


    @Test
    public void testGetLeagueByIdNotFound() throws Exception {

        when(leagueService.getLeagueById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/league/8/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }
}