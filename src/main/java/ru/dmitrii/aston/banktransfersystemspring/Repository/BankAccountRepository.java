package ru.dmitrii.aston.banktransfersystemspring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
