package terrato.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

import javax.validation.Valid;

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


    @GetMapping("team/{teamId}/player/new")
    public String newPlayer(@PathVariable String teamId, Model model){
        Team team = new Team();
        team.setId(teamId);
        Player player = new Player();
        player.setTeam(team);

        model.addAttribute("player", player);
        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());


        return "player/playerform";
    }

    @GetMapping("player/{playerId}/show")
    public String showPlayerById(@PathVariable String playerId, Model model) {
        model.addAttribute("player", playerService.getTeamPlayerById(playerId).block());

        return "player/show";
    }

    @GetMapping("player/{playerId}/update")
    public String updateTeamPlayerById(@PathVariable String playerId, Model model){
        model.addAttribute("player", playerService.getTeamPlayerById(playerId).block());

        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

        return "player/playerform";
    }

    @PostMapping("team/{teamId}/player")
    public String saveOrUpdatePlayer(@PathVariable String teamId, @Valid @ModelAttribute ("player") Player player,
                                     BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

            Team team = new Team();
            team.setId(teamId);
            player.setTeam(team);

            bindingResult.getAllErrors().forEach(ObjectError::toString);
            return "player/playerform";
        }

        Player playerUpdate = playerService.savePlayer(player, teamId).block();

        return "redirect:/player/" + playerUpdate.getId() + "/show";
    }



    @PostMapping
    @RequestMapping("player/{playerId}/delete")
    public String deletePlayer(@PathVariable String playerId) {

        Player player = playerService.getTeamPlayerById(playerId).block();
        String teamId = player.getTeam().getId();

        playerService.deletePlayerFromTeam(playerId);

        return "redirect:/team/" + teamId + "/show";
    }


}









