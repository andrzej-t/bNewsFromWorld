package com.nfw.bNewsFromWorld.registration;

import com.nfw.bNewsFromWorld.appuser.AppUser;
import com.nfw.bNewsFromWorld.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.register(registrationRequest);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "/login")
    public void logIn(@RequestBody LoginRequest loginRequest) {
        appUserService.logUserIn(loginRequest);
    }

    @PostMapping(path = "/logout")
    public void logOut(@RequestBody LoginRequest loginRequest) {
        appUserService.logUserOut(loginRequest);
    }

}

