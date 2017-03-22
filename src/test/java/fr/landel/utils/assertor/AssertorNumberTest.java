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

import org.junit.Test;

/**
 * Check {@link AssertorNumber}
 *
 * @since Jul 16, 2016
 * @author Gilles
 *
 */
public class AssertorNumberTest extends AbstractTest {

    /**
     * Test method for {@link AssertorNumber#AssertorNumber()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorNumber());
    }

    /**
     * Test method for {@link AssertorNumber#isEqual}.
     */
    @Test
    public void testIsEqualN() {
        assertTrue(Assertor.that(2).isEqual(2).isOK());
        assertFalse(Assertor.that(2).isEqual(1).isOK());
        assertFalse(Assertor.that(2).isEqual(null).isOK());
        assertFalse(Assertor.that((Integer) null).isEqual(1).isOK());
        assertTrue(Assertor.that((Integer) null).isEqual(null).isOK());

        assertTrue(Assertor.that(2).isEqual(2, "error1").isOK());
        assertFalse(Assertor.that(2).isEqual(1, "error1").isOK());
        assertFalse(Assertor.that(2).isEqual(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isEqual(1, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isEqual(null, "error1").isOK());

        assertTrue(Assertor.that(2).isEqual(2, "error1").and().isNotNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").or().isNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").xor().isNull().isOK());
        assertFalse(Assertor.that(2).isEqual(2, "error1").nand().isNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").nor().isNull().isOK());

        assertEquals("error1", Assertor.that(2).isEqual(1, "error1").getErrors().get());

        Assertor.that(2).isEqual(2).and(Assertor.that(12).isGT(10)).isOK();

        assertException(() -> {
            Assertor.that(2).isEqual(1, "error1 %1$s* %s %2$s*", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0 1");

        assertException(() -> {
            Assertor.that(2).isEqual(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNotEqual}.
     */
    @Test
    public void testIsNotEqualN() {
        assertTrue(Assertor.that(2).isNotEqual(3).isOK());
        assertFalse(Assertor.that(2).isNotEqual(2).isOK());
        assertTrue(Assertor.that(2).isNotEqual(null).isOK());
        assertTrue(Assertor.that((Integer) null).isNotEqual(1).isOK());

        assertTrue(Assertor.that(2).isNotEqual(3, "error1").isOK());
        assertFalse(Assertor.that(2).isNotEqual(2, "error1").isOK());
        assertTrue(Assertor.that(2).isNotEqual(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isNotEqual(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isNotEqual(2, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isNotEqual(2, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isNotEqual(2, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(2).not().isNotEqual(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isZero}.
     */
    @Test
    public void testIsZero() {
        assertTrue(Assertor.that(0).isZero().isOK());
        assertFalse(Assertor.that(12).isZero().isOK());
        assertFalse(Assertor.that(-1).isZero().isOK());

        assertTrue(Assertor.that(0.00).isZero().isOK());
        assertFalse(Assertor.that(0.0001).isZero().isOK());
        assertFalse(Assertor.that(-0.0001).isZero().isOK());

        assertFalse(Assertor.that((Integer) null).isZero().isOK());
        assertTrue(Assertor.that((Integer) null).not().isZero().isOK());

        assertEquals("error1", Assertor.that(2).isZero("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isZero("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(-1).isZero("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(0).not().isZero("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isPositive}.
     */
    @Test
    public void testIsPositive() {
        assertFalse(Assertor.that(0).isPositive().isOK());
        assertTrue(Assertor.that(12).isPositive().isOK());
        assertFalse(Assertor.that(-1).isPositive().isOK());

        assertFalse(Assertor.that(0.00).isPositive().isOK());
        assertTrue(Assertor.that(0.0001).isPositive().isOK());
        assertFalse(Assertor.that(-0.0001).isPositive().isOK());

        assertFalse(Assertor.that((Integer) null).isPositive().isOK());
        assertTrue(Assertor.that((Integer) null).not().isPositive().isOK());

        assertEquals("error1", Assertor.that(0).isPositive("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(0).isPositive("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.that(-1).isPositive("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(0.0001d).not().isPositive("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNegative}.
     */
    @Test
    public void testIsNegative() {
        assertFalse(Assertor.that(0).isNegative().isOK());
        assertFalse(Assertor.that(12).isNegative().isOK());
        assertTrue(Assertor.that(-1).isNegative().isOK());

        assertFalse(Assertor.that(0.00).isNegative().isOK());
        assertFalse(Assertor.that(0.0001).isNegative().isOK());
        assertTrue(Assertor.that(-0.0001).isNegative().isOK());

        assertFalse(Assertor.that((Integer) null).isNegative().isOK());
        assertTrue(Assertor.that((Integer) null).not().isNegative().isOK());

        assertEquals("error1", Assertor.that(0).isNegative("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(0).isNegative("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.that(1).isNegative("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(-0.00001d).not().isNegative("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGT}.
     */
    @Test
    public void testIsGT() {
        assertTrue(Assertor.that(2).isGT(1).isOK());
        assertFalse(Assertor.that(2).isGT(2).isOK());
        assertFalse(Assertor.that(2).isGT(3).isOK());
        assertTrue(Assertor.that(2).isGT(null).isOK());
        assertFalse(Assertor.that((Integer) null).isGT(1).isOK());

        assertTrue(Assertor.that(2).isGT(1, "error1").isOK());
        assertFalse(Assertor.that(2).isGT(2, "error1").isOK());
        assertFalse(Assertor.that(2).isGT(3, "error1").isOK());
        assertTrue(Assertor.that(2).isGT(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isGT(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isGT(2, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isGT(2, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isGT(2, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGTE}.
     */
    @Test
    public void testIsGTE() {
        assertTrue(Assertor.that(2).isGTE(1).isOK());
        assertTrue(Assertor.that(2).isGTE(2).isOK());
        assertFalse(Assertor.that(2).isGTE(3).isOK());
        assertTrue(Assertor.that(2).isGTE(null).isOK());
        assertFalse(Assertor.that((Integer) null).isGTE(1).isOK());

        assertTrue(Assertor.that(2).isGTE(1, "error1").isOK());
        assertTrue(Assertor.that(2).isGTE(2, "error1").isOK());
        assertFalse(Assertor.that(2).isGTE(3, "error1").isOK());
        assertTrue(Assertor.that(2).isGTE(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isGTE(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isGTE(3, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isGTE(3, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isGTE(3, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLT}.
     */
    @Test
    public void testIsLT() {
        assertTrue(Assertor.that(1).isLT(2).isOK());
        assertFalse(Assertor.that(2).isLT(2).isOK());
        assertFalse(Assertor.that(1).isLT(0).isOK());
        assertFalse(Assertor.that(2).isLT(null).isOK());
        assertTrue(Assertor.that((Integer) null).isLT(1).isOK());

        assertTrue(Assertor.that(1).isLT(2, "error1").isOK());
        assertFalse(Assertor.that(2).isLT(2, "error1").isOK());
        assertFalse(Assertor.that(1).isLT(0, "error1").isOK());
        assertFalse(Assertor.that(2).isLT(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isLT(1, "error1").isOK());
        assertEquals("error1", Assertor.that(2).isLT(1, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isLT(1, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isLT(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLTE}.
     */
    @Test
    public void testIsLTE() {
        assertTrue(Assertor.that(1).isLTE(2).isOK());
        assertTrue(Assertor.that(2).isLTE(2).isOK());
        assertFalse(Assertor.that(1).isLTE(0).isOK());
        assertFalse(Assertor.that(2).isLTE(null).isOK());
        assertTrue(Assertor.that((Integer) null).isLTE(1).isOK());

        assertTrue(Assertor.that(1).isLTE(2, "error1").isOK());
        assertTrue(Assertor.that(2).isLTE(2, "error1").isOK());
        assertFalse(Assertor.that(1).isLTE(0, "error1").isOK());
        assertFalse(Assertor.that(2).isLTE(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isLTE(1, "error1").isOK());
        assertEquals("error1", Assertor.that(2).isLTE(1, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isLTE(1, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isLTE(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }
}
