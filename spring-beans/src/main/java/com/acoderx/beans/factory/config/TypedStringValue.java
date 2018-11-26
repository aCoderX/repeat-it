package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-22
 */
public class TypedStringValue {
    public TypedStringValue(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
