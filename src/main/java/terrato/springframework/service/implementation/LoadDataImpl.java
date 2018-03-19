package terrato.springframework.service.implementation;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.TeamDto;
import terrato.springframework.service.LoadData;

import java.util.Set;

/**
 * Created by onenight on 2018-03-19.
 */
@Service
public class LoadDataImpl implements LoadData {

    @Override
    public Set<Team> loadLeagues() {
        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-Mashape-Key", "2tuWc5KZs6mshldLLj1izNPEZZy0p1lVYjjjsnDhgTLUIAYraS");
//        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(headers);




        RestTemplate restTemplate = new RestTemplate();
        TeamDto teamDto = restTemplate.getForObject(
                "https://sportsop-soccer-sports-open-data-v1.p.mashape.com/v1/leagues",
                TeamDto.class);

        return null;
    }
}