package com.example.api.exception;

import java.text.MessageFormat;

/**
 * Custom exception that is thrown when an attempt is made to create a new address that already exists for the customer
 *
 * @author René Araújo Vasconcelos - 1/9/2024 - 7:39 PM
 */
public class CustomerAddressAlreadyExistsException extends UnimedAlreadyExistsException {

    /**
     * Message template format for the exception
     */
    public static final String MSG_TEMPLATE = "Address with cep [{0}], complement [{1}], number [{2}] already Exists [id={3}] for another Customer [id={4}]";

    /**
     * complement from the address  that already exists
     */
    private final String complement;

    /**
     * number from the address that already exists
     */
    private final String number;

    /**
     * cep from the address that already exists
     */
    private final String cep;

    /**
     * id from the address from customer that already exists
     */
    private final Long customerAddressId;

    /**
     * id from the customer
     */
    private final Long customerId;

    /**
     * default constructor
     *
     * @param complement        complement from the address that already exists
     * @param number            number from the address that already exists
     * @param cep               cep from the address that already exists
     * @param customerAddressId id from the address that already exists
     * @param customerId        id from the customer
     * @author René Araújo Vasconcelos - 1/11/2024 - 11:53 PM
     */
    public CustomerAddressAlreadyExistsException(String complement, String number, String cep, Long customerAddressId, Long customerId) {
        this.complement = complement;
        this.number = number;
        this.cep = cep;
        this.customerAddressId = customerAddressId;
        this.customerId = customerId;
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
        return MessageFormat.format(MSG_TEMPLATE, cep, complement, number, customerAddressId, customerId);
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
