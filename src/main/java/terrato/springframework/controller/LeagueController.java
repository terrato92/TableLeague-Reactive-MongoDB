package terrato.springframework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import terrato.springframework.domain.League;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.TeamService;
import terrato.springframework.service.implementation.NationalityServiceImpl;

import javax.validation.Valid;

/**
 * Created by onenight on 2018-03-03.
 */
@Slf4j
@Controller
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private NationalityServiceImpl nationalityService;

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
        model.addAttribute("teams", teamService.findTeamByLeagueId(Long.valueOf(id)));

        return "league/show";
    }

    @PostMapping
    @RequestMapping("league/new")
    public String newLeague(Model model) {
        model.addAttribute("league", new League());

        model.addAttribute("nationalities", nationalityService.listAllNationalities());

        return "league/leagueform";
    }

    @PostMapping
    @RequestMapping("league/{id}/delete")
    public String deleteLeague(@PathVariable String id){
        leagueService.deleteLeagueById(Long.valueOf(id));
        return "redirect:/";
    }

    @PutMapping
    @RequestMapping("league/{leagueId}/update")
    public String updateLeague(@PathVariable String leagueId, Model model){
        model.addAttribute("league", leagueService.getLeagueById(Long.valueOf(leagueId)));

        model.addAttribute("nationalities", nationalityService.listAllNationalities());
        return "league/leagueform";
    }

    @PostMapping
    @RequestMapping("league")
    public String saveOrUpdate(@Valid @ModelAttribute League league, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "league/leagueform";
        }

        League savedLeague = leagueService.saveLeague(league);

        return "redirect:/";

    }


}
