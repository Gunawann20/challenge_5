package org.binaracademy.challenge_5.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.binaracademy.challenge_5.entity.User;
import org.binaracademy.challenge_5.request.LoginUserRequest;
import org.binaracademy.challenge_5.request.RegisterUserRequest;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Membuat user baru")
    @PostMapping(
            value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GeneralResponse register(@RequestBody RegisterUserRequest request){

        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String address = request.getAddress();

        return userService.register(username, email, password, address);
    }

    @Operation(summary = "Login user")
    @PostMapping(
            value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<User> login(@RequestBody LoginUserRequest request){
        return userService.login(request.getEmail(), request.getPassword());
    }

    @Operation(summary = "Update data user")
    @PutMapping(
            value = "update/user/{userId}"
    )
    public GeneralResponse update(@PathVariable Long userId, @RequestBody RegisterUserRequest request){

        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String address = request.getAddress();

        return userService.update(userId, username, email, password, address);
    }

    @Operation(summary = "Menghapus user")
    @DeleteMapping(
            value = "delete/user/{userId}"
    )
    public GeneralResponse delete(@PathVariable Long userId){
        return userService.delete(userId);
    }
}
