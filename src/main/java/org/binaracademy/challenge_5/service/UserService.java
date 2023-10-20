package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.entity.User;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;

public interface UserService {
    GeneralResponse register(String username, String email, String password, String address);
    Response<User> login(String email, String password);
    GeneralResponse update(Long userId, String username, String email, String password, String address);
    GeneralResponse delete(Long userId);
}
