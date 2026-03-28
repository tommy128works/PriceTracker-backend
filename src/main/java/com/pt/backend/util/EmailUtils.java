package com.pt.backend.util;

public class EmailUtils {
    public static String normalize(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
