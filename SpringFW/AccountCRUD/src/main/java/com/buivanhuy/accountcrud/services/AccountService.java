package com.buivanhuy.accountcrud.services;

import com.buivanhuy.accountcrud.dto.*;
import com.buivanhuy.accountcrud.dto.request.AccountFilterRequest;
import com.buivanhuy.accountcrud.dto.request.AccountPOSTRequest;
import com.buivanhuy.accountcrud.dto.response.AccountResponse;
import com.buivanhuy.accountcrud.dto.request.AccountPUTRequest;
import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.entities.Department;
import com.buivanhuy.accountcrud.entities.Group;
import com.buivanhuy.accountcrud.enums.Gender;
import com.buivanhuy.accountcrud.exceptions.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final GroupRepository groupRepository;
    private final GroupAccountRepository groupAccountRepository;

    @Transactional
    public Account createAccount(AccountPOSTRequest req) {
        Account account = new Account();
        account.setUsername(req.username());
        account.setEmail(req.email());
        account.setFullname(req.fullname());
        account.setPassword(req.password());
        account.setGender(Gender.valueOf(req.gender()));
        account.setDepartment(
                departmentRepository.findById(req.departmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found"))
        );
        account.setPosition(
                positionRepository.findById(req.positionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Position not found"))
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Page<Account> findAll(AccountFilterRequest filter, Pageable pageable) {
        Specification<Account> filterSpec = AccountSpecification.filter(filter);
        return accountRepository.findAll(filterSpec, pageable);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findWithDetailsById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    /**
     * Update all information for a specific user and return the modified user.
     *
     * @param id  the ID of the user to update.
     * @param req the user DTO update request.
     * @return updated user.
     */
    @Transactional
    public Account update(Long id, AccountPUTRequest req) {
        Account account = this.findById(id);

        account.setUsername(req.username());
        account.setEmail(req.email());
        account.setFullname(req.fullname());
        account.setGender(Gender.valueOf(req.gender()));
        account.setDepartment(departmentRepository.findById(req.departmentId()).orElseThrow());
        account.setPosition(positionRepository.findById(req.positionId()).orElseThrow());

        return account;
    }

    /**
     * Update basic information (username, fullname, email, gender, departmentId, positionId) for the current user.
     *
     * @param username     username of user.
     * @param fullname     fullname of user.
     * @param email        email of user.
     * @param gender       gender of user.
     * @param departmentId department ID of user.
     * @param positionId   position ID of user.
     */
    @Transactional
    public Account update(
            Long id,
            String username,
            String email,
            String fullname,
            String gender,
            Long departmentId,
            Long positionId
    ) {
        Account account = this.findById(id);
        updateIfPresent(account::setUsername, username);
        updateIfPresent(account::setEmail, email);
        updateIfPresent(account::setFullname, fullname);
        updateIfPresent(account::setGender, Gender.valueOf(gender));
        updateIfPresent(account::setDepartment, departmentRepository.findById(departmentId).orElseThrow());
        updateIfPresent(account::setPosition, positionRepository.findById(positionId).orElseThrow());

        return account;
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account account = this.findById(id);

        for (Group g : groupRepository.findByCreator_Id(id)) {
            g.setCreator(null);
        }
        groupRepository.flush();
        accountRepository.delete(account);
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
