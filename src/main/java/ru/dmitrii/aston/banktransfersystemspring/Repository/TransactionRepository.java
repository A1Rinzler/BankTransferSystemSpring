package ru.dmitrii.aston.banktransfersystemspring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;

import java.util.List;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 22.10.2026
 **/

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByFromAccount(int id);
}
