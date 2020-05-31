package org.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties
@Component
@Primary
public class ConfigurationApp {
    @Value("${report.name}")
    private  String reportName;

    @Value("${sonarqube.url}")
    private String sonarQubeURL;

    private String token;
}
