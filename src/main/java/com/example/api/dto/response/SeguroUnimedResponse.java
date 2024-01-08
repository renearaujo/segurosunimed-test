package com.example.api.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

/**
 * Class that implements a generic response
 * 
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:32 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeguroUnimedResponse<T> {

	private T data;
	private Object errors;
	
	/**
	 * Method that formats an error message to the generic response.
	 * 
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:33 PM
	 * 
	 * @param msgError the error message
	 */
    public void addErrorMsgToResponse(String msgError) {
		setErrors( new SeguroUnimedErrorResponse(LocalDateTime.now(), msgError) );
    }
}