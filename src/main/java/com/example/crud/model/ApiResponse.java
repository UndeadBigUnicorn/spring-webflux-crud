package com.example.crud.model;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {

    private int statusCode;
    private String message;
    private String details;

}
