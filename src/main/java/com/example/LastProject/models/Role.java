package com.example.LastProject.models;

public enum Role {
    USER,ADMIN,SOTRUDNIKPK;
    public String getAuthority() {
        return name();
    }
}
