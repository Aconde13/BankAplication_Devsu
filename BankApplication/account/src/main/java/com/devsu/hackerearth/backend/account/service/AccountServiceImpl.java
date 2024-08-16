package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

import com.devsu.hackerearth.backend.account.model.Account;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
        List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map(account -> {
            return new AccountDto(account.getId(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), account.getClientId());
        }).collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        Optional<Account> accountOp = accountRepository.findById(id);
        if(accountOp.isPresent()){
            Account account = accountOp.get();
            return new AccountDto(account.getId(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), account.getClientId());
        }
		return null;
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        Account account = new Account();
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());
        account = accountRepository.save(account);
        accountDto.setId(account.getId());
		return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
		Optional<Account> accountOp = accountRepository.findById(accountDto.getId());
        if(accountOp.isPresent()){
            Account account = accountOp.get();

            account.setNumber(accountDto.getNumber());
            account.setType(accountDto.getType());
            account.setInitialAmount(accountDto.getInitialAmount());
            account.setActive(accountDto.isActive());
            account.setClientId(accountDto.getClientId());
            account = accountRepository.save(account);

            return new AccountDto(account.getId(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), account.getClientId());
        }
		return null;
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
		Optional<Account> accountOp = accountRepository.findById(id);
        if(accountOp.isPresent()){
            Account account = accountOp.get();

            account.setActive(partialAccountDto.isActive());
            account = accountRepository.save(account);

            return new AccountDto(account.getId(), account.getNumber(), account.getType(),
            account.getInitialAmount(), account.isActive(), account.getClientId());
        }
		return null;
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
        accountRepository.deleteById(id);
    }
    
}
