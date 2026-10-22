package com.buivanhuy.accountcrud.services.impl;

import com.buivanhuy.accountcrud.dto.*;
import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Group;
import com.buivanhuy.accountcrud.entities.Position;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.repositories.AccountRepository;
import com.buivanhuy.accountcrud.repositories.DepartmentRepository;
import com.buivanhuy.accountcrud.repositories.GroupAccountRepository;
import com.buivanhuy.accountcrud.repositories.GroupRepository;
import com.buivanhuy.accountcrud.repositories.PositionRepository;
import com.buivanhuy.accountcrud.specifications.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final GroupRepository groupRepository;
    private final GroupAccountRepository groupAccountRepository;

    @Transactional
    public Account create(AccountDTO req) {
        Account account = new Account();
        account.setUsername(req.username());
        account.setEmail(req.email());
        account.setFullname(req.fullname());
        account.setPassword(req.password());
        account.setGender(Gender.valueOf(req.gender()));
        account.setDepartment(findDepartment(req.departmentId()));
        account.setPosition(findPosition(req.positionId()));

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Page<Account> findAll(AccountFilterRequest filter, Pageable pageable) {
        Specification<Account> filterSpec = AccountSpecification.filter(filter);
        return accountRepository.findAll(filterSpec, pageable);
    }

    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        return AccountResponse.from(getAccount(id));
    }

    @Transactional
    public AccountResponse update(Long id, AccountUpdateRequest req) {
        Account account = getAccount(id);

        if (!account.getUsername().equals(req.username()) && accountRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username đã tồn tại");
        }
        if (!account.getEmail().equals(req.email()) && accountRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã tồn tại");
        }

        account.setUsername(req.username());
        account.setEmail(req.email());
        account.setFullname(req.fullname());
        account.setGender(req.gender());
        account.setDepartment(findDepartment(req.departmentId()));
        account.setPosition(findPosition(req.positionId()));

        return AccountResponse.from(account);
    }

    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> notFound("Account", id));

        for (Group g : groupRepository.findByCreator_Id(id)) {
            g.setCreator(null);
        }
        groupRepository.flush();

        accountRepository.delete(account);
    }

    @Transactional(readOnly = true)
    public List<GroupMembershipResponse> getGroupsOfAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw notFound("Account", accountId);
        }
        return groupAccountRepository.findByAccount_Id(accountId).stream()
                .map(GroupMembershipResponse::from)
                .toList();
    }

    private Account getAccount(Long id) {
        return accountRepository.findWithDetailsById(id)
                .orElseThrow(() -> notFound("Account", id));
    }

    private Department findDepartment(Long id) {
        if (id == null) return null;
        return departmentRepository.findById(id)
                .orElseThrow(() -> notFound("Department", id));
    }

    private Position findPosition(Long id) {
        if (id == null) return null;
        return positionRepository.findById(id)
                .orElseThrow(() -> notFound("Position", id));
    }

    private ResponseStatusException notFound(String what, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, what + " không tồn tại: id=" + id);
    }
}
