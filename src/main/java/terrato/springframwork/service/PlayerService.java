package terrato.springframwork.service;

import terrato.springframwork.domain.Player;

import java.util.Collection;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerService {

    Player getTeamPlayerById(Long idTeam, Long idPlayer);

    Collection<Player> getPlayersFromTeam(Long idTeam);

    Player savePlayer(Player player);

    Collection<Player> deletePlayerFromTeam(Long idPlayer, Long idTeam);

}
