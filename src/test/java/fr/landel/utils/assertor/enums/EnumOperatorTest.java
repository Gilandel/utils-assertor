/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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

import fr.landel.utils.assertor.enums.EnumOperator;

/**
 * Check {@link EnumOperator}
 *
 * @since Nov 27, 2016
 * @author Gilles
 *
 */
public class EnumOperatorTest {

    /**
     * Test method for {@link EnumOperator}.
     */
    @Test
    public void test() {
        assertNotNull(EnumOperator.values());
        assertEquals(5, EnumOperator.values().length);

        assertEquals(EnumOperator.AND, EnumOperator.valueOf("AND"));
        assertEquals(EnumOperator.AND, EnumOperator.valueOf(EnumOperator.class, "AND"));

        assertEquals(0, EnumOperator.AND.ordinal());
        assertEquals("operator.and", EnumOperator.AND.getKey());
        assertEquals("AND", EnumOperator.AND.name());

        assertEquals(1, EnumOperator.OR.ordinal());
        assertEquals("operator.or", EnumOperator.OR.getKey());
        assertEquals("OR", EnumOperator.OR.name());

        assertEquals(2, EnumOperator.XOR.ordinal());
        assertEquals("operator.xor", EnumOperator.XOR.getKey());
        assertEquals("XOR", EnumOperator.XOR.name());

        assertEquals(3, EnumOperator.NAND.ordinal());
        assertEquals("operator.nand", EnumOperator.NAND.getKey());
        assertEquals("NAND", EnumOperator.NAND.name());

        assertEquals(4, EnumOperator.NOR.ordinal());
        assertEquals("operator.nor", EnumOperator.NOR.getKey());
        assertEquals("NOR", EnumOperator.NOR.name());
    }

    /**
     * Test method for {@link EnumOperator#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(" AND ", EnumOperator.AND.toString());
        assertEquals(" OR ", EnumOperator.OR.toString());
        assertEquals(" XOR ", EnumOperator.XOR.toString());
        assertEquals(" NAND ", EnumOperator.NAND.toString());
        assertEquals(" NOR ", EnumOperator.NOR.toString());
    }
}
