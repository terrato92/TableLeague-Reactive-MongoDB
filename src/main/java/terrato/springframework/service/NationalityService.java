package terrato.springframework.service;

import reactor.core.publisher.Flux;
import terrato.springframework.dto.NationalityDto;

/**
 * Created by onenight on 2018-03-05.
 */
public interface NationalityService {

    Flux<NationalityDto> listAllNationalities();
}
