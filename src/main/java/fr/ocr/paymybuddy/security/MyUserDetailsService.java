package fr.ocr.paymybuddy.security;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.getUserEntityByEmail(email);

        if(userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(userOpt.get());
    }
}
