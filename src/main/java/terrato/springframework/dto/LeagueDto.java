package terrato.springframework.dto;

import lombok.Getter;
import lombok.Setter;
import terrato.springframework.domain.Difficulty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onenight on 2018-03-19.
 */
@Getter
@Setter
public class LeagueDto {

    private String id;

    private Difficulty difficulty;

    private List<TeamDto> teams = new ArrayList<>();

    @NotNull
    private NationalityDto nationality;

    @NotBlank
    private String name;

}
