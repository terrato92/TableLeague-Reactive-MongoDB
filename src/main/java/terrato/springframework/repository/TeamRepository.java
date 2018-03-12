package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframework.domain.Team;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamRepository extends CrudRepository<Team, Long> {

}
