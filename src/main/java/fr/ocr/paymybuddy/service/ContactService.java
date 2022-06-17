package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.UserRepository;
import fr.ocr.paymybuddy.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Lazy
@Service
public class ContactService {

    private final UserRepository userRepository;
    private UserEntity currentUser;

    @Autowired
    public ContactService( UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        currentUser = authenticationService.getAuthenticatedUser().user();
    }

    public List<UserEntity> findUserNotInContact() {
        return this.userRepository.findUserEntityByContactsIsNotContainingAndIdIsNot(currentUser, currentUser.getId());
    }

    public Set<UserEntity> findCurrentUserContact() {
        return this.userRepository.getUserEntityByEmail(currentUser.getEmail()).get().getContacts();
    }

    public void addContactsToUser(Collection<UserEntity> contacts) {
        for(UserEntity contact : contacts) {
            if(!currentUser.getContacts().contains(contact) && !contact.getContacts().contains(currentUser)) {
                currentUser.addContact(contact);
                contact.addContact(currentUser);
            }
        }

        currentUser = userRepository.saveAndFlush(currentUser);
    }

    public void removeContactFromUser(Collection<UserEntity> contacts) {
        for(UserEntity contact : contacts) {
            currentUser.removeContact(contact);
            contact.removeContact(currentUser);
        }


        currentUser = userRepository.save(currentUser);
        userRepository.saveAllAndFlush(contacts);
    }
}
