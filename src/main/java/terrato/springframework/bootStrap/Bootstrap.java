package terrato.springframework.bootStrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import terrato.springframework.domain.*;
import terrato.springframework.repository.*;
import terrato.springframework.repository.reactiveRepository.*;

/**
 * Created by onenight on 2018-03-08.
 */
@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    LeagueReactiveRepository leagueReactiveRepository;

    @Autowired
    TeamReactiveRepository teamReactiveRepository;

    @Autowired
    NationalityReactiveRepository nationalityReactiveRepository;

    @Autowired
    PlayerReactiveRepository playerReactiveRepository;

    @Autowired
    BalanceOfMatchesReactiveRepository balanceOfMatchesReactiveRepository;

    PlayerRepository playerRepository;
    BalanceOfMatchesRepository balanceOfMatchesRepository;
    NationalityRepository nationalityRepository;
    LeagueRepository leagueRepository;
    TeamRepository teamRepository;

    public Bootstrap(PlayerRepository playerRepository,
                     BalanceOfMatchesRepository balanceOfMatchesRepository,
                     NationalityRepository nationalityRepository,
                     LeagueRepository leagueRepository,
                     TeamRepository teamRepository) {

        this.playerRepository = playerRepository;
        this.balanceOfMatchesRepository = balanceOfMatchesRepository;
        this.nationalityRepository = nationalityRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        League league = new League();
        league.setName("Serie A");


        Nationality poland = new Nationality();
        poland.setNationality("England");
        nationalityRepository.save(poland);

        Nationality poland1 = new Nationality();
        poland1.setNationality("Polska");
        nationalityRepository.save(poland1);

        Player player1 = new Player();
        player1.setNationality(poland);
        player1.setName("Ronaldo");
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setNationality(poland);
        player2.setName("Messi");
        playerRepository.save(player2);


//        Team team = new Team();
//        team.setName("Napoli");
//        team.addPlayer(player1);
//        team.addPlayer(player2);
//      teamRepository.save(team);

//        league.addTeam(team);
        leagueRepository.save(league);



        log.error("####################");

        log.error("Nationality Count: " + nationalityReactiveRepository.count().block().toString());

        log.error("####################");

        log.error("Player Count: " + leagueReactiveRepository.count().block().toString());

        log.error("####################");

        log.error("Teams Count: " + teamReactiveRepository.count().block().toString());

        log.error("####################");

        log.error("Count: " + playerReactiveRepository.count().block().toString());




    }


    //    @Override
//    public void run(String... args) throws Exception {
//
//       League league= leagueReactiveRepository.save(League
//               .builder()
//               .id("Serie A")
//               .name("Italian")
//               .difficulty(Difficulty.EASY).build()).block();

//        teamReactiveRepository.save(Team.builder()
//                .id("sad22")
//                .name("sadownik22")
//                .league(league)
//                .build())
//                .block();
//
//
//
//        balanceOfMatchesRepository.save(BalanceOfMatches
//                .builder()
//                .defeats(0).draws(2).wins(3).build());
//    }
}