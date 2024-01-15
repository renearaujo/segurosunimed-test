package com.example.api.exception;

import java.text.MessageFormat;

/**
 * Indicates that a cep was not found on the ViaCep api
 *
 * @author René Araújo Vasconcelos - 1/11/2024 - 3:19 PM
 */
public class CepNotFoundException extends UnimedNotFoundException {

    /**
     * Message template format for the exception
     */
    public static final String MSG_TEMPLATE = "The CEP [{0}] was Not Found on ViaCep Api.";

    /**
     * cep not found
     */
    private final String cep;

    /**
     * default constructor
     *
     * @param cep cep not found
     * @author René Araújo Vasconcelos - 1/11/2024 - 3:15 PM
     */
    public CepNotFoundException(String cep) {
        super();
        this.cep = cep;
    }

    /**
     * Override method to create a custom message
     *
     * @return the message formatted
     * @author René Araújo Vasconcelos - 1/11/2024 - 3:15 PM
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
