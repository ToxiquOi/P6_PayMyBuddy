package fr.ocr.paymybuddy.entity;

import fr.ocr.paymybuddy.exception.WalletBalanceException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

@Getter
@Setter
@Entity(name = "wallet")
@Table(name = "wallets")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @NotNull
    private UserEntity user;

    @Column(nullable = false)
    @Min(value = 0)
    private int balance = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Set<PaymentEntity> payments = new HashSet<>();

    @Transient
    public void addMoney(int value) {
        balance += value;
    }

    @Transient
    public void removeMoney(int value) throws WalletBalanceException {
        if(balance - value < 0) throw new WalletBalanceException();
        balance -= value;
    }

}
