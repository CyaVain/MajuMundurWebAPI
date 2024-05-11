package com.majumundur.Utils.Specification;

import com.majumundur.Models.TransactionDetails;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionDetailsSpec {

    public static Specification<TransactionDetails> getSpec(String transactionId){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.equal(root.get("transaction").get("id"), transactionId)
            );
            Predicate[] predicate = predicates.toArray(new Predicate[]{});
            return criteriaBuilder.and(predicate);
        });
    }
}
