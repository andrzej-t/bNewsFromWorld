package com.nfw.bNewsFromWorld.appuser;

import com.nfw.bNewsFromWorld.registration.LoginRequest;
import com.nfw.bNewsFromWorld.registration.RegistrationRequest;
import com.nfw.bNewsFromWorld.registration.token.ConfirmationToken;
import com.nfw.bNewsFromWorld.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {

        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }

    public void unlockAppUser(String email) { appUserRepository.unlockAppUser(email); }

    public void disableAppUser(String email) {
        appUserRepository.disableAppUser(email);
    }

    public void lockAppUser(String email) { appUserRepository.lockAppUser(email); }

    public void logUserIn(LoginRequest loginRequest) {
        if (loadUserByUsername(loginRequest.getEmail()).isEnabled() && bCryptPasswordEncoder.matches(loginRequest.getPassword(), loadUserByUsername(loginRequest.getEmail()).getPassword())) {
            unlockAppUser(loginRequest.getEmail());
        }
    }

    public void logUserOut(LoginRequest loginRequest) {
        lockAppUser(loginRequest.getEmail());
    }

}

