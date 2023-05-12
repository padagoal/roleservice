package com.ecore.roleapi.service.users;

import com.ecore.roleapi.beans.membership.Membership;
import com.ecore.roleapi.beans.users.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserAPI {

    private final UsersService usersService;

    @GetMapping(value = "/{userid}")
    public ResponseEntity<Users> getUser(@PathVariable(name = "userid") String userid ){
        return ResponseEntity.ok().body(usersService.getUser(userid).get());
    }
}
