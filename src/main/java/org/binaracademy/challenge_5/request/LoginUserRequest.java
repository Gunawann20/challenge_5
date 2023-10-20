package org.binaracademy.challenge_5.request;

import lombok.Data;

@Data
public class LoginUserRequest {

    private String email;
    private String password;
}
