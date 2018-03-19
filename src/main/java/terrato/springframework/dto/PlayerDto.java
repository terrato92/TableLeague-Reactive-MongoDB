package terrato.springframework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by onenight on 2018-03-19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto {

}
