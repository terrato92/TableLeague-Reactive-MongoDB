package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframework.domain.Player;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-03.
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findByName(String name);


}
