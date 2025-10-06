package com.htv.authentication.model.request.register;

public record RegisterRequest(String username, String email, String fullName, String password) {
}
