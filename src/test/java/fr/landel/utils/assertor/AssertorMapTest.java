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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.landel.utils.commons.expect.Expect;

/**
 * Check {@link AssertorMap}
 *
 * @since Jun 5, 2016
 * @author Gilles
 *
 */
public class AssertorMapTest extends AbstractTest {

    private final String ERROR = "error expected";

    /**
     * Test method for {@link AssertorMap#AssertorMap()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorMap());
    }

    /**
     * Test method for {@link AssertorMap#isEmpty}.
     * 
     * @throws IOException
     *             On error
     */
    @Test
    public void testIsEmpty() throws IOException {
        final String el = "element";

        final Map<String, Integer> map = new HashMap<>();

        final PredicateAssertorMap<String, Integer> assertMap = Assertor.that(map);

        assertMap.isEmpty().orElseThrow();

        Assertor.that(map).isEmpty().and(assertMap.isEmpty()).isOK();

        map.put(el, 1);

        Expect.exception(() -> {
            assertMap.isEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            assertMap.isEmpty().orElseThrow("map is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is not empty");

        Expect.exception(() -> {
            assertMap.isEmpty().orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Assertor.that((Map<String, Integer>) null).isEmpty().orElseThrow("this argument is required; it must not be null");
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty}.
     * 
     * @throws IOException
     *             On error
     */
    @Test
    public void testIsNotEmpty() throws IOException {
        final String el = "element";

        final Map<String, Integer> map = new HashMap<>();
        map.put(el, 1);

        final PredicateAssertorMap<String, Integer> assertMap = Assertor.that(map);

        assertMap.isNotEmpty().orElseThrow();

        Expect.exception(() -> {
            assertMap.not().isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        map.clear();

        Expect.exception(() -> {
            assertMap.isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        Expect.exception(() -> {
            assertMap.isNotEmpty().orElseThrow("map is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is empty");

        Expect.exception(() -> {
            assertMap.isNotEmpty().orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertFalse(Assertor.that((Map<String, Integer>) null).isNotEmpty().isOK());
    }

    /**
     * Test method for {@link AssertorMap#contains}.
     * 
     * @throws IOException
     *             On error
     */
    @Test
    public void testContains() throws IOException {
        final String key1 = "element1";
        final String key2 = "element2";
        final Integer val1 = 1;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);
        map.put(key2, null);

        final PredicateAssertorMap<String, Integer> assertMap = Assertor.that(map);

        assertMap.isNotNull().and().contains(key1).orElseThrow();

        final List<String> keys = Arrays.asList(key1);

        assertMap.contains(key1).orElseThrow();
        assertMap.contains(key1).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.contains(key1).orElseThrow(new IOException(), true);
        assertMap.contains(key1, val1).orElseThrow();
        assertMap.contains(key1, val1).orElseThrow("map doesn't contain the element %3$s*");
        assertMap.contains(key1, val1).orElseThrow(new IOException(), true);
        assertMap.contains(key2, null).orElseThrow();

        assertMap.containsAll(keys).orElseThrow();
        assertMap.containsAll(keys).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAll(keys).orElseThrow(new IOException(), true);
        assertMap.containsAll(map).orElseThrow();
        assertMap.containsAll(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAll(map).orElseThrow(new IOException(), true);

        assertMap.containsAny(keys).orElseThrow();
        assertMap.containsAny(keys).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAny(keys).orElseThrow(new IOException(), true);
        assertMap.containsAny(map).orElseThrow();
        assertMap.containsAny(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAny(map).orElseThrow(new IOException(), true);

        assertTrue(assertMap.contains(key1).and().isNotEmpty().isOK());
        assertTrue(assertMap.contains(key1).or().isEmpty().isOK());
        assertTrue(assertMap.contains(key1).xor().isEmpty().isOK());
        assertFalse(assertMap.contains(key1).nand().isEmpty().isOK());
        assertTrue(assertMap.contains(key1).nor().isEmpty().isOK());

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("element3", 2);

        assertFalse(Assertor.that(map).contains(key1, (Integer) null).isOK());
        assertTrue(Assertor.that(map).contains(key2, (Integer) null).isOK());
        assertFalse(Assertor.that(map).contains(key2, 1).isOK());
        assertFalse(Assertor.that(map).containsAll(Arrays.asList("element3")).isOK());
        assertFalse(Assertor.that(map).containsAll(map1).isOK());
        assertFalse(Assertor.that(map).containsAny(Arrays.asList("element3")).isOK());
        assertFalse(Assertor.that(map).containsAny(map1).isOK());

        assertFalse(Assertor.that(map).contains((String) null).isOK());
        assertFalse(Assertor.that(map).contains((String) null, (Integer) null).isOK());
        assertFalse(Assertor.that(map).containsAll((List<String>) null).isOK());
        assertFalse(Assertor.that(map).containsAll((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.that(map).containsAny((List<String>) null).isOK());
        assertFalse(Assertor.that(map).containsAny((Map<String, Integer>) null).isOK());

        assertFalse(Assertor.that((Map<String, Integer>) null).contains(key1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).contains(key1, val1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAll(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAll(map).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAny(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAny(map).isOK());
    }

    /**
     * Test method for {@link AssertorMap#doesNotContain}.
     * 
     * @throws IOException
     *             On error
     */
    @Test
    public void testDoesNotContain() throws IOException {
        final String key1 = "element1";
        final Integer val1 = 1;
        final String key2 = "element2";
        final Integer val2 = 2;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);

        final PredicateAssertorMap<String, Integer> assertMap = Assertor.that(map);

        final Map<String, Integer> map1 = new HashMap<>();
        map1.put("element3", 2);

        final List<String> keys = Arrays.asList("element3");

        assertMap.isNotNull().and().contains(key1).orElseThrow();

        assertMap.not().contains(key2).orElseThrow();
        assertMap.not().contains(key2).orElseThrow("map contains the element %2$s*");
        assertMap.not().contains(key2).orElseThrow(new IOException(), true);
        assertMap.not().contains(key2, val2).orElseThrow();
        assertMap.not().contains(key2, val2).orElseThrow("map contains the element %3$s*");
        assertMap.not().contains(key2, val2).orElseThrow(new IOException(), true);

        assertFalse(assertMap.not().containsAll(keys).isOK());
        assertFalse(assertMap.not().containsAll(map1).isOK());

        assertTrue(assertMap.not().containsAny(keys).isOK());
        assertTrue(assertMap.not().containsAny(map1).isOK());

        assertEquals("the map '[element1=1]' should NOT contain the key 'element1'",
                Assertor.that(map).not().contains(key1).getErrors().get());
        assertFalse(Assertor.that(map).not().contains(key1).isOK());
        assertFalse(Assertor.that(map).not().contains(key1, val1).isOK());
        assertFalse(Assertor.that(map).not().containsAll(map.keySet()).isOK());
        assertFalse(Assertor.that(map).not().containsAll(map).isOK());

        assertTrue(Assertor.that(map).not().contains(key1, (Integer) null).isOK());
        assertFalse(Assertor.that(map).not().containsAll(Arrays.asList("element3")).isOK());
        assertFalse(Assertor.that(map).not().containsAll(map1).isOK());

        assertTrue(Assertor.that(map).not().contains((String) null).isOK());
        assertTrue(Assertor.that(map).not().contains(key1, 3).isOK());
        assertTrue(Assertor.that(map).not().contains((String) null, (Integer) null).isOK());
        assertFalse(Assertor.that(map).not().containsAll((List<String>) null).isOK());
        assertFalse(Assertor.that(map).not().containsAll((Map<String, Integer>) null).isOK());

        assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1, val1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(map1).isOK());
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(Map, String, Object...)} .
     */
    @Test
    public void testIsNotEmptyOKMapOfQQString() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("f", "f");
            Assertor.that(map).isNotEmpty().orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(Map, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOMapOfQQString() {
        Assertor.that(new HashMap<String, String>()).isNotEmpty().orElseThrow();
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(java.util.Map)} .
     */
    @Test
    public void testIsNotEmptyOKMapOfQQ() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("fg", "fg");
            Assertor.that(map).isNotEmpty().orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(java.util.Map)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOMapOfQQ() {
        Assertor.that(new HashMap<String, String>()).isNotEmpty().orElseThrow();
    }

    /**
     * Test method for {@link AssertorMap#hasSize}.
     */
    @Test
    public void testHasSize() {
        final String key1 = "element1";
        final Integer val1 = 1;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);

        assertTrue(Assertor.that(map).hasSize(1).isOK());
        assertFalse(Assertor.that(map).hasSize(0).isOK());
        assertFalse(Assertor.that(map).hasSize(2).isOK());

        assertFalse(Assertor.that(map).hasSize(-1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).hasSize(1).isOK());
    }

    /**
     * Test method for {@link AssertorMap#hasNotSize}.
     */
    @Test
    public void testHasNotSize() {
        final String key1 = "element1";
        final Integer val1 = 1;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);

        assertFalse(Assertor.that(map).not().hasSize(1).isOK());
        assertTrue(Assertor.that(map).not().hasSize(0).isOK());
        assertTrue(Assertor.that(map).not().hasSize(2).isOK());

        assertFalse(Assertor.that(map).not().hasSize(-1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().hasSize(1).isOK());
    }
}
