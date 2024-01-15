package com.example.api.exception;

import java.text.MessageFormat;

/**
 * EmailAlreadyExistsException is a custom exception class that extends the RuntimeException class.
 * It is thrown when an attempt is made to create a new email address that already exists in the system.
 *
 * @author René Araújo Vasconcelos - 1/9/2024 - 7:39 PM
 */
public class EmailAlreadyExistsException extends UnimedAlreadyExistsException {

    /**
     * Message template format for the exception
     */
    public static final String MSG_TEMPLATE = "Email [{0}] Already Exists for another Customer with id = [{1}]";

    /**
     * email from the customer
     */
    private final String email;

    /**
     * id from the customer
     */
    private final Long id;

    /**
     * default constructor
     *
     * @param email email that exists
     * @param id    id from the customer
     * @author René Araújo Vasconcelos - 1/9/2024 - 7:50 PM
     */
    public EmailAlreadyExistsException(String email, Long id) {
        super();
        this.email = email;
        this.id = id;
    }

    /**
     * Overrided method to create a custom message
     *
     * @return the message formatted
     * @author René Araújo Vasconcelos - 1/9/2024 - 7:51 PM
     * @see #MSG_TEMPLATE
     */
    @Override
    public String getLocalizedMessage() {
        return MessageFormat.format(MSG_TEMPLATE, email, id);
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
