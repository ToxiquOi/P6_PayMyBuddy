package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.PaymentRepository;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.exception.WalletBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Lazy
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationService authenticationService;
    private final WalletService walletService;

    public UserEntity getCurrentUser() {
        return authenticationService.getAuthenticatedUser().user();
    }

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, AuthenticationService authenticationService, WalletService walletService) {
        this.paymentRepository = paymentRepository;
        this.authenticationService = authenticationService;
        this.walletService = walletService;
    }

    public Set<PaymentEntity> getCurrentUserPayment() {
        UserEntity currentUser = getCurrentUser();
        return paymentRepository.findAllBySenderIdIsOrReceiverIdIs(currentUser.getId(), currentUser.getId());
    }

    public void proceedToPayment(PaymentEntity payment) throws WalletBalanceException {
        if (payment == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }

        walletService.removeMoneyFromWallet(payment.getValue(), payment.getSender());
        walletService.addMoneyToWallet(payment.getValue(), payment.getReceiver());

        walletService.addPaymentToWalletHistory(payment, payment.getReceiver());
        walletService.addPaymentToWalletHistory(payment, payment.getSender());

        paymentRepository.save(payment);
        walletService.save(payment.getSender());
        walletService.save(payment.getReceiver());
    }

    private void save(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }

        paymentRepository.save(paymentEntity);
    }
}
