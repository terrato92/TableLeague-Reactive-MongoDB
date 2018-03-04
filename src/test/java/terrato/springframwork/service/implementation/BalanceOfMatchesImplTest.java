package terrato.springframwork.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframwork.domain.BalanceOfMatches;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.BalanceOfMatchesRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.DefeatService;
import terrato.springframwork.service.DrawService;
import terrato.springframwork.service.WinService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by onenight on 2018-03-04.
 */
public class BalanceOfMatchesImplTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    BalanceOfMatchesRepository balanceOfMatchesRepository;

    DefeatService defeatService;
    WinService winService;
    DrawService drawService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        defeatService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
        winService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
        drawService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
    }

    @Test
    public void winMatch() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setBalanceOfMatches(new BalanceOfMatches());

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        winService.winMatch(team.getId());
    }

    @Test
    public void drawMatch() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setBalanceOfMatches(new BalanceOfMatches());

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        drawService.drawMatch(team.getId());

    }

    @Test
    public void defeatMatch() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setBalanceOfMatches(new BalanceOfMatches());

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        defeatService.defeatMatch(team.getId());
    }

}