package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;

import fr.ocr.paymybuddy.exception.UserPasswordException;
import fr.ocr.paymybuddy.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserService(UserRepository userRepository, MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    public UserEntity getCurrentUser() {
        return userRepository.findById(authenticationService.getAuthenticatedUser().user().getId()).get();
    }


    public List<UserEntity> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(stringFilter);
        }
    }

    public long countUsers() {
        return userRepository.count();
    }

    public void deleteUser(UserEntity contact) {
        userRepository.delete(contact);
    }

    public void updatePassword(UserEntity user) throws UserPasswordException {
        if(passwordEncoder.matches(user.getPassword(), userDetailsService.loadUserByUsername(user.getEmail()).getPassword())) {
            throw new UserPasswordException("New password cannot be equal to the previous");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public void saveUser(UserEntity contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }

        String encodedPass = "";
        try {
            // Si l'utilisateur n'est pas trouver, c'est qu'il faut encoder son password
            encodedPass = userDetailsService.loadUserByUsername(contact.getEmail()).getPassword();
        } catch (UsernameNotFoundException ex) {
            contact.setPassword(passwordEncoder.encode(contact.getPassword()));
        }

        userRepository.save(contact);
    }

}
