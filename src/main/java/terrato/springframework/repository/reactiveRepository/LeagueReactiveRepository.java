package terrato.springframework.repository.reactiveRepository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import terrato.springframework.domain.League;

/**
 * Created by onenight on 2018-03-03.
 */
@Repository
public interface LeagueReactiveRepository extends ReactiveMongoRepository<League, String> {

}
