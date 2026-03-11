package com.josewolf.estoque_api.dto;

public record StandardError (
        Integer status,
        String error,
        String path,
        String message
){
}
