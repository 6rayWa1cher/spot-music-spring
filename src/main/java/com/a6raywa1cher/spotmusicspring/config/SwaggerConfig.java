package com.a6raywa1cher.spotmusicspring.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//	private final BuildProperties buildProperties;
//
//	@Autowired
//	public SwaggerConfig(BuildProperties buildProperties) {
//		this.buildProperties = buildProperties;
//	}

	@Bean
	public Docket api() {
		List<SecurityScheme> schemeList = new ArrayList<>();
		schemeList.add(new ApiKey("JWT", "jwt", "header"));
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("spot-music-spring")
//				.version(buildProperties.getVersion())
				.build();

		//noinspection Guava
		return new Docket(DocumentationType.SWAGGER_2)
				.produces(Collections.singleton("application/json"))
				.consumes(Collections.singleton("application/json"))
				.ignoredParameterTypes(Authentication.class)
				.securitySchemes(schemeList)
				.useDefaultResponseMessages(true)
				.apiInfo(apiInfo)
				.securityContexts(Collections.singletonList(securityContext()))
				.select()
				.apis(Predicates.or(
						Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")),
						RequestHandlerSelectors.basePackage("org.springframework.boot.actuate")))
//				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private SecurityContext securityContext() {
		//noinspection Guava
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(Predicates.not(Predicates.or(
						PathSelectors.ant("/auth/login"),
						PathSelectors.ant("/user/createtemp"))))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
		return Collections.singletonList(
				new SecurityReference("JWT", authorizationScopes));
	}
}
