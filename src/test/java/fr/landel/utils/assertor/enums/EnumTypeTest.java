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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.landel.utils.assertor.enums.EnumType;

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
        assertEquals(16, EnumType.values().length);

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
        check(14, EnumType.THROWABLE, "THROWABLE");
        check(15, EnumType.CALENDAR_FIELD, "CALENDAR_FIELD");
    }

    private void check(final int ordinal, final EnumType type, final String name) {
        assertEquals(ordinal, type.ordinal());
        assertEquals(name, type.name());
    }
}
