package br.com.eClinic.api.auth;

public class AuthResponse {
    
    private String message;
    private String token; // Adicione um token se estiver usando JWT

    // Construtor para mensagem simples
    public AuthResponse(String message) {
        this.message = message;
    }

    // Construtor para mensagem com token
    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}