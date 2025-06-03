package org.sumerge.authservice.Client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class SupabaseFeignConfig {

    @Value("${supabase.key}")
    private String supabaseKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("apikey", supabaseKey);
            template.header("Authorization", "Bearer " + supabaseKey);
            template.header("Content-Type", "application/json");
        };
    }
}
