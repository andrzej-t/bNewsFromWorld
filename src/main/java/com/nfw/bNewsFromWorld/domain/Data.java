package com.nfw.bNewsFromWorld.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("data")
    List<PublicationDto> publicationDtoList;
}
