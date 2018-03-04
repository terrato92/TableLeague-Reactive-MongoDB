package terrato.springframwork.service;

import terrato.springframwork.domain.Player;

import java.util.Collection;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerService {

    Player createPlayer(Player playerSource);

    Collection<Player> getPlayers();

    Collection<Player> getPlayersFromTeam(Long idTeam);


    Collection<Player> addPlayerToTeam(Player player, Long idTeam);


    void deletePlayer(Long idPlayer);

    Collection<Player> deletePlayerFromTeam(Long idPlayer, Long idTeam);

}
