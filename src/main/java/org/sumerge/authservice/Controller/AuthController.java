package org.sumerge.authservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.authservice.Common.ApiResponse;
import org.sumerge.authservice.Model.DTO.SignupRequest;
import org.sumerge.authservice.Model.DTO.SignupResponse;
import org.sumerge.authservice.Model.UserAccount;
import org.sumerge.authservice.Service.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse data = authService.signup(request);
            //TODO: Add the user to user_data table in user service database

            return ResponseEntity.status(201).body(
                    ApiResponse.success("User registered successfully",
                    201,
                            data));

        } catch (RuntimeException e) {
            ApiResponse<SignupResponse> error = ApiResponse.error(e.getMessage(), 400);
            return ResponseEntity.badRequest().body(error);
        }
    }
}
