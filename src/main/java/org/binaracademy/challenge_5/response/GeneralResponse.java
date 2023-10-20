package org.binaracademy.challenge_5.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralResponse {

    private Boolean error;
    private String message;

}
