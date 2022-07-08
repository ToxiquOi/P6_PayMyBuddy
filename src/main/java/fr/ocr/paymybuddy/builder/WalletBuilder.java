package fr.ocr.paymybuddy.builder;

import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.entity.WalletEntity;

public class WalletBuilder extends AbstractBuilder<WalletEntity> {
    public WalletBuilder() {
        this.instance = new WalletEntity();
    }

    public WalletBuilder addUser(UserEntity user) {
        this.instance.setUser(user);
        return this;
    }
}
