package com.nfw.bNewsFromWorld.apiNews;

import com.nfw.bNewsFromWorld.domain.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service
@NoArgsConstructor
@Getter
@Slf4j
public class NewsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private NewsConfig newsConfig;
    @Autowired
    public NewsClient(NewsConfig newsConfig) {
        this.newsConfig = newsConfig;
    }

    public Data getPublications(String search, String language, String categories, Integer page, String published_before) {
        URI url = UriComponentsBuilder.fromHttpUrl(newsConfig.getNewsApiEndpoint() + "/all")
                .queryParam("api_token", newsConfig.getToken())
                .queryParam("search", search)
                .queryParam("language", language)
                .queryParam("categories",categories)
                .queryParam("limit",5)
                .queryParam("page",page)
                .queryParam("published_before",published_before)
                .build()
                .encode()
                .toUri();
        try {
            Data newsResponse = restTemplate.getForObject(url, Data.class);
            return Optional.ofNullable(newsResponse)
                    .orElseThrow();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new Data();
        }
    }
}
