package com.nfw.bNewsFromWorld.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping(path = "/isLocked", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isLoggedInCheck(HttpServletRequest request) {

        return appUserService.isUserLocked(request);
    }
}
