package service.template.config.exceptionhandling;

import lombok.Data;

@Data
public class ErrorResponse {

    private final String message;

    public ErrorResponse(Exception ex) {
        this.message = ex.getMessage();
    }
}
