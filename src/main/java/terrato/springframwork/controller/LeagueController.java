package terrato.springframwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import terrato.springframwork.domain.League;
import terrato.springframwork.service.LeagueService;

import javax.validation.Valid;

/**
 * Created by onenight on 2018-03-03.
 */
@Slf4j
@Controller
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    @RequestMapping({"", "/", "/index"})
    public String showLeagues(Model model){
        model.addAttribute("leagues", leagueService.getLeagues());

        return "index";
    }

    @GetMapping
    @RequestMapping("league/{id}/show")
    public String showLeagueById(@PathVariable String id, Model model) {
        model.addAttribute("league", leagueService.getLeagueById(Long.valueOf(id)));

        return "league/show";
    }

    @PostMapping
    @RequestMapping("league/new")
    public String newLeague(Model model) {
        model.addAttribute("league", new League());

        return "league/leagueform";
    }

    @GetMapping
    @RequestMapping("league/{id}/delete")
    public String deleteLeague(@PathVariable String id){
        leagueService.deleteLeagueById(Long.valueOf(id));
        return "redirect:/show";
    }

    @PostMapping
    @RequestMapping("league/{leagueId}/update")
    public String updateLeague(@PathVariable String leagueId, Model model){
        model.addAttribute("league", leagueService.getLeagueById(Long.valueOf(leagueId)));

        return "league/leagueform";
    }

    @PostMapping
    @RequestMapping("league")
    public String saveOrUpdate(@Valid @ModelAttribute("league") League league, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "league/leagueform";
        }

        League savedLeague = leagueService.saveLeague(league);

        return "redirect:/league/" + savedLeague.getId() + "/show";

    }


}
