package org.binaracademy.challenge_5.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_5.entity.User;
import org.binaracademy.challenge_5.repository.UserRepository;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public GeneralResponse register(String username, String email, String password, String address) {
        GeneralResponse response = new GeneralResponse();
        try {
            if (userRepository.existsByEmail(email)){
                response.setError(true);
                response.setMessage("Email sudah digunakan");
            }else {
                User user = new User(username, email, password, address);
                log.info("membuat data user : "+ user.getUsername());
                userRepository.save(user);
                response.setError(false);
                response.setMessage("user created successfully");
                log.info("Berhasil membuat data user : "+user.getUsername());
            }
        }catch (Exception e){
            log.error("Terjadi kesalahan : "+ e.getMessage());
            response.setError(true);
            response.setMessage("Gagal membuat data user");
        }
        return response;
    }

    @Override
    public Response<User> login(String email, String password) {
        Response<User> response = new Response<>();
        try {
            log.info("Mencari data user");
            User user = userRepository.findByEmailAndPassword(email, password).orElse(null);
            if (user == null){
                log.info("Data user tidak ditemukan");
                response.setError(true);
                response.setMessage("Email atau password salah");
                response.setData(null);
            }else {
                log.info("Berhasil menemukan data user");
                response.setError(false);
                response.setMessage("Success");
                response.setData(user);
            }
        }catch (Exception e){
            log.error("Terjadi kesalahan : "+ e.getMessage());
            response.setError(true);
            response.setMessage("Email atau password salah");
            response.setData(null);
        }
        return response;
    }

    @Override
    public GeneralResponse update(Long userId, String username, String email, String password, String address) {
        GeneralResponse response = new GeneralResponse();
        try {
            log.info("Mencari data user id :"+ userId);
            User user = userRepository.findById(userId).orElse(null);
            if (user == null){
                log.info("data user id :"+userId+" tidak ditemukan");
                response.setError(true);
                response.setMessage("User tidak ditemukan");
            }else {
                log.info("data user ditemukan");
                if (Objects.equals(user.getEmail(), email)){
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAddress(address);

                    userRepository.save(user);
                    response.setError(false);
                    response.setMessage("Berhasil update data");
                }else {
                    if (userRepository.existsByEmail(email)){
                        response.setError(true);
                        response.setMessage("Silahkan gunakan email yang lain");
                    }else {
                        user.setUsername(username);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setAddress(address);
                    }
                }
            }
        }catch (Exception e){
            response.setError(true);
            response.setMessage("User tidak ditemukan");
        }
        return response;
    }

    @Override
    public GeneralResponse delete(Long userId) {
        GeneralResponse response = new GeneralResponse();
        try {
            userRepository.deleteById(userId);
            response.setError(false);
            response.setMessage("Berhasil menghapus data");
        }catch (Exception e){
            response.setError(true);
            response.setMessage("User tidak ditemukan");
        }
        return response;
    }
}
