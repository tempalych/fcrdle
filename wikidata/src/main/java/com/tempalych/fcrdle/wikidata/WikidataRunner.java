package com.tempalych.fcrdle.wikidata;

import com.tempalych.fcrdle.server.model.FootballClub;
import com.tempalych.fcrdle.server.model.WikiFootballClub;
import com.tempalych.fcrdle.server.repository.FootballClubRepository;
import com.tempalych.fcrdle.server.repository.WikiFootballClubRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class WikidataRunner {

    private FootballClubRepository footballClubRepository;
    private WikiFootballClubRepository wikiFootballClubRepository;

    private ApplicationContext applicationContext;

    @Bean
    public CommandLineRunner main() {
        return args -> {
            if (wikiFootballClubRepository.findAll().iterator().hasNext()) {
                System.out.println("Wiki list already exists");
                compareDbWithWiki();
            } else {
                try {
                    System.out.println("Getting Wiki list of clubs from wikidata...");
                    String queryString = Files.readString(Path.of(
                            "wikidata/src/main/resources/query.sparql"));

                    String url = "https://query.wikidata.org";
                    WebClient wikidataClient = WebClient.builder()
                            .baseUrl(url)
                            .filter(logRequest())
                            .build();

                    Mono<WikidataResponse> response = wikidataClient.post()
                            .uri("/sparql")
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                            .header(HttpHeaders.CONNECTION, "keep-alive")
                            .body(BodyInserters
                                    .fromFormData("query", queryString)
                                    .with("format", "json"))
                            .retrieve()
                            .bodyToMono(WikidataResponse.class);

                    response.subscribe(el -> {
                        for (WikidataResponse.Binding res : el.getResults().getBindings()) {

                            String[] location = res.getCoordinates().getValue().split(" ");
                            String lat = location[0].substring(6);
                            String lon = location[1].substring(0, location[1].length() - 1);

                            WikiFootballClub wikiFootballClub = new WikiFootballClub()
                                    .setName(res.getClubLabel().getValue())
                                    .setWikiClubId(res.getClub().getValue())
                                    .setLeague(res.getLeagueLabel().getValue())
                                    .setCity(res.getCityLabel().getValue())
                                    .setStadiumName(res.getStadiumLabel().getValue())
                                    .setStadiumCapacity(Integer.parseInt(res.getCapacity().getValue()))
                                    .setLocationLatitude(Double.parseDouble(lat))
                                    .setLocationLongitude(Double.parseDouble(lon));

                            wikiFootballClubRepository.save(wikiFootballClub);
                        }
                        compareDbWithWiki();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SpringApplication.exit(applicationContext, () -> 0);
        };
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(rq -> {
            System.out.println(rq.url());
            return Mono.just(rq);
        });
    }

    private void compareDbWithWiki() {
        List<WikiFootballClub> wikiList = new ArrayList<>();
        List<FootballClub> dbList = new ArrayList<>();

        wikiFootballClubRepository.findAll().forEach(wikiList::add);
        footballClubRepository.findAll().forEach(dbList::add);

        Map<String, String> leagueWikiDatabase = new HashMap<>();
        leagueWikiDatabase.put("Liga Portugal", "Primera Liga");
        leagueWikiDatabase.put("Bundesliga", "Bundesliga");
        leagueWikiDatabase.put("Ligue 1", "Lugue 1");
        leagueWikiDatabase.put("Premier League", "Premier League");
        leagueWikiDatabase.put("Russian Premier League", "Russian Premiere-League");
        leagueWikiDatabase.put("Serie A", "Serie A");
        leagueWikiDatabase.put("La Liga", "La Liga");

        List<FootballClubCompare> compares = new ArrayList<>();

        for (WikiFootballClub wikiFc: wikiList) {
            List<FootballClub> dbListFilteredByLeague = dbList.stream().filter(el ->
                    el.getLeague().equals(leagueWikiDatabase.get(wikiFc.getLeague())))
                    .collect(Collectors.toList());

            FootballClubCompare compare = new FootballClubCompare().setWikiFootballClub(wikiFc);

            // First, try to find db-club by name with similarity > 60%
            for (FootballClub dbFc: dbListFilteredByLeague) {
                SimilarityType similarity =
                        StringCompareUtils.compareStrings(wikiFc.getName(), dbFc.getName());
                if (similarity == SimilarityType.SIMILAR ||
                        similarity == SimilarityType.EQUALS) {
                    compare.setDbFootballClub(dbFc);
                    compare.setSimilarityType(similarity);
                    break;
                }
            }

            // If could not find db-club, try to select by minimum Levenshtein distance
            if (compare.getDbFootballClub() == null) {
                int minLevenshteinDistance = Integer.MAX_VALUE;
                for (FootballClub dbFc: dbListFilteredByLeague) {
                    int levenshteinDistance = StringCompareUtils.calculateLevenshteinDistance(
                            wikiFc.getName(), dbFc.getName());
                    if (levenshteinDistance < minLevenshteinDistance) {
                        minLevenshteinDistance = levenshteinDistance;
                        compare.setLevenshteinDistance(levenshteinDistance);
                        compare.setDbFootballClub(dbFc);
                    }
                }
            }
            compares.add(compare);
        }

        for (FootballClubCompare fcCompare: compares) {
            StringBuilder out = new StringBuilder();
            if (fcCompare.getWikiFootballClub() != null) {
                out.append(fcCompare.getWikiFootballClub().getLeague());
                out.append(": ");
                out.append(fcCompare.getWikiFootballClub().getName());
            } else {
                out.append("No club");
            }
            out.append(" >> ");
            if (fcCompare.getDbFootballClub() != null) {
                out.append(fcCompare.getDbFootballClub().getName());
            } else {
                out.append("No club");
            }
            out.append(" :: ");
            if (fcCompare.getSimilarityType() != null) {
                out.append(fcCompare.getSimilarityType());
            } else {
                out.append(fcCompare.getLevenshteinDistance());
            }
            System.out.println(out);
        }
        System.out.println("=== " + compares.size() + " ===");
    }

}
