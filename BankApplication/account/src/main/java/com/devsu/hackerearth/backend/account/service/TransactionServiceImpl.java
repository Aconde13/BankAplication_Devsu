package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(transaction -> {
            return new TransactionDto(transaction.getId(), transaction.getDate(),
                    transaction.getType(), transaction.getAmount(), transaction.getBalance(),
                    transaction.getAccountId());
        }).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        Optional<Transaction> transactionOp = transactionRepository.findById(id);
        if (transactionOp.isPresent()) {
            Transaction transaction = transactionOp.get();
            return new TransactionDto(transaction.getId(), transaction.getDate(),
                    transaction.getType(), transaction.getAmount(), transaction.getBalance(),
                    transaction.getAccountId());
        }
        return null;
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setAccountId(transactionDto.getAccountId());
        transaction = transactionRepository.save(transaction);
        transactionDto.setId(transaction.getId());
        return transactionDto;
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
        List<Transaction> transactions = transactionRepository.findByClientAndDateBetween(clientId,
                dateTransactionStart, dateTransactionEnd);
        transactions.stream().map(transaction -> {
            AccountDto account = accountService.getById(transaction.getAccountId());
            return new BankStatementDto(transaction.getDate(), account.getClientId().toString(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), transaction.getType(), transaction.getAmount(), transaction.getBalance());
        });
        return null;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
