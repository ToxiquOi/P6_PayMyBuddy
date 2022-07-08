package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.WalletRepository;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.WalletEntity;
import fr.ocr.paymybuddy.exception.WalletBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void addPaymentToWalletHistory(PaymentEntity payment, WalletEntity wallet) {
        wallet.getPayments().add(payment);
    }

    public void addMoneyToWallet(int money, WalletEntity wallet) {
        wallet.addMoney(money);
    }

    public void removeMoneyFromWallet(int money, WalletEntity wallet) throws WalletBalanceException {
        wallet.removeMoney(money);
    }

    public void save(WalletEntity wallet) {
        walletRepository.save(wallet);
    }
}
