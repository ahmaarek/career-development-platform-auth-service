package org.sumerge.authservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.sumerge.authservice.Client.SupabaseClient;
import org.sumerge.model.Common.ApiResponse;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private SupabaseClient supabaseClient;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<Object>> signUp(String email, String password) {
        try {
            String response = supabaseClient.signUp(Map.of("email", email, "password", password));

            // Parse JSON string to Map
            Map responseBody = objectMapper.readValue(response, Map.class);

            return ResponseEntity.ok(
                    ApiResponse.success("User created successfully", HttpStatus.CREATED.value(), responseBody));

        } catch (FeignException fe) {
            String errorBody = fe.contentUTF8();
            if (errorBody.contains("User already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Email already in use", HttpStatus.CONFLICT.value()));
            }

            return ResponseEntity.status(fe.status())
                    .body(ApiResponse.error("Signup failed", fe.status()));
        } catch (Exception e) {
            return ResponseEntity.status(	HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Unexpected error during signup", 	HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    public ResponseEntity<ApiResponse<Object>> login(String email, String password) {
        try {
            String response = supabaseClient.login(Map.of("email", email, "password", password));

            // Convert string response to Map
            Map responseBody = objectMapper.readValue(response, Map.class);

            return ResponseEntity.ok(
                    ApiResponse.success("Login successful", HttpStatus.OK.value(), responseBody)
            );

        } catch (FeignException fe) {
            String errorBody = fe.contentUTF8();

            if (errorBody.contains("Invalid login credentials")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Wrong email or password", HttpStatus.UNAUTHORIZED.value()));
            }

            return ResponseEntity.status(fe.status())
                    .body(ApiResponse.error("Login failed", fe.status()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
