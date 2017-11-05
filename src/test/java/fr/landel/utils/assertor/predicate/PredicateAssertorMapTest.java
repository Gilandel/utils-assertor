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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorMap;

/**
 * Check {@link AssertorMap}
 *
 * @since Jun 5, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorMapTest extends AbstractTest {

    private final String ERROR = "error expected";

    /**
     * Test method for {@link AssertorMap} .
     */
    @Test
    public void testPredicateGet() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);

        assertFalse(Assertor.<String, Integer> ofMap().hasHashCode(0).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasHashCode(Objects.hashCode(map)).that(map).isOK());

        assertTrue(Assertor.<String, Integer> ofMap().contains("key").and().hasHashCode(Objects.hashCode(map)).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().contains("key").or().hasHashCode(Objects.hashCode(map)).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().contains("key").xor().hasHashCode(Objects.hashCode(map)).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().contains("key").nand().hasHashCode(Objects.hashCode(map)).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().contains("key").nor().hasHashCode(Objects.hashCode(map)).that(map).isOK());
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

        final PredicateAssertorStepMap<String, Integer> assertMap = Assertor.<String, Integer> ofMap();

        assertMap.isEmpty().that(map).orElseThrow();

        Assertor.<String, Integer> ofMap().isEmpty().and(assertMap.isEmpty()).that(map).isOK();

        map.put(el, 1);

        assertException(() -> {
            assertMap.isEmpty().that(map).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            assertMap.isEmpty().that(map).orElseThrow("map is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is not empty");

        assertException(() -> {
            assertMap.isEmpty().that(map).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        Assertor.<String, Integer> ofMap().isEmpty().that((Map<String, Integer>) null)
                .orElseThrow("this argument is required; it must not be null");
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

        final PredicateAssertorStepMap<String, Integer> assertMap = Assertor.<String, Integer> ofMap();

        assertMap.isNotEmpty().that(map).orElseThrow();

        assertException(() -> {
            assertMap.not().isNotEmpty().that(map).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        map.clear();

        assertException(() -> {
            assertMap.isNotEmpty().that(map).orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            assertMap.isNotEmpty().that(map).orElseThrow("map is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is empty");

        assertException(() -> {
            assertMap.isNotEmpty().that(map).orElseThrow(new IOException(), true);
            fail(ERROR);
        }, IOException.class);

        assertFalse(Assertor.<String, Integer> ofMap().isNotEmpty().that((Map<String, Integer>) null).isOK());
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

        final List<String> keys = Arrays.asList(key1);

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("element3", 2);

        this.checkContains(Assertor.ofMap(), key1, key2, val1, keys, map, map1);
        this.checkContains(Assertor.ofMap(EnumAnalysisMode.STREAM), key1, key2, val1, keys, map, map1);
        this.checkContains(Assertor.ofMap(EnumAnalysisMode.PARALLEL), key1, key2, val1, keys, map, map1);

        assertFalse(Assertor.<String, Integer> ofMap().contains(key1).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().contains(key1, val1).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().containsAll(keys).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().containsAll(map).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().containsAny(keys).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().containsAny(map).that((Map<String, Integer>) null).isOK());
    }

    private void checkContains(final PredicateAssertorStepMap<String, Integer> assertMap, final String key1, final String key2,
            final Integer val1, final List<String> keys, final Map<String, Integer> map, final Map<String, Integer> map1)
            throws IOException {

        assertMap.isNotNull().and().contains(key1).that(map).orElseThrow();

        assertMap.contains(key1).that(map).orElseThrow();
        assertMap.contains(key1).that(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.contains(key1).that(map).orElseThrow(new IOException(), true);
        assertMap.contains(key1, val1).that(map).orElseThrow();
        assertMap.contains(key1, val1).that(map).orElseThrow("map doesn't contain the element %3$s*");
        assertMap.contains(key1, val1).that(map).orElseThrow(new IOException(), true);
        assertMap.contains(key2, null).that(map).orElseThrow();
        assertFalse(assertMap.contains(key2, 3).that(map).isOK());

        assertMap.containsAll(keys).that(map).orElseThrow();
        assertMap.containsAll(keys).that(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAll(keys).that(map).orElseThrow(new IOException(), true);
        assertMap.containsAll(map).that(map).orElseThrow();
        assertMap.containsAll(map).that(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAll(map).that(map).orElseThrow(new IOException(), true);

        assertMap.containsAny(keys).that(map).orElseThrow();
        assertMap.containsAny(keys).that(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAny(keys).that(map).orElseThrow(new IOException(), true);
        assertMap.containsAny(map).that(map).orElseThrow();
        assertMap.containsAny(map).that(map).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.containsAny(map).that(map).orElseThrow(new IOException(), true);

        assertTrue(assertMap.contains(key1).and().isNotEmpty().that(map).isOK());
        assertTrue(assertMap.contains(key1).or().isEmpty().that(map).isOK());
        assertTrue(assertMap.contains(key1).xor().isEmpty().that(map).isOK());
        assertFalse(assertMap.contains(key1).nand().isEmpty().that(map).isOK());
        assertTrue(assertMap.contains(key1).nor().isEmpty().that(map).isOK());

        assertFalse(assertMap.contains(key1, (Integer) null).that(map).isOK());
        assertTrue(assertMap.contains(key2, (Integer) null).that(map).isOK());
        assertFalse(assertMap.contains(key2, 1).that(map).isOK());
        assertFalse(assertMap.containsAll(Arrays.asList("element3")).that(map).isOK());
        assertFalse(assertMap.containsAll(map1).that(map).isOK());
        assertFalse(assertMap.containsAny(Arrays.asList("element3")).that(map).isOK());
        assertFalse(assertMap.containsAny(map1).that(map).isOK());

        assertFalse(assertMap.contains((String) null).that(map).isOK());
        assertFalse(assertMap.contains((String) null, (Integer) null).that(map).isOK());
        assertFalse(assertMap.containsAll((List<String>) null).that(map).isOK());
        assertFalse(assertMap.containsAll((Map<String, Integer>) null).that(map).isOK());
        assertFalse(assertMap.containsAny((List<String>) null).that(map).isOK());
        assertFalse(assertMap.containsAny((Map<String, Integer>) null).that(map).isOK());
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

        final Map<String, Integer> map1 = new HashMap<>();
        map1.put("element3", 2);

        final List<String> keys = Arrays.asList("element3");

        this.checkDoesNotContain(Assertor.ofMap(), key1, key2, val1, val2, keys, map, map1);
        this.checkDoesNotContain(Assertor.ofMap(EnumAnalysisMode.STREAM), key1, key2, val1, val2, keys, map, map1);
        this.checkDoesNotContain(Assertor.ofMap(EnumAnalysisMode.PARALLEL), key1, key2, val1, val2, keys, map, map1);

        assertFalse(Assertor.<String, Integer> ofMap().not().contains(key1).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().not().contains(key1, val1).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().not().containsAll(keys).that((Map<String, Integer>) null).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().not().containsAll(map1).that((Map<String, Integer>) null).isOK());
    }

    private void checkDoesNotContain(final PredicateAssertorStepMap<String, Integer> assertMap, final String key1, final String key2,
            final Integer val1, final Integer val2, final List<String> keys, final Map<String, Integer> map,
            final Map<String, Integer> map1) throws IOException {

        assertMap.isNotNull().and().contains(key1).that(map).orElseThrow();

        assertMap.not().contains(key2).that(map).orElseThrow();
        assertMap.not().contains(key2).that(map).orElseThrow("map contains the element %2$s*");
        assertMap.not().contains(key2).that(map).orElseThrow(new IOException(), true);
        assertMap.not().contains(key2, val2).that(map).orElseThrow();
        assertMap.not().contains(key2, val2).that(map).orElseThrow("map contains the element %3$s*");
        assertMap.not().contains(key2, val2).that(map).orElseThrow(new IOException(), true);

        assertFalse(assertMap.not().containsAll(keys).that(map).isOK());
        assertFalse(assertMap.not().containsAll(map1).that(map).isOK());

        assertTrue(assertMap.not().containsAny(keys).that(map).isOK());
        assertTrue(assertMap.not().containsAny(map1).that(map).isOK());

        assertEquals("the map '[element1=1]' should NOT contain the key 'element1'",
                assertMap.not().contains(key1).that(map).getErrors().get());
        assertFalse(assertMap.not().contains(key1).that(map).isOK());
        assertFalse(assertMap.not().contains(key1, val1).that(map).isOK());
        assertFalse(assertMap.not().containsAll(map.keySet()).that(map).isOK());
        assertFalse(assertMap.not().containsAll(map).that(map).isOK());

        assertTrue(assertMap.not().contains(key1, (Integer) null).that(map).isOK());
        assertFalse(assertMap.not().containsAll(Arrays.asList("element3")).that(map).isOK());
        assertFalse(assertMap.not().containsAll(map1).that(map).isOK());

        assertTrue(assertMap.not().contains((String) null).that(map).isOK());
        assertTrue(assertMap.not().contains(key1, 3).that(map).isOK());
        assertTrue(assertMap.not().contains((String) null, (Integer) null).that(map).isOK());
        assertFalse(assertMap.not().containsAll((List<String>) null).that(map).isOK());
        assertFalse(assertMap.not().containsAll((Map<String, Integer>) null).that(map).isOK());

    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(Map, String, Object...)} .
     */
    @Test
    public void testIsNotEmptyOKMapOfQQString() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("f", "f");
            Assertor.<String, String> ofMap().isNotEmpty().that(map).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(Map, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOMapOfQQString() {
        Assertor.<String, String> ofMap().isNotEmpty().that(new HashMap<String, String>()).orElseThrow();
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(java.util.Map)} .
     */
    @Test
    public void testIsNotEmptyOKMapOfQQ() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("fg", "fg");
            Assertor.<String, String> ofMap().isNotEmpty().that(map).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorMap#isNotEmpty(java.util.Map)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOMapOfQQ() {
        Assertor.<String, String> ofMap().isNotEmpty().that(new HashMap<String, String>()).orElseThrow();
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

        assertTrue(Assertor.<String, Integer> ofMap().hasSize(1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSize(0).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSize(2).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().hasSize(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSize(1).that((Map<String, Integer>) null).isOK());
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

        assertFalse(Assertor.<String, Integer> ofMap().not().hasSize(1).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().not().hasSize(0).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().not().hasSize(2).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().not().hasSize(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().not().hasSize(1).that((Map<String, Integer>) null).isOK());
    }
}
