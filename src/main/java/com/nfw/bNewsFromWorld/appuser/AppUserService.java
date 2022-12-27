package com.nfw.bNewsFromWorld.appuser;

import com.nfw.bNewsFromWorld.exception.EmptyFieldException;
import com.nfw.bNewsFromWorld.exception.InvalidCharactersException;
import com.nfw.bNewsFromWorld.exception.WrongCredentialsException;
import com.nfw.bNewsFromWorld.registration.token.ConfirmationToken;
import com.nfw.bNewsFromWorld.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public void unlockAppUser(String email) {
        appUserRepository.unlockAppUser(email);
    }

    public void disableAppUser(String email) {
        appUserRepository.disableAppUser(email);
    }

    public void lockAppUser(String email) {
        appUserRepository.lockAppUser(email);
    }

    public void logUserIn(HttpServletRequest httpServletRequest) {

        String email = httpServletRequest.getHeader("email");
        String password = httpServletRequest.getHeader("password");

        final Pattern pattern = Pattern.compile("^[A-Za-z0-9@.!_-]{1,30}$");

        if (email.isEmpty() || password.isEmpty()) {
            throw new EmptyFieldException("");
        } else if (!pattern.matcher(email).matches() || !pattern.matcher(password).matches()) {
            throw new InvalidCharactersException("");
        } else {
            try {
                if (loadUserByUsername(email).isEnabled() && bCryptPasswordEncoder.matches(password, loadUserByUsername(email).getPassword())) {
                    unlockAppUser(email);
                }
            } catch (RuntimeException e) {
                throw new WrongCredentialsException("");
            }
        }
    }

    public void logUserOut(HttpServletRequest httpServletRequest) {
        lockAppUser(httpServletRequest.getHeader("email"));
    }

    public Boolean isUserLocked(HttpServletRequest httpServletRequest) {

        String email = httpServletRequest.getHeader("email");
        String password = httpServletRequest.getHeader("password");

        if (appUserRepository.findByEmail(email).isPresent() && bCryptPasswordEncoder.matches(password, loadUserByUsername(email).getPassword())) {
            return appUserRepository.findByEmail(email).get().getLocked();
        } else return Boolean.TRUE;
    }

    public void removeNotEnabledAccounts() {
        appUserRepository.deleteNotEnabled();
    }
}
