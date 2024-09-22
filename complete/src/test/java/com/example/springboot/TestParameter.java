package com.example.springboot;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// example of a document in the collection urls_to_verify
/*
  [
    {
        "url": "http://localhost:8080/",
        "expectedBody": "Greetings from Spring Boot!"
    },
    {
        "url": "http://localhost:8080/actuator/health",
        "expectedBody": "{\"status\":\"UP\"}"
    }
   ]
 */

@Document(collection = "urls_to_verify")
public class TestParameter {
    @Id
    private String id;
    private String url;
    private String expectedBody;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpectedBody() {
        return expectedBody;
    }

    public void setExpectedBody(String expectedBody) {
        this.expectedBody = expectedBody;
    }
}