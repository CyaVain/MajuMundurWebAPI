package com.majumundur.Repositories;

import com.majumundur.Models.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//JpaSpecificationExecutor untuk menambah Method Untuk Keperluan Specification seperti Paging
public interface MerchantsRepository extends JpaRepository<Merchants, String>, JpaSpecificationExecutor<Merchants> {
    Merchants findByPhoneNumber(String phoneNumber);
}
