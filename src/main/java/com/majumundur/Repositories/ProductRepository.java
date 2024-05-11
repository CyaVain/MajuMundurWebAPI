package com.majumundur.Repositories;

import com.majumundur.Models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, String>, JpaSpecificationExecutor<Products> {

    Products findByCode(String code);

    Page<Products> findByMerchant_Id(Pageable pageable, String merchantId);
}
