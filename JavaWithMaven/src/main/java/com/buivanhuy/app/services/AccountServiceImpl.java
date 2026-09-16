package com.buivanhuy.app.services;

import com.buivanhuy.entities.Account;
import com.buivanhuy.app.repositories.ResourceRepository;

import java.util.List;

public class AccountServiceImpl implements Manageable<Account> {
    private final ResourceRepository<Account> accountRepo;

    public AccountServiceImpl(ResourceRepository<Account> accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public List<Account> index() {
        return accountRepo.findAll();
    }

    @Override
    public Account show(int id) {
        return accountRepo.findById(id);
    }

    @Override
    public boolean store(Account account) {
        return accountRepo.store(account);
    }

    @Override
    public boolean update(Account account) {
        return accountRepo.update(account);
    }

    @Override
    public boolean destroy(int id) {
        Account account = accountRepo.findById(id);
        if (account == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id);
        }

        return accountRepo.delete(account);
    }
}