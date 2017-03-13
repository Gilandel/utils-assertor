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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import fr.landel.utils.assertor.expect.Expect;

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

        Expect.exception(() -> {
            Assertor.that(colors).validates(null).and().validates((object) -> Matchers.contains(matcherList).matches(object)).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the predicate cannot be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that((List<Color>) null).validates((object) -> Matchers.hasSize(nbColors).matches(object)).and()
                    .validates((object) -> Matchers.contains(matcherList).matches(object)).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the object 'null' should validate the predicate", JUNIT_ERROR);
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
}
