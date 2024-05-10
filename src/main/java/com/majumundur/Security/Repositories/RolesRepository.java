package com.majumundur.Security.Repositories;

import com.majumundur.Security.Models.Roles;
import com.majumundur.Utils.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, String> {

    //Return berupa Optional karena jika tidak ketemu tidak menjadi masalah
    Optional<Roles> findByRole(RoleEnum role);
}
