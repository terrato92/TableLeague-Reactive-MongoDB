package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import terrato.springframework.domain.Nationality;
import terrato.springframework.repository.NationalityRepository;
import terrato.springframework.service.NationalityService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by onenight on 2018-03-05.
 */
@Service
public class NationalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    public NationalityServiceImpl(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public Set<Nationality> listAllNationalities() {
        Set<Nationality> states = new HashSet<>();
        nationalityRepository.findAll().forEach(states::add);
        return states;

    }
}
