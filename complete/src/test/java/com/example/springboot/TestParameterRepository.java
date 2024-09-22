package com.example.springboot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestParameterRepository extends MongoRepository<TestParameter, String> {
}