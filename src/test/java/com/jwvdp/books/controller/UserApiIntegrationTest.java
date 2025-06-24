package com.jwvdp.books.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void registerShouldReturnSuccessMessage() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "testuser");
        params.add("password", "password123");

        String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/user/register", 
                params, String.class);

        assertThat(response).isEqualTo("访问成功01");
    }
}
