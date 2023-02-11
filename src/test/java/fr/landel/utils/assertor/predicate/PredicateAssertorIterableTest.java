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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorIterable;

/**
 * Check {@link AssertorIterable}
 *
 * @since Jun 4, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorIterableTest extends AbstractTest {

	private static final String ERROR = "error expected";

	private static final String EL1 = "element1";
	private static final String EL2 = "element2";

	private static Set<String> set = new HashSet<>();
	private static Iterable<String> iterable = new Iterable<String>() {
		@Override
		public Iterator<String> iterator() {
			return set.iterator();
		}
	};

	@BeforeEach
	public void init() {
		set.add(EL1);
		set.add(EL2);
	}

	/**
	 * Test method for {@link AssertorIterable} .
	 */
	@Test
	public void testPredicateGet() {
		final String el = "element";

		final Set<String> set = new HashSet<>();
		set.add(el);
		final List<String> list = new ArrayList<>();
		list.add(el);
		final Queue<String> queue = new LinkedList<>();
		queue.add(el);

		assertFalse(Assertor.<Set<String>, String>ofIterable().hasHashCode(0).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasHashCode(Objects.hashCode(set)).that(set).isOK());

		assertFalse(Assertor.<String>ofSet().hasHashCode(0).that(set).isOK());
		assertFalse(Assertor.<String>ofList().hasHashCode(0).that(list).isOK());
		assertFalse(Assertor.<String>ofQueue().hasHashCode(0).that(queue).isOK());
	}

	/**
	 * Test method for {@link AssertorIterable#hasSizeGT}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasSizeGT() throws IOException {
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGT(2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGT(-1).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).and().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).or().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).xor().not().contains(EL1).that(set).isOK());
		assertFalse(
				Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).nand().not().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGT(1).nor().not().contains(EL1).that(set).isOK());

		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeGT(1).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSizeGT(2).that(iterable).isOK());

		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeGT(-1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the iterable cannot be null");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeGT(2).that(set).orElseThrow(),
				IllegalArgumentException.class, "the iterable '[element1, element2]' size should be greater than: 2");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().not().hasSizeGT(1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the iterable '[element1, element2]' size should NOT be greater than: 1");
	}

	/**
	 * Test method for {@link AssertorIterable#hasSizeGTE}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasSizeGTE() throws IOException {
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(3).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(-1).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).and().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).or().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).xor().not().contains(EL1).that(set).isOK());
		assertFalse(
				Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).nand().not().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeGTE(1).nor().not().contains(EL1).that(set).isOK());

		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeGTE(1).that(iterable).isOK());
		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeGTE(2).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSizeGTE(3).that(iterable).isOK());

		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeGTE(-1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the iterable cannot be null");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeGTE(3).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the iterable '[element1, element2]' size should be greater than or equal to: 3");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().not().hasSizeGTE(1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the iterable '[element1, element2]' size should NOT be greater than or equal to: 1");
	}

	/**
	 * Test method for {@link AssertorIterable#hasSizeLT}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasSizeLT() throws IOException {
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLT(2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLT(1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLT(1).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLT(-1).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).and().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).or().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).xor().not().contains(EL1).that(set).isOK());
		assertFalse(
				Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).nand().not().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLT(3).nor().not().contains(EL1).that(set).isOK());

		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeLT(3).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSizeLT(2).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSizeLT(1).that(iterable).isOK());

		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeLT(-1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the iterable cannot be null");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeLT(1).that(set).orElseThrow(),
				IllegalArgumentException.class, "the iterable '[element1, element2]' size should be lower than: 1");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().not().hasSizeLT(3).that(set).orElseThrow(),
				IllegalArgumentException.class, "the iterable '[element1, element2]' size should NOT be lower than: 3");
	}

	/**
	 * Test method for {@link AssertorIterable#hasSizeLTE}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasSizeLTE() throws IOException {
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(1).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(-1).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).and().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).or().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).xor().not().contains(EL1).that(set).isOK());
		assertFalse(
				Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).nand().not().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSizeLTE(3).nor().not().contains(EL1).that(set).isOK());

		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeLTE(3).that(iterable).isOK());
		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSizeLTE(2).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSizeLTE(1).that(iterable).isOK());

		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeLTE(-1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the size has to be greater than or equal to 0 and the iterable cannot be null");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().hasSizeLTE(1).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the iterable '[element1, element2]' size should be lower than or equal to: 1");
		assertException(() -> Assertor.<Set<String>, String>ofIterable().not().hasSizeLTE(3).that(set).orElseThrow(),
				IllegalArgumentException.class,
				"the iterable '[element1, element2]' size should NOT be lower than or equal to: 3");
	}

	/**
	 * Test method for {@link AssertorIterable#hasSize(int)}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasSize() throws IOException {
		assertTrue(Assertor.<Set<String>, String>ofIterable().isNotEmpty()
				.and(Assertor.<Set<String>, String>ofIterable().contains(EL1)).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSize(2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSize(1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSize(1).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSize(-1).that(set).isOK());

		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSize(2).and().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSize(2).or().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSize(2).xor().not().contains(EL1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().hasSize(2).nand().not().contains(EL1).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().hasSize(2).nor().not().contains(EL1).that(set).isOK());

		assertTrue(Assertor.<Iterable<String>, String>ofIterable().hasSize(2).that(iterable).isOK());
		assertFalse(Assertor.<Iterable<String>, String>ofIterable().hasSize(1).that(iterable).isOK());
	}

	/**
	 * Test method for {@link AssertorIterable#hasSize(int)}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testHasNotSize() throws IOException {
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().hasSize(2).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().not().hasSize(1).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().hasSize(2).that((Set<String>) null).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().hasSize(-1).that(set).isOK());

		assertFalse(Assertor.<Iterable<String>, String>ofIterable().not().hasSize(2).that(iterable).isOK());
		assertTrue(Assertor.<Iterable<String>, String>ofIterable().not().hasSize(1).that(iterable).isOK());
	}

	/**
	 * Test method for {@link AssertorIterable#isEmpty}.
	 * 
	 * @throws IOException On not empty iterable
	 */
	@Test
	public void testIsEmpty() throws IOException {
		final String el = "element";

		final Set<String> set = new HashSet<>();

		Assertor.<Set<String>, String>ofIterable().isEmpty().that(set).orElseThrow();

		set.add(el);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isEmpty().that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isEmpty().that(set).orElseThrow("iterable is not empty");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable is not empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isEmpty().that(set).orElseThrow(new IOException(), true);
			fail(ERROR);
		}, IOException.class);
	}

	/**
	 * Test method for {@link AssertorIterable#isNotEmpty}.
	 * 
	 * @throws IOException On empty iterable
	 */
	@Test
	public void testIsNotEmpty() throws IOException {
		final String el = "element";

		final Set<String> set = new HashSet<>();
		set.add(el);

		Assertor.<Set<String>, String>ofIterable().isNotEmpty().that(set).orElseThrow();

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().isNotEmpty().that(set)
					.orElseThrow("iterable is not empty");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable is not empty");

		set.clear();

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isNotEmpty().that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isNotEmpty().that(set).orElseThrow("iterable is empty");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable is empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().isNotEmpty().that(set).orElseThrow(new IOException(), true);
			fail(ERROR);
		}, IOException.class);

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().isNotEmpty().that((Iterable<String>) null).orElseThrow();
			fail();
		}, IllegalArgumentException.class, "the iterable 'null' should be NOT empty and NOT null");
	}

	/**
	 * Test method for {@link AssertorIterable#contains}.
	 * 
	 * @throws IOException On not contain
	 */
	@Test
	public void testContains() throws IOException {
		final String el1 = "element1";
		final String el2 = "element2";

		final Set<String> set = new HashSet<>();
		set.add(el1);

		Assertor.<Set<String>, String>ofIterable().contains(el1).that(set)
				.orElseThrow("iterable doesn't contain the element %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).contains(el1).that(set)
				.orElseThrow("iterable doesn't contain the element %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).contains(el1).that(set)
				.orElseThrow("iterable doesn't contain the element %s*");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains(el2).that(set)
					.orElseThrow("iterable doesn't contain the element %2$s*");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable doesn't contain the element " + el2);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains(el2).that(set).orElseThrow(new IOException(), true);
			fail(ERROR);
		}, IOException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains((String) null).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable '[element1]' should contain the object 'null'");

		set.clear();

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains(el1).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains(el1).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable cannot be null or empty");

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().contains(el1).that((Iterable<String>) null).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable cannot be null or empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().contains((String) null).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable cannot be null or empty");
	}

	/**
	 * Test method for {@link AssertorIterable#contains}.
	 * 
	 * @throws IOException On not contain
	 */
	@Test
	public void testContainsIterable() throws IOException {
		final String el1 = "element1";
		final String el2 = "element2";

		final Set<String> set = new HashSet<>();
		final Set<String> set2 = new HashSet<>();
		set.add(el1);
		set2.add(el1);

		Assertor.<Set<String>, String>ofIterable().containsAll(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");
		Assertor.<Set<String>, String>ofIterable().containsAny(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).containsAll(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).containsAny(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).containsAll(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).containsAny(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");

		set2.add(el2);
		Assertor.<Set<String>, String>ofIterable().containsAny(set2).that(set)
				.orElseThrow("iterable doesn't contain the list %s*");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll(set2).that(set)
					.orElseThrow("iterable doesn't contain the list %2$s*");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable doesn't contain the list " + set2.toString());

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll(set2).that(set).orElseThrow(new IOException(), true);
			fail(ERROR);
		}, IOException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll((Iterable<String>) null).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAny((Iterable<String>) null).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		set.clear();

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll(set2).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll(set2).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().contains(el1).that((Iterable<String>) null).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable cannot be null or empty");

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().containsAny(set2).that((Iterable<String>) null)
					.orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().containsAll((Iterable<String>) null).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		set.add(null);
		Assertor.<Set<String>, String>ofIterable().contains(null).that(set).orElseThrow();
	}

	/**
	 * Test method for {@link AssertorIterable#contains}.
	 * 
	 * @throws IOException On contain
	 */
	@Test
	public void testDoesNotContain() throws IOException {
		final String el1 = "element1";
		final String el2 = "element2";

		final Set<String> set = new HashSet<>();
		set.add(el1);

		Assertor.<Set<String>, String>ofIterable().not().contains(el2).that(set)
				.orElseThrow("iterable contains the element %s*");
		Assertor.<Set<String>, String>ofIterable().not().contains((String) null).that(set).orElseThrow();
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).not().contains(el2).that(set)
				.orElseThrow("iterable contains the element %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).not().contains((String) null).that(set)
				.orElseThrow();
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).not().contains(el2).that(set)
				.orElseThrow("iterable contains the element %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).not().contains((String) null).that(set)
				.orElseThrow();

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().contains(el1).that(set).orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable '[element1]' should NOT contain the object 'element1'");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().contains(el1).that(set)
					.orElseThrow("iterable contains the element %2$s*");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable contains the element " + el1);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().contains(el1).that(set).orElseThrow(new IOException(),
					false);
			fail(ERROR);
		}, IOException.class);

		set.clear();

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().not().contains(el1).that((Iterable<String>) null)
					.orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "the iterable cannot be null or empty");
	}

	/**
	 * Test method for {@link AssertorIterable#contains}.
	 * 
	 * @throws IOException On contain
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

		Assertor.<Set<String>, String>ofIterable().not().containsAll(set2).that(set)
				.orElseThrow("iterable contains the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.STREAM).not().containsAll(set2).that(set)
				.orElseThrow("iterable contains the list %s*");
		Assertor.<Set<String>, String>ofIterable(EnumAnalysisMode.PARALLEL).not().containsAll(set2).that(set)
				.orElseThrow("iterable contains the list %s*");

		set2.remove(el1);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().containsAll(set2).that(set)
					.orElseThrow("iterable contains the list %2$s*");
			fail(ERROR);
		}, IllegalArgumentException.class, "iterable contains the list " + set2.toString());

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().containsAll(set2).that(set).orElseThrow(new IOException(),
					true);
			fail(ERROR);
		}, IOException.class);

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().containsAll((Iterable<String>) null).that(set)
					.orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAll(set2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAll(set).that(set).isOK());
		assertTrue(Assertor.<Set<String>, String>ofIterable().not().containsAny(set2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAny(set).that(set).isOK());

		set.clear();

		assertException(() -> {
			Assertor.<Iterable<String>, String>ofIterable().not().containsAll(set2).that((Iterable<String>) null)
					.orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertException(() -> {
			Assertor.<Set<String>, String>ofIterable().not().containsAll((Iterable<String>) null).that(set)
					.orElseThrow();
			fail(ERROR);
		}, IllegalArgumentException.class, "neither iterables can be null or empty");

		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAll(set2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAll(set).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAny(set2).that(set).isOK());
		assertFalse(Assertor.<Set<String>, String>ofIterable().not().containsAny(set).that(set).isOK());
	}

	/**
	 * Test method for
	 * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
	 * .
	 */
	@Test
	public void testIsNotEmptyOKCollectionOfQString() {
		try {
			Assertor.ofIterable().isNotEmpty().that(Arrays.asList("")).orElseThrow("empty collection");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for
	 * {@link AssertorIterable#isNotEmpty(java.util.Collection, String, Object...)}
	 * .
	 */
	@Test
	public void testIsNotEmptyKOCollectionOfQString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofIterable().isNotEmpty().that(Collections.emptyList()).orElseThrow("empty collection");
		});
	}

	/**
	 * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)} .
	 */
	@Test
	public void testIsNotEmptyOKCollectionOfQ() {
		try {
			Assertor.ofIterable().isNotEmpty().that(Arrays.asList("")).orElseThrow();
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorIterable#isNotEmpty(java.util.Collection)} .
	 */
	@Test
	public void testIsNotEmptyKOCollectionOfQ() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofIterable().isNotEmpty().that(Collections.emptyList()).orElseThrow();
		});
	}

	/**
	 * Check {@link AssertorIterable#containsInOrder}
	 */
	@Test
	public void testContainsInOrder() {
		List<String> listTU = Arrays.asList("t", "u");
		List<String> listTUClone = Arrays.asList("t", "u");
		List<String> listUT = Arrays.asList("u", "t");
		List<String> listU = Arrays.asList("u");
		List<String> listTVTUV = Arrays.asList("t", "v", "t", "u", "v");
		List<String> listXTUTUV = Arrays.asList("x", "t", "u", "t", "u", "v");
		List<String> listTUV = Arrays.asList("t", "u", "v");
		List<String> listUV = Arrays.asList("u", "v");
		List<String> listZ = Arrays.asList("z");
		List<String> listTNull = Arrays.asList("t", null);

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
			PredicateAssertorStepIterable<List<String>, String> predicate = Assertor
					.<List<String>, String>ofIterable(mode);

			assertTrue(predicate.containsInOrder(listTUClone).that(listTU).isOK());
			assertFalse(predicate.not().containsInOrder(listTUClone).that(listTU).isOK());

			assertFalse(predicate.containsInOrder(listUT).that(listTU).isOK());
			assertTrue(predicate.not().containsInOrder(listUT).that(listTU).isOK());

			assertTrue(predicate.containsInOrder(listU).that(listTU).isOK());
			assertFalse(predicate.not().containsInOrder(listU).that(listTU).isOK());

			assertTrue(predicate.containsInOrder(listTU).that(listTVTUV).isOK());
			assertTrue(predicate.containsInOrder(listTU).that(listXTUTUV).isOK());
			assertTrue(predicate.containsInOrder(listTU).that(listTU).isOK());
			assertTrue(predicate.containsInOrder(listTNull).that(listTNull).isOK());
			assertTrue(predicate.containsInOrder(listTU).that(listTUV).isOK());
			assertTrue(predicate.containsInOrder(listUV).that(listTUV).isOK());
			assertFalse(predicate.containsInOrder(listTUV).that(listTU).isOK());
			assertFalse(predicate.containsInOrder(listUT).that(listTU).isOK());
			assertFalse(predicate.containsInOrder(listZ).that(listTU).isOK());

			assertFalse(predicate.not().containsInOrder(listTU).that(listTVTUV).isOK());
			assertFalse(predicate.not().containsInOrder(listTU).that(listXTUTUV).isOK());
			assertFalse(predicate.not().containsInOrder(listTU).that(listTU).isOK());
			assertFalse(predicate.not().containsInOrder(listTNull).that(listTNull).isOK());
			assertFalse(predicate.not().containsInOrder(listTU).that(listTUV).isOK());
			assertFalse(predicate.not().containsInOrder(listUV).that(listTUV).isOK());
			assertTrue(predicate.not().containsInOrder(listTUV).that(listTU).isOK());
			assertTrue(predicate.not().containsInOrder(listUT).that(listTU).isOK());
			assertTrue(predicate.not().containsInOrder(listZ).that(listTU).isOK());

			assertFalse(predicate.not().containsInOrder(listU).that((List<String>) null).isOK());
			assertFalse(predicate.not().containsInOrder((List<String>) null).that(listTU).isOK());
		}
	}

	/**
	 * Check {@link AssertorIterable#anyMatch}
	 */
	@Test
	public void testAnyMatch() {
		List<String> listtu = Arrays.asList("t", "u");
		List<String> listTu = Arrays.asList("T", "u");
		List<String> listTU = Arrays.asList("T", "U");
		List<String> listtNull = Arrays.asList("t", null);

		Predicate<String> predicate = e -> Objects.equals(e, StringUtils.lowerCase(e));

		assertTrue(Assertor.<String>ofList().anyMatch(predicate).that(listtu).isOK());

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

			PredicateAssertorStepIterable<List<String>, String> predicateAssertor = Assertor.<String>ofList(mode);
			PredicateStepIterable<List<String>, String> predicateStep = predicateAssertor.anyMatch(predicate);

			assertTrue(predicateStep.that(listtu).isOK());
			assertTrue(predicateStep.that(listTu).isOK());
			assertFalse(predicateStep.that(listTU).isOK());
			assertTrue(predicateStep.that(listtNull).isOK());

			assertException(() -> predicateStep.that(Collections.<String>emptyList()).orElseThrow(),
					IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateStep.that((List<String>) null).orElseThrow(), IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateAssertor.anyMatch(null).that(listTu).orElseThrow(),
					IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateStep.that(listTU).orElseThrow(), IllegalArgumentException.class,
					"any iterable element '[T, U]' should match the predicate");
		}
	}

	/**
	 * Check {@link AssertorIterable#allMatch}
	 */
	@Test
	public void testAllMatch() {
		List<String> listtu = Arrays.asList("t", "u");
		List<String> listTu = Arrays.asList("T", "u");
		List<String> listTU = Arrays.asList("T", "U");
		List<String> listtNull = Arrays.asList("t", null);

		Predicate<String> predicate = e -> Objects.equals(e, StringUtils.lowerCase(e));

		assertTrue(Assertor.<String>ofList().allMatch(predicate).that(listtu).isOK());

		for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

			PredicateAssertorStepIterable<List<String>, String> predicateAssertor = Assertor.<String>ofList(mode);
			PredicateStepIterable<List<String>, String> predicateStep = predicateAssertor.allMatch(predicate);

			assertTrue(predicateStep.that(listtu).isOK());
			assertFalse(predicateStep.that(listTu).isOK());
			assertFalse(predicateStep.that(listTU).isOK());
			assertTrue(predicateStep.that(listtNull).isOK());

			assertException(() -> predicateStep.that(Collections.<String>emptyList()).orElseThrow(),
					IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateStep.that((List<String>) null).orElseThrow(), IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateAssertor.allMatch(null).that(listTu).orElseThrow(),
					IllegalArgumentException.class,
					"the iterable cannot be null or empty and predicate cannot be null");
			assertException(() -> predicateStep.that(listTU).orElseThrow(), IllegalArgumentException.class,
					"all the iterable elements '[T, U]' should match the predicate");
		}
	}
}
