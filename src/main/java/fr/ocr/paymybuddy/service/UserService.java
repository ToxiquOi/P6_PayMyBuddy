package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;

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

    @Autowired
    public UserService(UserRepository userRepository, MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
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

        if (!passwordEncoder.matches(contact.getPassword(), encodedPass) && !contact.getPassword().equals(encodedPass)) {
            contact.setPassword(passwordEncoder.encode(contact.getPassword()));
        }

        userRepository.save(contact);
    }

}
