package com.nfw.bNewsFromWorld.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationDto {
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("url")
    String url;
    @JsonProperty("language")
    String language;
    @JsonProperty("published_at")
    LocalDate published_at;
    @JsonProperty("source")
    String source;
    @JsonProperty("categories")
    List<Category> categories;
}
