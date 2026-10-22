package com.buivanhuy.accountcrud.controllers;

import com.buivanhuy.accountcrud.dto.*;
import com.buivanhuy.accountcrud.dto.request.AccountFilterRequest;
import com.buivanhuy.accountcrud.dto.request.AccountPATCHRequest;
import com.buivanhuy.accountcrud.dto.request.AccountPOSTRequest;
import com.buivanhuy.accountcrud.dto.response.AccountResponse;
import com.buivanhuy.accountcrud.dto.request.AccountPUTRequest;
import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountPOSTRequest accountDTO) {
        Account account = accountService.createAccount(accountDTO);
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
    public ResponseEntity<Page<AccountResponse>> getAllAccounts(
            @ModelAttribute AccountFilterRequest filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(accountService.findAll(filter, pageable).map(AccountResponse::from));
    }

    /**
     * {@code GET /account/:id} : get the "id" account.
     *
     * @param id The id of the account to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the account in the body, or {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(AccountResponse.from(accountService.findById(id)));
    }

    /**
     * {@code PUT /account/:id} : update the "id" account.
     *
     * @param id   The id of the account to update.
     * @param req  The account update request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the updated account in the body.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountPUTRequest req) {
        return  ResponseEntity.ok(AccountResponse.from(accountService.update(id, req)));
    }

    /**
     * {@code PATCH /account/:id} : partially update the "id" account.
     *
     * @param id   The id of the account to partially update.
     * @param req  The account partial update request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the updated account in the body.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponse> partialUpdateAccount(@PathVariable Long id, @Valid @RequestBody AccountPATCHRequest req) {
        return ResponseEntity.ok(AccountResponse.from(accountService.update(id, req.username(), req.email(), req.fullname(), req.gender(), req.departmentId(), req.positionId())));
    }

    /**
     * {@code DELETE  /account/:id} : delete the "id" account.
     *
     * @param id the id of the account to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
