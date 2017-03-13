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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Check {@link EnumType}
 *
 * @since Mar 4, 2017
 * @author Gilles
 *
 */
public class EnumTypeTest {

    /**
     * Test method for {@link EnumType}.
     */
    @Test
    public void test() {
        assertNotNull(EnumType.values());
        assertEquals(15, EnumType.values().length);

        assertEquals(EnumType.ARRAY, EnumType.valueOf("ARRAY"));
        assertEquals(EnumType.ARRAY, EnumType.valueOf(EnumType.class, "ARRAY"));

        check(0, EnumType.UNKNOWN, "UNKNOWN");
        check(1, EnumType.BOOLEAN, "BOOLEAN");
        check(2, EnumType.ENUMERATION, "ENUMERATION");
        check(3, EnumType.NUMBER_INTEGER, "NUMBER_INTEGER");
        check(4, EnumType.NUMBER_DECIMAL, "NUMBER_DECIMAL");
        check(5, EnumType.DATE, "DATE");
        check(6, EnumType.CALENDAR, "CALENDAR");
        check(7, EnumType.CHARACTER, "CHARACTER");
        check(8, EnumType.CHAR_SEQUENCE, "CHAR_SEQUENCE");
        check(9, EnumType.CLASS, "CLASS");
        check(10, EnumType.ARRAY, "ARRAY");
        check(11, EnumType.ITERABLE, "ITERABLE");
        check(12, EnumType.MAP, "MAP");
        check(13, EnumType.TEMPORAL, "TEMPORAL");
        check(14, EnumType.CALENDAR_FIELD, "CALENDAR_FIELD");
    }

    private void check(final int ordinal, final EnumType type, final String name) {
        assertEquals(ordinal, type.ordinal());
        assertEquals(name, type.name());
    }
}
