package com.a6raywa1cher.spotmusicspring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "app")
@Data
@Validated
public class AppConfigProperties {
	@NotBlank
	private String jwt;
}
