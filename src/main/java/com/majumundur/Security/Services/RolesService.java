package com.majumundur.Security.Services;

import com.majumundur.Security.Models.Roles;
import com.majumundur.Utils.RoleEnum;

public interface RolesService {

    Roles getOrSave (RoleEnum role);
}
