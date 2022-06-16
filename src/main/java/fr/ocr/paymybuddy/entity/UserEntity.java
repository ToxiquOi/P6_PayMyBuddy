package fr.ocr.paymybuddy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<UserEntity> contacts = new HashSet<>();

    private void setContacts(Set<UserEntity> contacts) {
        this.contacts = contacts;
    }

    public void removeContact(UserEntity user) {
        contacts.remove(user);
    }

    public void addContact(UserEntity user) {
        contacts.add(user);
    }
}
