package com.majumundur.Security.Services;

import com.majumundur.Models.Customers;
import com.majumundur.Models.Merchants;
import com.majumundur.Security.Models.DTO.Request.CustomerRegisterRequest;
import com.majumundur.Models.DTO.Responses.ControllerResponse;
import com.majumundur.Security.Config.JwtUtils;
import com.majumundur.Security.Models.DTO.Request.LoginRequest;
import com.majumundur.Security.Models.DTO.Request.MerchantRegisterRequest;
import com.majumundur.Security.Models.DTO.Response.CustomerCreatedResponse;
import com.majumundur.Security.Models.DTO.Response.MerchantCreatedResponse;
import com.majumundur.Security.Models.Roles;
import com.majumundur.Security.Models.UserCredentials;
import com.majumundur.Security.Repositories.UserCredentialsRepository;
import com.majumundur.Services.CustomersService;
import com.majumundur.Services.MerchantsService;
import com.majumundur.Services.ValidationService;
import com.majumundur.Utils.RoleEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private PasswordEncoder encoder;
    private RolesService rolesService;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private ValidationService validation;
    private UserCredentialsRepository repository;
    private CustomersService customersService;
    private MerchantsService merchantsService;

    public AuthenticationServiceImpl(PasswordEncoder encoder, RolesService rolesService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, ValidationService validation, UserCredentialsRepository repository, CustomersService customersService, MerchantsService merchantsService) {
        this.encoder = encoder;
        this.rolesService = rolesService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.validation = validation;
        this.repository = repository;
        this.customersService = customersService;
        this.merchantsService = merchantsService;
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
    public ControllerResponse<?> registerAsMerchant(MerchantRegisterRequest request) {
        List<String> violations = validation.validate(request);
        if(violations != null) {
            ControllerResponse<List> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setData(violations);
            return response;
        }
        try {
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.CONFLICT.value());
                response.setMessage(HttpStatus.CONFLICT.getReasonPhrase());
                response.setData("Email is Already Used");
                return response;
            }
            Roles merchantRole = rolesService.getOrSave(RoleEnum.ROLE_MERCHANT);
            String hashPassword = encoder.encode(request.getPassword());

            UserCredentials credentials = UserCredentials.builder()
                    .email(request.getEmail())
                    .password(hashPassword)
                    .roles(List.of(merchantRole))
                    .build();
            repository.saveAndFlush(credentials);

            Merchants merchant = merchantsService.save(request, credentials);
            MerchantCreatedResponse dto = MerchantCreatedResponse.builder()
                    .merchantId(merchant.getId())
                    .merchantName(merchant.getName())
                    .merchantPhoneNumber(merchant.getPhoneNumber())
                    .merchantEmail(merchant.getEmail())
                    .role(RoleEnum.ROLE_MERCHANT)
                    .build();

            ControllerResponse<MerchantCreatedResponse> response = ControllerResponse.<MerchantCreatedResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());

            return  response;
        }
    }

    @Override
    @Transactional
    public ControllerResponse<?> registerAsCustomer(CustomerRegisterRequest request) {
        List<String> violations = validation.validate(request);
        if(violations != null) {
            ControllerResponse<List> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setData(violations);
            return response;
        }
        try{
            if(repository.findByEmail(request.getEmail()).isPresent()){
                ControllerResponse<String> response = new ControllerResponse<>();
                response.setStatusCode(HttpStatus.CONFLICT.value());
                response.setMessage(HttpStatus.CONFLICT.getReasonPhrase());
                response.setData("Email is Already Used");
                return response;
            }
            Roles customerRole = rolesService.getOrSave(RoleEnum.ROLE_CUSTOMER);
            String hashPassword = encoder.encode(request.getPassword());

            UserCredentials credentials = UserCredentials.builder()
                    .email(request.getEmail())
                    .password(hashPassword)
                    .roles(List.of(customerRole))
                    .build();
            repository.saveAndFlush(credentials);

            Customers customer = customersService.save(request, credentials);
            CustomerCreatedResponse dto = CustomerCreatedResponse.builder()
                    .customerId(customer.getId())
                    .customerName(customer.getName())
                    .customerEmail(customer.getEmail())
                    .customerPoints(customer.getRewardPoints())
                    .customerPhoneNumber(customer.getPhoneNumber())
                    .customerBirthDate(customer.getBirthDate().toString())
                    .role(RoleEnum.ROLE_CUSTOMER)
                    .build();

            ControllerResponse<CustomerCreatedResponse> response = ControllerResponse.<CustomerCreatedResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message(HttpStatus.CREATED.getReasonPhrase())
                    .data(dto)
                    .build();

            return response;
        }
        catch (Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setData(e.getMessage());

            return  response;
        }
    }

    @Override
    public ControllerResponse<?> login(LoginRequest request) {
        List<String> violations = validation.validate(request);
        if(violations != null){
            ControllerResponse<List> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setData(violations);
            return response;
        }
        try{

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );
            Authentication auth = authenticationManager.authenticate(authenticationToken);

            ControllerResponse<String> response = new ControllerResponse<>();

            SecurityContextHolder.getContext().setAuthentication(auth);
            UserCredentials credentials = (UserCredentials)auth.getPrincipal();
            String token =jwtUtils.generateToken(credentials);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            response.setData("Token: "+ token);

            return response;
        }
        catch(Exception e){
            ControllerResponse<String> response = new ControllerResponse<>();
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            response.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
            response.setData(e.getMessage());

            return  response;
        }
    }
}
