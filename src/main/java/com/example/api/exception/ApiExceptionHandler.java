package com.example.api.exception;

import com.example.api.dto.response.SeguroUnimedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * Class that implements a handler of exceptions using {@link ControllerAdvice}
 *
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:10 PM
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler<T> {

	/**
	 * Method that handles a {@link CustomerNotFoundException}
	 * 
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:11 PM
	 *
	 * @param exception customer not found exception
	 * @return the {@link ResponseEntity} with {@link HttpStatus#NOT_FOUND} status code
	 */
	@ExceptionHandler(value = {CustomerNotFoundException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected SeguroUnimedResponse<T> handleCustomerNotFoundException(CustomerNotFoundException exception) {
		return this.getResponse(exception.getLocalizedMessage());
    }

	/**
	 * Method that handles a {@link CepNotFoundException}
	 *
	 * @param exception customer not found exception
	 * @return the {@link ResponseEntity} with {@link HttpStatus#NOT_FOUND} status code
	 * @author René Araújo Vasconcelos - 1/11/2024 - 5:15 PM
	 */
	@ExceptionHandler(value = CepNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected SeguroUnimedResponse<T> handleCepNotFoundException(CepNotFoundException exception) {
		return this.getResponse(exception.getLocalizedMessage());
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
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected SeguroUnimedResponse<T> handleServerException(ServerErrorException exception) {
		return this.getResponse(exception.getLocalizedMessage());
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
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected SeguroUnimedResponse<T> handleBadRequestException(HttpClientErrorException.BadRequest exception) {
		return this.getResponse(exception.getLocalizedMessage());
	}

	/**
	 * Method that handles a {@link EmailAlreadyExistsException}
	 *
	 * @param exception email already exists exception
	 * @return the {@link ResponseEntity} with {@link HttpStatus#CONFLICT} status code
	 * @author René Araújo Vasconcelos - 1/9/2024 - 12:59 AM
	 */
	@ExceptionHandler(value = {EmailAlreadyExistsException.class})
	@ResponseStatus(value = HttpStatus.CONFLICT)
	protected SeguroUnimedResponse<T> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
		return this.getResponse(exception.getLocalizedMessage());
	}

	/**
	 * Creates a default response with the exception message
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:18 PM
	 * @param message the exception message
	 * @return a {@link SeguroUnimedResponse} with the exception message
	 */
	private SeguroUnimedResponse<T> getResponse(String... message) {
		SeguroUnimedResponse<T> response = new SeguroUnimedResponse<>();
		response.addErrorMsgToResponse(message);
		return response;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<SeguroUnimedResponse<T>> handleValidationErrors(MethodArgumentNotValidException ex) {
		String[] errors = ex.getBindingResult().getFieldErrors()
				.stream().map(FieldError::getDefaultMessage).toArray(String[]::new);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.getResponse(errors));
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SeguroUnimedResponse<T> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException e) {
		final String error = e.getName() + " should be of type " + Objects.requireNonNull(e.getRequiredType()).getName();
		log.error(error);
		return this.getResponse(error);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SeguroUnimedResponse<T> handleConstraintViolation(final ConstraintViolationException e) {
		final String[] errors = e.getConstraintViolations().stream()
				.map(violation -> violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage())
				.toArray(String[]::new);
		return this.getResponse(errors);
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public SeguroUnimedResponse<T> handleGenericException(Exception exception) {
		log.error("Unhandled exception occurred", exception);
		return this.getResponse("An unexpected error occurred");
	}

	/**
	 * Method that handles a {@link MalformedCepException}.
	 *
	 * @param exception the exception to be handled
	 * @return the {@link ResponseEntity} with {@link HttpStatus#BAD_REQUEST} status code
	 * @author René Araújo Vasconcelos - 1/11/2024 - 5:22 PM
	 */
	@ExceptionHandler(value = {MalformedCepException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected SeguroUnimedResponse<T> handleMalformedCepException(MalformedCepException exception) {
		return this.getResponse(exception.getLocalizedMessage());
	}

}