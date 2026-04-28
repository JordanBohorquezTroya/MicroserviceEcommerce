package com.ecommerce.product_service.exception;

import lombok.Getter;

@Getter
public class ResourceBadRequestException extends RuntimeException {
    private final String resource;
    private final String name;
    private final Object fieldValue;

    public ResourceBadRequestException(String resource, String name, Object fieldValue) {
        super(String.format("%s not found with %s: %s", resource, name, fieldValue));
        this.resource = resource;
        this.name = name;
        this.fieldValue = fieldValue;
    }
}
