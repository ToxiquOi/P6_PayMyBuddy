package fr.ocr.paymybuddy.dao;

import fr.ocr.paymybuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("select u from user u where lower(u.firstname) like lower(concat('%', :searchTerm, '%')) or lower(u.lastname) like lower(concat('%', :searchTerm, '%'))")
    List<UserEntity> search(@Param("searchTerm") String searchTerm);

    Optional<UserEntity> getUserEntityByEmail(@Email String email);
}
