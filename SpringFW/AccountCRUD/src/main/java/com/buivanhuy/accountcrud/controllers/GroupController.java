package com.buivanhuy.accountcrud.controllers;

import com.buivanhuy.accountcrud.dto.GroupRequest;
import com.buivanhuy.accountcrud.dto.GroupResponse;
import com.buivanhuy.accountcrud.dto.MemberResponse;
import com.buivanhuy.accountcrud.services.impl.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse create(@Valid @RequestBody GroupRequest req) {
        return groupService.create(req);
    }

    @GetMapping
    public List<GroupResponse> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public GroupResponse findById(@PathVariable Long id) {
        return groupService.findById(id);
    }

    @PutMapping("/{id}")
    public GroupResponse update(@PathVariable Long id, @Valid @RequestBody GroupRequest req) {
        return groupService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @PostMapping("/{groupId}/members/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse addMember(@PathVariable Long groupId, @PathVariable Long accountId) {
        return groupService.addMember(groupId, accountId);
    }

    @GetMapping("/{id}/accounts")
    public List<MemberResponse> getMembers(@PathVariable Long id) {
        return groupService.getMembers(id);
    }
}
