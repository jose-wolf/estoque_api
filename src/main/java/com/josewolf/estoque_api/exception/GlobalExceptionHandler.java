package com.josewolf.estoque_api.exception;

import com.josewolf.estoque_api.dto.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                               HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                request.getRequestURI(),
                "Campos preenchidos incorretamente!"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                request.getRequestURI(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                         HttpServletRequest request) {
        String userMessage = "Erro de integridade: este registro já existe ou está vinculado a outro dado.";

        if (ex.getMessage() != null && ex.getMessage().contains("category_name")) {
            userMessage = "Já existe uma categoria cadastrada com este nome!";
        }

        if (ex.getMessage().contains("tb_product") || ex.getMessage().contains("product_name")) {
            userMessage = "Já existe um produto com este nome e descrição cadastrados!";
        }

        StandardError err = new StandardError(
                HttpStatus.CONFLICT.value(),
                "Data Conflict",
                request.getRequestURI(),
                userMessage
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

}
