package fr.ocr.paymybuddy.entity;


import fr.ocr.paymybuddy.security.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private UserRole role;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany
    private List<UserEntity> contacts = new ArrayList<>();
}
