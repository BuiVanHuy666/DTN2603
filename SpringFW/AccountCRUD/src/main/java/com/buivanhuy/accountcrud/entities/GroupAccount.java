package com.buivanhuy.accountcrud.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "group_account")
@Getter
@Setter
@NoArgsConstructor
public class GroupAccount {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class GroupAccountId implements Serializable {
        @Column(name = "group_id")
        private Long groupId;

        @Column(name = "account_id")
        private Long accountId;
    }

    @EmbeddedId
    private GroupAccountId id = new GroupAccountId();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    public GroupAccount(Group group, Account account) {
        this.id = new GroupAccountId(group.getId(), account.getId());
        this.group = group;
        this.account = account;
        this.joinDate = LocalDateTime.now();
    }
}
