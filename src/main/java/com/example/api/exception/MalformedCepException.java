package com.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

/**
 * Exception for malformed Cep.
 *
 * @author René Araújo Vasconcelos - 1/11/2024 - 1:15 PM
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MalformedCepException extends RuntimeException {

    /**
     * Message template format for the exception
     */
    public static final String MSG_TEMPLATE = "The cep [{0}] is Malformed. CEP must be only numbers and has 8 digits.";

    /**
     * cep malformed
     */
    private final String cep;

    /**
     * default constructor
     *
     * @param cep cep malformed
     * @author René Araújo Vasconcelos - 1/11/2024 - 1:14 PM
     */
    public MalformedCepException(String cep) {
        super();
        this.cep = cep;
    }

    /**
     * Override method to create a custom message
     *
     * @return the message formatted
     * @author René Araújo Vasconcelos - 1/11/2024 - 1:15 PM
     * @see #MSG_TEMPLATE
     */
    @Override
    public String getLocalizedMessage() {
        return MessageFormat.format(MSG_TEMPLATE, cep);
    }

    /**
     * Override get message to return a custom message
     *
     * @return the {@link #getLocalizedMessage()}
     * @author René Araújo Vasconcelos - 1/11/2024 - 3:17 PM
     * @see #getLocalizedMessage()
     */
    @Override
    public String getMessage() {
        return this.getLocalizedMessage();
    }
}
