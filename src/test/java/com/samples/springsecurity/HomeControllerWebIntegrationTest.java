package com.samples.springsecurity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HomeControllerWebIntegrationTest {
	
    @LocalServerPort
    private int port;
	
	@Test
	public void testWithoutCredentials() throws IOException {
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+ port +"/", String.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
		String authenticateHeaderValue = response.getHeaders().get(HttpHeaders.WWW_AUTHENTICATE).get(0);
		assertThat(authenticateHeaderValue, equalTo("Basic realm=\"Spring Security Basic Authentication Sample\""));	
	}
	
	@Test
	public void testWithCredentials() throws IOException {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();		
		RestTemplate restTemplate = restTemplateBuilder.basicAuthorization("user", "password").build();;
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+ port +"/", String.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));		
		assertThat(response.getHeaders().get(HttpHeaders.WWW_AUTHENTICATE), is(nullValue()));	
	}
	
}
