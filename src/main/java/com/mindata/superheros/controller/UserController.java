package com.mindata.superheros.controller;

import com.mindata.superheros.aop.LogRequestExecutionTime;
import com.mindata.superheros.model.User;
import com.mindata.superheros.model.request.UserRequest;
import com.mindata.superheros.model.response.UserResponse;
import com.mindata.superheros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mindata.superheros.model.ModelUtils.getUserFromRequest;
import static com.mindata.superheros.model.ModelUtils.getUserResponseFromUser;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @LogRequestExecutionTime
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        User user = userService.register(getUserFromRequest(userRequest));
        return status(CREATED).body(getUserResponseFromUser(user));
    }

}
