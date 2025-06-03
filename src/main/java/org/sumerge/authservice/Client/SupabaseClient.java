package org.sumerge.authservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(
        name = "supabaseClient",
        url = "${supabase.url}/auth/v1",
        configuration = SupabaseFeignConfig.class
)
public interface SupabaseClient {

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    String signUp(@RequestBody Map<String, String> requestBody);

    @PostMapping(value = "/token?grant_type=password", consumes = MediaType.APPLICATION_JSON_VALUE)
    String login(@RequestBody Map<String, String> requestBody);
}
