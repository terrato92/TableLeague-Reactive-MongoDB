package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.NationalityRepository;
import terrato.springframework.repository.PlayerRepository;
import terrato.springframework.repository.TeamRepository;
import terrato.springframework.service.PlayerService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-04.
 */
public class PlayerServiceImplTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    NationalityRepository nationalityRepository;

    PlayerService playerService;


    @Before
    public void setUpTest() throws Exception {
        MockitoAnnotations.initMocks(this);

        playerService = new PlayerServiceImpl(playerRepository, teamRepository, nationalityRepository);
    }


    @Test
    public void getPlayersTest() throws Exception {
        Player player = new Player();
        player.setId(3L);
        Player player1 = new Player();
        player1.setId(2L);

        Set<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player);

        when(playerRepository.findAll()).thenReturn(players);

        assertEquals(2, players.size());

    }

    @Test
    public void getTeamPlayerByIdTest() throws Exception {

        Team team = new Team();
        Set<Player> players = new HashSet<>();
        Player player = new Player();
        player.setId(1L);
        players.add(player);
        team.setPlayers(players);

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        Player updatePlayer = playerService.getTeamPlayerById(team.getId(), player.getId());

        assertEquals(player.getTeam(), updatePlayer.getTeam());
    }

    @Test
    public void getPlayersFromTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1L);

        Player player = new Player();
        Player player1 = new Player();
        player1.setId(3L);
        player.setId(2L);

        team.addPlayer(player1);
        team.addPlayer(player);

        assertFalse(team.getPlayers().isEmpty());
        assertEquals(2, team.getPlayers().size());
    }

    @Test
    public void deletePlayerTest() throws Exception {

        Player player = new Player();
        player.setId(2L);

        playerRepository.delete(anyLong());

        verify(playerRepository, times(1)).delete(anyLong());
    }

    @Test
    public void deletePlayerFromTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1L);

        Player player = new Player();
        player.setId(2L);
        player.setTeam(team);

        Set<Player> players = new HashSet<>();
        players.add(player);

        team.setPlayers(players);

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        playerService.deletePlayerFromTeam(player.getId(), team.getId());

        assertTrue(team.getPlayers().isEmpty());

        assertNotNull(player);
    }

    @Test
    public void savePlayerTest(){
        Team team = new Team();
        team.setId(2L);

        Player player = new Player();
        player.setId(1L);
        player.setTeam(team);

        Optional<Team> teamOptional = Optional.of(new Team());

        Team savedTeam = new Team();
        savedTeam.setId(5L);
        savedTeam.addPlayer(player);
        savedTeam.getPlayers().iterator().next().setId(2L);

        when(teamRepository.findOne(anyLong())).thenReturn(teamOptional.get());
        when(teamRepository.save((Team) any())).thenReturn(savedTeam);

        Player player1 = playerService.savePlayer(player);

        assertEquals(player.getId(), player1.getId());

    }

}