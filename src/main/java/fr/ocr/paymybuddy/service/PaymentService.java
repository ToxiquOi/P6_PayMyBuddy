package fr.ocr.paymybuddy.service;

import fr.ocr.paymybuddy.dao.PaginatedPaymentRepository;
import fr.ocr.paymybuddy.dao.PaymentRepository;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.exception.WalletBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationService authenticationService;
    private final WalletService walletService;
    private final PaginatedPaymentRepository paginatedPaymentRepository;

    public UserEntity getCurrentUser() {
        return authenticationService.getAuthenticatedUser().user();
    }

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaginatedPaymentRepository paginatedPaymentRepository, AuthenticationService authenticationService, WalletService walletService) {
        this.paymentRepository = paymentRepository;
        this.authenticationService = authenticationService;
        this.walletService = walletService;
        this.paginatedPaymentRepository = paginatedPaymentRepository;
    }

    public Slice<PaymentEntity> getCurrentUserPayment(int page) {
        UserEntity currentUser = getCurrentUser();
        return paginatedPaymentRepository.findAllBySenderIdIsOrReceiverIdIsOrderByTransactionDateDesc(
                currentUser.getId(),
                currentUser.getId(),
                PageRequest.of(page, 4));
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

    public int countPaymentOfUser(int walletId) {
        return paymentRepository.countAllBySenderIdOrReceiverId(walletId, walletId);
    }

    private void save(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }

        paymentRepository.save(paymentEntity);
    }
}
