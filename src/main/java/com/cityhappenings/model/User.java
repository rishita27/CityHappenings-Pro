package com.cityhappenings.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String passwordHash;
    private String preferences; // JSON string for simplicity

}
