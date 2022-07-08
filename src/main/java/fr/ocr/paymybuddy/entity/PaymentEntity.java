package fr.ocr.paymybuddy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static org.hibernate.annotations.CascadeType.*;


@Getter
@Setter
@Entity(name = "payment")
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    private WalletEntity sender;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    private WalletEntity receiver;

    @NotNull
    private int value;

    @NotNull
    private Date transactionDate = new Date();

    @NotNull
    private String message;
}
