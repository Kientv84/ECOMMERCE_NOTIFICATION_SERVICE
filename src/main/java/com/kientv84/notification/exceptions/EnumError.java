package com.kientv84.notification.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EnumError {

    //----------- SHIPMENT------------
    SHIPMENT_DATA_EXISTED("SHIPMENT-DTE", "Data exit", HttpStatus.CONFLICT),
    SHIPMENT_GET_ERROR("SHIPMENT-GET-ERROR", "Have error in process get shipment", HttpStatus.BAD_REQUEST),
    SHIPMENT_ERR_NOT_FOUND("SHIPMENT-CATE_NF", "Not found sub shipment with id", HttpStatus.BAD_REQUEST),
    SHIPMENT_ERR_DEL_EM("SHIPMENT-CATE-GA", "List ids to delete is empty", HttpStatus.BAD_REQUEST),
    SHIPMENT_NOT_FOUND("SHIPMENT-404", "shipment not found", HttpStatus.NOT_FOUND),
    SHIPMENT_SERVICE_UNAVAILABLE("SHIPMENT-503", "shipment service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    //----------- SHIPMENT TRACKING------------
    SHIPMENT_TRACKING_GET_ERROR(" SHIPMENT_TRACKING-GET-ERROR", "Have error in process get shipment tracking", HttpStatus.BAD_REQUEST),
    //----------------------
    INVALID_SHIPMENT_METHOD("INVALID-METHOD", "Invalid shipment method", HttpStatus.NOT_FOUND),

    SHIPMENT_PROCESS_NOTNULL("SHIPMENT-PROCESS-NULL", "shipment argument be null!", HttpStatus.BAD_REQUEST),
    //----------- EXTERNAL SERVICES ------------
    KAFKA_NOT_MESSAGE("KAFKA-NOT-MESSAGE", "Not found message!", HttpStatus.NOT_FOUND),
    //----------- EXTERNAL SERVICES ------------
    INTERNAL_ERROR("SHIPMENT-S-999", "Unexpected internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;


    public static EnumError fromCode(String code) {
        for (EnumError e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown DispatchError code: " + code);
    }
}

