/*
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

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.utils.AssertorObject;
import fr.landel.utils.commons.function.PredicateThrowable;

/**
 * Check {@link AssertorObject}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class AssertorObjectTest extends AbstractTest {

	/**
	 * Test method for {@link AssertorObject#AssertorObject()} .
	 */
	@Test
	public void testConstructor() {
		assertNotNull(new AssertorObject());
	}

	/**
	 * Test method for {@link AssertorObject#getLocale()}
	 * {@link AssertorObject#setLocale(Locale)}.
	 */
	@Test
	public void testLocale() {
		try {
			Assertor.setLocale(Locale.US);
			assertEquals(Locale.US, Assertor.getLocale());
			assertEquals("Test 3.14", Assertor.that(1).isNotEqual(1, "Test %.2f", Math.PI).getErrors().get());
			assertEquals("Test 3,14",
					Assertor.that(1).isNotEqual(1, Locale.FRANCE, "Test %.2f", Math.PI).getErrors().get());

			assertEquals("Test 3.14", Assertor.that(1).not().isEqual(1, "Test %.2f", Math.PI).getErrors().get());
			assertEquals("Test 3,14",
					Assertor.that(1).not().isEqual(1, Locale.FRANCE, "Test %.2f", Math.PI).getErrors().get());

			Assertor.setLocale(Locale.FRANCE);
			assertEquals(Locale.FRANCE, Assertor.getLocale());
			assertEquals("Test 3,14", Assertor.that(1).isNotEqual(1, "Test %.2f", Math.PI).getErrors().get());
			assertEquals("Test 3.14",
					Assertor.that(1).isNotEqual(1, Locale.US, "Test %.2f", Math.PI).getErrors().get());

			// Reset
			Assertor.setLocale(Locale.getDefault());
			assertEquals(Locale.getDefault(), Assertor.getLocale());
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject}.
	 */
	@Test
	public void testObject() {
		AssertorStepCharSequence<String> assertor = Assertor.that("text");

		// intermediate condition (no call of isOK or orElseThrow), so no reset
		// and this condition is used in the next one
		assertor.contains("__");
		assertTrue(assertor.contains("ext").isOK());
		assertTrue(assertor.contains("__").or().contains("ext").isOK());
		assertTrue(assertor.contains("__").xor().contains("ext").isOK());

		assertFalse(assertor.contains("__").or().contains("ext").and("toto").contains("to").and().contains("r").isOK());
		assertTrue(assertor.contains("__").xor().contains("ext").and("toti").contains("to").and().contains("i").isOK());
		assertFalse(
				assertor.contains("ext").xor().contains("ext").and("toti").contains("to").and().contains("i").isOK());
		assertFalse(assertor.contains("__").xor().contains("__").and("toti").contains("to").and().contains("i").isOK());
		assertTrue(assertor.contains("__").nand().contains("__").and("toti").contains("to").and().contains("i").isOK());
		assertTrue(assertor.contains("__").nor().contains("__").and("toti").contains("to").and().contains("i").isOK());
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullOKObjectString() {
		try {
			Assertor.that((Object) null).isNull().orElseThrow("not null object");
			Assertor.that((Object) null).isNull().orElseThrow(new IllegalArgumentException(), true);
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullKOObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("").isNull().orElseThrow("not null object");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullOKObject() {
		try {
			Assertor.that((Object) null).isNull().orElseThrow();
			Assertor.that(Color.BLACK).not().isNull().orElseThrow();
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullKOObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("").isNull().orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullOKObjectString() {
		try {
			Assertor.that(1).isNotNull().orElseThrow("null object");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullKOObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that((Object) null).isNotNull().orElseThrow("null object");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullOKObject() {
		try {
			Assertor.that(1).isNotNull().orElseThrow();
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullKOObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that((Object) null).isNotNull().orElseThrow(new IllegalArgumentException(), true);
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualOKObjectObject() {
		try {
			Assertor.that("texte9").isNotEqual("texte10").and().isNotEqual(5).orElseThrow();
			Assertor.that(5).isNotEqual("texte10").orElseThrow();
			Assertor.that("texte9").isNotEqual(null).orElseThrow();
			Assertor.that((String) null).isNotEqual("texte10").orElseThrow();

			StringBuilder sb1 = new StringBuilder("texte9");
			StringBuilder sb2 = new StringBuilder("texte10");
			Assertor.that(sb1).isNotEqual(sb2).orElseThrow(new IllegalArgumentException(), true);
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKOObjectObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte11").isNotEqual("texte11").orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	public void testIsNotEqualOKCharSequence() {
		StringBuilder sb1 = new StringBuilder("texte11");
		StringBuilder sb2 = new StringBuilder("texte11");
		assertTrue(Assertor.that((Object) sb1).isNotEqual(sb2).isOK());
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKONull() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that((Object) null).isNotEqual(null).orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 * 
	 * @throws IOException On errors
	 */
	@Test
	public void testIsNotEqualKONullException() throws IOException {
		assertThrows(IOException.class, () -> {
			Assertor.that((Object) null).isNotEqual(null).orElseThrow(new IOException(), true);
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualOKObjectObjectString() {
		try {
			Assertor.that("texte8").isNotEqual("texte7").orElseThrow("equal");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKOObjectObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte6").isNotEqual("texte6").orElseThrow("equal");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKONOT() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte6").not().isNotEqual("texte").orElseThrow("equal");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 * 
	 * @throws IOException exception if isEqual fails
	 */
	@Test
	public void testIsEqualOKObjectObject() throws IOException {
		try {
			Assertor.that("texte4").isEqual("texte4").orElseThrow();
			Assertor.that(5).isEqual(5).orElseThrow(new IOException(), true);

			StringBuilder sb1 = new StringBuilder("texte4");
			StringBuilder sb2 = new StringBuilder("texte4");
			assertFalse(Assertor.that((Object) sb1).isEqual(sb2).isOK());

			assertTrue(Assertor.that('A').isEqual((char) 65).isOK());

			Assertor.that(Color.BLACK).isEqual(new Color(0)).orElseThrow(new IOException(), true);
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOObjectObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte5").isEqual("texte3").orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualOKObjectObjectString() {
		try {
			Assertor.that("texte0").isEqual("texte0").orElseThrow("not equals");
			Assertor.that((Object) null).isEqual(null).orElseThrow("not equals");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOObjectObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte1").isEqual("texte2").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKONullObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that((Object) null).isEqual("texte2").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOObjectNullString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("texte1").isEqual(null).orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOIntegerStringString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that(5).isEqual("test").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOStringIntegerString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that("test").isEqual(5).orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isInstanceOf(Class)} .
	 */
	@Test
	public void testIsInstanceOfOKClassOfQObject() {
		try {
			Assertor.that(new IOException()).isInstanceOf(IOException.class).orElseThrow(new IllegalArgumentException(),
					true);
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}
	}

	/**
	 * Test method for {@link AssertorObject#isInstanceOf(Class)} .
	 */
	@Test
	public void testIsInstanceOfKOClassOfQObject() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.that(new Exception()).isInstanceOf(IOException.class).orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isInstanceOf(Class)} .
	 */
	@Test
	public void testIsInstanceOfOKClassOfQObjectString() {
		try {
			Assertor.that(new IOException()).isInstanceOf(IOException.class).orElseThrow("not instance of");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}

		assertException(() -> {
			Assertor.that(new Exception()).isInstanceOf(IOException.class).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");

		assertException(() -> {
			Assertor.that((Object) null).isInstanceOf(IOException.class).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");

		assertException(() -> {
			Assertor.that(new Exception()).isInstanceOf(null).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");
	}

	/**
	 * Test method for {@link AssertorObject#isAssignableFrom} .
	 */
	@Test
	public void testIsAssignableFrom() {
		assertTrue(Assertor.that(Color.BLACK).isAssignableFrom(Color.class).isOK());
		assertFalse(Assertor.that(Color.BLACK).isAssignableFrom(Point.class).isOK());
		assertFalse(Assertor.that((Color) null).isAssignableFrom(Color.class).isOK());
		assertFalse(Assertor.that(Color.BLACK).isAssignableFrom(null).isOK());

		assertException(() -> {
			Assertor.that((Object) null).isAssignableFrom(Exception.class).orElseThrow("msg");
		}, IllegalArgumentException.class, "msg");

	}

	/**
	 * Test method for {@link AssertorObject#not} .
	 */
	@Test
	public void testNot() {
		assertTrue(Assertor.that("text").not().isNull().isOK());
		assertTrue(Assertor.that("text").not().isNull().and().isNotNull().isOK());
		assertTrue(Assertor.that("text").not().not().isNotNull().isOK());
		assertFalse(Assertor.that("text").not().isNotNull().isOK());
	}

	/**
	 * Test method for {@link AssertorObject#hasHashCode} .
	 * 
	 * @throws IOException On errors
	 */
	@Test
	public void testHasHashCode() throws IOException {
		int hashCode = IOException.class.hashCode();

		assertTrue(Assertor.that(IOException.class).hasHashCode(hashCode).isOK());
		assertFalse(Assertor.that(IOException.class).hasHashCode(1).isOK());

		assertTrue(Assertor.that(IOException.class).isNotNull().and().hasHashCode(hashCode).isOK());
		assertTrue(Assertor.that(IOException.class).isNull().or().hasHashCode(hashCode).isOK());
		assertTrue(Assertor.that(IOException.class).isNull().xor().hasHashCode(hashCode).isOK());
		assertTrue(Assertor.that(IOException.class).isNull().or().not().hasHashCode(1).isOK());

		assertTrue(Assertor.that(IOException.class).hasHashCode(hashCode).and("ere").contains('e').isOK());
		assertTrue(Assertor.that(IOException.class).hasHashCode(hashCode).or("ere").contains('e').isOK());
		assertTrue(Assertor.that(IOException.class).hasHashCode(hashCode).xor("ara").contains('e').isOK());

		assertTrue(Assertor.that((Class<?>) null).hasHashCode(0).isOK());

		assertException(() -> {
			Assertor.that(IOException.class).hasHashCode(5, "The hash codes don't match (%d != %2$d*)", hashCode)
					.orElseThrow();
		}, IllegalArgumentException.class, "The hash codes don't match (" + hashCode + " != 5)");

		assertException(() -> Assertor.that(Exception.class).hasHashCode(1, "bad hash code").orElseThrow(),
				IllegalArgumentException.class, "bad hash code");

		assertException(() -> Assertor.that(Exception.class).hasHashCode(1).orElseThrow(),
				IllegalArgumentException.class, "the object 'Exception' should have the hash code '1'");

		assertException(() -> Assertor.that((Class<?>) null).hasHashCode(1).orElseThrow(),
				IllegalArgumentException.class, "the object 'null' should have the hash code '1'");

		assertException(() -> Assertor.that(Exception.class).hasHashCode(0).orElseThrow(),
				IllegalArgumentException.class, "the object 'Exception' should have the hash code '0'");

		assertException(() -> Assertor.that((Class<?>) null).not().hasHashCode(0).orElseThrow(),
				IllegalArgumentException.class, "the object 'null' should NOT have the hash code '0'");
	}

	/**
	 * Test method for {@link AssertorObject#validates} .
	 */
	@Test
	public void testValidatesPredicateThrowable() {
		assertTrue(Assertor.that((Object) 0).validates(Objects::nonNull).isOK());

		final PredicateThrowable<String, IOException> predicateFile = (path) -> {
			if (!new File(path).exists()) {
				throw new IOException();
			}
			return true;
		};

		assertFalse(Assertor.that("/var/log/dev.log").validates(predicateFile).isOK());
		assertFalse(Assertor.that("/var/log/dev.log").not().validates(predicateFile).isOK());

		assertFalse(Assertor.that("/var/log/dev.log").validates(null).isOK());

		assertTrue(Assertor.that((Object) null).validates(Objects::isNull, "Path is invalid").isOK());

		assertTrue(Assertor.that((Object) 0).validates(Objects::nonNull, "Path is invalid").isOK());

		assertFalse(Assertor.that("/var/log/dev.log")
				.validates(predicateFile, "Path '%1$s*' provide by '%s' is invalid", "John").isOK());

		assertTrue(Assertor.that((Object) 0).validates(Objects::nonNull, Locale.US, "Path is invalid").isOK());

		assertEquals(
				"Path '/var/log/dev.log' provided by 'John' is invalid in '10.27'ms", Assertor
						.that("/var/log/dev.log").validates(predicateFile, Locale.US,
								"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.getErrors().get());

		assertEquals(
				"Path '/var/log/dev.log' provided by 'John' is invalid in '10,27'ms", Assertor
						.that("/var/log/dev.log").validates(predicateFile, Locale.FRANCE,
								"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.getErrors().get());
	}

	/**
	 * Test method for {@link AssertorObject#validates} .
	 */
	@Test
	public void testValidatesPredicate() {
		assertTrue(Assertor.that((Object) 0).validates((Predicate<Object>) Objects::nonNull).isOK());

		final Predicate<String> predicateFile = (path) -> {
			if (!new File(path).exists()) {
				return false;
			}
			return true;
		};

		assertFalse(Assertor.that("/var/log/dev.log").validates(predicateFile).isOK());

		assertFalse(Assertor.that("/var/log/dev.log").validates((Predicate<String>) null).isOK());

		assertTrue(
				Assertor.that((Object) null).validates((Predicate<Object>) Objects::isNull, "Path is invalid").isOK());

		assertTrue(Assertor.that((Object) 0).validates((Predicate<Object>) Objects::nonNull, "Path is invalid").isOK());

		assertFalse(Assertor.that("/var/log/dev.log")
				.validates(predicateFile, "Path '%1$s*' provide by '%s' is invalid", "John").isOK());

		assertTrue(Assertor.that((Object) 0)
				.validates((Predicate<Object>) Objects::nonNull, Locale.US, "Path is invalid").isOK());

		assertEquals(
				"Path '/var/log/dev.log' provided by 'John' is invalid in '10.27'ms", Assertor
						.that("/var/log/dev.log").validates(predicateFile, Locale.US,
								"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.getErrors().get());

		assertEquals(
				"Path '/var/log/dev.log' provided by 'John' is invalid in '10,27'ms", Assertor
						.that("/var/log/dev.log").validates(predicateFile, Locale.FRANCE,
								"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.getErrors().get());
	}

	/**
	 * Test method for {@link EnumType#getType} .
	 */
	@Test
	public void testGetType() {
		assertEquals(EnumType.BOOLEAN, EnumType.getType(true));
		assertEquals(EnumType.BOOLEAN, EnumType.getType(Boolean.FALSE));

		assertEquals(EnumType.NUMBER_INTEGER, EnumType.getType((byte) 1));
		assertEquals(EnumType.NUMBER_INTEGER, EnumType.getType((short) 1));
		assertEquals(EnumType.NUMBER_INTEGER, EnumType.getType(1));
		assertEquals(EnumType.NUMBER_INTEGER, EnumType.getType(1L));
		assertEquals(EnumType.NUMBER_INTEGER, EnumType.getType(new BigInteger("12")));

		assertEquals(EnumType.NUMBER_DECIMAL, EnumType.getType(3.25f));
		assertEquals(EnumType.NUMBER_DECIMAL, EnumType.getType(3.25d));
		assertEquals(EnumType.NUMBER_DECIMAL, EnumType.getType(new BigDecimal("12.25")));

		assertEquals(EnumType.ARRAY, EnumType.getType(new Object[0]));

		assertEquals(EnumType.ENUMERATION, EnumType.getType(EnumOperator.AND));

		assertEquals(EnumType.ITERABLE, EnumType.getType(Collections.EMPTY_LIST));
		assertEquals(EnumType.ITERABLE, EnumType.getType(Collections.EMPTY_SET));

		assertEquals(EnumType.MAP, EnumType.getType(Collections.EMPTY_MAP));

		assertEquals(EnumType.DATE, EnumType.getType(new Date()));
		assertEquals(EnumType.CALENDAR, EnumType.getType(Calendar.getInstance()));
		assertEquals(EnumType.TEMPORAL, EnumType.getType(LocalDateTime.now()));

		assertEquals(EnumType.CHAR_SEQUENCE, EnumType.getType(""));
		assertEquals(EnumType.CHAR_SEQUENCE, EnumType.getType(new StringBuilder()));

		assertEquals(EnumType.CLASS, EnumType.getType(new Date().getClass()));

		assertEquals(EnumType.TEMPORAL, EnumType.getType(LocalDateTime.now()));

		assertEquals(EnumType.THROWABLE, EnumType.getType(new IOException()));

		assertEquals(EnumType.UNKNOWN, EnumType.getType(Color.BLACK));
		assertEquals(EnumType.UNKNOWN, EnumType.getType(null));

		assertEquals(EnumType.CHARACTER, EnumType.getType('e'));
	}
}
