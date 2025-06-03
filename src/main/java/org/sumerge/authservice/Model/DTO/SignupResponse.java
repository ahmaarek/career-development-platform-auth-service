package org.sumerge.authservice.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {
    private Long id;
    private String email;
    private String token;
}
