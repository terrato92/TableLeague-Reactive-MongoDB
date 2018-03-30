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
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
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

        teamController = new TeamController(teamService, leagueService, nationalityService, playerService, teamToTeamConvert, teamDtoToTeam);

        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }


    @Test
    public void newTeamTest() throws Exception {
        League league = new League();
        league.setId("ManCity");

        Flux<Nationality> nationalityFlux = nationalityService.listAllNationalities();

        when(leagueService.getLeagueById(anyString())).thenReturn(Mono.just(league));
        when(nationalityService.listAllNationalities()).thenReturn(nationalityFlux);

        mockMvc.perform(get("/league/1/team/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/teamform"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    public void testShowTeamById() throws Exception {

        Player player = new Player();
        player.setId("ronnie");
        player.setName("CR7");

        Player player1 = new Player();
        player1.setId("Loe");
        player1.setName("messi");

        Set<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player);

        Team team = new Team();
        team.setId("asd");

        team.setPlayers(players);
        team.addPlayer(player);

        player.setTeam(team);

        when(playerService.getPlayersFromTeam(anyString())).thenReturn(Mono.just(players));
        when(teamService.findTeamById(anyString())).thenReturn(Mono.just(team));


        mockMvc.perform(get("/team/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/show"))
                .andExpect(model().attributeExists("players"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    public void testListAllTeams() throws Exception {
        Team team = new Team();
        Team team1 = new Team();
        Team team2 = new Team();

        Set<Team> teams = new HashSet<Team>();
        teams.add(team);
        teams.add(team2);
        teams.add(team1);

        League league = new League();
        league.setId("kkk");
        league.setTeams(teams);

        when(leagueService.showLeagueTeams(anyString())).thenReturn(Mono.just(league.getTeams()));

        mockMvc.perform(get("/league/1/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("league/team/list"))
                .andExpect(model().attributeExists("teams"));


    }

    @Test
    public void testUpdateTeamForm() throws Exception {
        Team team = new Team();
        team.setId("Chelsea");

        when(teamService.findTeamById(anyString())).thenReturn(Mono.just(team));

        mockMvc.perform(get("/team/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/teamform"))
                .andExpect(model().attributeExists("team"));


    }

    @Test
    public void saveOrUpdate() throws Exception {
        Team team = new Team();
        team.setId("ManU");

        when(teamService.findTeamById(anyString())).thenReturn(Mono.just(team));

        mockMvc.perform(post("/league/3/team").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("leagueId","")
                .param("name","Chelsea"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/3/show"));
    }


    @Test
    public void deleteTeamFromLeagueTest() throws Exception {
        Team team = new Team();
        team.setId("lech");

        when(teamService.findTeamById(anyString())).thenReturn(Mono.just(team));

        mockMvc.perform(post("/team/1/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/league/1/show"));

    }

}