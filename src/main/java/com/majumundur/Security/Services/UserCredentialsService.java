package com.majumundur.Security.Services;

import com.majumundur.Security.Models.UserCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserCredentialsService extends UserDetailsService {

    UserCredentials loadByUserId(String userId);
    void deleteByEmail(String email);
}
