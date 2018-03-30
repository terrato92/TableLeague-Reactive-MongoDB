package terrato.springframework.repository.reactiveRepository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import terrato.springframework.domain.Team;

/**
 * Created by onenight on 2018-03-03.
 */
@Repository
public interface TeamReactiveRepository extends ReactiveMongoRepository<Team, String> {

}
