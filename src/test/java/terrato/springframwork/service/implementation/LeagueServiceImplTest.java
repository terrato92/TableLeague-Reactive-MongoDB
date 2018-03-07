package terrato.springframwork.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframwork.domain.League;
import terrato.springframwork.repository.LeagueRepository;
import terrato.springframwork.service.LeagueService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by onenight on 2018-03-03.
 */
public class LeagueServiceImplTest {


    @Mock
    LeagueRepository leagueRepository;

    LeagueService leagueService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        leagueService = new LeagueServiceImpl(leagueRepository);
    }

    @Test
    public void getLeaguesTest() throws Exception {
        League league = new League();
        league.setId(1L);

        Set<League> leagues = new HashSet<>();
        leagues.add(league);

        when(leagueRepository.findAll()).thenReturn(leagues);

        assertEquals(1, leagues.size());
        verify(leagueRepository, never()).findOne(anyLong());

    }

    @Test
    public void getLeagueById() throws Exception {
        League league = new League();
        league.setId(2L);

        Optional<League> leagueOptional = Optional.of(league);

        when(leagueRepository.findOne(anyLong())).thenReturn(leagueOptional.get());

        League returnedLeague = leagueService.getLeagueById(league.getId());

        assertNotNull("NULL returned", returnedLeague);
        verify(leagueRepository, times(1)).findOne(anyLong());
        verify(leagueRepository, never()).findAll();


    }

    @Test
    public void addLeague() throws Exception {
        League newLegue = new League();
        newLegue.setId(1L);

        leagueRepository.save(newLegue);
        verify(leagueRepository, times(1)).save(newLegue);
    }

    @Test
    public void deleteLeagueById() throws Exception {
        leagueRepository.delete(anyLong());

        verify(leagueRepository, times(1)).delete(anyLong());
    }

}