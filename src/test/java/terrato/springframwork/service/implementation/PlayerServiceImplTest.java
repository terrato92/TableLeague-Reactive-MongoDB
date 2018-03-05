package terrato.springframwork.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframwork.domain.Player;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.NationalityRepository;
import terrato.springframwork.repository.PlayerRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.PlayerService;

import java.util.HashSet;
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
    public void getPlayersFromTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1L);

        Player player = new Player();
        player.setId(2L);

        Set<Player> players = new HashSet<>();
        players.add(player);

        team.setPlayers(players);

        assertFalse(team.getPlayers().isEmpty());
        assertEquals(1, team.getPlayers().size());

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

}