package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties
@NoArgsConstructor
@Data
public class CommonResponse<T> implements Serializable {

    // ATTRIBUTES //
    private static final long serialVersionUID = 1000000000000000L;
    private int status;
    private String message;

    // CommonResponse Constructor without message
    public CommonResponse(int status) {
        this.status = status;
    }

    // CommonResponse Constructor with message
    public CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
