package terrato.springframework.repository.reactiveRepository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import terrato.springframework.domain.Nationality;

/**
 * Created by onenight on 2018-03-05.
 */
@Repository
public interface NationalityReactiveRepository extends ReactiveMongoRepository<Nationality, String> {

}
