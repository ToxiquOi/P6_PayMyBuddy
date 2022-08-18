package fr.ocr.paymybuddy.dao;

import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.WalletEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PaginatedPaymentRepository extends PagingAndSortingRepository<PaymentEntity, Integer> {
    Slice<PaymentEntity> findAllBySenderOrReceiver(WalletEntity sender, WalletEntity receiver, Pageable pageable);
    Slice<PaymentEntity> findAllBySenderIdIsOrReceiverIdIsOrderByTransactionDateDesc(int id1, int id2, Pageable pageable);
}
