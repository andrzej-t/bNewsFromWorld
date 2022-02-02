package com.nfw.bNewsFromWorld.registration;

import com.nfw.bNewsFromWorld.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @PostMapping
    public String register(HttpServletRequest httpServletRequest) {
        return registrationService.register(httpServletRequest);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "/login")
    public void logIn(HttpServletRequest httpServletRequest) {
        appUserService.logUserIn(httpServletRequest);
    }

    @PostMapping(path = "/logout")
    public void logOut(HttpServletRequest httpServletRequest) {
        appUserService.logUserOut(httpServletRequest);
    }
}
