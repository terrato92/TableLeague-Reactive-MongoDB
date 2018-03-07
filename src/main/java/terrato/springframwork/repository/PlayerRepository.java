package terrato.springframwork.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframwork.domain.Player;

import java.util.Optional;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Set<Player>> getAllByTeamId(Long idTeam);


}
