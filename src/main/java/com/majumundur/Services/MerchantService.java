package com.majumundur.Services;

import com.majumundur.Models.Merchants;
import com.majumundur.Security.Models.DTO.Request.MerchantCreateRequest;
import com.majumundur.Security.Models.UserCredentials;

public interface MerchantService {
    public Merchants save(MerchantCreateRequest request, UserCredentials credentials);
}
