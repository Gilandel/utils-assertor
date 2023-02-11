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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.commons.MessagesAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.DateUtils;

/**
 * Check {@link HelperMessage}
 *
 * @since Aug 9, 2016
 * @author Gilles
 *
 */
public class HelperMessageTest extends AbstractTest {

	/**
	 * Test method for {@link HelperMessage#PreFormatter()} .
	 */
	@Test
	public void testConstructor() {
		assertNotNull(new HelperMessage());
	}

	/**
	 * Test method for {@link HelperMessage#getMessage} .
	 */
	@Test
	public void testGetMessage1() {
		// TEST GET MESSAGE

		assertEquals("default", HelperMessage.getMessage("default", null, null, null, null));
		assertEquals("message", HelperMessage.getMessage("default", null, "message", null, null));
		assertEquals("message ", HelperMessage.getMessage("default", null, "message %s", null, null));
		assertEquals("message test",
				HelperMessage.getMessage("default", null, "message %s", null, new Object[] { "test" }));
		assertEquals("message 23.26",
				HelperMessage.getMessage("default", Locale.US, "message %.2f", null, new Object[] { 23.256f }));
		assertEquals("message 23,26",
				HelperMessage.getMessage("default", Locale.FRANCE, "message %.2f", null, new Object[] { 23.256f }));

		Assertor.setLocale(Locale.FRANCE);
		assertException(() -> {
			Assertor.that(23.6f).isEqual(25.6f).orElseThrow();
			fail();
		}, IllegalArgumentException.class, "the number '23,600' should be equal to '25,600'");
		Assertor.setLocale(Locale.US);

		assertException(() -> {
			Assertor.that("texte11").not().isEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s",
					"args");
			fail();
		}, IllegalArgumentException.class, "texte 'texte11' is not equal to 'texte11', args");

		assertException(() -> {
			Assertor.that("texte11").isEqual(true).orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s", "args");
			fail();
		}, IllegalArgumentException.class, "texte 'true' is not equal to 'texte11', args");
	}

	/**
	 * Test method for {@link HelperMessage#getMessage} .
	 */
	@Test
	public void testGetMessage2() {
		try {
			Assertor.that("texte11").isNotEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s",
					"args");
			fail("Expect an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("texte 'texte11' is not equal to 'texte11', args", e.getMessage());
		}

		try {
			Assertor.that("texte11").isEqual("texte12")
					.orElseThrow("texte '%2$s*' is not equal to '%1$s*' or '%s*' != '%s*'...");
			fail("Expect an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("texte 'texte12' is not equal to 'texte11' or 'texte11' != 'texte12'...", e.getMessage());
		}

		assertException(() -> {
			Assertor.that("texte11").isBlank().or().isNotEqual("texte11").orElseThrow();
			fail("Expect an exception");
		}, IllegalArgumentException.class,
				"the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte11' should NOT be equal to 'texte11'");

		assertException(() -> {
			Assertor.that((String) null).contains('a').or().isEqual("texte11").orElseThrow();
			fail("Expect an exception");
		}, IllegalArgumentException.class,
				"the char sequence cannot be null and the searched substring cannot be null or empty");

		try {
			Assertor.that("texte11").isNotBlank().and().isNotEqual("texte11").orElseThrow();
			fail("Expect an exception");
		} catch (IllegalArgumentException e) {
			assertEquals("the char sequence 'texte11' should NOT be equal to 'texte11'", e.getMessage());
		}

		try {
			Assertor.that("texte11").isBlank().or().not().isEqual("texte11").orElseThrow();
			fail("Expect an exception");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte11' should NOT be equal to 'texte11'",
					e.getMessage());
		}

		try {
			Assertor.that("texte11").isBlank().or("texte12").isEqual("texte13").orElseThrow();
			fail("Expect an exception");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"the char sequence 'texte11' should be null, empty or blank OR the char sequence 'texte12' should be equal to 'texte13'",
					e.getMessage());
		}
	}

	/**
	 * Test method for {@link HelperMessage#getMessage} .
	 */
	@Test
	public void testGetMessage3() {
		assertException(() -> {
			Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow();
			fail("Expect an exception");
		}, IllegalArgumentException.class,
				"the char sequence 'texte11' should be null, empty or blank"
						+ " OR the char sequence 'texte12' should NOT start with 'text'"
						+ " OR the char sequence 'texte12' should be null, empty or blank");

		// prerequisites == false
		assertException(() -> {
			Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank().orElseThrow();
			fail("Expect an exception");
		}, IllegalArgumentException.class,
				"the char sequence 'texte11' should be null, empty or blank"
						+ " OR the char sequence 'texte12' should NOT start with 'text'"
						+ " OR the char sequence 'texte12' should be null, empty or blank");

		assertException(() -> {
			Assertor.that("texte11").isBlank().or("texte12").not().startsWith("text").or().isBlank()
					.orElseThrow((errors, parameters) -> new IllegalArgumentException(errors));
			fail("Expect an exception");
		}, IllegalArgumentException.class,
				"the char sequence 'texte11' should be null, empty or blank"
						+ " OR the char sequence 'texte12' should NOT start with 'text'"
						+ " OR the char sequence 'texte12' should be null, empty or blank");
		// previous assertion is invalid (prerequisites == false), only first
		// prerequisite error set as message
		assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty",
				Assertor.that("text1").contains((CharSequence) null).and("text2").isBlank().getErrors().get());

		Assertor.that(12).isGT(12).nor("text").contains("ex").orElseThrow();
		Assertor.that(12).not().isGT(12).or("text").not().contains("ex").orElseThrow();
		Assertor.that(12).isLTE(12).or("text").not().contains("ex").orElseThrow();

		assertEquals("the number '12' should be greater than '12' NAND the char sequence 'text' should contain 'ex'",
				Assertor.that(12).isGT(12).nand("text").contains("ex").getErrors().get());
		assertEquals("the char sequence 'text' should NOT contain 'ex'",
				Assertor.that(12).not().isGT(12).and("text").not().contains("ex").getErrors().get());
		assertEquals("the char sequence 'text' should NOT contain 'ex'",
				Assertor.that(12).isLTE(12).and("text").not().contains("ex").getErrors().get());

		assertFalse(Assertor.that("text").isNotEmpty().and(Assertor.that("other").contains((String) null)).isOK());
		assertFalse(Assertor.that("text").isEmpty().and(Assertor.that("other").contains((String) null)).isOK());
	}

	/**
	 * Test method for {@link HelperMessage#getMessage} .
	 */
	@Test
	public void testGetMessageNullObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte11").isNotEqual("texte11").orElseThrow("texte '%2$s*' is not equal to '%1$s*', %s",
					(Object[]) null);
		});
	}

	/**
	 * Test method for {@link HelperMessage#convertParams(java.util.List)}.
	 */
	@Test
	public void testConvertParams() {
		final List<ParameterAssertor<?>> parameters = new ArrayList<>();

		final Date date1 = new Date(1464475553640L);
		final Calendar calendar1 = DateUtils.getCalendar(date1);
		final LocalDateTime time = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
		final IOException exception = new IOException("error");
		Integer[] integers = new Integer[] { 12, 10 };
		final List<String> texts = Arrays.asList("text1", "text2");
		final Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");

		parameters.add(new ParameterAssertor<>(true, EnumType.BOOLEAN));
		parameters.add(new ParameterAssertor<>(integers, EnumType.ARRAY));
		parameters.add(new ParameterAssertor<>(Calendar.YEAR, EnumType.CALENDAR_FIELD));
		parameters.add(new ParameterAssertor<>(Calendar.ZONE_OFFSET, EnumType.CALENDAR_FIELD));
		parameters.add(new ParameterAssertor<>("text", EnumType.CHAR_SEQUENCE));
		parameters.add(new ParameterAssertor<>(HelperMessage.class, EnumType.CLASS));
		parameters.add(new ParameterAssertor<>(date1, EnumType.DATE));
		parameters.add(new ParameterAssertor<>(calendar1, EnumType.DATE));
		parameters.add(new ParameterAssertor<>(EnumOperator.AND, EnumType.ENUMERATION));
		parameters.add(new ParameterAssertor<>(texts, EnumType.ITERABLE));
		parameters.add(new ParameterAssertor<>(map, EnumType.MAP));
		parameters.add(new ParameterAssertor<>(3.25f, EnumType.NUMBER_DECIMAL));
		parameters.add(new ParameterAssertor<>(12, EnumType.NUMBER_INTEGER));
		parameters.add(new ParameterAssertor<>(exception, EnumType.THROWABLE));
		parameters.add(new ParameterAssertor<>(time, EnumType.TEMPORAL));
		parameters.add(new ParameterAssertor<>(Color.BLACK, EnumType.UNKNOWN));

		HelperMessage.getDefaultMessage(MSG.BOOLEAN.TRUE, true, false, null, parameters);

		parameters.add(new ParameterAssertor<>(null, null));

		assertException(() -> HelperMessage.getDefaultMessage(MSG.BOOLEAN.TRUE, true, false, null, parameters),
				NullPointerException.class, "parameter type");

		Object[] convertedParams = HelperMessage.convertParams(parameters);

		assertEquals(parameters.size(), convertedParams.length);

		int i = 0;
		assertEquals(true, convertedParams[i++]);
		assertEquals("[12, 10]", convertedParams[i++].toString());
		assertEquals("YEAR", convertedParams[i++]);
		assertEquals(Calendar.ZONE_OFFSET, convertedParams[i++]);
		assertEquals("text", convertedParams[i++]);
		assertEquals(HelperMessage.class.getSimpleName(), convertedParams[i++]);
		assertEquals(date1, convertedParams[i++]);
		assertEquals(calendar1.getTime(), convertedParams[i++]);
		assertEquals(EnumOperator.AND, convertedParams[i++]);
		assertEquals("[text1, text2]", convertedParams[i++].toString());
		assertEquals("[key1=value1, key2=value2]", convertedParams[i++].toString());
		assertEquals(3.25f, convertedParams[i++]);
		assertEquals(12, convertedParams[i++]);
		assertEquals(exception, convertedParams[i++]);
		assertEquals(time, convertedParams[i++]);
		assertEquals(Color.BLACK, convertedParams[i]);
	}

	/**
	 * Test method for {@link ParameterAssertor#toString()}.
	 */
	@Test
	public void testParameterToString() {
		assertEquals(
				"{" + ParameterAssertor.class.getCanonicalName() + ": {object: true, type: BOOLEAN, checked: false}}",
				new ParameterAssertor<>(true, EnumType.BOOLEAN).toString());
	}

	/**
	 * Test method for {@link MessagesAssertor#append}.
	 */
	@Test
	public void testMessagesAssertor() {
		MessagesAssertor m = new MessagesAssertor();

		assertException(() -> m.append(EnumOperator.AND), UnsupportedOperationException.class,
				"An operator can only be applied on a previous error");

		m.append("key", false, null, null);
		assertTrue(m.isPreconditionsNotEmpty());
		assertFalse(m.isMessagesNotEmpty());

		m.append(EnumOperator.AND);
		m.append("key", false, null, null, null);

		assertTrue(m.isPreconditionsNotEmpty());
		assertTrue(m.isMessagesNotEmpty());

		MessagesAssertor m2 = new MessagesAssertor();

		m2.append(m);

		assertTrue(m2.isPreconditionsNotEmpty());
		assertFalse(m2.isMessagesNotEmpty());

		MessagesAssertor m3 = new MessagesAssertor();
		MessagesAssertor m4 = new MessagesAssertor();

		m4.append(m3);

		assertFalse(m4.isPreconditionsNotEmpty());
		assertFalse(m4.isMessagesNotEmpty());

		m3.append("key", false, null, null, null);
		m4.append(m3);

		assertFalse(m4.isPreconditionsNotEmpty());
		assertTrue(m4.isMessagesNotEmpty());
	}
}
