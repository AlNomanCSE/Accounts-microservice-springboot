package com.noman.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String apiPath;
    private String errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
