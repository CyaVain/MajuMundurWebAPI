package com.majumundur.Repositories;

import com.majumundur.Models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customers, String> {

    Customers findByEmail(String email);
}
