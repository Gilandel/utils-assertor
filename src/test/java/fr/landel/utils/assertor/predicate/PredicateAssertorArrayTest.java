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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorArray;

/**
 * Check {@link AssertorArray}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class PredicateAssertorArrayTest extends AbstractTest {

    /**
     * Test method for {@link AssertorArray} .
     */
    @Test
    public void testPredicateGet() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.ofArray().hasHashCode(0).that(array).isOK());
        assertTrue(Assertor.ofArray().hasHashCode(Objects.hashCode((Object[]) array)).that(array).isOK());
    }

    /**
     * Test method for {@link AssertorArray#hasLength} .
     */
    @Test
    public void testHasLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.ofArray().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().hasLength(1).that(array).isOK());
        assertFalse(Assertor.ofArray().hasLength(1).that((String[]) null).isOK());
        assertFalse(Assertor.ofArray().hasLength(-1).that(array).isOK());

        assertTrue(Assertor.ofArray().hasLength(2).and(Assertor.ofArray().contains(null).or().validates(a -> "2".equals(a[1]))).that(array)
                .isOK());
        assertTrue(Assertor.ofArray().isNotEmpty().and().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().or().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().xor().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().isEmpty().nand().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().nor().hasLength(2).that(array).isOK());

        assertTrue(Assertor.ofArray().hasLength(2).and(Assertor.ofArray().contains("2")).xor().contains("1").that(array).isOK());

        assertException(
                () -> Assertor.ofArray().hasLength(12, Locale.US, "the array has not the specified length %2$d*").that(array).orElseThrow(),
                IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasNotLength} .
     */
    @Test
    public void testHasNotLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.ofArray().not().hasLength(1).that(array).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(1).that((String[]) null).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(-1).that(array).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isEmpty()} .
     */
    @Test
    public void testIsEmpty() {
        assertFalse(Assertor.ofArray().isEmpty().that(new String[] {null, "2"}).isOK());
        assertTrue(Assertor.ofArray().isEmpty().that(new String[] {}).isOK());
        assertTrue(Assertor.ofArray().isEmpty().that((String[]) null).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isNotEmpty} .
     */
    @Test
    public void testIsNotEmpty() {
        try {
            Assertor.ofArray().not().isNotEmpty().that(new String[0]).orElseThrow("not empty array");
            Assertor.ofArray().isNotEmpty().that(new String[] {""}).orElseThrow("empty array");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().isNotEmpty().that(new Object[0]).orElseThrow("empty array");
            fail();
        }, IllegalArgumentException.class, "empty array");

        assertException(() -> {
            Assertor.ofArray().not().isNotEmpty().that(new String[] {"z"}).orElseThrow("not empty array");
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
            Assertor.ofArray().contains(null).that(array).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().contains(null).that((Object[]) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the array cannot be null or empty");

        assertException(() -> {
            Assertor.ofArray().contains(null).that(new String[] {"1", "3"}).orElseThrow("array hasn't null element");
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
            Assertor.ofArray().not().contains(null).that(array).orElseThrow("array has null element");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().not().contains(null).that((Object[]) null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");

        assertException(() -> {
            Assertor.ofArray().not().contains(null).that(new String[] {"", null}).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.ofArray().contains("2").that(new String[] {null, "2"}).isOK());
        assertFalse(Assertor.ofArray().contains((String) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().contains("").that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().containsAny(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray().containsAny(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAny(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAny((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().containsAny(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(
                Assertor.ofArray(EnumAnalysisMode.STREAM).containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray(EnumAnalysisMode.PARALLEL).containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"})
                .isOK());
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testDoesNotContain() {
        assertFalse(Assertor.ofArray().not().contains("2").that(new String[] {null, "2"}).isOK());
        assertFalse(Assertor.ofArray().not().contains((String) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().contains("").that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().not().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {null, "3"}).that((String[]) null).isOK());

        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray().not().containsAny(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(Assertor.ofArray(EnumAnalysisMode.STREAM).not().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"})
                .isOK());
        assertTrue(Assertor.ofArray(EnumAnalysisMode.PARALLEL).not().containsAll(new String[] {null, "4"})
                .that(new String[] {null, "2", "3"}).isOK());
    }
}
