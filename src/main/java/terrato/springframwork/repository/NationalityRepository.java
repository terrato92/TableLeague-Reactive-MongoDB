package terrato.springframwork.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframwork.domain.Nationality;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-05.
 */
public interface NationalityRepository extends CrudRepository<Nationality, Long> {

    Optional<Nationality> findByState(String state);
}
