package terrato.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


/**
 * Created by onenight on 2018-03-05.
 */
@Getter
@Setter
@Document
public class Nationality {

    @Id
    private String id = UUID.randomUUID().toString();

    private String nationality;

}
