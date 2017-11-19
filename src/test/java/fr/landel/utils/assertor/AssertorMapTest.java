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

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorMap;

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

        final AssertorStepMap<Map<String, Integer>, String, Integer> assertMap = Assertor.that(map);

        assertMap.isEmpty().orElseThrow();

        Assertor.that(map).isEmpty().and(assertMap.isEmpty()).isOK();

        map.put(el, 1);

        assertException(() -> {
            assertMap.isEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            assertMap.isEmpty().orElseThrow("map is not empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is not empty");

        assertException(() -> {
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

        final AssertorStepMap<Map<String, Integer>, String, Integer> assertMap = Assertor.that(map);

        assertMap.isNotEmpty().orElseThrow();

        assertException(() -> {
            assertMap.not().isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        map.clear();

        assertException(() -> {
            assertMap.isNotEmpty().orElseThrow();
            fail(ERROR);
        }, IllegalArgumentException.class);

        assertException(() -> {
            assertMap.isNotEmpty().orElseThrow("map is empty");
            fail(ERROR);
        }, IllegalArgumentException.class, "map is empty");

        assertException(() -> {
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

        final List<String> keys = Arrays.asList(key1);

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("element3", 2);

        this.checkContains(Assertor.that(map), key1, key2, val1, keys, map, map1);
        this.checkContains(Assertor.that(map, EnumAnalysisMode.STREAM), key1, key2, val1, keys, map, map1);
        this.checkContains(Assertor.that(map, EnumAnalysisMode.PARALLEL), key1, key2, val1, keys, map, map1);

        assertFalse(Assertor.that((Map<String, Integer>) null).contains(key1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).contains(key1, val1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAll(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAll(map).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAny(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).containsAny(map).isOK());
    }

    private void checkContains(final AssertorStepMap<Map<String, Integer>, String, Integer> assertMap, final String key1, final String key2,
            final Integer val1, final List<String> keys, final Map<String, Integer> map, final Map<String, Integer> map1)
            throws IOException {

        assertMap.isNotNull().and().contains(key1).orElseThrow();

        assertMap.contains(key1).orElseThrow();
        assertMap.contains(key1).orElseThrow("map doesn't contain the element %2$s*");
        assertMap.contains(key1).orElseThrow(new IOException(), true);
        assertMap.contains(key1, val1).orElseThrow();
        assertMap.contains(key1, val1).orElseThrow("map doesn't contain the element %3$s*");
        assertMap.contains(key1, val1).orElseThrow(new IOException(), true);
        assertMap.contains(key2, null).orElseThrow();
        assertFalse(assertMap.contains(key2, 3).isOK());

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

        assertFalse(assertMap.contains(key1, (Integer) null).isOK());
        assertTrue(assertMap.contains(key2, (Integer) null).isOK());
        assertFalse(assertMap.contains(key2, 1).isOK());
        assertFalse(assertMap.containsAll(Arrays.asList("element3")).isOK());
        assertFalse(assertMap.containsAll(map1).isOK());
        assertFalse(assertMap.containsAny(Arrays.asList("element3")).isOK());
        assertFalse(assertMap.containsAny(map1).isOK());

        assertFalse(assertMap.contains((String) null).isOK());
        assertFalse(assertMap.contains((String) null, (Integer) null).isOK());
        assertFalse(assertMap.containsAll((List<String>) null).isOK());
        assertFalse(assertMap.containsAll((Map<String, Integer>) null).isOK());
        assertFalse(assertMap.containsAny((List<String>) null).isOK());
        assertFalse(assertMap.containsAny((Map<String, Integer>) null).isOK());
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

        this.checkDoesNotContain(Assertor.that(map), key1, key2, val1, val2, keys, map, map1);
        this.checkDoesNotContain(Assertor.that(map, EnumAnalysisMode.STREAM), key1, key2, val1, val2, keys, map, map1);
        this.checkDoesNotContain(Assertor.that(map, EnumAnalysisMode.PARALLEL), key1, key2, val1, val2, keys, map, map1);

        assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1, val1).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(keys).isOK());
        assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(map1).isOK());
    }

    private void checkDoesNotContain(final AssertorStepMap<Map<String, Integer>, String, Integer> assertMap, final String key1,
            final String key2, final Integer val1, final Integer val2, final List<String> keys, final Map<String, Integer> map,
            final Map<String, Integer> map1) throws IOException {

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

        assertEquals("the map '[element1=1]' should NOT contain the key 'element1'", assertMap.not().contains(key1).getErrors().get());
        assertFalse(assertMap.not().contains(key1).isOK());
        assertFalse(assertMap.not().contains(key1, val1).isOK());
        assertFalse(assertMap.not().containsAll(map.keySet()).isOK());
        assertFalse(assertMap.not().containsAll(map).isOK());

        assertTrue(assertMap.not().contains(key1, (Integer) null).isOK());
        assertFalse(assertMap.not().containsAll(Arrays.asList("element3")).isOK());
        assertFalse(assertMap.not().containsAll(map1).isOK());

        assertTrue(assertMap.not().contains((String) null).isOK());
        assertTrue(assertMap.not().contains(key1, 3).isOK());
        assertTrue(assertMap.not().contains((String) null, (Integer) null).isOK());
        assertFalse(assertMap.not().containsAll((List<String>) null).isOK());
        assertFalse(assertMap.not().containsAll((Map<String, Integer>) null).isOK());

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
