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
package fr.landel.utils.assertor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import org.junit.jupiter.api.Test;

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
	 * @throws IOException On error
	 */
	@Test
	public void testIsEmpty() throws IOException {
		final String el = "element";

		final Map<String, Integer> map = new HashMap<>();

		final AssertorStepMap<String, Integer> assertMap = Assertor.that(map);

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

		Assertor.that((Map<String, Integer>) null).isEmpty()
				.orElseThrow("this argument is required; it must not be null");
	}

	/**
	 * Test method for {@link AssertorMap#isNotEmpty}.
	 * 
	 * @throws IOException On error
	 */
	@Test
	public void testIsNotEmpty() throws IOException {
		final String el = "element";

		final Map<String, Integer> map = new HashMap<>();
		map.put(el, 1);

		final AssertorStepMap<String, Integer> assertMap = Assertor.that(map);

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
	 * @throws IOException On error
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

	private void checkContains(final AssertorStepMap<String, Integer> assertMap, final String key1, final String key2,
			final Integer val1, final List<String> keys, final Map<String, Integer> map,
			final Map<String, Integer> map1) throws IOException {

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
	 * @throws IOException On error
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
		this.checkDoesNotContain(Assertor.that(map, EnumAnalysisMode.PARALLEL), key1, key2, val1, val2, keys, map,
				map1);

		assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).not().contains(key1, val1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(keys).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).not().containsAll(map1).isOK());
	}

	private void checkDoesNotContain(final AssertorStepMap<String, Integer> assertMap, final String key1,
			final String key2, final Integer val1, final Integer val2, final List<String> keys,
			final Map<String, Integer> map, final Map<String, Integer> map1) throws IOException {

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
				assertMap.not().contains(key1).getErrors().get());
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
	@Test
	public void testIsNotEmptyKOMapOfQQString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that(new HashMap<String, String>()).isNotEmpty().orElseThrow();
		});
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
	@Test
	public void testIsNotEmptyKOMapOfQQ() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that(new HashMap<String, String>()).isNotEmpty().orElseThrow();
		});
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

		assertTrue(Assertor.that(map).hasSizeGT(1).isOK());
		assertTrue(Assertor.that(map).hasSizeGT(0).isOK());
		assertFalse(Assertor.that(map).hasSizeGT(2).isOK());
		assertFalse(Assertor.that(map).hasSizeGT(3).isOK());

		assertFalse(Assertor.that(map).hasSizeGT(-1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).hasSizeGT(1).isOK());

		assertException(() -> Assertor.that(map).hasSizeGT(-1).orElseThrow(), IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the map cannot be null");
		assertException(() -> Assertor.that(map).hasSizeGT(2).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should be greater than: 2");
		assertException(() -> Assertor.that(map).not().hasSizeGT(1).orElseThrow(), IllegalArgumentException.class,
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

		assertTrue(Assertor.that(map).hasSizeGTE(1).isOK());
		assertTrue(Assertor.that(map).hasSizeGTE(0).isOK());
		assertTrue(Assertor.that(map).hasSizeGTE(2).isOK());
		assertFalse(Assertor.that(map).hasSizeGTE(3).isOK());

		assertFalse(Assertor.that(map).hasSizeGTE(-1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).hasSizeGTE(1).isOK());

		assertException(() -> Assertor.that(map).hasSizeGTE(-1).orElseThrow(), IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the map cannot be null");
		assertException(() -> Assertor.that(map).hasSizeGTE(3).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should be greater than or equal to: 3");
		assertException(() -> Assertor.that(map).not().hasSizeGTE(1).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should NOT be greater than or equal to: 1");
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

		assertFalse(Assertor.that(map).hasSizeLT(1).isOK());
		assertFalse(Assertor.that(map).hasSizeLT(0).isOK());
		assertFalse(Assertor.that(map).hasSizeLT(2).isOK());
		assertTrue(Assertor.that(map).hasSizeLT(3).isOK());

		assertFalse(Assertor.that(map).hasSizeLT(-1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).hasSizeLT(1).isOK());

		assertException(() -> Assertor.that(map).hasSizeLT(-1).orElseThrow(), IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the map cannot be null");
		assertException(() -> Assertor.that(map).hasSizeLT(1).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should be lower than: 1");
		assertException(() -> Assertor.that(map).not().hasSizeLT(3).orElseThrow(), IllegalArgumentException.class,
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

		assertFalse(Assertor.that(map).hasSizeLTE(1).isOK());
		assertFalse(Assertor.that(map).hasSizeLTE(0).isOK());
		assertTrue(Assertor.that(map).hasSizeLTE(2).isOK());
		assertTrue(Assertor.that(map).hasSizeLTE(3).isOK());

		assertFalse(Assertor.that(map).hasSizeLTE(-1).isOK());
		assertFalse(Assertor.that((Map<String, Integer>) null).hasSizeLTE(1).isOK());

		assertException(() -> Assertor.that(map).hasSizeLTE(-1).orElseThrow(), IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the map cannot be null");
		assertException(() -> Assertor.that(map).hasSizeLTE(1).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should be lower than or equal to: 1");
		assertException(() -> Assertor.that(map).not().hasSizeLTE(3).orElseThrow(), IllegalArgumentException.class,
				"the map '[element1=1, element2=2]' size should NOT be lower than or equal to: 3");
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
		Map<String, Integer> mapTVU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("v", 3),
				Pair.of("t", 1), Pair.of("u", 2), Pair.of("v", 3));
		// t, v, u (old keys are replaced)
		Map<String, Integer> mapXTUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("x", 4), Pair.of("t", 1),
				Pair.of("u", 2), Pair.of("t", 1), Pair.of("u", 2), Pair.of("v", 3));
		// x, t, u , v (old keys are replaced)
		Map<String, Integer> mapTNull = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of(null, 2));
		Map<String, Integer> mapZ = MapUtils2.newMap(LinkedHashMap::new, Pair.of("z", 5));
		Map<String, Integer> mapUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("u", 2), Pair.of("v", 3));
		Map<String, Integer> mapTUV = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2),
				Pair.of("v", 3));

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
			assertTrue(Assertor.that(mapTU, mode).containsInOrder(mapTUClone).isOK());
			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapUT).isOK());
			assertTrue(Assertor.that(mapTU, mode).containsInOrder(mapU).isOK());

			assertFalse(Assertor.that(mapTVU, mode).containsInOrder(mapTU).isOK());
			assertTrue(Assertor.that(mapXTUV, mode).containsInOrder(mapTU).isOK());
			assertTrue(Assertor.that(mapTU, mode).containsInOrder(mapTUClone).isOK());
			assertTrue(Assertor.that(mapTNull, mode).containsInOrder(mapTNull).isOK());
			assertTrue(Assertor.that(mapTUV, mode).containsInOrder(mapTU).isOK());
			assertTrue(Assertor.that(mapTUV, mode).containsInOrder(mapUV).isOK());
			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapTUV).isOK());
			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapUT).isOK());
			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapZ).isOK());

			// NOT
			assertFalse(Assertor.that(mapTU, mode).not().containsInOrder(mapTUClone).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapUT).isOK());
			assertFalse(Assertor.that(mapTU, mode).not().containsInOrder(mapU).isOK());

			assertTrue(Assertor.that(mapTVU, mode).not().containsInOrder(mapTU).isOK());
			assertFalse(Assertor.that(mapXTUV, mode).not().containsInOrder(mapTU).isOK());
			assertFalse(Assertor.that(mapTU, mode).not().containsInOrder(mapTUClone).isOK());
			assertFalse(Assertor.that(mapTNull, mode).not().containsInOrder(mapTNull).isOK());
			assertFalse(Assertor.that(mapTUV, mode).not().containsInOrder(mapTU).isOK());
			assertFalse(Assertor.that(mapTUV, mode).not().containsInOrder(mapUV).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapTUV).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapUT).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapZ).isOK());

			// Keys and values

			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapZ.keySet()).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapZ.keySet()).isOK());

			assertFalse(Assertor.that(mapTU, mode).containsValuesInOrder(mapZ.values()).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsValuesInOrder(mapZ.values()).isOK());

			assertFalse(Assertor.that(mapTU, mode).containsInOrder(mapTU2).isOK());
			assertFalse(Assertor.that(mapTU, mode).containsValuesInOrder(mapTU2.values()).isOK());
			assertTrue(Assertor.that(mapTU, mode).containsInOrder(mapTU2.keySet()).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsInOrder(mapTU2).isOK());
			assertTrue(Assertor.that(mapTU, mode).not().containsValuesInOrder(mapTU2.values()).isOK());
			assertFalse(Assertor.that(mapTU, mode).not().containsInOrder(mapTU2.keySet()).isOK());

			assertFalse(Assertor.that(mapTU2, mode).containsInOrder(mapUV).isOK());
			assertTrue(Assertor.that(mapTU2, mode).containsValuesInOrder(mapUV.values()).isOK());
			assertFalse(Assertor.that(mapTU2, mode).containsInOrder(mapUV.keySet()).isOK());
			assertTrue(Assertor.that(mapTU2, mode).not().containsInOrder(mapUV).isOK());
			assertFalse(Assertor.that(mapTU2, mode).not().containsValuesInOrder(mapUV.values()).isOK());
			assertTrue(Assertor.that(mapTU2, mode).not().containsInOrder(mapUV.keySet()).isOK());

			assertTrue(Assertor.that(mapTU, mode).containsInOrder(mapTU2.keySet()).isOK());
			assertFalse(Assertor.that(mapTU, mode).not().containsInOrder(mapTU2.keySet()).isOK());

			testContainsInOrderException(mapTUClone, mapTUV, mapXTUV, mode);
		}
	}

	private void testContainsInOrderException(final Map<String, Integer> mapTU, final Map<String, Integer> mapTUV,
			final Map<String, Integer> mapXTUV, final EnumAnalysisMode mode) {

		assertException(() -> Assertor.that(mapTU, mode).containsInOrder(mapTUV).orElseThrow(),
				IllegalArgumentException.class,
				"the map '[t=1, u=2]' should contain all entries from second map '[t=1, u=2, v=3]' in the same order");

		assertException(() -> Assertor.that(mapXTUV, mode).not().containsInOrder(mapTU).orElseThrow(),
				IllegalArgumentException.class,
				"the map '[x=4, t=1, u=2, v=3]' should NOT contain all entries from second map '[t=1, u=2]' or should be in an other order");

		assertException(() -> Assertor.that((Map<String, Integer>) null, mode).containsInOrder(mapTU).orElseThrow(),
				IllegalArgumentException.class, "neither maps can be null or empty");

		assertException(
				() -> Assertor.that(Collections.<String, Integer>emptyMap(), mode).containsInOrder(mapTU).orElseThrow(),
				IllegalArgumentException.class, "neither maps can be null or empty");

		assertException(() -> Assertor.that(mapTU, mode).containsInOrder((Map<String, Integer>) null).orElseThrow(),
				IllegalArgumentException.class, "neither maps can be null or empty");

		assertException(() -> Assertor.that(mapTU, mode).containsInOrder((List<String>) null).orElseThrow(),
				IllegalArgumentException.class, "neither map nor keys can be null or empty");

		assertException(() -> Assertor.that((Map<String, Integer>) null, mode).containsInOrder((List<String>) null)
				.orElseThrow(), IllegalArgumentException.class, "neither map nor keys can be null or empty");

		assertException(() -> Assertor.that(mapTU, mode).containsValuesInOrder((List<Integer>) null).orElseThrow(),
				IllegalArgumentException.class, "neither map nor values can be null or empty");

		assertException(() -> Assertor.that((Map<String, Integer>) null, mode).containsValuesInOrder(mapTU.values())
				.orElseThrow(), IllegalArgumentException.class, "neither map nor values can be null or empty");

		assertException(() -> Assertor.that((Map<String, Integer>) null, mode)
				.containsValuesInOrder((List<Integer>) null).orElseThrow(), IllegalArgumentException.class,
				"neither map nor values can be null or empty");
	}

	/**
	 * Check {@link AssertorMap#containsValues}
	 */
	@Test
	public void testContainsValue() {
		Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

		assertTrue(Assertor.that(mapTU).containsValue(1).isOK());
		assertFalse(Assertor.that(mapTU).containsValue(0).isOK());

		assertException(() -> Assertor.that(mapTU).containsValue(0).orElseThrow(), IllegalArgumentException.class,
				"the map '[t=1, u=2]' should contain the value '0'");

		assertException(() -> Assertor.that((Map<String, Integer>) null).containsValue(0).orElseThrow(),
				IllegalArgumentException.class, "the map cannot be null or empty");

		assertException(() -> Assertor.that(mapTU).containsValue(null).orElseThrow(), IllegalArgumentException.class,
				"the map '[t=1, u=2]' should contain the value 'null'");
	}

	/**
	 * Check {@link AssertorMap#containsAnyValues}
	 */
	@Test
	public void testContainsAnyValues() {
		Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
			AssertorStepMap<String, Integer> assertorTU = Assertor.that(mapTU, mode);

			assertTrue(assertorTU.containsAnyValues(Arrays.asList(1, 2)).isOK());
			assertTrue(assertorTU.containsAnyValues(Arrays.asList(1, 2, 3)).isOK());
			assertTrue(assertorTU.containsAnyValues(Arrays.asList(0, 1)).isOK());
			assertFalse(assertorTU.containsAnyValues(Arrays.asList(0)).isOK());

			assertFalse(Assertor.that((Map<String, Integer>) null, mode).containsAnyValues(Arrays.asList(0)).isOK());
			assertFalse(assertorTU.containsAnyValues((List<Integer>) null).isOK());

			assertException(() -> assertorTU.containsAnyValues(Arrays.asList(0)).orElseThrow(),
					IllegalArgumentException.class, "the map '[t=1, u=2]' should contain any values '[0]'");

			assertException(
					() -> Assertor.that((Map<String, Integer>) null, mode).containsAnyValues(Arrays.asList(0))
							.orElseThrow(),
					IllegalArgumentException.class, "neither map nor values can be null or empty");

			assertException(() -> assertorTU.containsAnyValues((List<Integer>) null).orElseThrow(),
					IllegalArgumentException.class, "neither map nor values can be null or empty");
		}
	}

	/**
	 * Check {@link AssertorMap#containsAllValues}
	 */
	@Test
	public void testContainsAllValues() {
		Map<String, Integer> mapTU = MapUtils2.newMap(LinkedHashMap::new, Pair.of("t", 1), Pair.of("u", 2));

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
			AssertorStepMap<String, Integer> assertorTU = Assertor.that(mapTU, mode);

			assertTrue(assertorTU.containsAllValues(Arrays.asList(1, 2)).isOK());
			assertFalse(assertorTU.containsAllValues(Arrays.asList(1, 2, 3)).isOK());
			assertFalse(assertorTU.containsAllValues(Arrays.asList(0, 1)).isOK());
			assertFalse(assertorTU.containsAllValues(Arrays.asList(0)).isOK());

			assertFalse(Assertor.that((Map<String, Integer>) null, mode).containsAllValues(Arrays.asList(0)).isOK());
			assertFalse(assertorTU.containsAllValues((List<Integer>) null).isOK());

			assertException(() -> assertorTU.containsAllValues(Arrays.asList(0)).orElseThrow(),
					IllegalArgumentException.class, "the map '[t=1, u=2]' should contain all values '[0]'");

			assertException(
					() -> Assertor.that((Map<String, Integer>) null, mode).containsAllValues(Arrays.asList(0))
							.orElseThrow(),
					IllegalArgumentException.class, "neither map nor values can be null or empty");

			assertException(() -> assertorTU.containsAllValues((List<Integer>) null).orElseThrow(),
					IllegalArgumentException.class, "neither map nor values can be null or empty");
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

		assertTrue(Assertor.that(maptu).anyMatch(predicate).isOK());

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

			assertTrue(Assertor.that(maptu, mode).anyMatch(predicate).isOK());
			assertTrue(Assertor.that(mapTu, mode).anyMatch(predicate).isOK());
			assertFalse(Assertor.that(mapTU, mode).anyMatch(predicate).isOK());
			assertFalse(Assertor.that(maptNull, mode).anyMatch(predicate).isOK());
			assertFalse(Assertor.that(maptUNull, mode).anyMatch(predicate).isOK());

			assertException(
					() -> Assertor.that(Collections.<String, Integer>emptyMap(), mode).anyMatch(predicate)
							.orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that((Map<String, Integer>) null, mode).anyMatch(predicate).orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that(mapTu, mode).anyMatch(null).orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that(mapTU, mode).anyMatch(predicate).orElseThrow(),
					IllegalArgumentException.class, "any map entry '[T=1, U=2]' should match the predicate");
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

		assertTrue(Assertor.that(maptu).allMatch(predicate).isOK());

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

			assertTrue(Assertor.that(maptu, mode).allMatch(predicate).isOK());
			assertFalse(Assertor.that(mapTu, mode).allMatch(predicate).isOK());
			assertFalse(Assertor.that(mapTU, mode).allMatch(predicate).isOK());
			assertFalse(Assertor.that(maptNull, mode).allMatch(predicate).isOK());
			assertFalse(Assertor.that(maptUNull, mode).allMatch(predicate).isOK());

			assertException(
					() -> Assertor.that(Collections.<String, Integer>emptyMap(), mode).allMatch(predicate)
							.orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that((Map<String, Integer>) null, mode).allMatch(predicate).orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that(mapTu, mode).allMatch(null).orElseThrow(),
					IllegalArgumentException.class, "the map cannot be null or empty and predicate cannot be null");
			assertException(() -> Assertor.that(mapTU, mode).allMatch(predicate).orElseThrow(),
					IllegalArgumentException.class, "all the map entries '[T=1, U=2]' should match the predicate");
		}
	}
}
