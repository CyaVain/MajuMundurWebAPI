package com.majumundur.Repositories;

import com.majumundur.Models.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchants, String> {
    Merchants findByPhoneNumber(String phoneNumber);
}
