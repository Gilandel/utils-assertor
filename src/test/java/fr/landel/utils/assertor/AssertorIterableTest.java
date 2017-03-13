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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import fr.landel.utils.assertor.expect.Expect;

/**
 * Check {@link AssertorIterable}
 *
 * @since Jun 4, 2016
 * @author Gilles
 *
 */
public class AssertorIterableTest extends AbstractTest {

    private final String ERROR = "error expected";

    /**
     * Test method for {@link AssertorIterable#AssertorIterable()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorIterable());
    }

    /**
     * Test method for {@link AssertorIterable#hasSize(int)}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testHasSize() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        assertTrue(Assertor.that(set).isNotEmpty().and(Assertor.that(el).isNotBlank()).isOK());

        assertTrue(Assertor.that(set).hasSize(1).isOK());
        assertFalse(Assertor.that(set).hasSize(2).isOK());
        assertFalse(Assertor.that((Set<String>) null).hasSize(1).isOK());
        assertFalse(Assertor.that(set).hasSize(-1).isOK());

        assertTrue(Assertor.that(set).hasSize(1).and().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).or().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).xor().not().contains(el).isOK());
        assertFalse(Assertor.that(set).hasSize(1).nand().not().contains(el).isOK());
        assertTrue(Assertor.that(set).hasSize(1).nor().not().contains(el).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertTrue(Assertor.that(iterable).hasSize(1).isOK());
        assertFalse(Assertor.that(iterable).hasSize(2).isOK());
    }

    /**
     * Test method for {@link AssertorIterable#hasSize(int)}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testHasNotSize() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        assertFalse(Assertor.that(set).not().hasSize(1).isOK());
        assertTrue(Assertor.that(set).not().hasSize(2).isOK());
        assertFalse(Assertor.that((Set<String>) null).not().hasSize(1).isOK());
        assertFalse(Assertor.that(set).not().hasSize(-1).isOK());

        final Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return set.iterator();
            }
        };

        assertFalse(Assertor.that(iterable).not().hasSize(1).isOK());
        assertTrue(Assertor.that(iterable).not().hasSize(2).isOK());
    }

    /**
     * Test method for {@link AssertorIterable#isEmpty}.
     * 
     * @throws IOException
     *             On not empty iterable
     */
    @Test
    public void testIsEmpty() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();

        Assertor.that(set).isEmpty().orElseThrow();

        set.add(el);

        Expect.exception(() -> {
            Assertor.that(set).isEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(set).isEmpty().orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        Expect.exception(() -> {
            Assertor.that(set).isEmpty().orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty}.
     * 
     * @throws IOException
     *             On empty iterable
     */
    @Test
    public void testIsNotEmpty() throws IOException {
        final String el = "element";

        final Set<String> set = new HashSet<>();
        set.add(el);

        Assertor.that(set).isNotEmpty().orElseThrow();

        Expect.exception(() -> {
            Assertor.that(set).not().isNotEmpty().orElseThrow("iterable is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is not empty");

        set.clear();

        Expect.exception(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow("iterable is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable is empty");

        Expect.exception(() -> {
            Assertor.that(set).isNotEmpty().orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).isNotEmpty().orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the iterable 'should be NOT empty and NOT null", JUNIT_ERROR);
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On not contain
     */
    @Test
    public void testContains() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        set.add(el1);

        Assertor.that(set).contains(el1).orElseThrow("iterable doesn't contain the element %s*");

        Expect.exception(() -> {
            Assertor.that(set).contains(el2).orElseThrow("iterable doesn't contain the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the element " + el2);

        Expect.exception(() -> {
            Assertor.that(set).contains(el2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Expect.exception(() -> {
            Assertor.that(set).contains((String) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should contain the object 'null'", JUNIT_ERROR);

        set.clear();

        Expect.exception(() -> {
            Assertor.that(set).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(set).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).contains((String) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null", JUNIT_ERROR);
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On not contain
     */
    @Test
    public void testContainsIterable() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        final Set<String> set2 = new HashSet<>();
        set.add(el1);
        set2.add(el1);

        Assertor.that(set).containsAll(set2).orElseThrow("iterable doesn't contain the list %s*");
        Assertor.that(set).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");

        set2.add(el2);
        Assertor.that(set).containsAny(set2).orElseThrow("iterable doesn't contain the list %s*");

        Expect.exception(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow("iterable doesn't contain the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable doesn't contain the list " + set2.toString(), JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Expect.exception(() -> {
            Assertor.that(set).containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).containsAny((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        set.clear();

        Expect.exception(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            Assertor.that(set).containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).containsAny(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        set.add(null);
        Assertor.that(set).contains(null).orElseThrow();
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On contain
     */
    @Test
    public void testDoesNotContain() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        set.add(el1);

        Assertor.that(set).not().contains(el2).orElseThrow("iterable contains the element %s*");
        Assertor.that(set).not().contains((String) null).orElseThrow();

        Expect.exception(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable '[element1]' should NOT contain the object 'element1'", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow("iterable contains the element %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the element " + el1, JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).not().contains(el1).orElseThrow(new IOException(), false);
            fail(ERROR);
        }, IOException.class);

        set.clear();

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).not().contains(el1).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "the iterable cannot be null", JUNIT_ERROR);
    }

    /**
     * Test method for {@link AssertorIterable#contains}.
     * 
     * @throws IOException
     *             On contain
     */
    @Test
    public void testDoesNotContainIterable() throws IOException {
        final String el1 = "element1";
        final String el2 = "element2";

        final Set<String> set = new HashSet<>();
        final Set<String> set2 = new HashSet<>();
        set.add(el1);
        set2.add(el1);
        set2.add(el2);

        Assertor.that(set).not().containsAll(set2).orElseThrow("iterable contains the list %s*");

        set2.remove(el1);

        Expect.exception(() -> {
            Assertor.that(set).not().containsAll(set2).orElseThrow("iterable contains the list %2$s*");
            fail(ERROR);
        }, IllegalArgumentException.class, "iterable contains the list " + set2.toString());

        Expect.exception(() -> {
            Assertor.that(set).not().containsAll(set2).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Expect.exception(() -> {
            Assertor.that(set).not().containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        assertFalse(Assertor.that(set).not().containsAll(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAll(set).isOK());
        assertTrue(Assertor.that(set).not().containsAny(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set).isOK());

        set.clear();

        Expect.exception(() -> {
            Assertor.that((Iterable<String>) null).not().containsAll(set2).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        Expect.exception(() -> {
            Assertor.that(set).not().containsAll((Iterable<String>) null).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class, "neither iterables can be null", JUNIT_ERROR);

        assertFalse(Assertor.that(set).not().containsAll(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAll(set).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set2).isOK());
        assertFalse(Assertor.that(set).not().containsAny(set).isOK());
    }

    /**
     * Test method for
     * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQString() {
        try {
            Assertor.that(Arrays.asList("")).isNotEmpty().orElseThrow("empty collection");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOCollectionOfQString() {
        Assertor.that(Collections.emptyList()).isNotEmpty().orElseThrow("empty collection");
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test
    public void testIsNotEmptyOKCollectionOfQ() {
        try {
            Assertor.that(Arrays.asList("")).isNotEmpty().orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOCollectionOfQ() {
        Assertor.that(Collections.emptyList()).isNotEmpty().orElseThrow();
    }
}
