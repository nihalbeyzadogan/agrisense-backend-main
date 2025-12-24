package io.agrisense.adapter.in.web.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;

/**
 * Global Exception Mapper for handling exceptions and returning standardized HTTP responses.
 * 
 * - IllegalArgumentException → 404 Not Found (e.g., sensor not found)
 * - ConstraintViolationException → 400 Bad Request (validation errors)
 * - Other exceptions → 500 Internal Server Error
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        // Handle validation errors
        if (exception instanceof ConstraintViolationException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Validation failed", exception.getMessage()))
                    .build();
        }

        // Handle resource not found errors
        if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Resource not found", exception.getMessage()))
                    .build();
        }

        // Handle all other exceptions as internal server errors
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal server error", exception.getMessage()))
                .build();
    }

    /**
     * Standard error response structure
     */
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
