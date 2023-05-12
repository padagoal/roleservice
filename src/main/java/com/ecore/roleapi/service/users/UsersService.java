package com.ecore.roleapi.service.users;

import com.ecore.roleapi.beans.users.Users;

import java.util.Optional;

public interface UsersService {
    Optional<Users> getUser(String id);


}
