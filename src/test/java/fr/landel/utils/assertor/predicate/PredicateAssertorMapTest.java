/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorMap;
import fr.landel.utils.commons.MapUtils2;

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
     * Test method for {@link AssertorMap#hasSizeGT}.
     */
    @Test
    public void testHasSizeGT() {
        final String key1 = "element1";
        final String key2 = "element2";
        final Integer val1 = 1;
        final Integer val2 = 2;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);
        map.put(key2, val2);

        assertTrue(Assertor.<String, Integer> ofMap().hasSizeGT(1).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeGT(0).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGT(2).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGT(3).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGT(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGT(1).that((Map<String, Integer>) null).isOK());

        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeGT(-1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the size has to be greater than or equal to 0 and the map cannot be null");
        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeGT(2).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should be greater than: 2");
        assertException(() -> Assertor.<String, Integer> ofMap().not().hasSizeGT(1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should NOT be greater than: 1");
    }

    /**
     * Test method for {@link AssertorMap#hasSizeGTE}.
     */
    @Test
    public void testHasSizeGTE() {
        final String key1 = "element1";
        final String key2 = "element2";
        final Integer val1 = 1;
        final Integer val2 = 2;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);
        map.put(key2, val2);

        assertTrue(Assertor.<String, Integer> ofMap().hasSizeGTE(1).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeGTE(0).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeGTE(2).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGTE(3).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGTE(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeGTE(1).that((Map<String, Integer>) null).isOK());

        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeGTE(-1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the size has to be greater than or equal to 0 and the map cannot be null");
        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeGTE(3).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should be greater than or equal to: 3");
        assertException(() -> Assertor.<String, Integer> ofMap().not().hasSizeGTE(1).that(map).orElseThrow(),
                IllegalArgumentException.class, "the map '[element1=1, element2=2]' size should NOT be greater than or equal to: 1");
    }

    /**
     * Test method for {@link AssertorMap#hasSizeLT}.
     */
    @Test
    public void testHasSizeLT() {
        final String key1 = "element1";
        final String key2 = "element2";
        final Integer val1 = 1;
        final Integer val2 = 2;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);
        map.put(key2, val2);

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLT(1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLT(0).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLT(2).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeLT(3).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLT(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLT(1).that((Map<String, Integer>) null).isOK());

        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeLT(-1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the size has to be greater than or equal to 0 and the map cannot be null");
        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeLT(1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should be lower than: 1");
        assertException(() -> Assertor.<String, Integer> ofMap().not().hasSizeLT(3).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should NOT be lower than: 3");
    }

    /**
     * Test method for {@link AssertorMap#hasSizeLTE}.
     */
    @Test
    public void testHasSizeLTE() {
        final String key1 = "element1";
        final String key2 = "element2";
        final Integer val1 = 1;
        final Integer val2 = 2;

        final Map<String, Integer> map = new HashMap<>();
        map.put(key1, val1);
        map.put(key2, val2);

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLTE(1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLTE(0).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeLTE(2).that(map).isOK());
        assertTrue(Assertor.<String, Integer> ofMap().hasSizeLTE(3).that(map).isOK());

        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLTE(-1).that(map).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().hasSizeLTE(1).that((Map<String, Integer>) null).isOK());

        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeLTE(-1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the size has to be greater than or equal to 0 and the map cannot be null");
        assertException(() -> Assertor.<String, Integer> ofMap().hasSizeLTE(1).that(map).orElseThrow(), IllegalArgumentException.class,
                "the map '[element1=1, element2=2]' size should be lower than or equal to: 1");
        assertException(() -> Assertor.<String, Integer> ofMap().not().hasSizeLTE(3).that(map).orElseThrow(),
                IllegalArgumentException.class, "the map '[element1=1, element2=2]' size should NOT be lower than or equal to: 3");
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

    /**
     * Check
     * {@link AssertorMap#containsInOrder(StepAssertor, Map, fr.landel.utils.assertor.commons.MessageAssertor)}
     */
    @Test
    public void testContainsInOrder() {
        Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));
        Map<String, Integer> mapTU2 = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 2), Pair.of("u", 3));
        Map<String, Integer> mapTUClone = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));
        Map<String, Integer> mapUT = MapUtils2.newMap(LinkedHashMap::new, Pair.of("u", 2), Pair.of("t", 1));
        Map<String, Integer> mapU = Collections.singletonMap("u", 2);
        Map<String, Integer> mapTVU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("v", 3), Pair.of("t", 1),
                Pair.of("u", 2), Pair.of("v", 3));
        // t, v, u (old keys are replaced)
        Map<String, Integer> mapXTUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("x", 4), Pair.of("t", 1), Pair.of("u", 2),
                Pair.of("t", 1), Pair.of("u", 2), Pair.of("v", 3));
        // x, t, u , v (old keys are replaced)
        Map<String, Integer> mapTNull = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of(null, 2));
        Map<String, Integer> mapZ = MapUtils2.newMap(LinkedHashMap::new, Pair.of("z", 5));
        Map<String, Integer> mapUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("u", 2), Pair.of("v", 3));
        Map<String, Integer> mapTUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2), Pair.of("v", 3));

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
            PredicateAssertorStepMap<String, Integer> predicate = Assertor.<String, Integer> ofMap(mode);

            assertTrue(predicate.containsInOrder(mapTUClone).that(mapTU).isOK());
            assertFalse(predicate.containsInOrder(mapUT).that(mapTU).isOK());
            assertTrue(predicate.containsInOrder(mapU).that(mapTU).isOK());

            assertFalse(predicate.containsInOrder(mapTU).that(mapTVU).isOK());
            assertTrue(predicate.containsInOrder(mapTU).that(mapXTUV).isOK());
            assertTrue(predicate.containsInOrder(mapTUClone).that(mapTU).isOK());
            assertTrue(predicate.containsInOrder(mapTNull).that(mapTNull).isOK());
            assertTrue(predicate.containsInOrder(mapTU).that(mapTUV).isOK());
            assertTrue(predicate.containsInOrder(mapUV).that(mapTUV).isOK());
            assertFalse(predicate.containsInOrder(mapTUV).that(mapTU).isOK());
            assertFalse(predicate.containsInOrder(mapUT).that(mapTU).isOK());
            assertFalse(predicate.containsInOrder(mapZ).that(mapTU).isOK());

            // NOT
            assertFalse(predicate.not().containsInOrder(mapTUClone).that(mapTU).isOK());
            assertTrue(predicate.not().containsInOrder(mapUT).that(mapTU).isOK());
            assertFalse(predicate.not().containsInOrder(mapU).that(mapTU).isOK());

            assertTrue(predicate.not().containsInOrder(mapTU).that(mapTVU).isOK());
            assertFalse(predicate.not().containsInOrder(mapTU).that(mapXTUV).isOK());
            assertFalse(predicate.not().containsInOrder(mapTUClone).that(mapTU).isOK());
            assertFalse(predicate.not().containsInOrder(mapTNull).that(mapTNull).isOK());
            assertFalse(predicate.not().containsInOrder(mapTU).that(mapTUV).isOK());
            assertFalse(predicate.not().containsInOrder(mapUV).that(mapTUV).isOK());
            assertTrue(predicate.not().containsInOrder(mapTUV).that(mapTU).isOK());
            assertTrue(predicate.not().containsInOrder(mapUT).that(mapTU).isOK());
            assertTrue(predicate.not().containsInOrder(mapZ).that(mapTU).isOK());

            // Keys and values

            assertFalse(predicate.containsInOrder(mapZ.keySet()).that(mapTU).isOK());
            assertTrue(predicate.not().containsInOrder(mapZ.keySet()).that(mapTU).isOK());

            assertFalse(predicate.containsValuesInOrder(mapZ.values()).that(mapTU).isOK());
            assertTrue(predicate.not().containsValuesInOrder(mapZ.values()).that(mapTU).isOK());

            assertFalse(predicate.containsInOrder(mapTU2).that(mapTU).isOK());
            assertFalse(predicate.containsValuesInOrder(mapTU2.values()).that(mapTU).isOK());
            assertTrue(predicate.containsInOrder(mapTU2.keySet()).that(mapTU).isOK());
            assertTrue(predicate.not().containsInOrder(mapTU2).that(mapTU).isOK());
            assertTrue(predicate.not().containsValuesInOrder(mapTU2.values()).that(mapTU).isOK());
            assertFalse(predicate.not().containsInOrder(mapTU2.keySet()).that(mapTU).isOK());

            assertFalse(predicate.containsInOrder(mapUV).that(mapTU2).isOK());
            assertTrue(predicate.containsValuesInOrder(mapUV.values()).that(mapTU2).isOK());
            assertFalse(predicate.containsInOrder(mapUV.keySet()).that(mapTU2).isOK());
            assertTrue(predicate.not().containsInOrder(mapUV).that(mapTU2).isOK());
            assertFalse(predicate.not().containsValuesInOrder(mapUV.values()).that(mapTU2).isOK());
            assertTrue(predicate.not().containsInOrder(mapUV.keySet()).that(mapTU2).isOK());

            assertTrue(predicate.containsInOrder(mapTU2.keySet()).that(mapTU).isOK());
            assertFalse(predicate.not().containsInOrder(mapTU2.keySet()).that(mapTU).isOK());
        }
    }

    /**
     * Check {@link AssertorMap#containsValues}
     */
    @Test
    public void testContainsValue() {
        Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

        assertTrue(Assertor.<String, Integer> ofMap().containsValue(1).that(mapTU).isOK());
        assertFalse(Assertor.<String, Integer> ofMap().containsValue(0).that(mapTU).isOK());
    }

    /**
     * Check {@link AssertorMap#containsAnyValues}
     */
    @Test
    public void testContainsAnyValues() {
        Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
            PredicateAssertorStepMap<String, Integer> predicate = Assertor.<String, Integer> ofMap(mode);

            assertTrue(predicate.containsAnyValues(Arrays.asList(1, 2)).that(mapTU).isOK());
            assertTrue(predicate.containsAnyValues(Arrays.asList(1, 2, 3)).that(mapTU).isOK());
            assertTrue(predicate.containsAnyValues(Arrays.asList(0, 1)).that(mapTU).isOK());
            assertFalse(predicate.containsAnyValues(Arrays.asList(0)).that(mapTU).isOK());

            assertFalse(predicate.containsAnyValues(Arrays.asList(0)).that((Map<String, Integer>) null).isOK());
            assertFalse(predicate.containsAnyValues((List<Integer>) null).that(mapTU).isOK());
        }
    }

    /**
     * Check {@link AssertorMap#containsAllValues}
     */
    @Test
    public void testContainsAllValues() {
        Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
            PredicateAssertorStepMap<String, Integer> predicate = Assertor.<String, Integer> ofMap(mode);

            assertTrue(predicate.containsAllValues(Arrays.asList(1, 2)).that(mapTU).isOK());
            assertFalse(predicate.containsAllValues(Arrays.asList(1, 2, 3)).that(mapTU).isOK());
            assertFalse(predicate.containsAllValues(Arrays.asList(0, 1)).that(mapTU).isOK());
            assertFalse(predicate.containsAllValues(Arrays.asList(0)).that(mapTU).isOK());

            assertFalse(predicate.containsAllValues(Arrays.asList(0)).that((Map<String, Integer>) null).isOK());
            assertFalse(predicate.containsAllValues((List<Integer>) null).that(mapTU).isOK());
        }
    }

    /**
     * Check {@link AssertorMap#anyMatch}
     */
    @Test
    public void testAnyMatch() {
        Map<String, Integer> maptu = MapUtils2.newHashMap(Pair.of("t", 2), Pair.of("u", 3));
        Map<String, Integer> mapTu = MapUtils2.newHashMap(Pair.of("T", 2), Pair.of("u", 2));
        Map<String, Integer> mapTU = MapUtils2.newHashMap(Pair.of("T", 1), Pair.of("U", 2));
        Map<String, Integer> maptNull = MapUtils2.newHashMap(Pair.of("t", 1), Pair.of(null, null));
        Map<String, Integer> maptUNull = MapUtils2.newHashMap(Pair.of("t", 1), Pair.of("U", null));

        Predicate<Entry<String, Integer>> predicate = e -> Objects.equals(e.getKey(), StringUtils.lowerCase(e.getKey()))
                && e.getValue() > 1;

        assertTrue(Assertor.<String, Integer> ofMap().anyMatch(predicate).that(maptu).isOK());

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

            PredicateAssertorStepMap<String, Integer> predicateAssertor = Assertor.<String, Integer> ofMap(mode);
            PredicateStepMap<String, Integer> predicateStep = predicateAssertor.anyMatch(predicate);

            assertTrue(predicateStep.that(maptu).isOK());
            assertTrue(predicateStep.that(mapTu).isOK());
            assertFalse(predicateStep.that(mapTU).isOK());
            assertFalse(predicateStep.that(maptNull).isOK());
            assertFalse(predicateStep.that(maptUNull).isOK());

            assertException(() -> predicateStep.that(Collections.<String, Integer> emptyMap()).orElseThrow(),
                    IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that((Map<String, Integer>) null).orElseThrow(), IllegalArgumentException.class,
                    "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateAssertor.anyMatch(null).that(mapTu).orElseThrow(), IllegalArgumentException.class,
                    "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that(mapTU).orElseThrow(), IllegalArgumentException.class,
                    "any map entry '[T=1, U=2]' should match the predicate");
        }
    }

    /**
     * Check {@link AssertorMap#allMatch}
     */
    @Test
    public void testAllMatch() {
        Map<String, Integer> maptu = MapUtils2.newHashMap(Pair.of("t", 2), Pair.of("u", 3));
        Map<String, Integer> mapTu = MapUtils2.newHashMap(Pair.of("T", 2), Pair.of("u", 2));
        Map<String, Integer> mapTU = MapUtils2.newHashMap(Pair.of("T", 1), Pair.of("U", 2));
        Map<String, Integer> maptNull = MapUtils2.newHashMap(Pair.of("t", 1), Pair.of(null, null));
        Map<String, Integer> maptUNull = MapUtils2.newHashMap(Pair.of("t", 1), Pair.of("U", null));

        Predicate<Entry<String, Integer>> predicate = e -> Objects.equals(e.getKey(), StringUtils.lowerCase(e.getKey()))
                && e.getValue() > 1;

        assertTrue(Assertor.<String, Integer> ofMap().allMatch(predicate).that(maptu).isOK());

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

            PredicateAssertorStepMap<String, Integer> predicateAssertor = Assertor.<String, Integer> ofMap(mode);
            PredicateStepMap<String, Integer> predicateStep = predicateAssertor.allMatch(predicate);

            assertTrue(predicateStep.that(maptu).isOK());
            assertFalse(predicateStep.that(mapTu).isOK());
            assertFalse(predicateStep.that(mapTU).isOK());
            assertFalse(predicateStep.that(maptNull).isOK());
            assertFalse(predicateStep.that(maptUNull).isOK());

            assertException(() -> predicateStep.that(Collections.<String, Integer> emptyMap()).orElseThrow(),
                    IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that((Map<String, Integer>) null).orElseThrow(), IllegalArgumentException.class,
                    "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateAssertor.allMatch(null).that(mapTu).orElseThrow(), IllegalArgumentException.class,
                    "the map cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that(mapTU).orElseThrow(), IllegalArgumentException.class,
                    "all the map entries '[T=1, U=2]' should match the predicate");
        }
    }
}
