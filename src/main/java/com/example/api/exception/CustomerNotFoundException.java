package com.example.api.exception;

import java.text.MessageFormat;

/**
 * Exception that representing when a customer was not found
 * 
 * @author René Araújo Vasconcelos - 1/8/2024 - 11:13 AM
 */
public class CustomerNotFoundException extends UnimedNotFoundException {

    /**
     * Message template format for the exception
     */
    public static final String MSG_TEMPLATE = "Customer with id = [{0}] not found";

    /**
     * id from the customer
     */
    private final Long id;

    /**
     * default constructor
     *
     * @param id id from the customer
     * @author René Araújo Vasconcelos - 1/9/2024 - 11:43 PM
     */
    public CustomerNotFoundException(Long id) {
        super();
        this.id = id;
    }

    /**
     * Override method to create a custom message
     *
     * @return the message formatted
     * @author René Araújo Vasconcelos - 1/9/2024 - 11:43 PM
     * @see #MSG_TEMPLATE
     */
    @Override
    public String getLocalizedMessage() {
        return MessageFormat.format(MSG_TEMPLATE, id);
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