package com.buivanhuy.accountcrud.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "`group`")
public class Group extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Account creator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupAccount> groupAccounts = new HashSet<>();

    @PrePersist
    void onCreate() {
        if (createdDate == null) createdDate = LocalDateTime.now();
    }

    public void addMember(Account account) {
        GroupAccount ga = new GroupAccount(this, account);
        groupAccounts.add(ga);
        account.getGroupAccounts().add(ga);
    }
}
