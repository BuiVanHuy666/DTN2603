package com.buivanhuy.accountcrud.services.impl;

import com.buivanhuy.accountcrud.dto.GroupRequest;
import com.buivanhuy.accountcrud.dto.GroupResponse;
import com.buivanhuy.accountcrud.dto.MemberResponse;
import com.buivanhuy.accountcrud.entities.Account;
import com.buivanhuy.accountcrud.entities.Group;
import com.buivanhuy.accountcrud.entities.GroupAccount;
import com.buivanhuy.accountcrud.entities.GroupAccount.GroupAccountId;
import com.buivanhuy.accountcrud.repositories.AccountRepository;
import com.buivanhuy.accountcrud.repositories.GroupAccountRepository;
import com.buivanhuy.accountcrud.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;
    private final GroupAccountRepository groupAccountRepository;

    @Transactional
    public GroupResponse create(GroupRequest req) {
        Group group = new Group();
        group.setName(req.name());
        group.setCreator(findCreator(req.creatorId()));
        return GroupResponse.from(groupRepository.save(group));
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream().map(GroupResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public GroupResponse findById(Long id) {
        return GroupResponse.from(getGroup(id));
    }

    @Transactional
    public GroupResponse update(Long id, GroupRequest req) {
        Group group = getGroup(id);
        group.setName(req.name());
        group.setCreator(findCreator(req.creatorId()));
        return GroupResponse.from(group);
    }

    @Transactional
    public void delete(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> notFound("Group", id));
        // cascade + orphanRemoval sẽ xóa các dòng group_account của group
        groupRepository.delete(group);
    }

    /** CREATE: thêm account vào group. */
    @Transactional
    public MemberResponse addMember(Long groupId, Long accountId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> notFound("Group", groupId));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> notFound("Account", accountId));

        if (groupAccountRepository.existsById(new GroupAccountId(groupId, accountId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account đã ở trong group này");
        }

        group.addMember(account);
        // flush để @PrePersist điền joinDate rồi mới trả về
        groupRepository.flush();

        GroupAccount ga = groupAccountRepository.findById(new GroupAccountId(groupId, accountId))
                .orElseThrow();
        return MemberResponse.from(ga);
    }

    /** READ: group đang có các account nào. */
    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw notFound("Group", groupId);
        }
        return groupAccountRepository.findByGroup_Id(groupId).stream()
                .map(MemberResponse::from)
                .toList();
    }

    // ---------- helpers ----------
    private Group getGroup(Long id) {
        return groupRepository.findWithCreatorById(id)
                .orElseThrow(() -> notFound("Group", id));
    }

    private Account findCreator(Long creatorId) {
        if (creatorId == null) return null;
        return accountRepository.findById(creatorId)
                .orElseThrow(() -> notFound("Account", creatorId));
    }

    private ResponseStatusException notFound(String what, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, what + " không tồn tại: id=" + id);
    }
}
