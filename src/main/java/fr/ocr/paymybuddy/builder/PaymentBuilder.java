package fr.ocr.paymybuddy.builder;

import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.UserEntity;

import java.util.Date;

public class PaymentBuilder extends AbstractBuilder<PaymentEntity> {

    public PaymentBuilder() {
        this.instance = new PaymentEntity();
        this.instance.setTransactionDate(new Date());
    }

    public PaymentBuilder addReceiver(UserEntity user) {
        this.instance.setReceiver(user.getWallet());
        return this;
    }

    public PaymentBuilder addSender(UserEntity user) {
        this.instance.setSender(user.getWallet());
        return this;
    }

    public PaymentBuilder addValue(int value) {
        this.instance.setValue(value);
        return this;
    }

    public PaymentBuilder addMessage(String msg) {
        this.instance.setMessage(msg);
        return this;
    }
}
