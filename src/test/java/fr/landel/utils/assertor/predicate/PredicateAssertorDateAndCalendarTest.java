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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.utils.AssertorDate;
import fr.landel.utils.commons.DateUtils;

/**
 * Test {@link AssertorDate}
 *
 * @since May 29, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorDateAndCalendarTest extends AbstractTest {

    /**
     * Test method for {@link AssertorDate#isEqual}.
     */
    @Test
    public void testIsEqualOK() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553640L);
        final Calendar calendar1 = DateUtils.getCalendar(date1);
        final Calendar calendar2 = DateUtils.getCalendar(date2);

        assertTrue(Assertor.ofCalendar().isNotNull().that(calendar1).isOK());
        assertFalse(Assertor.ofCalendar().not().isAround(calendar2, Calendar.SECOND, 5).that(calendar1).isOK());
        assertTrue(Assertor.ofCalendar().isEqual(calendar2).and().isEqual(calendar1).that(calendar1).isOK());
        assertTrue(Assertor.ofCalendar().isEqual(calendar2).or().isEqual(calendar1).that(calendar1).isOK());
        assertFalse(Assertor.ofCalendar().isEqual(calendar2).xor().isEqual(calendar1).that(calendar1).isOK());
        assertFalse(Assertor.ofCalendar().isEqual(calendar2).nand().isEqual(calendar1).that(calendar1).isOK());
        assertFalse(Assertor.ofCalendar().isEqual(calendar2).nor().isEqual(calendar1).that(calendar1).isOK());

        assertTrue(Assertor.ofCalendar().isEqual(calendar2).that(calendar1).isOK());
        assertFalse(Assertor.ofCalendar().isAfter(calendar2).and().isAround(calendar2, Calendar.MONTH, 1).that(calendar1).isOK());
        assertTrue(Assertor.ofCalendar().isEqual(calendar2)
                .xor(Assertor.ofCalendar().isAfter(calendar2).and().isAround(calendar2, Calendar.MONTH, 1)).that(calendar1).isOK());

        assertTrue(Assertor.ofDate().isNotNull().that(date1).isOK());
        assertFalse(Assertor.ofDate().not().isAround(date2, Calendar.SECOND, 5).that(date1).isOK());
        assertTrue(Assertor.ofDate().isEqual(date2).and().isEqual(date1).that(date1).isOK());
        assertTrue(Assertor.ofDate().isEqual(date2).or().isEqual(date1).that(date1).isOK());
        assertFalse(Assertor.ofDate().isEqual(date2).xor().isEqual(date1).that(date1).isOK());
        assertFalse(Assertor.ofDate().isEqual(date2).nand().isEqual(date1).that(date1).isOK());
        assertFalse(Assertor.ofDate().isEqual(date2).nor().isEqual(date1).that(date1).isOK());

        assertTrue(Assertor.ofDate().isEqual(date2).that(date1).isOK());
        assertFalse(Assertor.ofDate().isAfter(date2).and().isAround(date2, Calendar.MONTH, 1).that(date1).isOK());
        assertTrue(Assertor.ofDate().isEqual(date2).xor(Assertor.ofDate().isAfter(date2).and().isAround(date2, Calendar.MONTH, 1))
                .that(date1).isOK());

        try {
            Assertor.ofDate().isEqual((Date) null).that((Date) null).orElseThrow();
            Assertor.ofCalendar().isEqual((Calendar) null).that((Calendar) null).orElseThrow();

            Assertor.ofDate().isEqual(date2).that(date1).orElseThrow();

            Assertor.ofCalendar().isEqual(calendar2).that(calendar1).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        final Date date3 = new Date(1464475553641L);

        assertException(() -> {
            Assertor.ofDate().isEqual(date3).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isEqual(date1).that(date3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AssertorDate#isEqual}.
     */
    @Test
    public void testIsEqualKO() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553641L);

        Assertor.ofDate().isAround(date2, Calendar.SECOND, 5).that(date1).orElseThrow();
        Assertor.ofDate().isAround(date1, Calendar.SECOND, 5).that(date1).orElseThrow();

        try {
            Assertor.ofDate().isEqual(date2).that(date1).orElseThrow();
            Assertor.ofDate().isEqual(date2).and().isEqual(date2).that((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        assertException(() -> {
            Assertor.ofDate().isEqual((Date) null).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        try {
            Assertor.ofDate().isEqual(date2).that((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertCalendar#isAround}.
     */
    @Test
    public void testIsAroundOK() {
        try {
            final Calendar calendar1 = Calendar.getInstance();
            final Calendar calendar2 = Calendar.getInstance();

            calendar1.set(2016, 05, 29, 5, 5, 6);
            calendar2.set(2016, 05, 29, 5, 5, 5);

            Assertor.ofCalendar().isAround(calendar2, Calendar.SECOND, 5).that(calendar1).orElseThrow();
            Assertor.ofCalendar().not().isAround(calendar2, Calendar.MILLISECOND, 5).that(calendar1).orElseThrow();

            calendar1.set(2016, 05, 29, 5, 5, 1);

            Assertor.ofCalendar().isAround(calendar2, Calendar.SECOND, 5).that(calendar1).orElseThrow();

            assertFalse(Assertor.ofCalendar().isAround(calendar2, -1, 2).that(calendar1).isOK());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertCalendar#isAround}.
     */
    @Test
    public void testIsAroundKO() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.set(2016, 05, 29, 5, 5, 9);
        c2.set(2016, 05, 29, 5, 5, 5);

        try {
            // Check is date1 is not around the date2 by max 5s (after)
            Assertor.ofCalendar().isAround(c2, Calendar.SECOND, 2).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.ofCalendar().isAround(c2, Calendar.SECOND, 2).that((Calendar) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.ofCalendar().isAround(c2, -1, -1).that((Calendar) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date2 = null
            Assertor.ofCalendar().isAround((Calendar) null, Calendar.SECOND, 2).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.ofCalendar().isAround(c2, Calendar.SECOND, 0).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check unsupported calendar field
            Assertor.ofCalendar().isAround(c2, 20, 0).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 13);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.ofCalendar().isAround(c2, Calendar.SECOND, 2).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertCalendar#isNotAround}.
     */
    @Test
    public void testIsNotAroundOK() {
        final Calendar c1 = Calendar.getInstance();
        final Calendar c2 = Calendar.getInstance();

        c1.set(2016, 05, 29, 5, 5, 11);
        c2.set(2016, 05, 29, 5, 5, 5);

        assertTrue(Assertor.ofCalendar().not().isAround(c2, Calendar.SECOND, 5).that(c1).isOK());
        assertTrue(Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 5).that(c1).isOK());

        assertTrue(Assertor.ofDate().not().isAround(c2.getTime(), Calendar.SECOND, 5).that(c1.getTime()).isOK());
        assertTrue(Assertor.ofDate().isNotAround(c2.getTime(), Calendar.SECOND, 5).that(c1.getTime()).isOK());

        c1.set(2016, 05, 29, 5, 5, 1);

        assertTrue(Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 3).that(c1).isOK());
        assertFalse(Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 0).that(c1).isOK());
        assertTrue(Assertor.ofCalendar().isNotAround(c2, -1, 0).that(c1).isOK());

        assertException(() -> {
            Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 5).that((Calendar) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofCalendar().isNotAround((Calendar) null, Calendar.SECOND, 5).that(c1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofCalendar().isNotAround((Calendar) null, Calendar.SECOND, 5).that(c1).orElseThrow();
            fail();
        }, IllegalArgumentException.class,
                "neither dates can be null, calendar field has to be a supported value and calendar amount different to 0");

        assertException(() -> {
            Assertor.ofDate().isNotAround((Date) null, Calendar.SECOND, 5).that(c1.getTime()).orElseThrow();
            fail();
        }, IllegalArgumentException.class,
                "neither dates can be null, calendar field has to be a supported value and calendar amount different to 0");
    }

    /**
     * Test method for {@link AssertCalendar#isNotAround}.
     */
    @Test
    public void testIsNotAroundKO() {
        final Calendar c1 = Calendar.getInstance();
        final Calendar c2 = Calendar.getInstance();

        c1.set(2016, 05, 29, 5, 5, 9);
        c2.set(2016, 05, 29, 5, 5, 5);

        try {
            // Check is date1 is not around the date2 by max 5s (after)
            Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 5).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 0).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check unsupported calendar field
            Assertor.ofCalendar().isNotAround(c2, 20, 0).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 11);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.ofCalendar().isNotAround(c2, Calendar.SECOND, 5).that(c1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertorDate#isNotEqual}.
     */
    @Test
    public void testIsNotEqualOK() {
        try {
            final Date date1 = new Date(1464475553640L);
            final Date date2 = new Date(1464475553641L);

            Assertor.ofCalendar().isNotEqual(DateUtils.getCalendar(date2)).that(DateUtils.getCalendar(date1)).orElseThrow();
            Assertor.ofDate().isNotEqual(date2).that(date1).orElseThrow();
            Assertor.ofDate().isNotEqual(date2).that((Date) null).orElseThrow();
            Assertor.ofDate().isNotEqual((Date) null).that(date1).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorDate#isNotEqual}.
     */
    @Test
    public void testIsNotEqualKO() {
        Date date1 = new Date(1464475553640L);
        Date date2 = new Date(1464475553640L);

        try {
            Assertor.ofDate().isNotEqual(date2).that(date1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.ofDate().isNotEqual((Date) null).that((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for
     * {@link AssertorDate#isBetween(StepAssertor, Comparable, Comparable, fr.landel.utils.assertor.commons.MessageAssertor)}.
     */
    @Test
    public void testIsBetween() {
        Date date1 = new Date(1464475553640L);
        Date date2 = new Date(1464475553641L);
        Date date3 = new Date(1464475553642L);

        PredicateAssertorStepDate predicateDate = Assertor.ofDate();

        assertTrue(predicateDate.isBetween(date1, date3).that(date2).isOK());
        assertTrue(predicateDate.isBetween(date1, date3).that(date1).isOK());
        assertTrue(predicateDate.isBetween(date1, date3).that(date3).isOK());
        assertFalse(predicateDate.isBetween(date2, date3).that(date1).isOK());
        assertFalse(predicateDate.isBetween(date1, date2).that(date3).isOK());

        assertFalse(predicateDate.not().isBetween(date1, date3).that(date2).isOK());
        assertFalse(predicateDate.not().isBetween(date1, date3).that(date1).isOK());
        assertFalse(predicateDate.not().isBetween(date1, date3).that(date3).isOK());
        assertTrue(predicateDate.not().isBetween(date2, date3).that(date1).isOK());
        assertTrue(predicateDate.not().isBetween(date1, date2).that(date3).isOK());

        assertException(() -> predicateDate.isBetween(null, null).that((Date) null).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");

        assertException(() -> predicateDate.isBetween(date2, date3).that(date1).orElseThrow(), IllegalArgumentException.class,
                Pattern.compile("the date '.*' should be between '.*' and '.*'"));

        Calendar cal1 = DateUtils.getCalendar(date1);
        Calendar cal2 = DateUtils.getCalendar(date2);
        Calendar cal3 = DateUtils.getCalendar(date3);

        PredicateAssertorStepCalendar predicateCalendar = Assertor.ofCalendar();

        assertTrue(predicateCalendar.isBetween(cal1, cal3).that(cal2).isOK());
        assertTrue(predicateCalendar.isBetween(cal1, cal3).that(cal1).isOK());
        assertTrue(predicateCalendar.isBetween(cal1, cal3).that(cal3).isOK());
        assertFalse(predicateCalendar.isBetween(cal2, cal3).that(cal1).isOK());
        assertFalse(predicateCalendar.isBetween(cal1, cal2).that(cal3).isOK());

        assertFalse(predicateCalendar.not().isBetween(cal1, cal3).that(cal2).isOK());
        assertFalse(predicateCalendar.not().isBetween(cal1, cal3).that(cal1).isOK());
        assertFalse(predicateCalendar.not().isBetween(cal1, cal3).that(cal3).isOK());
        assertTrue(predicateCalendar.not().isBetween(cal2, cal3).that(cal1).isOK());
        assertTrue(predicateCalendar.not().isBetween(cal1, cal2).that(cal3).isOK());

        assertException(() -> predicateCalendar.isBetween(cal2, cal3).that(cal1).orElseThrow(), IllegalArgumentException.class,
                Pattern.compile("the date '.*' should be between '.*' and '.*'"));

        assertException(() -> predicateCalendar.isBetween(null, null).that((Calendar) null).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
    }

    /**
     * Test method for {@link AssertorDate#isAfter}.
     */
    @Test
    public void testIsAfterOK() {
        try {
            final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
            final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
            final Date date1 = cal1.getTime();
            final Date date2 = cal2.getTime();

            Assertor.ofDate().isAfter(date2).that(date1).orElseThrow();

            Assertor.ofDate().isAfter(date2, -1, -1).that(date1).orElseThrow();
            Assertor.ofDate().isAfter(date2, -1, 0).that(date1).orElseThrow();
            Assertor.ofCalendar().isAfter(cal2, -1, -1).that(cal1).orElseThrow();
            Assertor.ofDate().isAfter(date2, Calendar.HOUR, 1).that(date1).orElseThrow();
            Assertor.ofCalendar().isAfter(cal2, Calendar.HOUR, 1).that(cal1).orElseThrow();
            Assertor.ofDate().not().isAfter(date2, Calendar.HOUR, -1).that(date1).orElseThrow();
            Assertor.ofCalendar().not().isAfter(cal2, Calendar.HOUR, -1).that(cal1).orElseThrow();
            Assertor.ofDate().not().isAfter(date1, Calendar.HOUR, 1).that(date2).orElseThrow();
            Assertor.ofCalendar().not().isAfter(cal1, Calendar.HOUR, 1).that(cal2).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertCalendar#isAfter}.
     */
    @Test
    public void testIsAfterKO() {
        final Date date1 = new Date(1464475553640L);
        Date date2 = new Date(1464475553641L);
        Calendar calendar1 = DateUtils.getCalendar(date1);
        Calendar calendar2 = DateUtils.getCalendar(date2);

        assertFalse(Assertor.ofDate().isAfter(date2).that(date1).isOK());
        assertFalse(Assertor.ofCalendar().isAfter(calendar2).that(calendar1).isOK());

        date2 = new Date(1464475553640L);

        try {
            Assertor.ofDate().isAfter(date2).that(date1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.ofDate().isAfter(date2).that((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.ofDate().isAfter((Date) null).that((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.ofDate().isAfter((Date) null).that(date1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.ofDate().isAfter(date2).that(date1).orElseThrow(new IOException(), true);
            fail();
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertorDate#isAfterOrEqual}.
     */
    @Test
    public void testIsAfterOrEqualOK() {
        try {
            final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
            final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
            Date date1 = cal1.getTime();
            final Date date2 = cal2.getTime();

            Assertor.ofDate().isAfterOrEqual(date2).that(date1).orElseThrow();
            Assertor.ofCalendar().isAfterOrEqual(cal2).that(cal1).orElseThrow();

            assertTrue(Assertor.ofDate().isAfterOrEqual(date2, Calendar.HOUR, 1).that(date1).isOK());
            assertTrue(Assertor.ofCalendar().isAfterOrEqual(cal2, Calendar.HOUR, 1).that(cal1).isOK());

            date1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1).getTime();

            Assertor.ofDate().isAfterOrEqual(date2).that(date1).orElseThrow();
            assertTrue(Assertor.ofDate().isAfterOrEqual(date2, -1, -1).that(date1).isOK());
            assertTrue(Assertor.ofCalendar().isAfterOrEqual(cal2, -1, -1).that(cal1).isOK());
            assertTrue(Assertor.ofDate().isAfterOrEqual(date2, Calendar.HOUR, 1).that(date1).isOK());
            assertTrue(Assertor.ofCalendar().isAfterOrEqual(cal2, Calendar.HOUR, 1).that(cal1).isOK());
            assertFalse(Assertor.ofCalendar().isAfterOrEqual(cal2, Calendar.HOUR, -1).that(cal1).isOK());
            assertFalse(Assertor.ofCalendar().isAfterOrEqual(cal1, Calendar.HOUR, -1).that(cal2).isOK());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorDate#isAfterOrEqual}.
     */
    @Test
    public void testIsAfterOrEqualKO() {
        final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        final Date date1 = cal1.getTime();
        final Date date2 = cal2.getTime();

        assertFalse(Assertor.ofDate().isAfterOrEqual(date2).that(date1).isOK());
        assertFalse(Assertor.ofCalendar().isAfterOrEqual(cal2).that(cal1).isOK());

        assertException(() -> {
            Assertor.ofDate().isAfterOrEqual(date2).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isAfterOrEqual((Date) null).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isAfterOrEqual((Date) null).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isAfterOrEqual(date2).that(date1).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorDate#isBefore}.
     */
    @Test
    public void testIsBefore() {
        final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
        final Date date1 = cal1.getTime();
        final Date date2 = cal2.getTime();

        assertTrue(Assertor.ofDate().isBefore(date1).that(date2).isOK());
        assertTrue(Assertor.ofCalendar().isBefore(cal1).that(cal2).isOK());

        Assertor.ofDate().isBefore(date1, -1, -1).that(date2).orElseThrow();
        Assertor.ofCalendar().isBefore(cal1, Calendar.HOUR, 1).that(cal2).orElseThrow();
        Assertor.ofDate().isBefore(date1, Calendar.HOUR, 1).that(date2).orElseThrow();
        Assertor.ofDate().not().isBefore(date1, Calendar.HOUR, -1).that(date2).orElseThrow();
        Assertor.ofCalendar().not().isBefore(cal2, Calendar.HOUR, 1).that(cal1).orElseThrow();

        assertException(() -> {
            Assertor.ofDate().isBefore(date2).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBefore(date1).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBefore(date1).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBefore((Date) null).that(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBefore((Date) null).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBefore(date1).that(date1).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorDate#isBeforeOrEqual}.
     */
    @Test
    public void testIsBeforeOrEqual() {
        final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        Date date1 = cal1.getTime();
        final Date date2 = cal2.getTime();

        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2).that(date1).isOK());
        assertTrue(Assertor.ofCalendar().isBeforeOrEqual(cal2).that(cal1).isOK());

        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2, -1, -1).that(date1).isOK());
        assertTrue(Assertor.ofCalendar().isBeforeOrEqual(cal2, -1, -1).that(cal1).isOK());
        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2, Calendar.HOUR, 1).that(date1).isOK());
        assertTrue(Assertor.ofCalendar().isBeforeOrEqual(cal2, Calendar.HOUR, 1).that(cal1).isOK());
        assertFalse(Assertor.ofCalendar().isBeforeOrEqual(cal2, Calendar.MINUTE, 1).that(cal1).isOK());
        assertFalse(Assertor.ofCalendar().isBeforeOrEqual(cal1, Calendar.HOUR, 1).that(cal2).isOK());

        date1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1).getTime();

        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2).that(date1).isOK());
        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2, -1, -1).that(date1).isOK());
        assertTrue(Assertor.ofDate().isBeforeOrEqual(date2, Calendar.HOUR, 1).that(date1).isOK());

        final Date date3 = new Date(1464475553641L);
        final Date date4 = new Date(1464475553640L);

        assertException(() -> {
            Assertor.ofDate().isBeforeOrEqual(date4).that(date3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBeforeOrEqual(date4).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBeforeOrEqual((Date) null).that(date3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBeforeOrEqual((Date) null).that((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofDate().isBeforeOrEqual(date4).that(date3).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }
}
