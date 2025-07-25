package com.lidaqian.user_center.confgiration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class Config {

    private final Datasource datasource;

    @Data
    @Component
    public static class Datasource {
        @Value("${spring.datasource.url}")
        private String url;

        @Value("${spring.datasource.username}")
        private String username;

        @Value("${spring.datasource.password}")
        private String password;
    }
}
