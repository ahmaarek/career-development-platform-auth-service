package org.sumerge.authservice.Controller;

import org.sumerge.authservice.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.model.Common.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signUp(@RequestBody Map<String, String> body) {
        return authService.signUp(body.get("email"), body.get("password"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody Map<String, String> body) {
        return authService.login(body.get("email"), body.get("password"));
    }
}
