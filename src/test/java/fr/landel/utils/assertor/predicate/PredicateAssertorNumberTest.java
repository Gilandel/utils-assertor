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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.utils.AssertorNumber;

/**
 * Check {@link AssertorNumber}
 *
 * @since Jul 16, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorNumberTest extends AbstractTest {

    /**
     * Test method for {@link AssertorNumber} .
     */
    @Test
    public void testPredicateGet() {
        assertFalse(Assertor.<Integer> ofNumber().hasHashCode(0).that(1).isOK());
        assertTrue(Assertor.<Integer> ofNumber().hasHashCode(Objects.hashCode(1)).that(1).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isLT(2).and().hasHashCode(Objects.hashCode(1)).that(1).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLT(2).or().hasHashCode(Objects.hashCode(1)).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(2).xor().hasHashCode(Objects.hashCode(1)).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(2).nand().hasHashCode(Objects.hashCode(1)).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(2).nor().hasHashCode(Objects.hashCode(1)).that(1).isOK());
    }

    /**
     * Test method for {@link AssertorNumber#isEqual}.
     */
    @Test
    public void testIsEqualN() {
        assertTrue(Assertor.<Integer> ofNumber().isEqual(2).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(1).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(null).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(1).that((Integer) null).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isEqual(null).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isEqual(2, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(1, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(null, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(1, "error1").that((Integer) null).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isEqual(null, "error1").that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isEqual(2, "error1").and().isNotNull().that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isEqual(2, "error1").or().isNull().that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isEqual(2, "error1").xor().isNull().that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isEqual(2, "error1").nand().isNull().that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isEqual(2, "error1").nor().isNull().that(2).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isEqual(1, "error1").that(2).getErrors().get());

        Assertor.<Integer> ofNumber().isEqual(2).and(Assertor.<Integer> ofNumber().isGT(10)).that(2).isOK();

        assertException(() -> {
            Assertor.<Integer> ofNumber().isEqual(1, "error1 %1$s* %s %2$s*", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0 1");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isEqual(1, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNotEqual}.
     */
    @Test
    public void testIsNotEqualN() {
        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(3).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isNotEqual(2).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(null).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(1).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(3, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isNotEqual(2, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(null, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isNotEqual(1, "error1").that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isNotEqual(2, "error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isNotEqual(2, "error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isNotEqual(2, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.<Integer> ofNumber().not().isNotEqual(1, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isZero}.
     */
    @Test
    public void testIsZero() {
        assertTrue(Assertor.<Integer> ofNumber().isZero().that(0).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isZero().that(12).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isZero().that(-1).isOK());

        assertTrue(Assertor.<Double> ofNumber().isZero().that(0.00).isOK());
        assertFalse(Assertor.<Double> ofNumber().isZero().that(0.0001).isOK());
        assertFalse(Assertor.<Double> ofNumber().isZero().that(-0.0001).isOK());

        assertFalse(Assertor.<Integer> ofNumber().isZero().that((Integer) null).isOK());
        assertTrue(Assertor.<Integer> ofNumber().not().isZero().that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isZero("error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isZero("error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isZero("error1").that(-1).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.<Integer> ofNumber().not().isZero("error1").that(0).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isPositive}.
     */
    @Test
    public void testIsPositive() {
        assertFalse(Assertor.<Integer> ofNumber().isPositive().that(0).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isPositive().that(12).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isPositive().that(-1).isOK());

        assertFalse(Assertor.<Double> ofNumber().isPositive().that(0.00).isOK());
        assertTrue(Assertor.<Double> ofNumber().isPositive().that(0.0001).isOK());
        assertFalse(Assertor.<Double> ofNumber().isPositive().that(-0.0001).isOK());

        assertFalse(Assertor.<Integer> ofNumber().isPositive().that((Integer) null).isOK());
        assertTrue(Assertor.<Integer> ofNumber().not().isPositive().that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isPositive("error1").that(0).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isPositive("error1 %1$s* %s", 0).that(0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isPositive("error1").that(-1).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.<Double> ofNumber().not().isPositive("error1").that(0.0001d).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNegative}.
     */
    @Test
    public void testIsNegative() {
        assertFalse(Assertor.<Integer> ofNumber().isNegative().that(0).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isNegative().that(12).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isNegative().that(-1).isOK());

        assertFalse(Assertor.<Double> ofNumber().isNegative().that(0.00).isOK());
        assertFalse(Assertor.<Double> ofNumber().isNegative().that(0.0001).isOK());
        assertTrue(Assertor.<Double> ofNumber().isNegative().that(-0.0001).isOK());

        assertFalse(Assertor.<Integer> ofNumber().isNegative().that((Integer) null).isOK());
        assertTrue(Assertor.<Integer> ofNumber().not().isNegative().that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isNegative("error1").that(0).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isNegative("error1 %1$s* %s", 0).that(0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isNegative("error1").that(1).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.<Double> ofNumber().not().isNegative("error1").that(-0.00001d).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGT}.
     */
    @Test
    public void testIsGT() {
        assertTrue(Assertor.<Integer> ofNumber().isGT(1).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(2).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(3).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGT(null).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(1).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isGT(1, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(2, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(3, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGT(null, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGT(1, "error1").that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isGT(2, "error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isGT(2, "error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isGT(2, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGTE}.
     */
    @Test
    public void testIsGTE() {
        assertTrue(Assertor.<Integer> ofNumber().isGTE(1).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGTE(2).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGTE(3).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGTE(null).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGTE(1).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isGTE(1, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGTE(2, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGTE(3, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isGTE(null, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isGTE(1, "error1").that((Integer) null).isOK());

        assertEquals("error1", Assertor.<Integer> ofNumber().isGTE(3, "error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isGTE(3, "error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isGTE(3, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLT}.
     */
    @Test
    public void testIsLT() {
        assertTrue(Assertor.<Integer> ofNumber().isLT(2).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(2).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(0).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(null).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLT(1).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isLT(2, "error1").that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(2, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(0, "error1").that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLT(null, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLT(1, "error1").that((Integer) null).isOK());
        assertEquals("error1", Assertor.<Integer> ofNumber().isLT(1, "error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isLT(1, "error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isLT(1, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLTE}.
     */
    @Test
    public void testIsLTE() {
        assertTrue(Assertor.<Integer> ofNumber().isLTE(2).that(1).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLTE(2).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLTE(0).that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLTE(null).that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLTE(1).that((Integer) null).isOK());

        assertTrue(Assertor.<Integer> ofNumber().isLTE(2, "error1").that(1).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLTE(2, "error1").that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLTE(0, "error1").that(1).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isLTE(null, "error1").that(2).isOK());
        assertTrue(Assertor.<Integer> ofNumber().isLTE(1, "error1").that((Integer) null).isOK());
        assertEquals("error1", Assertor.<Integer> ofNumber().isLTE(1, "error1").that(2).getErrors().get());

        assertException(() -> {
            Assertor.<Integer> ofNumber().isLTE(1, "error1 %1$s* %s", 0).that(2).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.<Integer> ofNumber().isLTE(1, "error1").that(2).orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isBetween}.
     */
    @Test
    public void testIsBetween() {
        Assertor.<Integer> ofNumber().isBetween(1, 3).that(2).orElseThrow(JUNIT_THROWABLE);

        // precondition
        assertFalse(Assertor.<Integer> ofNumber().isBetween(3, 3).that(2).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isBetween(null, 3).that(2).isOK());

        // check
        assertFalse(Assertor.<Integer> ofNumber().isBetween(1, 3).that(0).isOK());
        assertFalse(Assertor.<Integer> ofNumber().isBetween(1, 3).that(3).isOK());

        assertException(() -> {
            Assertor.<Double> ofNumber().isBetween(1_111.1, 2_111.2, Locale.US, "%1$,.3f* is not between %2$,.3f* and %3$,.3f*")
                    .that(2_111.2).orElseThrow();
        }, IllegalArgumentException.class, "2,111.200 is not between 1,111.100 and 2,111.200");
    }
}
