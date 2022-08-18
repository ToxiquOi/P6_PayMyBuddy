package fr.ocr.paymybuddy.dao;

import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    Set<PaymentEntity> findAllBySenderIdIsOrReceiverIdIs(int id1, int id2);
    int countAllBySenderIdOrReceiverId(int id1, int id2);
}
