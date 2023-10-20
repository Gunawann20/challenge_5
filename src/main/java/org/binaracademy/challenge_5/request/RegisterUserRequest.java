package org.binaracademy.challenge_5.request;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;
    private String email;
    private String password;
    private String address;

}
