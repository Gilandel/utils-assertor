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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorArray;

/**
 * Check {@link AssertorArray}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class AssertorArrayTest extends AbstractTest {

    /**
     * Test method for {@link AssertorArray#AssertorArray()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorArray());
    }

    /**
     * Test method for {@link AssertorArray} .
     */
    @Test
    public void testPredicateGet() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.that(array).hasHashCode(0).isOK());
        assertTrue(Assertor.that(array).hasHashCode(Objects.hashCode((Object[]) array)).isOK());
    }

    /**
     * Test method for {@link AssertorArray#hasLength} .
     */
    @Test
    public void testHasLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.that(array).hasLength(2).isOK());
        assertFalse(Assertor.that(array).hasLength(1).isOK());
        assertFalse(Assertor.that((String[]) null).hasLength(1).isOK());
        assertFalse(Assertor.that(array).hasLength(-1).isOK());

        assertTrue(Assertor.that(array).hasLength(2).and(Assertor.that(true).isFalse().or().isTrue()).isOK());
        assertTrue(Assertor.that(array).isNotEmpty().and().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().or().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().xor().hasLength(2).isOK());
        assertFalse(Assertor.that(array).isEmpty().nand().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().nor().hasLength(2).isOK());

        assertTrue(Assertor.that(array).hasLength(2).and(Assertor.that("ee").contains("ee")).and().contains("2").isOK());

        assertException(() -> Assertor.that(array).hasLength(12, Locale.US, "the array has not the specified length %2$d*").orElseThrow(),
                IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasNotLength} .
     */
    @Test
    public void testHasNotLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.that(array).not().hasLength(1).isOK());
        assertFalse(Assertor.that(array).not().hasLength(2).isOK());
        assertFalse(Assertor.that((String[]) null).not().hasLength(1).isOK());
        assertFalse(Assertor.that(array).not().hasLength(-1).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isEmpty()} .
     */
    @Test
    public void testIsEmpty() {
        assertFalse(Assertor.that(new String[] {null, "2"}).isEmpty().isOK());
        assertTrue(Assertor.that(new String[] {}).isEmpty().isOK());
        assertTrue(Assertor.that((String[]) null).isEmpty().isOK());
    }

    /**
     * Test method for {@link AssertorArray#isNotEmpty} .
     */
    @Test
    public void testIsNotEmpty() {
        try {
            Assertor.that(new String[0]).not().isNotEmpty().orElseThrow("not empty array");
            Assertor.that(new String[] {""}).isNotEmpty().orElseThrow("empty array");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(new Object[0]).isNotEmpty().orElseThrow("empty array");
            fail();
        }, IllegalArgumentException.class, "empty array");

        assertException(() -> {
            Assertor.that(new String[] {"z"}).not().isNotEmpty().orElseThrow("not empty array");
            fail();
        }, IllegalArgumentException.class, "not empty array");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContainsNull() {
        try {
            String[] array = new String[] {null, "2"};
            Assertor.that(array).contains(null).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that((Object[]) null).contains(null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the array cannot be null or empty");

        assertException(() -> {
            Assertor.that(new String[] {"1", "3"}).contains(null).orElseThrow("array hasn't null element");
            fail();
        }, IllegalArgumentException.class, "array hasn't null element");
    }

    /**
     * Test method for {@link AssertorArray#doesNotContainNull} .
     */
    @Test
    public void testDoesNotContainNull() {
        try {
            String[] array = new String[] {"1", "2"};
            Assertor.that(array).not().contains(null).orElseThrow("array has null element");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that((Object[]) null).not().contains(null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");

        assertException(() -> {
            Assertor.that(new String[] {"", null}).not().contains(null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.that(new String[] {null, "2"}).contains("2").isOK());
        assertFalse(Assertor.that(new String[] {}).contains((String) null).isOK());
        assertFalse(Assertor.that((String[]) null).contains("").isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {}).containsAll((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).containsAll(new String[] {null, "3"}).isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {null, "3"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {}).containsAny((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).containsAny(new String[] {null, "3"}).isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.STREAM).containsAll(new String[] {null, "3"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.PARALLEL).containsAll(new String[] {null, "3"}).isOK());
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testDoesNotContain() {
        assertFalse(Assertor.that(new String[] {null, "2"}).not().contains("2").isOK());
        assertFalse(Assertor.that(new String[] {}).not().contains((String) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().contains("").isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {}).not().containsAll((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().containsAll(new String[] {null, "3"}).isOK());

        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {null, "4"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {}).not().containsAny((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().containsAny(new String[] {null, "3"}).isOK());

        assertTrue(
                Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.STREAM).not().containsAll(new String[] {null, "4"}).isOK());
        assertTrue(
                Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.PARALLEL).not().containsAll(new String[] {null, "4"}).isOK());
    }
}
