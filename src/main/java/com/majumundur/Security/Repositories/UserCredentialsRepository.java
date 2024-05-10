package com.majumundur.Security.Repositories;

import com.majumundur.Security.Models.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {

    Optional<UserCredentials> findByEmail (String username);
}
