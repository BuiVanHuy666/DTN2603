package com.buivanhuy.accountcrud.controllers;

import com.buivanhuy.accountcrud.dto.AccountCreateRequest;
import com.buivanhuy.accountcrud.dto.AccountResponse;
import com.buivanhuy.accountcrud.dto.AccountUpdateRequest;
import com.buivanhuy.accountcrud.dto.GroupMembershipResponse;
import com.buivanhuy.accountcrud.services.impl.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody AccountCreateRequest req) {
        return accountService.create(req);
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PutMapping("/{id}")
    public AccountResponse update(@PathVariable Long id, @Valid @RequestBody AccountUpdateRequest req) {
        return accountService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("/{id}/groups")
    public List<GroupMembershipResponse> getGroups(@PathVariable Long id) {
        return accountService.getGroupsOfAccount(id);
    }
}
