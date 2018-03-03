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
    public String showLeague(Model model){
        model.addAttribute("leagues", leagueService.getLeagues());

        return "league/index";
    }

    @GetMapping
    @RequestMapping("leagues/{id}/show")
    public String showLeagueById(@PathVariable String id, Model model) {
        model.addAttribute("league", leagueService.getLeagueById(Long.valueOf(id)));

        return "league/" + id + "show";
    }

    @RequestMapping("league/new")
    public String newLeague(Model model) {
        model.addAttribute("league", leagueService.addLeague(new League()));

        return "league/show";
    }

    @GetMapping
    @RequestMapping("league/{id}/delete")
    public String deleteLeague(@PathVariable String id){
        leagueService.deleteLeagueById(Long.valueOf(id));
        return "redirect:/show";
    }

    @PostMapping
    @RequestMapping("league/{id}/update")
    public String saveOrUpdateLeague(@Valid @ModelAttribute ("league") League league, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "league/leagueform";
        }

        League league1 = leagueService.addLeague(league);

        return "league/" + league1.getId() + "/show";

    }


}
