package terrato.springframwork.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import terrato.springframwork.domain.League;

/**
 * Created by onenight on 2018-03-03.
 */
@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {
}
