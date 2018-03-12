package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframework.domain.BalanceOfMatches;

/**
 * Created by onenight on 2018-03-04.
 */
public interface BalanceOfMatchesRepository extends CrudRepository<BalanceOfMatches, Long> {
}
