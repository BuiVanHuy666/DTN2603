package com.buivanhuy.app.services;

import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.utils.helper.Validation;

import java.util.List;

public class AccountServiceImpl implements Manageable<Account> {
    private final ResourceRepository<Account> accountRepo;
    private final ResourceRepository<Department> departmentRepo;
    private final ResourceRepository<Position> positionRepo;

    public AccountServiceImpl(
            ResourceRepository<Account> accountRepo,
            ResourceRepository<Department> departmentRepo,
            ResourceRepository<Position> positionRepo
    ) {
        this.accountRepo = accountRepo;
        this.departmentRepo = departmentRepo;
        this.positionRepo = positionRepo;
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
        validateAccount(account);
        return accountRepo.store(account);
    }

    @Override
    public boolean update(Account account) {
        Validation.requireNotNull(account, "Dữ liệu tài khoản không được để trống!");

        Validation.validateEntityExists(account.getId(), accountRepo, "Tài khoản cần cập nhật");

        validateAccount(account);

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

    private void validateAccount(Account account) {
        Validation.requireNotNull(account, "Account không được để trống!");

        Validation.validateStringLength(account.getFullName(), 3, 100, "Họ và tên");

        Validation.validateStringLength(account.getUsername(), 3, 100, "Username");

        Validation.validateEmail(account.getEmail());

        Validation.requireNotNull(account.getGender(), "Giới tính không hợp lệ hoặc để trống!");

        Validation.validatePastOrPresent(account.getCreateDate(), "Ngày tạo");

        Validation.requireNotNull(account.getDepartment(), "Phòng ban không được để trống!");
        Validation.validateEntityExists(account.getDepartment().getId(), departmentRepo, "Phòng ban");

        Validation.requireNotNull(account.getPosition(), "Chức vụ không được để trống!");
        Validation.validateEntityExists(account.getPosition().getId(), positionRepo, "Chức vụ");
    }
}