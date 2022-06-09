package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ContactService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public ContactService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public List<UserEntity> findUserNotInContact() {
        UserEntity authUser = authenticationService.getAuthenticatedUser().user();
        return this.userRepository.findUserEntityByContactsIsNotContainingAndIdIsNot(authUser, authUser.getId());
    }

    public List<UserEntity> findCurrentUserContact() {
        UserEntity authUser = authenticationService.getAuthenticatedUser().user();
        return this.userRepository.findById(authUser.getId()).get().getContacts();
    }

    public void addContactsToUser(Collection<UserEntity> contacts) {
        UserEntity authUser = authenticationService.getAuthenticatedUser().user();
        Hibernate.initialize(authUser.getContacts());

        if(authUser.getContacts() == null) {
            authUser.setContacts(new ArrayList<>());
        }

        for(UserEntity contact : contacts) {
            if(!authUser.getContacts().contains(contact) && !contact.getContacts().contains(authUser)) {
                contact.getContacts().add(authUser);
                authUser.getContacts().add(contact);
            }
        };

        userRepository.save(authUser);
        userRepository.saveAll(contacts);
    }
}
