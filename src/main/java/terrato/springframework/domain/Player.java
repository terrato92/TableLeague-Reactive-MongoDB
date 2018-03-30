package terrato.springframework.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Created by onenight on 2018-03-02.
 */
@Data
@Document
public class Player {

    private String id = UUID.randomUUID().toString();

    private String name;

    private int age;

    private Team team;

    private Position position;

    private Nationality nationality;
}
