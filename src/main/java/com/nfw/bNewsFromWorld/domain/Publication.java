package com.nfw.bNewsFromWorld.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class Publication {
    private String title;
    private String description;
    private String url;
    private String language;
    private LocalDate published_at;
    private String source;
    private List<Category> categories;
}
