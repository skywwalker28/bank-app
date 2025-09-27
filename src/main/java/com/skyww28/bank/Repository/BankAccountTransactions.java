package com.skyww28.bank.Repository;

import com.skyww28.bank.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountTransactions extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount." +
            "id = :accountId ORDER BY t.timestamp DESC")
    List<Transaction> findByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount." +
            "id = :accountId OR t.toAccount.id = :accountId) AND t.type = :type ORDER BY t.timestamp DESC")
    List<Transaction> findByAccountIdAndType(@Param("accountId") Long accountId, @Param("type") String type);

}
