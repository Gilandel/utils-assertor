/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.assertor;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import fr.landel.utils.commons.NumberUtils;

/**
 * List of supported type
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
public enum EnumType {
    /**
     * Unknown type
     */
    UNKNOWN,

    /**
     * Boolean type
     */
    BOOLEAN,

    /**
     * Enumeration type
     */
    ENUMERATION,

    /**
     * Number, integer type
     */
    NUMBER_INTEGER,

    /**
     * Number, floating point type
     */
    NUMBER_DECIMAL,

    /**
     * Date type
     */
    DATE,

    /**
     * Calendar type
     */
    CALENDAR,

    /**
     * Character type
     */
    CHARACTER,

    /**
     * String type
     */
    CHAR_SEQUENCE,

    /**
     * Class type
     */
    CLASS,

    /**
     * Array type
     */
    ARRAY,

    /**
     * Iterable type
     */
    ITERABLE,

    /**
     * Map type
     */
    MAP,

    /**
     * Temporal type
     */
    TEMPORAL,

    /**
     * Calendar field (only used by converter)
     */
    CALENDAR_FIELD;

    /**
     * Get the type of an object
     * 
     * @param object
     *            the object
     * @return The type or {@link EnumType#UNKNOWN}
     */
    protected static EnumType getType(final Object object) {
        EnumType type = UNKNOWN;
        if (object != null) {
            final Class<?> clazz = object.getClass();
            if (Number.class.isAssignableFrom(clazz)) {
                if (NumberUtils.isNumberInteger(clazz)) {
                    type = NUMBER_INTEGER;
                } else {
                    type = NUMBER_DECIMAL;
                }
            } else if (CharSequence.class.isAssignableFrom(clazz)) {
                type = CHAR_SEQUENCE;
            } else if (Boolean.class.isAssignableFrom(clazz)) {
                type = BOOLEAN;
            } else if (Character.class.isAssignableFrom(clazz)) {
                type = CHARACTER;
            } else if (clazz.isArray()) {
                type = ARRAY;
            } else if (Iterable.class.isAssignableFrom(clazz)) {
                type = ITERABLE;
            } else if (Map.class.isAssignableFrom(clazz)) {
                type = MAP;
            } else if (clazz.isEnum()) {
                type = ENUMERATION;
            } else if (Date.class.isAssignableFrom(clazz)) {
                type = DATE;
            } else if (Calendar.class.isAssignableFrom(clazz)) {
                type = CALENDAR;
            } else if (Temporal.class.isAssignableFrom(clazz)) {
                type = TEMPORAL;
            } else if (Class.class.isInstance(object)) {
                type = CLASS;
            }
        }
        return type;
    }
}
