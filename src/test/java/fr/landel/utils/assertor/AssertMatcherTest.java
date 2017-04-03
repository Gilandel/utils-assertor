/*
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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.DateUtils;
import fr.landel.utils.commons.MapUtils2;
import fr.landel.utils.commons.expect.Expect;

/**
 * Check assert matcher
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class AssertMatcherTest extends AbstractTest {

    /**
     * Test method for {@link Expect#that(Object, Matcher)} .
     */
    @Test
    public void testThatOK() {
        final String red = "red";
        final String green = "green";
        final String blue = "blue";
        final String alpha = "alpha";

        final int max = 255;

        final int nbColors = 4;
        final List<Color> colors = new ArrayList<>(nbColors);

        colors.add(Color.BLACK);
        colors.add(Color.WHITE);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);

        Matcher<Color> matcherAlpha = Matchers.hasProperty(alpha, Matchers.is(max));

        Matcher<Color> matcherBlack = Matchers.allOf(Matchers.hasProperty(green, Matchers.is(0)), Matchers.hasProperty(red, Matchers.is(0)),
                Matchers.hasProperty(blue, Matchers.is(0)), matcherAlpha);

        Matcher<Color> matcherWhite = Matchers.allOf(Matchers.hasProperty(green, Matchers.is(max)),
                Matchers.hasProperty(red, Matchers.is(max)), Matchers.hasProperty(blue, Matchers.is(max)), matcherAlpha);

        Matcher<Color> matcherBlue = Matchers.allOf(Matchers.hasProperty(green, Matchers.is(0)), Matchers.hasProperty(red, Matchers.is(0)),
                Matchers.hasProperty(blue, Matchers.is(max)), matcherAlpha);

        Matcher<Color> matcherCyan = Matchers.allOf(Matchers.hasProperty(green, Matchers.is(max)),
                Matchers.hasProperty(red, Matchers.is(0)), Matchers.hasProperty(blue, Matchers.is(max)), matcherAlpha);

        List<Matcher<? super Color>> matcherList = Arrays.<Matcher<? super Color>> asList(matcherBlack, matcherWhite, matcherBlue,
                matcherCyan);

        assertTrue(Assertor.that(colors).validates((object) -> Matchers.hasSize(nbColors).matches(object)).and()
                .validates((object) -> Matchers.contains(matcherList).matches(object)).isOK());

        assertException(() -> {
            Assertor.that(colors).validates(null).and().validates((object) -> Matchers.contains(matcherList).matches(object)).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the predicate cannot be null");

        assertException(() -> {
            Assertor.that((List<Color>) null).validates((object) -> Matchers.hasSize(nbColors).matches(object)).and()
                    .validates((object) -> Matchers.contains(matcherList).matches(object)).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the object 'null' should validate the predicate");
    }

    /**
     * Test method for {@link Expect#that(Object, Matcher)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThatKO() {
        final List<Color> colors = new ArrayList<>();

        colors.add(Color.BLACK);
        colors.add(Color.WHITE);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);

        Assertor.that(colors).validates((object) -> Matchers.hasSize(colors.size() - 1).matches(object)).orElseThrow();
    }

    /**
     * Test for method {@link PredicateStep#that(Object)}
     */
    @Test
    public void testMatches() {
        final PredicateStepNumber<Integer> predicate = Assertor.matcherNumber(Integer.class).isGT(13);

        assertTrue(predicate.that(158).isOK());
        assertFalse(predicate.that(12).isOK());
        assertTrue(predicate.that(158).isOK());

        Assertor.matcherNumber(Integer.class).isGT(13).and(Assertor.matcherNumber(Integer.class).isGT(10)).and().isEqual(11).that(15)
                .isOK();
        Assertor.matcherNumber(Integer.class).isGT(13).and(Assertor.matcherNumber(Long.class).isGT(18L)).and().isEqual(11).that(15).isOK();

        Assertor.matcherBoolean().isFalse().that(false).orElseThrow(JUNIT_THROWABLE);
        Assertor.matcherCharSequence(String.class).startsWith("t").that("test").orElseThrow(JUNIT_THROWABLE);
        Assertor.matcherThrowable(IOException.class).hasCauseAssignableFrom(IllegalArgumentException.class, false)
                .that(new IOException(new IllegalArgumentException())).orElseThrow(JUNIT_THROWABLE);
        Assertor.matcherArray(String.class).hasLength(1).that(new String[] {""}).orElseThrow(JUNIT_THROWABLE);
        Assertor.matcherIterable(CastUtils.getTypedListClass(String.class)).contains("").that(Arrays.asList(""))
                .orElseThrow(JUNIT_THROWABLE);

        Assertor.matcherCalendar().isAfter(new GregorianCalendar(2000, 0, 1)).that(Calendar.getInstance()).orElseThrow(JUNIT_THROWABLE);
        Assertor.matcherDate().isAfter(new GregorianCalendar(2000, 0, 1).getTime()).that(new Date()).orElseThrow(JUNIT_THROWABLE);

        final Date date1 = new Date(1464475553640L);
        final Date date2 = new Date(1464475553641L);
        final LocalDateTime localDateTime1 = DateUtils.getLocalDateTime(date1);
        final LocalDateTime localDateTime2 = DateUtils.getLocalDateTime(date2);
        Assertor.matcherTemporal(CastUtils.getClass(localDateTime1)).isAfter(localDateTime1).that(localDateTime2)
                .orElseThrow(JUNIT_THROWABLE);

        Assertor.matcherMap(String.class, Integer.class).contains("test").that(MapUtils2.newHashMap("test", 12))
                .orElseThrow(JUNIT_THROWABLE);

        Assertor.matcherEnum(EnumType.class).hasName("UNKNOWN").that(EnumType.UNKNOWN).orElseThrow(JUNIT_THROWABLE);

        Assertor.matcherClass(IOException.class).isAssignableFrom(Exception.class).that(IOException.class).orElseThrow(JUNIT_THROWABLE);

        Assertor.matcherObject(Color.class).isNotNull().that(Color.BLACK).orElseThrow(JUNIT_THROWABLE);

        assertException(() -> {
            Assertor.that(15).isGT(13).that(15).isOK();
        }, UnsupportedOperationException.class);

        assertException(() -> {
            Assertor.matcherNumber(Integer.class).isGT(13).and(11).isEqual(11).that(15).isOK();
        }, UnsupportedOperationException.class);

        assertException(() -> {
            Assertor.matcherNumber(Integer.class).isGT(13).and(Assertor.that(12).isGT(10)).and().isEqual(11).that(15).isOK();
        }, UnsupportedOperationException.class);

        final Matcher<Integer> matcher = new AssertorMatcher<>(predicate);
        assertThat(158, matcher);
    }
}
