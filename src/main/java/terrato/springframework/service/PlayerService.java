package terrato.springframework.service;

import terrato.springframework.domain.Player;

import java.util.Collection;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerService {

    Player getTeamPlayerById(Long idPlayer);

    Collection<Player> getPlayersFromTeam(Long idTeam);

    Player savePlayer(Player player, Long teamId);

    void deletePlayerFromTeam(Long idPlayer);

}
