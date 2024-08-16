package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t.* FROM Transaction t INNER JOIN Account ac ON t.account_id = ac.id WHERE t.date > :start_date AND t.date < :end_date AND ac.client_id = :client_id",
    nativeQuery = true)
    List<Transaction> findByClientAndDateBetween(@Param("client_id") Long clientId, @Param("start_date") Date startDate, @Param("end_date") Date endDate);

}
