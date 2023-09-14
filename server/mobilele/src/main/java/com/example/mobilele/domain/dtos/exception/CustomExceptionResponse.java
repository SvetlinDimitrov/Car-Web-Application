package com.example.mobilele.domain.dtos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@Data
@AllArgsConstructor
public class CustomExceptionResponse {

    private List<String> messages;

}
