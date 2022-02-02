package com.nfw.bNewsFromWorld.controllers;

import com.nfw.bNewsFromWorld.apiNews.NewsClient;
import com.nfw.bNewsFromWorld.appuser.AppUserService;
import com.nfw.bNewsFromWorld.domain.PublicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsClient newsClient;
    private final AppUserService appUserService;

    @GetMapping(value = "/results")
    public List<PublicationDto> getAllPublications(@RequestParam String searchedText, @RequestParam String language, @RequestParam String category, @RequestParam Integer page, @RequestParam String published_before, HttpServletRequest httpServletRequest) {

        if (!appUserService.isUserLocked(httpServletRequest)) {
            return newsClient.getPublications(searchedText, language, category, page, published_before).getPublicationDtoList();
        } else {
            return Collections.emptyList();
        }
    }
}
