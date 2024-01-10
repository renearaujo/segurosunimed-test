package com.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Class that implements a generic response error
 * 
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:31 PM
 */
@Getter
@Setter
@AllArgsConstructor
@JsonRootName("errors")
public class SeguroUnimedErrorResponse {
	
	@NotNull(message="Details cannot be null")
    private String details;

}