package com.example.loginsystem.model;

import lombok.Data;

@Data
public class User {
    private String email;
    private String password;
    private String uid;
    private String displayName;
}
