package com.nfw.bNewsFromWorld.registration;

import com.nfw.bNewsFromWorld.appuser.AppUserRepository;
import com.nfw.bNewsFromWorld.exception.EmptyFieldException;
import com.nfw.bNewsFromWorld.exception.InvalidCharactersException;
import com.nfw.bNewsFromWorld.exception.UserExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationRequestValidator implements Predicate<RegistrationRequest> {

    private final AppUserRepository appUserRepository;

    @Override
    public boolean test(RegistrationRequest registrationRequest) {

        String name = registrationRequest.getName();
        String surname = registrationRequest.getSurname();
        String email = registrationRequest.getEmail();
        String password = registrationRequest.getPassword();

        final Pattern pattern = Pattern.compile("^[A-Za-z0-9@.!_-]{1,30}$");

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new EmptyFieldException("");
        } else if (!pattern.matcher(name).matches() || !pattern.matcher(surname).matches() || !pattern.matcher(email).matches() || !pattern.matcher(password).matches()) {
            throw new InvalidCharactersException("");
        } else if (appUserRepository.findByEmail(email).isPresent()) {
            throw new UserExistsException("");
        }
        return true;
    }
}
