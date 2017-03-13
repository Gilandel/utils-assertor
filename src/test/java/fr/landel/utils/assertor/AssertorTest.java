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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

/**
 * Assert generated type object
 *
 * @since Aug 2, 2016
 * @author Gilles
 *
 */
public class AssertorTest extends AbstractTest {

    /**
     * Test method for {@link Assertor#Assertor()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new Assertor());
    }

    /**
     * Test method for {@link fr.landel.utils.assertor.Assertor#that}.
     */
    @Test
    public void testThatN() {
        assertTrue(PredicateAssertorBoolean.class.isAssignableFrom(Assertor.that(true).getClass()));
        assertTrue(PredicateAssertorIterable.class.isAssignableFrom(Assertor.that(new ArrayList<Color>()).getClass()));
        assertTrue(PredicateAssertor.class.isAssignableFrom(Assertor.that(Color.BLACK).getClass()));
        assertTrue(PredicateAssertorMap.class.isAssignableFrom(Assertor.that(new HashMap<String, Integer>()).getClass()));
        assertTrue(PredicateAssertorNumber.class.isAssignableFrom(Assertor.that(12).getClass()));
        assertTrue(PredicateAssertorCharSequence.class.isAssignableFrom(Assertor.that("test").getClass()));
        assertTrue(PredicateAssertorArray.class.isAssignableFrom(Assertor.that(new String[0]).getClass()));
        assertTrue(PredicateAssertorDate.class.isAssignableFrom(Assertor.that(new Date()).getClass()));
        assertTrue(PredicateAssertorCalendar.class.isAssignableFrom(Assertor.that(Calendar.getInstance()).getClass()));
        assertTrue(PredicateAssertorClass.class.isAssignableFrom(Assertor.that(String.class).getClass()));
    }
}
