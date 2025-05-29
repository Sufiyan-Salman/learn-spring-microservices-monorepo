package com.learn.microservices.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

//	@GetMapping("/auth/tokens")
//	public Map<String, String> getTokens(
//			@RegisteredOAuth2AuthorizedClient("learning-microservices-api-gateway")
//			OAuth2AuthorizedClient authorizedClient
//	) {
//		String accessToken = authorizedClient.getAccessToken().getTokenValue();
//		String refreshToken = authorizedClient.getRefreshToken() != null
//				? authorizedClient.getRefreshToken().getTokenValue()
//				: "N/A";
//
//		return Map.of(
//				"access_token", accessToken,
//				"refresh_token", refreshToken
//		);
//	}

}
