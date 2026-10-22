package com.buivanhuy.accountcrud.controllers;

import com.buivanhuy.accountcrud.dto.*;
import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.services.impl.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Create a new account.
     *
     * @param accountDTO The account to create.
     * @return The created account response.
     */
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountDTO accountDTO) {
        Account account = accountService.create(accountDTO);
        return ResponseEntity
                .created(URI.create("/api/accounts/" + account.getId()))
                .body(AccountResponse.from(account));
    }

    /**
     * {@code GET /account} : get all accounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accounts in the body.
     */
    @GetMapping
    public ResponseEntity<Page<AccountResponse>> findAll(
            @ModelAttribute AccountFilterRequest filter,
            @PageableDefault(page = 0, size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.findAll(filter, pageable).map(AccountResponse::from));
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
