package terrato.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

/**
 * Created by onenight on 2018-03-05.
 */
@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final NationalityService nationalityService;

    public PlayerController(PlayerService playerService, TeamService teamService, NationalityService nationalityService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.nationalityService = nationalityService;
    }


    @PostMapping
    @RequestMapping("team/{teamId}/player/new")
    public String newPlayer(@PathVariable Long teamId, Model model){
        Team team = new Team();
        team.setId(teamId);
        Player player = new Player();
        player.setTeam(team);

        model.addAttribute("player", player);
        model.addAttribute("nationalities", nationalityService.listAllNationalities());


        return "player/playerform";
    }

    @GetMapping
    @RequestMapping("team/{teamId}/player/{playerId}/show")
    public String showPlayerById(@PathVariable String teamID,
                                 @PathVariable String playerId, Model model) {
        model.addAttribute("player", playerService.getTeamPlayerById(Long.valueOf(playerId)));

        return "team/player/show";
    }

    @GetMapping
    @RequestMapping("player{playerId}/update")
    public String updateTeamPlayerById(
                                       @PathVariable Long playerId, Model model){
        model.addAttribute("player", playerService.getTeamPlayerById(playerId));

        model.addAttribute("nationalities", nationalityService.listAllNationalities());

        return "player/playerform";
    }

    @PostMapping
    @RequestMapping("team/{teamId}/player")
    public String saveOrUpdatePlayer(@ModelAttribute Player player, @PathVariable Long teamId) {

        Player playerUpdate = playerService.savePlayer(player, teamId);

        return "redirect:/team/" + teamId + "/player/" + playerUpdate.getId() + "/show";
    }



    @GetMapping
    @RequestMapping("team/{teamId}/player/{playerId}/delete")
    public String deletePlayer(@PathVariable String teamId, @PathVariable String playerId) {
        playerService.deletePlayerFromTeam(Long.valueOf(playerId), Long.valueOf(teamId));

        return "redirect:/team/" + teamId + "/player/show";
    }

//    @PostMapping
//    @RequestMapping("league/{leagueId}/team/{teamId}/player/new")
//    public String addPlayer(@PathVariable String leagueId, @PathVariable String teamId, Model model) {
////        Team team = teamService.findTeamByLeagueId(Long.valueOf(leagueId));
////
////        Player player = new Player();
////        player.setTeam(team);
////        model.addAttribute("player", player);
////
////        player.setNationality(new Nationality());
////        model.addAttribute("nationalList", nationalityService.listAllNationalities());
////
////        return "team/player/playerform";
//    }


}









