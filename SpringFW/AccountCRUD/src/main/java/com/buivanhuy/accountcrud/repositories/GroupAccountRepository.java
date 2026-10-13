package com.buivanhuy.accountcrud.repositories;

import com.buivanhuy.accountcrud.entities.GroupAccount;
import com.buivanhuy.accountcrud.entities.GroupAccount.GroupAccountId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupAccountRepository extends JpaRepository<GroupAccount, GroupAccountId> {

    // Account này đang tham gia các group nào
    @EntityGraph(attributePaths = "group")
    List<GroupAccount> findByAccount_Id(Long accountId);

    // Group này đang có các account nào
    @EntityGraph(attributePaths = "account")
    List<GroupAccount> findByGroup_Id(Long groupId);
}
