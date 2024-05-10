package com.majumundur.Security.Services;

import com.majumundur.Models.DTO.Requests.CustomersCreateRequest;
import com.majumundur.Security.Config.JwtUtils;
import com.majumundur.Security.DTO.LoginRequest;
import com.majumundur.Security.Models.Roles;
import com.majumundur.Security.Models.UserCredentials;
import com.majumundur.Security.Repositories.UserCredentialsRepository;
import com.majumundur.Services.ValidationService;
import com.majumundur.Utils.RoleEnum;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Validation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthencticationService {
    private PasswordEncoder encoder;
    private RolesService rolesService;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private ValidationService validation;
    private UserCredentialsRepository repository;

    public AuthenticationServiceImpl(PasswordEncoder encoder, RolesService rolesService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, ValidationService validation, UserCredentialsRepository repository) {
        this.encoder = encoder;
        this.rolesService = rolesService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.validation = validation;
        this.repository = repository;
    }

    // Annotasi Untuk Menjalanan Method Setelah Build
    @PostConstruct
    // Inisialisasi SuperAdmin agar terbuat setiap program berjalan
    public void initializeSuperAdmin(){
        String username = "superadmin@gmail.com";
        String password = "superadmin123";
        Optional<UserCredentials> credentials = repository.findByEmail(username);
        if(credentials.isPresent()) return;

        Roles superAdmin = rolesService.getOrSave(RoleEnum.ROLE_SUPER_ADMIN);

        String hashPassword = encoder.encode(password);

        UserCredentials credential = UserCredentials.builder()
                .email(username)
                .password(hashPassword)
                .roles(List.of(superAdmin))
                .build();

        repository.saveAndFlush(credential);
    }

    @Override
    public UserCredentials registerAsCustomer(CustomersCreateRequest request) {
        return null;
    }

    @Override
    public String login(LoginRequest request) {
        return null;
    }
}
