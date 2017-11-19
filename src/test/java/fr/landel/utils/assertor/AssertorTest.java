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
