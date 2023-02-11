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
package fr.landel.utils.assertor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.utils.AssertorDate;
import fr.landel.utils.commons.DateUtils;

/**
 * Test {@link AssertorDate}
 *
 * @since May 29, 2016
 * @author Gilles
 *
 */
public class AssertorDateAndCalendarTest extends AbstractTest {

    /**
     * Test method for {@link AssertorDate#AssertorDate()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorDate());
    }

    /**
     * Test method for {@link AssertorDate#isEqual}.
     */
    @Test
    public void testIsEqualOK() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553640L);
        final Calendar calendar1 = DateUtils.getCalendar(date1);
        final Calendar calendar2 = DateUtils.getCalendar(date2);

        assertTrue(Assertor.that(calendar1).isNotNull().isOK());
        assertFalse(Assertor.that(calendar1).not().isAround(calendar2, Calendar.SECOND, 5).isOK());
        assertTrue(Assertor.that(calendar1).isEqual(calendar2).and().isEqual(calendar1).isOK());
        assertTrue(Assertor.that(calendar1).isEqual(calendar2).or().isEqual(calendar1).isOK());
        assertFalse(Assertor.that(calendar1).isEqual(calendar2).xor().isEqual(calendar1).isOK());
        assertFalse(Assertor.that(calendar1).isEqual(calendar2).nand().isEqual(calendar1).isOK());
        assertFalse(Assertor.that(calendar1).isEqual(calendar2).nor().isEqual(calendar1).isOK());

        assertFalse(Assertor.that(calendar1).isEqual(calendar2).xor(Assertor.that(true).isTrue()).and().isEqual(calendar1).isOK());

        assertTrue(Assertor.that(date1).isNotNull().isOK());
        assertFalse(Assertor.that(date1).not().isAround(date2, Calendar.SECOND, 5).isOK());
        assertTrue(Assertor.that(date1).isEqual(date2).and().isEqual(date1).isOK());
        assertTrue(Assertor.that(date1).isEqual(date2).or().isEqual(date1).isOK());
        assertFalse(Assertor.that(date1).isEqual(date2).xor().isEqual(date1).isOK());
        assertFalse(Assertor.that(date1).isEqual(date2).nand().isEqual(date1).isOK());
        assertFalse(Assertor.that(date1).isEqual(date2).nor().isEqual(date1).isOK());

        assertFalse(Assertor.that(date1).isEqual(date2).xor(Assertor.that(true).isTrue()).and().isEqual(date1).isOK());

        try {
            Assertor.that((Date) null).isEqual((Date) null).orElseThrow();
            Assertor.that((Calendar) null).isEqual((Calendar) null).orElseThrow();

            Assertor.that(date1).isEqual(date2).orElseThrow();

            Assertor.that(calendar1).isEqual(calendar2).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        final Date date3 = new Date(1464475553641L);

        assertException(() -> {
            Assertor.that(date1).isEqual(date3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date3).isEqual(date1).orElseThrow();
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

        Assertor.that(date1).isAround(date2, Calendar.SECOND, 5).orElseThrow();
        Assertor.that(date1).isAround(date1, Calendar.SECOND, 5).orElseThrow();

        try {
            Assertor.that(date1).isEqual(date2).orElseThrow();
            Assertor.that((Date) null).isEqual(date2).and().isEqual(date2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        assertException(() -> {
            Assertor.that(date1).isEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        try {
            Assertor.that((Date) null).isEqual(date2).orElseThrow();
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

            Assertor.that(calendar1).isAround(calendar2, Calendar.SECOND, 5).orElseThrow();
            Assertor.that(calendar1).not().isAround(calendar2, Calendar.MILLISECOND, 5).orElseThrow();

            calendar1.set(2016, 05, 29, 5, 5, 1);

            Assertor.that(calendar1).isAround(calendar2, Calendar.SECOND, 5).orElseThrow();

            assertFalse(Assertor.that(calendar1).isAround(calendar2, -1, 2).isOK());
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
            Assertor.that(c1).isAround(c2, Calendar.SECOND, 2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.that((Calendar) null).isAround(c2, Calendar.SECOND, 2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.that((Calendar) null).isAround(c2, -1, -1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date2 = null
            Assertor.that(c1).isAround((Calendar) null, Calendar.SECOND, 2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.that(c1).isAround(c2, Calendar.SECOND, 0).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check unsupported calendar field
            Assertor.that(c1).isAround(c2, 20, 0).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 13);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.that(c1).isAround(c2, Calendar.SECOND, 2).orElseThrow();
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

        assertTrue(Assertor.that(c1).not().isAround(c2, Calendar.SECOND, 5).isOK());
        assertTrue(Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 5).isOK());

        assertTrue(Assertor.that(c1.getTime()).not().isAround(c2.getTime(), Calendar.SECOND, 5).isOK());
        assertTrue(Assertor.that(c1.getTime()).isNotAround(c2.getTime(), Calendar.SECOND, 5).isOK());

        c1.set(2016, 05, 29, 5, 5, 1);

        assertTrue(Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 3).isOK());
        assertFalse(Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 0).isOK());
        assertTrue(Assertor.that(c1).isNotAround(c2, -1, 0).isOK());

        assertException(() -> {
            Assertor.that((Calendar) null).isNotAround(c2, Calendar.SECOND, 5).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(c1).isNotAround((Calendar) null, Calendar.SECOND, 5).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(c1).isNotAround((Calendar) null, Calendar.SECOND, 5).orElseThrow();
            fail();
        }, IllegalArgumentException.class,
                "neither dates can be null, calendar field has to be a supported value and calendar amount different to 0");

        assertException(() -> {
            Assertor.that(c1.getTime()).isNotAround((Date) null, Calendar.SECOND, 5).orElseThrow();
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
            Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 5).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 0).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check unsupported calendar field
            Assertor.that(c1).isNotAround(c2, 20, 0).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 11);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.that(c1).isNotAround(c2, Calendar.SECOND, 5).orElseThrow();
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

            Assertor.that(DateUtils.getCalendar(date1)).isNotEqual(DateUtils.getCalendar(date2)).orElseThrow();
            Assertor.that(date1).isNotEqual(date2).orElseThrow();
            Assertor.that((Date) null).isNotEqual(date2).orElseThrow();
            Assertor.that(date1).isNotEqual((Date) null).orElseThrow();
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
            Assertor.that(date1).isNotEqual(date2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((Date) null).isNotEqual((Date) null).orElseThrow();
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

        assertTrue(Assertor.that(date2).isBetween(date1, date3).isOK());
        assertTrue(Assertor.that(date1).isBetween(date1, date3).isOK());
        assertTrue(Assertor.that(date3).isBetween(date1, date3).isOK());
        assertFalse(Assertor.that(date1).isBetween(date2, date3).isOK());
        assertFalse(Assertor.that(date3).isBetween(date1, date2).isOK());

        assertFalse(Assertor.that(date2).not().isBetween(date1, date3).isOK());
        assertFalse(Assertor.that(date1).not().isBetween(date1, date3).isOK());
        assertFalse(Assertor.that(date3).not().isBetween(date1, date3).isOK());
        assertTrue(Assertor.that(date1).not().isBetween(date2, date3).isOK());
        assertTrue(Assertor.that(date3).not().isBetween(date1, date2).isOK());

        assertException(() -> Assertor.that((Date) null).isBetween(null, null).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
        assertException(() -> Assertor.that((Date) null).isBetween(date1, date3).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
        assertException(() -> Assertor.that((Date) null).isBetween(null, date3).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
        assertException(() -> Assertor.that((Date) null).isBetween(date1, null).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
        assertException(() -> Assertor.that(date1).isBetween(date1, null).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");
        assertException(() -> Assertor.that(date1).isBetween(null, date3).orElseThrow(), IllegalArgumentException.class,
                "neither dates can be null");

        assertException(() -> Assertor.that(date1).isBetween(date2, date3).orElseThrow(), IllegalArgumentException.class,
                Pattern.compile("the date '.*' should be between '.*' and '.*'"));

        Calendar cal1 = DateUtils.getCalendar(date1);
        Calendar cal2 = DateUtils.getCalendar(date2);
        Calendar cal3 = DateUtils.getCalendar(date3);

        assertTrue(Assertor.that(cal2).isBetween(cal1, cal3).isOK());
        assertTrue(Assertor.that(cal1).isBetween(cal1, cal3).isOK());
        assertTrue(Assertor.that(cal3).isBetween(cal1, cal3).isOK());
        assertFalse(Assertor.that(cal1).isBetween(cal2, cal3).isOK());
        assertFalse(Assertor.that(cal3).isBetween(cal1, cal2).isOK());

        assertFalse(Assertor.that(cal2).not().isBetween(cal1, cal3).isOK());
        assertFalse(Assertor.that(cal1).not().isBetween(cal1, cal3).isOK());
        assertFalse(Assertor.that(cal3).not().isBetween(cal1, cal3).isOK());
        assertTrue(Assertor.that(cal1).not().isBetween(cal2, cal3).isOK());
        assertTrue(Assertor.that(cal3).not().isBetween(cal1, cal2).isOK());

        assertException(() -> Assertor.that(cal1).isBetween(cal2, cal3).orElseThrow(), IllegalArgumentException.class,
                Pattern.compile("the date '.*' should be between '.*' and '.*'"));

        assertException(() -> Assertor.that((Calendar) null).isBetween(null, null).orElseThrow(), IllegalArgumentException.class,
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

            Assertor.that(date1).isAfter(date2).orElseThrow();

            Assertor.that(date1).isAfter(date2, -1, -1).orElseThrow();
            Assertor.that(date1).isAfter(date2, -1, 0).orElseThrow();
            Assertor.that(cal1).isAfter(cal2, -1, -1).orElseThrow();
            Assertor.that(date1).isAfter(date2, Calendar.HOUR, 1).orElseThrow();
            Assertor.that(cal1).isAfter(cal2, Calendar.HOUR, 1).orElseThrow();
            Assertor.that(date1).not().isAfter(date2, Calendar.HOUR, -1).orElseThrow();
            Assertor.that(cal1).not().isAfter(cal2, Calendar.HOUR, -1).orElseThrow();
            Assertor.that(date2).not().isAfter(date1, Calendar.HOUR, 1).orElseThrow();
            Assertor.that(cal2).not().isAfter(cal1, Calendar.HOUR, 1).orElseThrow();
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

        assertFalse(Assertor.that(date1).isAfter(date2).isOK());
        assertFalse(Assertor.that(calendar1).isAfter(calendar2).isOK());

        date2 = new Date(1464475553640L);

        try {
            Assertor.that(date1).isAfter(date2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((Date) null).isAfter(date2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((Date) null).isAfter((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that(date1).isAfter((Date) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that(date1).isAfter(date2).orElseThrow(new IOException(), true);
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

            Assertor.that(date1).isAfterOrEqual(date2).orElseThrow();
            Assertor.that(cal1).isAfterOrEqual(cal2).orElseThrow();

            assertTrue(Assertor.that(date1).isAfterOrEqual(date2, Calendar.HOUR, 1).isOK());
            assertTrue(Assertor.that(cal1).isAfterOrEqual(cal2, Calendar.HOUR, 1).isOK());

            date1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1).getTime();

            Assertor.that(date1).isAfterOrEqual(date2).orElseThrow();
            assertTrue(Assertor.that(date1).isAfterOrEqual(date2, -1, -1).isOK());
            assertTrue(Assertor.that(cal1).isAfterOrEqual(cal2, -1, -1).isOK());
            assertTrue(Assertor.that(date1).isAfterOrEqual(date2, Calendar.HOUR, 1).isOK());
            assertTrue(Assertor.that(cal1).isAfterOrEqual(cal2, Calendar.HOUR, 1).isOK());
            assertFalse(Assertor.that(cal1).isAfterOrEqual(cal2, Calendar.HOUR, -1).isOK());
            assertFalse(Assertor.that(cal2).isAfterOrEqual(cal1, Calendar.HOUR, -1).isOK());
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

        assertFalse(Assertor.that(date1).isAfterOrEqual(date2).isOK());
        assertFalse(Assertor.that(cal1).isAfterOrEqual(cal2).isOK());

        assertException(() -> {
            Assertor.that((Date) null).isAfterOrEqual(date2).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date1).isAfterOrEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Date) null).isAfterOrEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date1).isAfterOrEqual(date2).orElseThrow(new IOException(), true);
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

        assertTrue(Assertor.that(date2).isBefore(date1).isOK());
        assertTrue(Assertor.that(cal2).isBefore(cal1).isOK());

        Assertor.that(date2).isBefore(date1, -1, -1).orElseThrow();
        Assertor.that(cal2).isBefore(cal1, Calendar.HOUR, 1).orElseThrow();
        Assertor.that(date2).isBefore(date1, Calendar.HOUR, 1).orElseThrow();
        Assertor.that(date2).not().isBefore(date1, Calendar.HOUR, -1).orElseThrow();
        Assertor.that(cal1).not().isBefore(cal2, Calendar.HOUR, 1).orElseThrow();

        assertException(() -> {
            Assertor.that(date1).isBefore(date2).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date1).isBefore(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Date) null).isBefore(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date1).isBefore((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Date) null).isBefore((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date1).isBefore(date1).orElseThrow(new IOException(), true);
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

        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2).isOK());
        assertTrue(Assertor.that(cal1).isBeforeOrEqual(cal2).isOK());

        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2, -1, -1).isOK());
        assertTrue(Assertor.that(cal1).isBeforeOrEqual(cal2, -1, -1).isOK());
        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2, Calendar.HOUR, 1).isOK());
        assertTrue(Assertor.that(cal1).isBeforeOrEqual(cal2, Calendar.HOUR, 1).isOK());
        assertFalse(Assertor.that(cal1).isBeforeOrEqual(cal2, Calendar.MINUTE, 1).isOK());
        assertFalse(Assertor.that(cal2).isBeforeOrEqual(cal1, Calendar.HOUR, 1).isOK());

        date1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1).getTime();

        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2).isOK());
        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2, -1, -1).isOK());
        assertTrue(Assertor.that(date1).isBeforeOrEqual(date2, Calendar.HOUR, 1).isOK());

        final Date date3 = new Date(1464475553641L);
        final Date date4 = new Date(1464475553640L);

        assertException(() -> {
            Assertor.that(date3).isBeforeOrEqual(date4).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Date) null).isBeforeOrEqual(date4).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date3).isBeforeOrEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Date) null).isBeforeOrEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(date3).isBeforeOrEqual(date4).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }
}
