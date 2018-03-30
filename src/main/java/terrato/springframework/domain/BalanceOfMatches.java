package terrato.springframework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by onenight on 2018-03-02.
 */
@Data
@Document
public class BalanceOfMatches {

    @Id
    private String id;

    private int wins = 0;

    private int defeats = 0;

    private int draws = 0;

}
