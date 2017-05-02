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
        assertTrue(AssertorStepBoolean.class.isAssignableFrom(Assertor.that(true).getClass()));
        assertTrue(AssertorStepIterable.class.isAssignableFrom(Assertor.that(new ArrayList<Color>()).getClass()));
        assertTrue(AssertorStep.class.isAssignableFrom(Assertor.that(Color.BLACK).getClass()));
        assertTrue(AssertorStepMap.class.isAssignableFrom(Assertor.that(new HashMap<String, Integer>()).getClass()));
        assertTrue(AssertorStepNumber.class.isAssignableFrom(Assertor.that(12).getClass()));
        assertTrue(AssertorStepCharSequence.class.isAssignableFrom(Assertor.that("test").getClass()));
        assertTrue(AssertorStepArray.class.isAssignableFrom(Assertor.that(new String[0]).getClass()));
        assertTrue(AssertorStepDate.class.isAssignableFrom(Assertor.that(new Date()).getClass()));
        assertTrue(AssertorStepCalendar.class.isAssignableFrom(Assertor.that(Calendar.getInstance()).getClass()));
        assertTrue(AssertorStepClass.class.isAssignableFrom(Assertor.that(String.class).getClass()));
        assertTrue(AssertorStepThrowable.class.isAssignableFrom(Assertor.that(new IllegalArgumentException()).getClass()));
    }
}
