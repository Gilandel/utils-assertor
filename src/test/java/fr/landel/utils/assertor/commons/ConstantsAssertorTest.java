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

import org.junit.Test;

/**
 * Check {@link ConstantsAssertor}
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class ConstantsAssertorTest extends AbstractTest {

    /**
     * Test method for {@link ConstantsAssertor#getProperty} .
     */
    @Test
    public void testGetProperty() {
        assertEquals(DEFAULT_ASSERTION, ConstantsAssertor.getProperty(null, ""));
        assertEquals("the boolean should be true", ConstantsAssertor.getProperty("boolean.true"));
        assertEquals("the boolean should be true", ConstantsAssertor.getProperty("boolean.true", "arg"));
        assertEquals("the object '{0}' should be null", ConstantsAssertor.getProperty("object.null"));
        assertEquals("the object 'arg' should be null", ConstantsAssertor.getProperty("object.null", "arg"));
        assertEquals("the object 'arg' should be null", ConstantsAssertor.getProperty("object.null", "arg", ""));
    }
}
