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
        assertEquals(6, EnumStep.values().length);

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
    }
}
