package terrato.springframework.service;

import reactor.core.publisher.Mono;
import terrato.springframework.domain.Player;

import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerService {

    Mono<Player> getTeamPlayerById(String idPlayer);

    Mono<Set<Player>> getPlayersFromTeam(String idTeam);

    Mono<Player> savePlayer(Player player, String teamId);

    Mono<Void> deletePlayerFromTeam(String idPlayer);

}
