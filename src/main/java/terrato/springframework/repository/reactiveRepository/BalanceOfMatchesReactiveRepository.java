package terrato.springframework.repository.reactiveRepository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import terrato.springframework.domain.BalanceOfMatches;

/**
 * Created by onenight on 2018-03-04.
 */
@Repository
public interface BalanceOfMatchesReactiveRepository extends ReactiveMongoRepository<BalanceOfMatches, String> {
}
