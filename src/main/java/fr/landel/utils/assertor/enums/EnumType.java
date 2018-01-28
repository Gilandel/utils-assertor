/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.assertor.enums;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import fr.landel.utils.commons.CastUtils;
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
     * Throwable type
     */
    THROWABLE,

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
    public static EnumType getType(final Object object) {
        EnumType type = UNKNOWN;
        if (object != null) {
            type = getTypeFromClass(CastUtils.getClass(object), object);
        }
        return type;
    }

    /**
     * Get the type of a class
     * 
     * @param clazz
     *            the class
     * @param object
     *            the object (may be {@code null})
     * @param <T>
     *            the object type
     * @return The type or {@link EnumType#UNKNOWN}
     */
    private static <T> EnumType getTypeFromClass(final Class<T> clazz, final T object) {
        EnumType type = UNKNOWN;

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
        } else if (Throwable.class.isAssignableFrom(clazz)) {
            type = THROWABLE;
        } else if (Class.class.isInstance(object)) {
            type = CLASS;
        }

        return type;
    }
}
