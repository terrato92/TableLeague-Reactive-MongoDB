package terrato.springframwork.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by onenight on 2018-03-02.
 */
@Data
@Table
@Entity
public class BalanceOfMatches {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int wins = 0;

//    @Column(table = "league", name = "defeat")
    private int defeats = 0;

//    @Column(table = "league", name = "draws")
    private int draws = 0;
}
