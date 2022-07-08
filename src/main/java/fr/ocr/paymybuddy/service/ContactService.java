package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Lazy
@Service
public class ContactService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;


    @Autowired
    public ContactService( UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    private UserEntity getCurrentUser() {
        return userRepository.findById(authenticationService.getAuthenticatedUser().user().getId()).get();
    }

    public List<UserEntity> findUserNotInContact() {
        UserEntity currentUser = getCurrentUser();
        return this.userRepository.findUserEntityByContactsIsNotContainingAndIdIsNot(currentUser, currentUser.getId());
    }

    public Set<UserEntity> findCurrentUserContact() {
        return getCurrentUser().getContacts();
    }

    public void addContactsToUser(Collection<UserEntity> contacts) {
        UserEntity currentUser = getCurrentUser();
        for(UserEntity contact : contacts) {
            if(!currentUser.getContacts().contains(contact) && !contact.getContacts().contains(currentUser)) {
                currentUser.addContact(contact);
                contact.addContact(currentUser);
            }
        }

        userRepository.saveAndFlush(currentUser);
    }

    public void removeContactFromUser(Collection<UserEntity> contacts) {
        UserEntity currentUser = getCurrentUser();
        for(UserEntity contact : contacts) {
            contact.removeContact(currentUser);
            currentUser.removeContact(contact);
        }

        userRepository.saveAll(contacts);
        userRepository.saveAndFlush(currentUser);
    }
}
