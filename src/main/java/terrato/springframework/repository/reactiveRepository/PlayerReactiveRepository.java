package terrato.springframework.repository.reactiveRepository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.Player;

import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Repository
public interface PlayerReactiveRepository extends ReactiveMongoRepository<Player, String> {

    Mono<Set<Player>> findByTeamId(String teamId);

}
