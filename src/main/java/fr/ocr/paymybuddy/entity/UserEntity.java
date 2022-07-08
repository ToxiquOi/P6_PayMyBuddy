package fr.ocr.paymybuddy.entity;

import fr.ocr.paymybuddy.builder.WalletBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

@Getter
@Setter
@Entity(name = "user")
@Table(name = "users")
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

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({MERGE, PERSIST, REFRESH})
    private WalletEntity wallet;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({MERGE, PERSIST, REFRESH})
    private Set<UserEntity> contacts = new HashSet<>();

    public UserEntity() {
        this.wallet = new WalletBuilder()
                .addUser(this)
                .build();
    }

    @Transient
    private void setContacts(Set<UserEntity> contacts) {
        this.contacts = contacts;
    }

    @Transient
    public void removeContact(UserEntity user) {
        this.contacts.removeIf(contact -> contact.getId() == user.getId());
    }

    @Transient
    public void addContact(UserEntity user) {
        this.contacts.add(user);
    }
}
