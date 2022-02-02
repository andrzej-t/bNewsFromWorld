package com.nfw.bNewsFromWorld.apiNews;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
public class NewsConfig {
    @Value("${news.api.endpoint.prod}")
    private String newsApiEndpoint;

    @Value("${news.api.token}")
    private String token;
}
