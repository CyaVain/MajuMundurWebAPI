package com.majumundur.Services;

import com.majumundur.Models.Merchants;
import com.majumundur.Repositories.MerchantRepository;
import com.majumundur.Security.Models.DTO.Request.MerchantCreateRequest;
import com.majumundur.Security.Models.UserCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantServiceImpl implements MerchantService {
    private MerchantRepository repository;

    public MerchantServiceImpl(MerchantRepository repository){
        this.repository = repository;
    }
    @Transactional
    @Override
    public Merchants save(MerchantCreateRequest request, UserCredentials credentials) {
        Merchants merchant = Merchants.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .email(credentials.getEmail())
                .password(credentials.getPassword())
                .productsList(null)
                .build();
        repository.save(merchant);
        return merchant;
    }
}
