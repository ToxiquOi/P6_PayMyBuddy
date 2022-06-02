package fr.ocr.paymybuddy.dao;

import fr.ocr.paymybuddy.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
