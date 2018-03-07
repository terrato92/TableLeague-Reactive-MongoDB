package terrato.springframwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import terrato.springframwork.domain.Nationality;
import terrato.springframwork.domain.Player;
import terrato.springframwork.domain.Team;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.PlayerService;
import terrato.springframwork.service.TeamService;

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

    @GetMapping
    @RequestMapping("team/{teamId}/show")
    public String showTeamPlayersById(@PathVariable String teamId, Model model) {
        model.addAttribute("teams", playerService.getPlayersFromTeam(Long.valueOf(teamId)));

        return "team/players/show";
    }

    @GetMapping
    @RequestMapping("team/{teamId}/player/{playerId}/show")
    public String showPlayerById(@PathVariable String teamID,
                                 @PathVariable String playerId, Model model) {
        model.addAttribute("player", playerService.getTeamPlayerById(Long.valueOf(teamID), Long.valueOf(playerId)));

        return "team/player/show";
    }

    @PostMapping
    @RequestMapping("team/{teamId}/player")
    public String updatePlayer(@ModelAttribute("palyer") Player player) {

        Player playerUpdate = playerService.getTeamPlayerById(player.getTeam().getId(), player.getId());

        return "redirect:/team/" + playerUpdate.getTeam().getId() + "/player/" + playerUpdate.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("team/{teamId}/player/{playerId}")
    public String deletePlayer(@PathVariable String teamId, @PathVariable String playerId) {
        playerService.deletePlayerFromTeam(Long.valueOf(playerId), Long.valueOf(teamId));

        return "redirect:/team/" + teamId + "/players";
    }

    @PostMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/player/new")
    public String addPlayer(@PathVariable String leagueId, @PathVariable String teamId, Model model) {
        Team team = teamService.findTeamById(Long.valueOf(leagueId), Long.valueOf(teamId));

        Player player = new Player();
        player.setTeam(team);
        model.addAttribute("player", player);

        player.setState(new Nationality());
        model.addAttribute("nationalList", nationalityService.listAllStates());

        return "team/player/playerform";
    }


}









