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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.utils.AssertorTemporal;
import fr.landel.utils.commons.DateUtils;

/**
 * Test {@link AssertorTemporal}
 *
 * @since May 29, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorTemporalTest extends AbstractTest {

    /**
     * Test method for {@link AssertorTemporal} .
     */
    @Test
    public void testPredicateGet() {
        final Date date1 = new Date(1464475553640L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);

        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().hasHashCode(0).that(localDateTime1).isOK());
        assertTrue(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().hasHashCode(Objects.hashCode(localDateTime1)).that(localDateTime1).isOK());

        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().hasHashCode(0)
                .and(Assertor.<ChronoLocalDateTime<?>> ofTemporal().hasHashCode(0)).that(localDateTime1).isOK());
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

        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotNull().that(localDateTime1).isOK());
        assertFalse(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isAround(localDateTime2, temporalAmount).that(localDateTime1).isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).and().isEqual(localDateTime1).that(localDateTime1)
                .isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).or().isEqual(localDateTime1).that(localDateTime1)
                .isOK());
        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).xor().isEqual(localDateTime1)
                .that(localDateTime1).isOK());
        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).nand().isEqual(localDateTime1)
                .that(localDateTime1).isOK());
        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).nor().isEqual(localDateTime1)
                .that(localDateTime1).isOK());

        assertFalse(Assertor.that(localDateTime1).isEqual(localDateTime2).xor(Assertor.that(true).isTrue()).and().isEqual(localDateTime1)
                .isOK());

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual((Temporal) null).that((ChronoLocalDateTime<?>) null).orElseThrow();

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).that(localDateTime1).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        final LocalDateTime localDateTime3 = DateUtils.getLocalDateTime(new Date(1464475553641L));

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime3).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime1).that(localDateTime3).orElseThrow();
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

        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, temporalAmount).that(localDateTime1).orElseThrow();
        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime1, temporalAmount).that(localDateTime1).orElseThrow();

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).that(localDateTime1).orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).and().isEqual(date2).that((LocalDateTime) null)
                    .orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual((LocalDateTime) null).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isEqual(localDateTime2).that((LocalDateTime) null).orElseThrow();
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

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, Duration.ofSeconds(5)).that(localDateTime1)
                    .orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isAround(localDateTime2, Duration.of(5, ChronoUnit.MILLIS))
                    .that(localDateTime1).orElseThrow();

            calendar1.set(2016, 05, 29, 5, 5, 1);
            localDateTime1 = DateUtils.getLocalDateTime(calendar1);

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, Duration.ofSeconds(5)).that(localDateTime1)
                    .orElseThrow();

            assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, null).that(localDateTime1).isOK());
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
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, temporalAmount).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, temporalAmount).that((LocalDateTime) null)
                    .orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date1 = null
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, temporalAmount).that((LocalDateTime) null)
                    .orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check date2 = null
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround((LocalDateTime) null, temporalAmount).that(localDateTime1)
                    .orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            // Check calendar amount = zero
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, null).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        c2.set(2016, 05, 29, 5, 5, 13);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAround(localDateTime2, temporalAmount).that(localDateTime1).orElseThrow();
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

        assertTrue(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isAround(localDateTime2, temporalAmount).that(localDateTime1).isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, temporalAmount).that(localDateTime1).isOK());

        c1.set(2016, 05, 29, 5, 5, 1);
        localDateTime1 = DateUtils.getLocalDateTime(c1);

        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, Duration.ofSeconds(3)).that(localDateTime1)
                .isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, Duration.ZERO).that(localDateTime1).isOK());

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, temporalAmount).that((LocalDateTime) null)
                    .orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround((LocalDateTime) null, temporalAmount)
                    .that(DateUtils.getLocalDateTime(c1)).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "neither temporal can be null");
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
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, temporalAmount).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        // Check calendar amount = zero
        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, Duration.ZERO).that(localDateTime1).orElseThrow();

        // Check unsupported null amount
        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, null).that(localDateTime1).orElseThrow();

        c2.set(2016, 05, 29, 5, 5, 11);
        localDateTime2 = DateUtils.getLocalDateTime(c2);

        try {
            // Check is date1 is not around the date2 by max 5s (before)
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotAround(localDateTime2, temporalAmount).that(localDateTime1).orElseThrow();
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

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotEqual(localDateTime2).that(localDateTime1).orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotEqual(localDateTime2).that((LocalDateTime) null).orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotEqual((LocalDateTime) null).that(localDateTime1).orElseThrow();
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
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotEqual(localDateTime2).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isNotEqual((LocalDateTime) null).that((LocalDateTime) null).orElseThrow();
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

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2).that(localDateTime1).orElseThrow();

            assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2, Duration.ZERO).that(localDateTime1).isOK());
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2, Duration.ofHours(1)).that(localDateTime1).orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isAfter(localDateTime2, Duration.ofHours(-1)).that(localDateTime1)
                    .orElseThrow();
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isAfter(localDateTime1, Duration.ofHours(1)).that(localDateTime2)
                    .orElseThrow();
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

        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2).that(localDateTime1).isOK());

        assertFalse(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2, (TemporalAmount) null).that(localDateTime1).isOK());
        assertTrue(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime1, (TemporalAmount) null).that(localDateTime2).isOK());

        date2 = new Date(1464475553640L);
        localDateTime2 = DateUtils.getLocalDateTime(date2);

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2).that((LocalDateTime) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter((LocalDateTime) null).that((LocalDateTime) null).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter((LocalDateTime) null).that(localDateTime1).orElseThrow();
            fail();
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }

        try {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfter(localDateTime2).that(localDateTime1).orElseThrow(new IOException(),
                    true);
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

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2).that(localDateTime1).orElseThrow();

            assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2, Duration.ofHours(1))
                    .that(localDateTime1).isOK());

            cal1 = new GregorianCalendar(2016, 0, 1, 1, 1, 1);
            localDateTime1 = DateUtils.getLocalDateTime(cal1);

            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2).that(localDateTime1).orElseThrow();
            assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2, Duration.ZERO).that(localDateTime1)
                    .isOK());
            assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2, Duration.ofHours(1))
                    .that(localDateTime1).isOK());
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

        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2).that(localDateTime1).isOK());

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual((LocalDateTime) null).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual((LocalDateTime) null).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2).that(localDateTime1)
                    .orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isAfterOrEqual(localDateTime2, Duration.ofHours(1)).that(localDateTime1)
                    .orElseThrow(new IOException(), true);
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

        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1).that(localDateTime2).isOK());

        Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1, Duration.ofHours(1)).that(localDateTime2).orElseThrow();
        Assertor.<ChronoLocalDateTime<?>> ofTemporal().not().isBefore(localDateTime1, Duration.ofHours(-1)).that(localDateTime2)
                .orElseThrow();

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1, Duration.ZERO).that(localDateTime2).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime2).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime2, Duration.ofHours(1)).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore((LocalDateTime) null).that(localDateTime1).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore((LocalDateTime) null).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBefore(localDateTime1).that(localDateTime1).orElseThrow(new IOException(),
                    true);
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

        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2).that(localDateTime1).isOK());

        assertFalse(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2, Duration.ZERO).that(localDateTime1).isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2, Duration.ofHours(1)).that(localDateTime1)
                .isOK());
        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime1, Duration.ofHours(1)).that(localDateTime2)
                .isOK());
        assertFalse(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2, Duration.ofHours(-1))
                .that(localDateTime1).isOK());

        cal1 = new GregorianCalendar(2016, 0, 1, 2, 1, 1);
        localDateTime1 = DateUtils.getLocalDateTime(cal1);

        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2).that(localDateTime1).isOK());
        assertTrue(
                Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2, Duration.ZERO).that(localDateTime1).isOK());
        assertTrue(Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime2, Duration.ofHours(1)).that(localDateTime1)
                .isOK());

        final Date date3 = new Date(1464475553641L);
        final Date date4 = new Date(1464475553640L);

        final LocalDateTime localDateTime3 = DateUtils.getLocalDateTime(date3);
        final LocalDateTime localDateTime4 = DateUtils.getLocalDateTime(date4);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime4).that(localDateTime3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime4).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual((LocalDateTime) null).that(localDateTime3).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual((LocalDateTime) null).that((LocalDateTime) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.<ChronoLocalDateTime<?>> ofTemporal().isBeforeOrEqual(localDateTime4).that(localDateTime3)
                    .orElseThrow(new IOException(), true);
            fail();
        }, IOException.class);
    }
}
