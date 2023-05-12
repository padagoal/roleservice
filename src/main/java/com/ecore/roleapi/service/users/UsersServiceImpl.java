package com.ecore.roleapi.service.users;

import com.ecore.roleapi.beans.users.Users;
import com.ecore.roleapi.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService{
    private final String URL_GET_USER = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/users/%s";

    @Override
    public Optional<Users> getUser(String id) {
        RestTemplate restTemplate = new RestTemplate();
        Users user = restTemplate.getForObject(String.format(URL_GET_USER,id),Users.class);
        if(user ==null){
            log.warn("The user ID: [{}] was not found",id);
            throw new ResourceNotFoundException(
                    String.format("The user ID: [%s] was not found", id));
        }
        return Optional.of(user);
    }

}
