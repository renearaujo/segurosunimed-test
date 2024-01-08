package com.example.api.exception;

import com.example.api.dto.response.SeguroUnimedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;

/**
 * Class that implements a handler of exceptions using {@link ControllerAdvice}
 * 
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:10 PM
 */
@ControllerAdvice
public class ApiExceptionHandler<T> {
	
	/**
	 * Method that handles a {@link CustomerNotFoundException}
	 * 
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:11 PM
	 *
	 * @param exception customer not found exception
	 * @return the {@link ResponseEntity} with {@link HttpStatus#NOT_FOUND} status code
	 */
	@ExceptionHandler(value = { CustomerNotFoundException.class })
    protected ResponseEntity<SeguroUnimedResponse<T>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.createResponse(exception.getLocalizedMessage()));
    }

	/**
	 * Method that handles a {@link ServerErrorException}.
	 * 
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:14 PM
	 *
	 * @param exception the exception to be handled
	 *
	 * @return  the {@link ResponseEntity} with {@link HttpStatus#INTERNAL_SERVER_ERROR} status code
	 */
	@ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<SeguroUnimedResponse<T>> handleServerException(ServerErrorException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this.createResponse(exception.getLocalizedMessage()));
    }

	/**
	 * Method that handles a {@link HttpClientErrorException.BadRequest}.
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:18 PM
	 *
	 * @param exception the exception to be handled
	 *
	 * @return  the {@link ResponseEntity} with {@link HttpStatus#BAD_REQUEST} status code
	 */
	@ExceptionHandler(value = { HttpClientErrorException.BadRequest.class })
	protected ResponseEntity<SeguroUnimedResponse<T>> handleBadRequestException(HttpClientErrorException.BadRequest exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.createResponse(exception.getLocalizedMessage()));
	}

	/**
	 * Creates a default response with the exception message
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:18 PM
	 * @param exceptionMessage the exception message
	 * @return a {@link SeguroUnimedResponse} with the exception message
	 */
	private SeguroUnimedResponse<T> createResponse(String exceptionMessage) {
		SeguroUnimedResponse<T> response = new SeguroUnimedResponse<>();
		response.addErrorMsgToResponse(exceptionMessage);
		return response;
	}

}