package fr.ocr.paymybuddy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @ManyToOne(optional = false)
    private UserEntity sender;

    @ManyToOne(optional = false)
    private UserEntity receiver;

    @Column(nullable = false)
    private Date transactionDate;

    @Column(nullable = false)
    private String message;
}
