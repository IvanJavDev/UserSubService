package org.example.usersubscriptionservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Map<String, String> details;

    // Конструктор для ошибок без деталей
    public ErrorResponse(String message) {
        this.message = message;
        this.details = null;
    }
}
