package terrato.springframwork.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import terrato.springframwork.domain.League;
import terrato.springframwork.service.LeagueService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by onenight on 2018-03-03.
 */
public class LeagueControllerTest {

    @Mock
    Model model;

    @Mock
    LeagueService leagueService;

    LeagueController leagueController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        leagueController = new LeagueController(leagueService);
        mockMvc = MockMvcBuilders.standaloneSetup(leagueController).build();
    }

    @Test
    public void showLeagueTest() throws Exception {
        League league = new League();
        League league1 = new League();
        Set<League> leagues = new HashSet<>();
        league.setId(1L);
        league1.setId(2L);
        leagues.add(league);
        leagues.add(league1);

        when(leagueService.getLeagues()).thenReturn(leagues);

        ArgumentCaptor<Set> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = leagueController.showLeague(model);

        assertEquals("league/index", viewName);
        verify(leagueService, times(1)).getLeagues();
        verify(model, times(1)).addAttribute(eq("leagues"), argumentCaptor.capture());

        assertEquals(2, leagues.size());

    }

    @Test
    public void showLeagueById() throws Exception {
        League league = new League();
        league.setId(1L);

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);

        mockMvc.perform(get("/leagues/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/show"));
    }

    @Test
    public void newLeague() throws Exception {

        mockMvc.perform(get("/league/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/show"));
    }

    @Test
    public void deleteLeague() throws Exception {

        mockMvc.perform(get("/league/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/show"));

        verify(leagueService, times(1)).deleteLeagueById(anyLong());
    }

    @Test
    public void saveOrUpdateLeague() throws Exception {
        League league = new League();
        league.setId(1L);

        when(leagueService.getLeagueById(anyLong())).thenReturn(league);

        mockMvc.perform(get("/league/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/leagueform"));

    }

}