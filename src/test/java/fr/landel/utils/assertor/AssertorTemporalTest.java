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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import fr.landel.utils.assertor.expect.Expect;
import fr.landel.utils.commons.DateUtils;

/**
 * Test {@link AssertorTemporal}
 *
 * @since May 29, 2016
 * @author Gilles
 *
 */
public class AssertorTemporalTest extends AbstractTest {

    /**
     * Test method for {@link AssertorTemporal#AssertorTemporal()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorTemporal());
    }

    /**
     * Test method for {@link AssertorTemporal#isEqual}.
     */
    @Test
    public void testIsEqualOK() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553640L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);

        final TemporalAmount temporalAmount = Duration.ofSeconds(5);

        assertTrue(Assertor.that(localDateTime1).isNotNull().isOK());
        assertFalse(Assertor.that(localDateTime1).not().isAround(localDateTime2, temporalAmount).isOK());
        assertTrue(Assertor.that(localDateTime1).isEqual(localDateTime2).and().isEqual(localDateTime1).isOK());
        assertTrue(Assertor.that(localDateTime1).isEqual(localDateTime2).or().isEqual(localDateTime1).isOK());
        assertFalse(Assertor.that(localDateTime1).isEqual(localDateTime2).xor().isEqual(localDateTime1).isOK());
        assertFalse(Assertor.that(localDateTime1).isEqual(localDateTime2).nand().isEqual(localDateTime1).isOK());
        assertFalse(Assertor.that(localDateTime1).isEqual(localDateTime2).nor().isEqual(localDateTime1).isOK());

        assertFalse(Assertor.that(localDateTime1).isEqual(localDateTime2).xor(Assertor.that(true).isTrue()).and().isEqual(localDateTime1)
                .isOK());

        try {
            Assertor.that((Temporal) null).isEqual((Temporal) null).orElseThrow();

            Assertor.that(localDateTime1).isEqual(localDateTime2).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        final LocalDateTime localDateTime3 = DateUtils.getLocalDateTime(new Date(1464475553641L));

        Expect.exception(() -> {
            Assertor.that(date1).isEqual(localDateTime3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime3).isEqual(date1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AssertorTemporal#isEqual}.
     */
    @Test
    public void testIsEqualKO() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553641L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);

        final TemporalAmount temporalAmount = Duration.ofSeconds(5);

        Assertor.that(localDateTime1).isAround(localDateTime2, temporalAmount).orElseThrow();
        Assertor.that(localDateTime1).isAround(localDateTime1, temporalAmount).orElseThrow();

        try {
            Assertor.that(localDateTime1).isEqual(localDateTime2).orElseThrow();
            Assertor.that((Date) null).isEqual(localDateTime2).and().isEqual(date2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isEqual((Date) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        try {
            Assertor.that((Date) null).isEqual(localDateTime2).orElseThrow();
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

            LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(calendar1);
            final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(calendar2);

            Assertor.that(localDateTime1).isAround(localDateTime2, Duration.ofSeconds(5)).orElseThrow();
            Assertor.that(localDateTime1).not().isAround(localDateTime2, Duration.of(5, ChronoUnit.MILLIS)).orElseThrow();

            calendar1.set(2016, 05, 29, 5, 5, 1);
            localDateTime1 = DateUtils.getLocalDateTime(calendar1);

            Assertor.that(localDateTime1).isAround(localDateTime2, Duration.ofSeconds(5)).orElseThrow();

            assertFalse(Assertor.that(localDateTime1).isAround(localDateTime2, null).isOK());
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

        LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(c1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(c2);

        final TemporalAmount temporalAmount = Duration.ofSeconds(2);

        try {
            // Check is date1 is not around the date2 by max 5s (after)
            Assertor.that(localDateTime1).isAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.that((LocalDateTime) null).isAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.that((LocalDateTime) null).isAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date2 = null
            Assertor.that(localDateTime1).isAround((LocalDateTime) null, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.that(localDateTime1).isAround(localDateTime2, null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 13);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.that(localDateTime1).isAround(localDateTime2, temporalAmount).orElseThrow();
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

        final TemporalAmount temporalAmount = Duration.ofSeconds(5);

        LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(c1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(c2);

        assertTrue(Assertor.that(localDateTime1).not().isAround(localDateTime2, temporalAmount).isOK());
        assertTrue(Assertor.that(localDateTime1).isNotAround(localDateTime2, temporalAmount).isOK());

        c1.set(2016, 05, 29, 5, 5, 1);
        localDateTime1 = DateUtils.getLocalDateTime(c1);

        assertTrue(Assertor.that(localDateTime1).isNotAround(localDateTime2, Duration.ofSeconds(3)).isOK());
        assertTrue(Assertor.that(localDateTime1).isNotAround(localDateTime2, Duration.ZERO).isOK());

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isNotAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(DateUtils.getLocalDateTime(c1)).isNotAround((LocalDateTime) null, temporalAmount).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "neither temporal can be null", JUNIT_ERROR);
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

        final TemporalAmount temporalAmount = Duration.ofSeconds(5);

        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(c1);
        LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(c2);

        try {
            // Check is date1 is not around the date2 by max 5s (after)
            Assertor.that(localDateTime1).isNotAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        // Check calendar amount = zero
        Assertor.that(localDateTime1).isNotAround(localDateTime2, Duration.ZERO).orElseThrow();

        // Check unsupported null amount
        Assertor.that(localDateTime1).isNotAround(localDateTime2, null).orElseThrow();

        c2.set(2016, 05, 29, 5, 5, 11);
        localDateTime2 = DateUtils.getLocalDateTime(c2);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.that(localDateTime1).isNotAround(localDateTime2, temporalAmount).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertorTemporal#isNotEqual}.
     */
    @Test
    public void testIsNotEqualOK() {
        try {
            final Date date1 = new Date(1464475553640L);
            final Date date2 = new Date(1464475553641L);

            final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
            final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);

            Assertor.that(localDateTime1).isNotEqual(localDateTime2).orElseThrow();
            Assertor.that((LocalDateTime) null).isNotEqual(localDateTime2).orElseThrow();
            Assertor.that(localDateTime1).isNotEqual((LocalDateTime) null).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorTemporal#isNotEqual}.
     */
    @Test
    public void testIsNotEqualKO() {
        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553640L);

        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);

        try {
            Assertor.that(localDateTime1).isNotEqual(localDateTime2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((LocalDateTime) null).isNotEqual((LocalDateTime) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertorTemporal#isAfter}.
     */
    @Test
    public void testIsAfterOK() {
        try {
            final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
            final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);

            final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(cal1);
            final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(cal2);

            Assertor.that(localDateTime1).isAfter(localDateTime2).orElseThrow();

            assertFalse(Assertor.that(localDateTime1).isAfter(localDateTime2, Duration.ZERO).isOK());
            Assertor.that(localDateTime1).isAfter(localDateTime2, Duration.ofHours(1)).orElseThrow();
            Assertor.that(localDateTime1).not().isAfter(localDateTime2, Duration.ofHours(-1)).orElseThrow();
            Assertor.that(localDateTime2).not().isAfter(localDateTime1, Duration.ofHours(1)).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct. " + e.getMessage());
        }
    }

    /**
     * Test method for {@link AssertCalendar#isAfter}.
     */
    @Test
    public void testIsAfterKO() {
        final Date date1 = new Date(1464475553640L);
        Date date2 = new Date(1464475553641L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);

        assertFalse(Assertor.that(localDateTime1).isAfter(localDateTime2).isOK());

        date2 = new Date(1464475553640L);
        localDateTime2 = DateUtils.getLocalDateTime(date2);

        try {
            Assertor.that(localDateTime1).isAfter(localDateTime2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((LocalDateTime) null).isAfter(localDateTime2).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that((LocalDateTime) null).isAfter((LocalDateTime) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that(localDateTime1).isAfter((LocalDateTime) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.that(localDateTime1).isAfter(localDateTime2).orElseThrow(new IOException(), true);
            fail();
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link AssertorTemporal#isAfterOrEqual}.
     */
    @Test
    public void testIsAfterOrEqualOK() {
        try {
            Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
            final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);

            LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(cal1);
            final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(cal2);

            Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2).orElseThrow();

            assertTrue(Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2, Duration.ofHours(1)).isOK());

            cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
            localDateTime1 = DateUtils.getLocalDateTime(cal1);

            Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2).orElseThrow();
            assertTrue(Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2, Duration.ZERO).isOK());
            assertTrue(Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2, Duration.ofHours(1)).isOK());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorTemporal#isAfterOrEqual}.
     */
    @Test
    public void testIsAfterOrEqualKO() {
        final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);

        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(cal1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(cal2);

        assertFalse(Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2).isOK());

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isAfterOrEqual(localDateTime2).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isAfterOrEqual((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isAfterOrEqual((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isAfterOrEqual(localDateTime2, Duration.ofHours(1)).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorTemporal#isBefore}.
     */
    @Test
    public void testIsBefore() {
        final Calendar cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);

        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(cal1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(cal2);

        assertTrue(Assertor.that(localDateTime2).isBefore(localDateTime1).isOK());

        Assertor.that(localDateTime2).isBefore(localDateTime1, Duration.ofHours(1)).orElseThrow();
        Assertor.that(localDateTime2).not().isBefore(localDateTime1, Duration.ofHours(-1)).orElseThrow();

        Expect.exception(() -> {
            Assertor.that(localDateTime2).isBefore(localDateTime1, Duration.ZERO).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isBefore(localDateTime2).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isBefore(localDateTime2, Duration.ofHours(1)).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isBefore(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isBefore(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isBefore((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isBefore((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime1).isBefore(localDateTime1).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorTemporal#isBeforeOrEqual}.
     */
    @Test
    public void testIsBeforeOrEqual() {
        Calendar cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
        final Calendar cal2 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(cal1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(cal2);

        assertTrue(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2).isOK());

        assertFalse(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2, Duration.ZERO).isOK());
        assertTrue(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2, Duration.ofHours(1)).isOK());
        assertFalse(Assertor.that(localDateTime2).isBeforeOrEqual(localDateTime1, Duration.ofHours(1)).isOK());
        assertFalse(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2, Duration.ofHours(-1)).isOK());

        cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        localDateTime1 = DateUtils.getLocalDateTime(cal1);

        assertTrue(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2).isOK());
        assertTrue(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2, Duration.ZERO).isOK());
        assertTrue(Assertor.that(localDateTime1).isBeforeOrEqual(localDateTime2, Duration.ofHours(1)).isOK());

        final Date date3 = new Date(1464475553641L);
        final Date date4 = new Date(1464475553640L);

        final LocalDateTime localDateTime3 = DateUtils.getLocalDateTime(date3);
        final LocalDateTime localDateTime4 = DateUtils.getLocalDateTime(date4);

        Expect.exception(() -> {
            Assertor.that(localDateTime3).isBeforeOrEqual(localDateTime4).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isBeforeOrEqual(localDateTime4).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime3).isBeforeOrEqual((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that((LocalDateTime) null).isBeforeOrEqual((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(localDateTime3).isBeforeOrEqual(localDateTime4).orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }
}
