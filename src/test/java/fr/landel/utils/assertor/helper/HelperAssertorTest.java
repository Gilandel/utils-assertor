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
package fr.landel.utils.assertor.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.StepCharSequence;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.commons.ResultAssertor;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;

/**
 * Check {@link HelperAssertor}
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class HelperAssertorTest extends AbstractTest {

	/**
	 * Test method for {@link HelperAssertor#HelperAssertor()} .
	 */
	@Test
	public void testConstructor() {
		assertNotNull(new HelperAssertor());
		assertNotNull(new HelperStep());
		assertNotNull(new HelperEnd());
	}

	/**
	 * Test method for {@link HelperAssertor#combine} .
	 */
	@Test
	public void testCombine() {
		final Predicate<String> apTrue = (obj) -> true;
		final Predicate<String> apFalse = (obj) -> false;

		final BiPredicate<String, Boolean> aTrue = (obj, not) -> true;
		// final BiPredicate<String, Boolean> aFalse = (obj, not) -> false;

		final Predicate<Boolean> bpTrue = (obj) -> true;
		// final Predicate<Boolean> bpFalse = (obj) -> false;

		final BiPredicate<Boolean, Boolean> bTrue = (obj, not) -> true;
		final BiPredicate<Boolean, Boolean> bFalse = (obj, not) -> false;

		final StepAssertor<String> a = new StepAssertor<>("test", EnumType.CHAR_SEQUENCE, null);
		final StepAssertor<Boolean> b = new StepAssertor<>(true, EnumType.BOOLEAN, null);

		// precondition: true & true, valid: true & true
		StepAssertor<String> step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.CONTAINS, false);
		StepAssertor<Boolean> step2 = new StepAssertor<>(b, bpTrue, bTrue, false,
				MessageAssertor.of(null, "test", new Object[0]), MSG.BOOLEAN.TRUE, false);

		StepAssertor<String> step = new StepAssertor<>(step1, step2, EnumOperator.AND);

		ResultAssertor result = HelperAssertor.combine(step, false);

		assertTrue(result.isPrecondition());
		assertTrue(result.isValid());

		// ALL PRECONDITIONS KO

		step1 = new StepAssertor<>(a, apFalse, aTrue, false, null, MSG.CSQ.CONTAINS, false);
		step2 = new StepAssertor<>(b, bpTrue, bTrue, false, null, MSG.BOOLEAN.TRUE, false);

		step = new StepAssertor<>(step1, step2, EnumOperator.AND);

		result = HelperAssertor.combine(step, true);

		assertFalse(result.isPrecondition());
		assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty",
				HelperAssertor.getMessage(result));

		assertEquals("", HelperAssertor.getMessage(new ResultAssertor(false, true, null, Collections.emptyList())));

		// INVALID SUB

		step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.BLANK, false);
		step2 = new StepAssertor<>(b, bpTrue, bFalse, false, null, MSG.BOOLEAN.TRUE, false);

		step = new StepAssertor<>(step1, step2, EnumOperator.AND);

		result = HelperAssertor.combine(step, true);

		assertTrue(result.isPrecondition());
		assertFalse(result.isValid());
		assertEquals("(the boolean should be true)", HelperAssertor.getMessage(result));

		// OPERATOR NULL (== AND)

		step = new StepAssertor<>(step1, step2, null);

		result = HelperAssertor.combine(step, true);

		assertTrue(result.isPrecondition());
		assertFalse(result.isValid());
		assertEquals("(the boolean should be true)", HelperAssertor.getMessage(result));

		assertFalse(Assertor.that("test").isNotBlank().and(Assertor.that((CharSequence) null).contains("test")).isOK());

		// SUB NULL

		step = new StepAssertor<>(step1, (StepAssertor<Integer>) null, EnumOperator.AND);

		result = HelperAssertor.combine(step, true);

		assertTrue(result.isPrecondition());
		assertTrue(result.isValid());

		// STEP PREDICATE NULL

		StepCharSequence<CharSequence> step5 = () -> (StepAssertor<CharSequence>) null;
		assertTrue(Assertor.that("test").isNotBlank().and(step5).isOK());

		// MATCHER (with creation)

		assertException(() -> {
			HelperAssertor.combine(a, "test", true, false);
		}, IllegalArgumentException.class, "StepAssertor chain must contain a matcher step");

		assertException(() -> {
			final StepAssertor<String> a1 = new StepAssertor<>(EnumType.CHAR_SEQUENCE, null);
			final StepAssertor<String> b1 = new StepAssertor<>(a1, "test", EnumType.CHAR_SEQUENCE, EnumOperator.AND,
					null);
			HelperAssertor.combine(new StepAssertor<>(b1), null, true, false);
		}, UnsupportedOperationException.class, "Creation step cannot be used in Predicate mode");

		assertException(() -> {
			final StepAssertor<String> a1 = new StepAssertor<>("", EnumType.CHAR_SEQUENCE, null);
			HelperAssertor.combine(new StepAssertor<>(a1), null, true, false);
		}, UnsupportedOperationException.class, "Creation step cannot be used in Predicate mode");
	}

	/**
	 * Test method for {@link HelperAssertor#getLastChecked} .
	 */
	@Test
	public void testGetLastChecked() {
		final List<ParameterAssertor<?>> params = new ArrayList<>();

		assertNull(HelperAssertor.getLastChecked(params));

		params.add(new ParameterAssertor<>(true, EnumType.BOOLEAN));

		assertNull(HelperAssertor.getLastChecked(params));

		params.add(new ParameterAssertor<>(true, EnumType.BOOLEAN, true));

		assertTrue((Boolean) HelperAssertor.getLastChecked(params));
	}

	/**
	 * Test method for {@link HelperAssertor#isValid} .
	 */
	@Test
	public void testIsValid() {
		assertTrue(HelperAssertor.isValid(true, true, null));
		assertFalse(HelperAssertor.isValid(true, false, null));
		assertFalse(HelperAssertor.isValid(false, true, null));
		assertFalse(HelperAssertor.isValid(false, false, null));

		assertTrue(HelperAssertor.isValid(true, true, EnumOperator.AND));
		assertFalse(HelperAssertor.isValid(true, false, EnumOperator.AND));
		assertFalse(HelperAssertor.isValid(false, true, EnumOperator.AND));
		assertFalse(HelperAssertor.isValid(false, false, EnumOperator.AND));

		assertTrue(HelperAssertor.isValid(true, true, EnumOperator.OR));
		assertTrue(HelperAssertor.isValid(true, false, EnumOperator.OR));
		assertTrue(HelperAssertor.isValid(false, true, EnumOperator.OR));
		assertFalse(HelperAssertor.isValid(false, false, EnumOperator.OR));

		assertFalse(HelperAssertor.isValid(true, true, EnumOperator.XOR));
		assertTrue(HelperAssertor.isValid(true, false, EnumOperator.XOR));
		assertTrue(HelperAssertor.isValid(false, true, EnumOperator.XOR));
		assertFalse(HelperAssertor.isValid(false, false, EnumOperator.XOR));

		assertFalse(HelperAssertor.isValid(true, true, EnumOperator.NAND));
		assertFalse(HelperAssertor.isValid(true, false, EnumOperator.NAND));
		assertFalse(HelperAssertor.isValid(false, true, EnumOperator.NAND));
		assertTrue(HelperAssertor.isValid(false, false, EnumOperator.NAND));

		assertFalse(HelperAssertor.isValid(true, true, EnumOperator.NOR));
		assertTrue(HelperAssertor.isValid(true, false, EnumOperator.NOR));
		assertTrue(HelperAssertor.isValid(false, true, EnumOperator.NOR));
		assertTrue(HelperAssertor.isValid(false, false, EnumOperator.NOR));
	}

	/**
	 * Check {@link ConstantsAssertor}
	 */
	@Test
	public void testConstantsAssertor()
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Method loadProperties = ConstantsAssertor.class.getDeclaredMethod("loadProperties", String.class);
		loadProperties.setAccessible(true);

		Properties props = (Properties) loadProperties.invoke(null, "assertor_messages.properties");
		assertEquals(308, props.size());

		props = (Properties) loadProperties.invoke(null, "assertor_messages2.properties");
		assertEquals(0, props.size());

		props = (Properties) loadProperties.invoke(null, "");
		assertEquals(0, props.size());
	}
}