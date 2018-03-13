//package terrato.springframwork.bootStrap;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import terrato.springframework.domain.League;
//import terrato.springframework.domain.Nationality;
//import terrato.springframework.domain.Team;
//import terrato.springframework.repository.LeagueRepository;
//import terrato.springframework.repository.NationalityRepository;
//import terrato.springframework.repository.PlayerRepository;
//import terrato.springframework.repository.TeamRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Created by onenight on 2018-03-08.
// */
//@Component
//public class BootStrapDev implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final LeagueRepository leagueRepository;
//    private final TeamRepository teamRepository;
//    private final PlayerRepository playerRepository;
//    private final NationalityRepository nationalityRepository;
//
//    public BootStrapDev(LeagueRepository leagueRepository,
//                        TeamRepository teamRepository,
//                        PlayerRepository playerRepository,
//                        NationalityRepository nationalityRepository) {
//
//        this.leagueRepository = leagueRepository;
//        this.teamRepository = teamRepository;
//        this.playerRepository = playerRepository;
//        this.nationalityRepository = nationalityRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event){
//        leagueRepository.save(getLeagues());
//    }
//
//    private List<League> getLeagues(){
//        List<League> leagues = new ArrayList<>();
//
//
//        Optional<Nationality> englandOptionalUOM = nationalityRepository.findByState("England");
//
//        if (!englandOptionalUOM.isPresent()) {
//            throw new RuntimeException("Expected UOM not found");
//        }
//
//        Nationality england = englandOptionalUOM.get();
//
//
//        League league = new League();
//        league.setId(2L);
//        league.setNationality(england);
//
//        league.addTeam(new Team("Manchester"));
//
//        leagues.add(league);
//
//        return leagues;
//}
//
//}
