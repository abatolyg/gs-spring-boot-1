package com.example.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HelloControllerITestParameterized {    

    @Autowired
    private TestRestTemplate template;
    
    @Autowired
    private TestParameterRepository repository;

    @ParameterizedTest
    @CsvSource({
        "http://localhost:8080/, Greetings from Spring Boot!",
        "http://localhost:8080/actuator/health, {\"status\":\"UP\"}"
    })
    public void testEndpoints(String url, String expectedBody) throws Exception {
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(expectedBody);
    }

    // Alternatively, using MethodSource for more complex data
    @ParameterizedTest
    @MethodSource("provideTestParameters")
    public void testEndpointsWithMethodSource(String url, String expectedBody) throws Exception {
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(expectedBody);
    }

    private static Stream<Arguments> provideTestParameters() {
        return Stream.of(
            Arguments.of("http://localhost:8080/", "Greetings from Spring Boot!"),
            Arguments.of("http://localhost:8080/actuator/health", "{\"status\":\"UP\"}")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestParametersFromMongoDB")
    public  void testEndpointsWithMethodSourceMongoDB(String url, String expectedBody) throws Exception {
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(expectedBody);
    }

    private Stream<Arguments> provideTestParametersFromMongoDB() {
        return repository.findAll().stream()
                .map(param -> Arguments.of(param.getUrl(), param.getExpectedBody()));
    }
}