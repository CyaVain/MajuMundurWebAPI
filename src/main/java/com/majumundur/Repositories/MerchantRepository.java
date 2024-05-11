package com.majumundur.Repositories;

import com.majumundur.Models.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchants, String> {
    Merchants findByPhoneNumber(String phoneNumber);
}
