package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import terrato.springframework.converter.NationalityToNationalityDto;
import terrato.springframework.dto.NationalityDto;
import terrato.springframework.repository.reactiveRepository.NationalityReactiveRepository;
import terrato.springframework.service.NationalityService;

/**
 * Created by onenight on 2018-03-05.
 */
@Service
public class NationalityServiceImpl implements NationalityService {

    private final NationalityReactiveRepository nationalityRepository;
    private final NationalityToNationalityDto nationalityToNationalityDto;

    public NationalityServiceImpl(NationalityReactiveRepository nationalityRepository, NationalityToNationalityDto nationalityToNationalityDto) {
        this.nationalityRepository = nationalityRepository;
        this.nationalityToNationalityDto = nationalityToNationalityDto;
    }

    @Override
    public Flux<NationalityDto> listAllNationalities() {

        return nationalityRepository.findAll().map(nationality -> nationalityToNationalityDto.convert(nationality));

    }
}
