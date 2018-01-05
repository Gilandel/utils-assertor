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

/**
 * Check {@link EnumStep}
 *
 * @since Nov 27, 2016
 * @author Gilles
 *
 */
public class EnumStepTest {

    /**
     * Test method for {@link EnumStep}.
     */
    @Test
    public void test() {
        assertNotNull(EnumStep.values());
        assertEquals(10, EnumStep.values().length);

        assertEquals(EnumStep.CREATION, EnumStep.valueOf("CREATION"));
        assertEquals(EnumStep.CREATION, EnumStep.valueOf(EnumStep.class, "CREATION"));

        assertEquals(0, EnumStep.CREATION.ordinal());
        assertEquals("CREATION", EnumStep.CREATION.name());
        assertEquals(1, EnumStep.NOT.ordinal());
        assertEquals("NOT", EnumStep.NOT.name());
        assertEquals(2, EnumStep.OBJECT.ordinal());
        assertEquals("OBJECT", EnumStep.OBJECT.name());
        assertEquals(3, EnumStep.OPERATOR.ordinal());
        assertEquals("OPERATOR", EnumStep.OPERATOR.name());
        assertEquals(4, EnumStep.ASSERTION.ordinal());
        assertEquals("ASSERTION", EnumStep.ASSERTION.name());
        assertEquals(5, EnumStep.SUB.ordinal());
        assertEquals("SUB", EnumStep.SUB.name());
        assertEquals(6, EnumStep.PREDICATE.ordinal());
        assertEquals("PREDICATE", EnumStep.PREDICATE.name());
        assertEquals(7, EnumStep.PREDICATE_OBJECT.ordinal());
        assertEquals("PREDICATE_OBJECT", EnumStep.PREDICATE_OBJECT.name());
        assertEquals(8, EnumStep.SUB_ASSERTOR.ordinal());
        assertEquals("SUB_ASSERTOR", EnumStep.SUB_ASSERTOR.name());
        assertEquals(9, EnumStep.PROPERTY.ordinal());
        assertEquals("PROPERTY", EnumStep.PROPERTY.name());
    }
}
