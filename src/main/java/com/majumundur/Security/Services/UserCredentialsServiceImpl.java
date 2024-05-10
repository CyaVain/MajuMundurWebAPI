package com.majumundur.Security.Services;

import com.majumundur.Security.Models.UserCredentials;
import com.majumundur.Security.Repositories.UserCredentialsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {
    private UserCredentialsRepository repository;
    public UserCredentialsServiceImpl(UserCredentialsRepository repository){
        this.repository = repository;
    }

    @Override
    public UserCredentials loadByUserId(String userId) {
        return repository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN,"You Do Not Have Permission")
        );
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN,"You Do Not Have Permission")
        );
    }

    @Override
    public void deleteByEmail(String email) {
        UserCredentials credential = repository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN,"You Do Not Have Permission")
        );
        repository.delete(credential);
    }
}
