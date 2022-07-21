package fr.ocr.paymybuddy.dao;

import fr.ocr.paymybuddy.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {

}
