package com.majumundur.Security.Services;

import com.majumundur.Security.Models.Roles;
import com.majumundur.Security.Repositories.RolesRepository;
import com.majumundur.Utils.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RolesServiceImpl  implements  RolesService{

    private RolesRepository repository;

    public RolesServiceImpl(RolesRepository repository){
        this.repository = repository;
    }
    @Override
    public Roles getOrSave(RoleEnum role) {
        Optional<Roles> optionalRole = repository.findByRole(role);
        if(optionalRole.isPresent()){
            return optionalRole.get();
        }

        Roles instance = new Roles();
        instance.setRole(role);
        repository.save(instance);
        return instance;
    }
}
