package com.buivanhuy.accountcrud.specifications;

import com.buivanhuy.accountcrud.dto.request.AccountFilterRequest;
import com.buivanhuy.accountcrud.entities.Account;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> filter(AccountFilterRequest filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.search())) {
                String pattern = "%" + filter.search().trim().toLowerCase() + "%";
                Predicate usernameLike = cb.like(cb.lower(root.get("username")), pattern);
                Predicate emailLike = cb.like(cb.lower(root.get("email")), pattern);
                Predicate fullnameLike = cb.like(cb.lower(root.get("fullname")), pattern);
                predicates.add(cb.or(usernameLike, emailLike, fullnameLike));
            }

            if (filter.gender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.gender()));
            }

            if (filter.departmentId() != null) {
                predicates.add(cb.equal(root.get("department").get("id"), filter.departmentId()));
            }

            if (filter.positionName() != null) {
                predicates.add(cb.equal(root.join("position", JoinType.LEFT).get("name"), filter.positionName()));
            }

            if (filter.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createDate"), filter.createdFrom()));
            }
            if (filter.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createDate"), filter.createdTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
