package fr.ocr.paymybuddy.security;

import lombok.Getter;

@Getter
public enum UserRole {
    UNKNOWN("UNKNOWN"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
