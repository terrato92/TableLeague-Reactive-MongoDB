package terrato.springframework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import terrato.springframework.domain.Power;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onenight on 2018-03-19.
 */
@Data
@NoArgsConstructor
public class TeamDto {


    private String id;
    private String team;
    private String leagueId;

    private NationalityDto nationality;

    private List<PlayerDto> players = new ArrayList<>();

    private BalanceOfMatchesDto balanceOfMatches = new BalanceOfMatchesDto();

    private Power power;

    private Integer points;




}
