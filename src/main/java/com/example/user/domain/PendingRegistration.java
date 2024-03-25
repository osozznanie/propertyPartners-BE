package com.example.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(value = "pending_registrations")
public class PendingRegistration {

    @Id
    private String id;
    private User user;
    private LocalDateTime createdAt;


    public PendingRegistration(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}
