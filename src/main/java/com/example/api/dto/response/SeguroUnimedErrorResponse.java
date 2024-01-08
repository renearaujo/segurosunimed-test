package com.example.api.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * Class that implements a generic response error
 * 
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:31 PM
 */
@Getter
@Setter
@AllArgsConstructor
public class SeguroUnimedErrorResponse {
	
	@NotNull(message="Timestamp cannot be null")
	private LocalDateTime timestamp;
	
	@NotNull(message="Details cannot be null")
    private String details;

}