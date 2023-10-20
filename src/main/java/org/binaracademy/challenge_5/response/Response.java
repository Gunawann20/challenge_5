package org.binaracademy.challenge_5.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {

    private Boolean error;
    private String message;
    private T data;

}
