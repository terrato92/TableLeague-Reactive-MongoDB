package terrato.springframwork.service.implementation;

import org.springframework.stereotype.Service;
import terrato.springframwork.domain.Nationality;
import terrato.springframwork.repository.NationalityRepository;
import terrato.springframwork.service.NationalityService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by onenight on 2018-03-05.
 */
@Service
public class NatinalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    public NatinalityServiceImpl(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public Set<Nationality> listAllStates() {
        Set<Nationality> states = new HashSet<>();
        nationalityRepository.findAll().forEach(states::add);
        return states;

    }
}
