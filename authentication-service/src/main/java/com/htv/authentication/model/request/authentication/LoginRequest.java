package com.htv.authentication.model.request.authentication;

public record LoginRequest(String username, String email, String password) {
}
