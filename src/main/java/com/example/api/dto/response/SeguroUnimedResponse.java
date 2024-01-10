package com.example.api.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class SeguroUnimedResponse<T> {

	private T data;
	private List<SeguroUnimedErrorResponse> errors;

	/**
	 * Method that formats an error message to the generic response.
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:33 PM
	 *
	 * @param msgError the error message
	 */
	private void addErrorMsgToResponse(String msgError) {

		if (errors == null) {
			List<SeguroUnimedErrorResponse> list = new ArrayList<>();
			this.setErrors(list);
		}

		errors.add(new SeguroUnimedErrorResponse(msgError));
	}

	public void addErrorMsgToResponse(String... item) {

		if (item != null) {
			Arrays.stream(item).sequential().forEach(this::addErrorMsgToResponse);
		}
	}
}