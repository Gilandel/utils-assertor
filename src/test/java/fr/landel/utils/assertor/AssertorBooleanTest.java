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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Objects;

import org.junit.Test;

/**
 * Check {@link AssertorBoolean}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class AssertorBooleanTest extends AbstractTest {

    /**
     * Test method for {@link AssertorBoolean#AssertorBoolean()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorBoolean());
    }

    /**
     * Test method for {@link AssertorBoolean} .
     */
    @Test
    public void testPredicateGet() {
        assertFalse(Assertor.that(true).hasHashCode(0).isOK());
        assertTrue(Assertor.that(true).hasHashCode(Objects.hashCode(true)).isOK());
    }

    /**
     * Test method for {@link AssertorBoolean#isFalse()} .
     */
    @Test
    public void testIsFalse() {
        StepAssertor<Boolean> assertorResult = new StepAssertor<>(true, EnumType.BOOLEAN, null);
        assertFalse(AssertorBoolean.isFalse(assertorResult, null).getChecker().test(true, false));

        assertTrue(Assertor.that(true).isFalse().or(Assertor.that("").isEmpty()).isOK());

        try {
            Assertor.that(false).isFalse().orElseThrow("not false");
            Assertor.that(false).isFalse().and(true).not().isFalse().orElseThrow("not false");
            Assertor.that(false).isFalse().orElseThrow(new IllegalArgumentException(), true);
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(true).isFalse().orElseThrow("not false");
            fail();
        }, IllegalArgumentException.class, "not false");

        assertException(() -> {
            Assertor.that(true).isFalse("%s, '%s*'", "not false").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not false, 'true'");

        assertException(() -> {
            Assertor.that(true).isFalse().orElseThrow(new IOException("not false"), true);
            fail();
        }, IOException.class, "not false");
    }

    /**
     * Test method for {@link AssertorBoolean#isTrue()} .
     */
    @Test
    public void testIsTrue() {
        try {
            assertTrue(Assertor.that(true).isTrue().orElseThrow());

            // get last false
            assertFalse(Assertor.that(true).isTrue().and(false).not().isTrue().orElseThrow("not true"));
            assertTrue(Assertor.that(true).isTrue().orElseThrow(new IllegalArgumentException(), true));

            assertEquals("", Assertor.that(true).isTrue().and("").isEmpty().orElseThrow());

            assertTrue(Assertor.that(true).isTrue().and().not().isFalse().orElseThrow());
            assertTrue(Assertor.that(true).isTrue().or().isFalse().orElseThrow());
            assertTrue(Assertor.that(true).isTrue().xor().isFalse().orElseThrow());
            assertFalse(Assertor.that(true).isTrue().nand().isFalse().isOK());
            assertTrue(Assertor.that(true).isTrue().nor().isFalse().orElseThrow());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(false).isTrue().orElseThrow("not true");
            fail();
        }, IllegalArgumentException.class, "not true");

        assertException(() -> {
            Assertor.that(false).isTrue("%s, '%s*'", "not true").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not true, 'false'");

        assertException(() -> {
            Assertor.that(false).isTrue().orElseThrow(new IOException("not true"), true);
            fail();
        }, IOException.class, "not true");
    }
}
