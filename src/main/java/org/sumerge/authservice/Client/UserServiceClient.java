package org.sumerge.authservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.sumerge.authservice.Common.ApiResponse;
import org.sumerge.authservice.Model.DTO.CreateUserRequest;

import java.util.Map;

@FeignClient(
        name = "UserServiceClient",
        url = "${userService.url}"
)
public interface UserServiceClient {

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> createUser(@RequestBody CreateUserRequest requestBody);

}