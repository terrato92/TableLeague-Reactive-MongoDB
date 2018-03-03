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
    private Long balance_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "team_balance",
    joinColumns = {@JoinColumn(name = "balance_id")},
    inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private Team team;

    private int wins = 0;

    private int defeats = 0;

    private int draws = 0;
}
