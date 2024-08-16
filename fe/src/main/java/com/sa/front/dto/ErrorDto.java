package com.sa.front.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String statusCode;
    private String status;
    private String message;
}
