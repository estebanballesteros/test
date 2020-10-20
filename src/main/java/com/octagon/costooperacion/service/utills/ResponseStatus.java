package com.octagon.costooperacion.service.utills;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {

    /**
     * Response 'HTTP' status codes
     */
    OK(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "La operación se realizo exitosamente. "),
    CREATED(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), "La entidad se creo exitosamente. "),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Los campos ingresados son incorrectos. "),

    /**
     * Response 'Prestamos' Specific Business status codes (1100-1200)
     */
    INVALID_TRANSACTION_TYPE(1301, "Invalid transaction type", "Tipo de transaccion invalida"),
    INVALID_CHANNEL(1302, "Invalid channel", "Canal invalido"),
    INVALID_CURRENCY(1302, "Invalid currency ", "Moneda invalida"),
    INVALID_PAYMENT_METHOD(1303, "Invalid payment method", "Forma de pago invalida"),
    INVALID_CUSTOMER_TYPE(1304, "Invalid customer type", "Tipo de cliente invalido"),
        PRICING_EXISTS(1305, "Pricing already exists, please create a new one or update", "Pricing existente");


    private final Number code;
    private final String message;
    private final String description;

    ResponseStatus(Number code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Number getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
