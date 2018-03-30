package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.League;
import terrato.springframework.repository.reactiveRepository.LeagueReactiveRepository;
import terrato.springframework.service.LeagueService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-03.
 */
public class LeagueServiceImplTest {

    private static final String ID = "aaa";

    @Mock
    LeagueReactiveRepository leagueRepository;

    LeagueService leagueService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        leagueService = new LeagueServiceImpl(leagueRepository, leagueToLeagueDto, leagueDtoToLeague);
    }


    @Test
    public void testGetLeagueById() throws Exception {
        League league = new League();
        league.setId(ID);

        Optional<League> leagueOptional = Optional.of(league);

        when(leagueRepository.findById(anyString())).thenReturn(Mono.just(leagueOptional.get()));

        League returnedLeague = leagueService.getLeagueById(league.getId()).block();

        assertNotNull("NULL returned", returnedLeague);
        verify(leagueRepository, times(1)).findById(anyString());
        verify(leagueRepository, never()).findAll();

    }

    @Test
    public void testGetLeagues() throws Exception {
        League league = new League();

        when(leagueService.getLeagues()).thenReturn(Flux.just(league));

        List<League> leagues = leagueService.getLeagues().collectList().block();

        assertEquals(leagues.size(), 1);
        verify(leagueRepository, times(1)).findAll();
        verify(leagueRepository, never()).findById(anyString());
    }

    @Test
    public void testAddLeague() throws Exception {
        League newLeague = new League();
        newLeague.setId(ID);

        leagueRepository.save(newLeague);
        verify(leagueRepository, times(1)).save(newLeague);
    }

    @Test
    public void testDeleteLeagueById() throws Exception {

        when(leagueRepository.deleteById(anyString())).thenReturn(Mono.empty());

        leagueService.deleteLeagueById(ID);

        verify(leagueRepository,times(1)).deleteById(anyString());
    }


}