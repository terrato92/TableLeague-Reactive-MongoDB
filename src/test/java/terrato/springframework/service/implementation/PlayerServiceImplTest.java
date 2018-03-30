package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.reactiveRepository.LeagueReactiveRepository;
import terrato.springframework.repository.reactiveRepository.NationalityReactiveRepository;
import terrato.springframework.repository.reactiveRepository.PlayerReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.PointsService;
import terrato.springframework.service.TeamService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-04.
 */
public class PlayerServiceImplTest {

    @Mock
    PlayerReactiveRepository playerRepository;

    @Mock
    TeamReactiveRepository teamRepository;

    @Mock
    NationalityReactiveRepository nationalityRepository;

    @Mock
    LeagueReactiveRepository leagueRepository;

    PlayerService playerService;

    TeamService teamService;

    PointsService pointsService;

    @Before
    public void setUpTest() throws Exception {
        MockitoAnnotations.initMocks(this);

        playerService = new PlayerServiceImpl(playerRepository, teamRepository, nationalityRepository);
        teamService = new TeamServiceImpl(leagueRepository, balanceOfMatchesRepository, teamToTeamConvert, balanceOfMatchesDtoToBalanceOfMatches, nationalityDto, teamRepository, pointsService);
    }


//    @Test
//    public void getPlayersTest() throws Exception {
//        Team team = new Team();
//        team.setId("slask");
//
//        Player player = new Player();
//        Set<Player> players = new HashSet<>();
//        players.add(player);
//        players.add(new Player());
//
//        team.setPlayers(players);
//
//        when(playerService.getPlayersFromTeam(anyString())).thenReturn(Mono.just(team.getPlayers()));
//
//
//        assertThat(1, is(equalTo(playerRepository.count())));
//
//    }

    @Test
    public void getTeamPlayerByIdTest() throws Exception {

//        Team team = new Team();
//        Set<Player> players = new HashSet<>();
//        Player player = new Player();
//        player.setId(1L);
//        players.add(player);
//        team.setPlayers(players);
//
//        when(teamRepository.findOne(anyLong())).thenReturn(team);
//
//        Player updatePlayer = playerService.getTeamPlayerById(team.getId(), player.getId());
//
//        assertEquals(player.getTeam(), updatePlayer.getTeam());
    }

    @Test
    public void getPlayersFromTeamTest() throws Exception {
        Team team = new Team();
        team.setId("legia");

        Player player = new Player();
        Player player1 = new Player();
        player1.setId("kuchy");
        player.setId("pazdan");

        team.addPlayer(player1);
        team.addPlayer(player);

        assertFalse(team.getPlayers().isEmpty());
        assertEquals(2, team.getPlayers().size());
    }

    @Test
    public void deletePlayerTest() throws Exception {

        Player player = new Player();
        player.setId("cr7");

        playerRepository.deleteById(anyString());

        verify(playerRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void deletePlayerFromTeamTest() throws Exception {
//        Team team = new Team();
//        team.setId(1L);
//
//        Player player = new Player();
//        player.setId(2L);
//        player.setTeam(team);
//
//        Set<Player> players = new HashSet<>();
//        players.add(player);
//
//        team.setPlayers(players);
//
//        when(teamRepository.findOne(anyLong())).thenReturn(team);
//
//        playerService.deletePlayerFromTeam(player.getId(), team.getId());
//
//        assertTrue(team.getPlayers().isEmpty());
//
//        assertNotNull(player);
    }

    @Test
    public void savePlayerTest(){
//        Team team = new Team();
//        team.setId(2L);
//
//        Player player = new Player();
//        player.setId(1L);
//        player.setTeam(team);
//
//        Optional<Team> teamOptional = Optional.of(new Team());
//
//        Team savedTeam = new Team();
//        savedTeam.setId(5L);
//        savedTeam.addPlayer(player);
//        savedTeam.getPlayers().iterator().next().setId(2L);
//
//        when(teamRepository.findOne(anyLong())).thenReturn(teamOptional.get());
//        when(teamRepository.save((Team) any())).thenReturn(savedTeam);
//
//        Player player1 = playerService.savePlayer(player);
//
//        assertEquals(player.getId(), player1.getId());

    }

}