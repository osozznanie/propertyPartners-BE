package com.example.user.repository;

import com.example.user.domain.PendingRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PendingRegistrationRepository extends MongoRepository<PendingRegistration, String> {
}
