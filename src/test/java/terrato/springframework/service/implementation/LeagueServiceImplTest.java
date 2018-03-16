package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframework.domain.League;
import terrato.springframework.exception.NotFoundException;
import terrato.springframework.repository.LeagueRepository;
import terrato.springframework.service.LeagueService;

import java.util.Optional;

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
    public void testGetLeagueById() throws Exception {
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
    public void testAddLeague() throws Exception {
        League newLeague = new League();
        newLeague.setId(1L);

        leagueRepository.save(newLeague);
        verify(leagueRepository, times(1)).save(newLeague);
    }

    @Test
    public void testDeleteLeagueById() throws Exception {
        League league = new League();
        league.setId(2L);

        when(leagueRepository.findOne(anyLong())).thenReturn(league);

        leagueService.deleteLeagueById(league.getId());

    }

    @Test(expected = NotFoundException.class)
    public void testGetLeagueByIdNotFound() throws Exception {
        League league = leagueService.getLeagueById(1L);

        when(leagueRepository.findOne(anyLong())).thenReturn(league);

    }
}