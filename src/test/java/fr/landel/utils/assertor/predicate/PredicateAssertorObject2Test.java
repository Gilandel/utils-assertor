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
package fr.landel.utils.assertor.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.utils.AssertorObject;
import fr.landel.utils.commons.Comparators;
import fr.landel.utils.commons.function.PredicateThrowable;

/**
 * Check {@link AssertorObject}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class PredicateAssertorObject2Test extends AbstractTest {

	/**
	 * Test method for {@link AssertorObject#getLocale()}
	 * {@link AssertorObject#setLocale(Locale)}.
	 */
	@Test
	public void testLocale() {
		try {
			Assertor.setLocale(Locale.US);
			assertEquals(Locale.US, Assertor.getLocale());
			assertEquals("Test 3.14",
					Assertor.ofObject().isNotEqual(1, "Test %.2f", Math.PI).that(1).getErrors().get());
			assertEquals("Test 3,14",
					Assertor.ofObject().isNotEqual(1, Locale.FRANCE, "Test %.2f", Math.PI).that(1).getErrors().get());

			assertEquals("Test 3.14",
					Assertor.ofObject().not().isEqual(1, "Test %.2f", Math.PI).that(1).getErrors().get());
			assertEquals("Test 3,14", Assertor.ofObject().not().isEqual(1, Locale.FRANCE, "Test %.2f", Math.PI).that(1)
					.getErrors().get());

			Assertor.setLocale(Locale.FRANCE);
			assertEquals(Locale.FRANCE, Assertor.getLocale());
			assertEquals("Test 3,14",
					Assertor.ofObject().isNotEqual(1, "Test %.2f", Math.PI).that(1).getErrors().get());
			assertEquals("Test 3.14",
					Assertor.ofObject().isNotEqual(1, Locale.US, "Test %.2f", Math.PI).that(1).getErrors().get());

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
		PredicateAssertorStepObject<String> assertor = Assertor.ofObject();

		// intermediate condition (no call of isOK or orElseThrow), so no reset
		// and this condition is used in the next one
		assertor.isNotNull();
		assertTrue(assertor.isNotNull().that("text").isOK());

		assertTrue(
				assertor.isNotNull().and().not().isNull().and(Assertor.<String>ofObject().isEqual("")).that("").isOK());
		assertTrue(
				assertor.isNotNull().or().not().isNull().or(Assertor.<String>ofObject().isEqual("")).that("").isOK());
		assertTrue(
				assertor.isNotNull().xor().not().isNull().xor(Assertor.<String>ofObject().isEqual("")).that("").isOK());
		assertFalse(assertor.isNotNull().nand().not().isNull().nand(Assertor.<String>ofObject().isEqual("")).that("")
				.isOK());
		assertTrue(
				assertor.isNotNull().nor().not().isNull().nor(Assertor.<String>ofObject().isEqual("")).that("").isOK());

		assertFalse(Assertor.ofObject().hasHashCode(0).that(Comparators.CHAR).isOK());
		assertTrue(Assertor.ofObject().hasHashCode(Objects.hashCode(Comparators.CHAR)).that(Comparators.CHAR).isOK());

		assertFalse(Assertor.ofObject().hasHashCode(0).and().isEqual(Comparators.CHAR).that(Comparators.CHAR).isOK());
		assertTrue(Assertor.ofObject().hasHashCode(0).or().isEqual(Comparators.CHAR).that(Comparators.CHAR).isOK());
		assertTrue(Assertor.ofObject().hasHashCode(0).xor().isEqual(Comparators.CHAR).that(Comparators.CHAR).isOK());
		assertFalse(Assertor.ofObject().hasHashCode(0).nand().isEqual(Comparators.CHAR).that(Comparators.CHAR).isOK());
		assertTrue(Assertor.ofObject().hasHashCode(0).nor().isEqual(Comparators.CHAR).that(Comparators.CHAR).isOK());
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullOKObjectString() {
		try {
			Assertor.ofObject().isNull().that((Object) null).orElseThrow("not null object");
			Assertor.ofObject().isNull().that((Object) null).orElseThrow(new IllegalArgumentException(), true);
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
			Assertor.ofObject().isNull().that("").orElseThrow("not null object");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNull()} .
	 */
	@Test
	public void testIsNullOKObject() {
		try {
			Assertor.ofObject().isNull().that((Object) null).orElseThrow();
			Assertor.ofObject().not().isNull().that(Color.BLACK).orElseThrow();
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
			Assertor.ofObject().isNull().that("").orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullOKObjectString() {
		try {
			Assertor.ofObject().isNotNull().that(1).orElseThrow("null object");
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
			Assertor.ofObject().isNotNull().that((Object) null).orElseThrow("null object");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotNull()} .
	 */
	@Test
	public void testIsNotNullOKObject() {
		try {
			Assertor.ofObject().isNotNull().that(1).orElseThrow();
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
			Assertor.ofObject().isNotNull().that((Object) null).orElseThrow(new IllegalArgumentException(), true);
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualOKObjectObject() {
		try {
			Assertor.ofObject().isNotEqual("texte10").and().isNotEqual(5).that("texte9").orElseThrow();
			Assertor.ofObject().isNotEqual("texte10").that(5).orElseThrow();
			Assertor.ofObject().isNotEqual(null).that("texte9").orElseThrow();
			Assertor.ofObject().isNotEqual("texte10").that((String) null).orElseThrow();

			StringBuilder sb1 = new StringBuilder("texte9");
			StringBuilder sb2 = new StringBuilder("texte10");
			Assertor.ofObject().isNotEqual(sb2).that(sb1).orElseThrow(new IllegalArgumentException(), true);
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
			Assertor.ofObject().isNotEqual("texte11").that("texte11").orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	public void testIsNotEqualOKCharSequence() {
		StringBuilder sb1 = new StringBuilder("texte11");
		StringBuilder sb2 = new StringBuilder("texte11");
		Assertor.ofObject().isNotEqual(sb2).that(sb1).orElseThrow();
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKONull() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().isNotEqual(null).that((Object) null).orElseThrow();
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
			Assertor.ofObject().isNotEqual(null).that((Object) null).orElseThrow(new IOException(), true);
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualOKObjectObjectString() {
		try {
			Assertor.ofObject().isNotEqual("texte7").that("texte8").orElseThrow("equal");
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
			Assertor.ofObject().isNotEqual("texte6").that("texte6").orElseThrow("equal");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isNotEqual(Object)} .
	 */
	@Test
	public void testIsNotEqualKONOT() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().not().isNotEqual("texte").that("texte6").orElseThrow("equal");
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
			Assertor.ofObject().isEqual("texte4").that("texte4").orElseThrow();
			Assertor.ofObject().isEqual(5).that(5).orElseThrow(new IOException(), true);

			StringBuilder sb1 = new StringBuilder("texte4");
			StringBuilder sb2 = new StringBuilder("texte4");
			assertFalse(Assertor.ofObject().isEqual(sb2).that(sb1).isOK());

			assertTrue(Assertor.ofObject().isEqual((char) 65).that('A').isOK());

			Assertor.ofObject().isEqual(new Color(0)).that(Color.BLACK).orElseThrow(new IOException(), true);
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
			Assertor.ofObject().isEqual("texte3").that("texte5").orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualOKObjectObjectString() {
		try {
			Assertor.ofObject().isEqual("texte0").that("texte0").orElseThrow("not equals");
			Assertor.ofObject().isEqual(null).that((Object) null).orElseThrow("not equals");
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
			Assertor.ofObject().isEqual("texte2").that("texte1").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKONullObjectString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().isEqual("texte2").that((Object) null).orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOObjectNullString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().isEqual(null).that("texte1").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOIntegerStringString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().isEqual("test").that(5).orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isEqual(Object)} .
	 */
	@Test
	public void testIsEqualKOStringIntegerString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Assertor.ofObject().isEqual(5).that("test").orElseThrow("not equals");
		});
	}

	/**
	 * Test method for {@link AssertorObject#isInstanceOf(Class)} .
	 */
	@Test
	public void testIsInstanceOfOKClassOfQObject() {
		try {
			Assertor.ofObject().isInstanceOf(IOException.class).that(new IOException())
					.orElseThrow(new IllegalArgumentException(), true);
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
			Assertor.ofObject().isInstanceOf(IOException.class).that(new Exception()).orElseThrow();
		});
	}

	/**
	 * Test method for {@link AssertorObject#isInstanceOf(Class)} .
	 */
	@Test
	public void testIsInstanceOfOKClassOfQObjectString() {
		try {
			Assertor.ofObject().isInstanceOf(IOException.class).that(new IOException()).orElseThrow("not instance of");
		} catch (IllegalArgumentException e) {
			fail("The test isn't correct");
		}

		assertException(() -> {
			Assertor.ofObject().isInstanceOf(IOException.class).that(new Exception()).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");

		assertException(() -> {
			Assertor.ofObject().isInstanceOf(IOException.class).that((Object) null).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");

		assertException(() -> {
			Assertor.ofObject().isInstanceOf(null).that(new Exception()).orElseThrow("not instance of");
			fail();
		}, IllegalArgumentException.class, "not instance of");
	}

	/**
	 * Test method for {@link AssertorObject#isAssignableFrom} .
	 */
	@Test
	public void testIsAssignableFrom() {
		assertTrue(Assertor.ofObject().isAssignableFrom(Color.class).that(Color.BLACK).isOK());
		assertFalse(Assertor.ofObject().isAssignableFrom(Point.class).that(Color.BLACK).isOK());
		assertFalse(Assertor.ofObject().isAssignableFrom(Color.class).that((Color) null).isOK());
		assertFalse(Assertor.ofObject().isAssignableFrom(null).that(Color.BLACK).isOK());

		assertException(() -> {
			Assertor.ofObject().isAssignableFrom(Exception.class).that((Object) null).orElseThrow("msg");
		}, IllegalArgumentException.class, "msg");

	}

	/**
	 * Test method for {@link AssertorObject#not} .
	 */
	@Test
	public void testNot() {
		assertTrue(Assertor.ofObject().not().isNull().that("text").isOK());
		assertTrue(Assertor.ofObject().not().isNull().and().isNotNull().that("text").isOK());
		assertTrue(Assertor.ofObject().not().not().isNotNull().that("text").isOK());
		assertFalse(Assertor.ofObject().not().isNotNull().that("text").isOK());
	}

	/**
	 * Test method for {@link AssertorObject#hasHashCode} .
	 * 
	 * @throws IOException On errors
	 */
	@Test
	public void testHasHashCode() throws IOException {
		int hashCode = IOException.class.hashCode();

		assertTrue(Assertor.ofObject().hasHashCode(hashCode).that(IOException.class).isOK());
		assertFalse(Assertor.ofObject().hasHashCode(1).that(IOException.class).isOK());

		assertTrue(Assertor.ofObject().isNotNull().and().hasHashCode(hashCode).that(IOException.class).isOK());
		assertTrue(Assertor.ofObject().isNull().or().hasHashCode(hashCode).that(IOException.class).isOK());
		assertTrue(Assertor.ofObject().isNull().xor().hasHashCode(hashCode).that(IOException.class).isOK());
		assertTrue(Assertor.ofObject().isNull().or().not().hasHashCode(1).that(IOException.class).isOK());

		assertTrue(Assertor.ofObject().hasHashCode(0).that((Class<?>) null).isOK());

		assertException(() -> {
			Assertor.ofObject().hasHashCode(5, "The hash codes don't match (%d != %2$d*)", hashCode)
					.that(IOException.class).orElseThrow();
		}, IllegalArgumentException.class, "The hash codes don't match (" + hashCode + " != 5)");

		assertException(() -> Assertor.ofObject().hasHashCode(1, "bad hash code").that(Exception.class).orElseThrow(),
				IllegalArgumentException.class, "bad hash code");

		assertException(() -> Assertor.ofObject().hasHashCode(1).that(Exception.class).orElseThrow(),
				IllegalArgumentException.class, "the object 'Exception' should have the hash code '1'");

		assertException(() -> Assertor.ofObject().hasHashCode(1).that((Class<?>) null).orElseThrow(),
				IllegalArgumentException.class, "the object 'null' should have the hash code '1'");

		assertException(() -> Assertor.ofObject().hasHashCode(0).that(Exception.class).orElseThrow(),
				IllegalArgumentException.class, "the object 'Exception' should have the hash code '0'");

		assertException(() -> Assertor.ofObject().not().hasHashCode(0).that((Class<?>) null).orElseThrow(),
				IllegalArgumentException.class, "the object 'null' should NOT have the hash code '0'");
	}

	/**
	 * Test method for {@link AssertorObject#validates} .
	 */
	@Test
	public void testValidatesPredicateThrowable() {
		assertTrue(Assertor.ofObject().validates(Objects::nonNull).that((Object) 0).isOK());

		final PredicateThrowable<String, IOException> predicateFile = (path) -> {
			if (!new File(path).exists()) {
				throw new IOException();
			}
			return true;
		};

		assertFalse(Assertor.<String>ofObject().validates(predicateFile).that("/var/log/dev.log").isOK());

		assertFalse(Assertor.ofObject().validates(null).that("/var/log/dev.log").isOK());

		assertTrue(Assertor.ofObject().validates(Objects::isNull, "Path is invalid").that((Object) null).isOK());

		assertTrue(Assertor.ofObject().validates(Objects::nonNull, "Path is invalid").that((Object) 0).isOK());

		assertFalse(
				Assertor.<String>ofObject().validates(predicateFile, "Path '%1$s*' provide by '%s' is invalid", "John")
						.that("/var/log/dev.log").isOK());

		assertTrue(
				Assertor.ofObject().validates(Objects::nonNull, Locale.US, "Path is invalid").that((Object) 0).isOK());

		assertEquals("Path '/var/log/dev.log' provided by 'John' is invalid in '10.27'ms",
				Assertor.<String>ofObject().validates(predicateFile, Locale.US,
						"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.that("/var/log/dev.log").getErrors().get());

		assertEquals("Path '/var/log/dev.log' provided by 'John' is invalid in '10,27'ms",
				Assertor.<String>ofObject().validates(predicateFile, Locale.FRANCE,
						"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.that("/var/log/dev.log").getErrors().get());
	}

	/**
	 * Test method for {@link AssertorObject#validates} .
	 */
	@Test
	public void testValidatesPredicate() {
		assertTrue(Assertor.ofObject().validates((Predicate<Object>) Objects::nonNull).that((Object) 0).isOK());

		final Predicate<String> predicateFile = (path) -> {
			if (!new File(path).exists()) {
				return false;
			}
			return true;
		};

		assertFalse(Assertor.<String>ofObject().validates(predicateFile).that("/var/log/dev.log").isOK());

		assertFalse(Assertor.<String>ofObject().validates((Predicate<String>) null).that("/var/log/dev.log").isOK());

		assertTrue(Assertor.ofObject().validates((Predicate<Object>) Objects::isNull, "Path is invalid")
				.that((Object) null).isOK());

		assertTrue(Assertor.ofObject().validates((Predicate<Object>) Objects::nonNull, "Path is invalid")
				.that((Object) 0).isOK());

		assertFalse(
				Assertor.<String>ofObject().validates(predicateFile, "Path '%1$s*' provide by '%s' is invalid", "John")
						.that("/var/log/dev.log").isOK());

		assertTrue(Assertor.ofObject().validates((Predicate<Object>) Objects::nonNull, Locale.US, "Path is invalid")
				.that((Object) 0).isOK());

		assertEquals("Path '/var/log/dev.log' provided by 'John' is invalid in '10.27'ms",
				Assertor.<String>ofObject().validates(predicateFile, Locale.US,
						"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.that("/var/log/dev.log").getErrors().get());

		assertEquals("Path '/var/log/dev.log' provided by 'John' is invalid in '10,27'ms",
				Assertor.<String>ofObject().validates(predicateFile, Locale.FRANCE,
						"Path '%1$s*' provided by '%s' is invalid in '%.2f'ms", "John", 10.26589f)
						.that("/var/log/dev.log").getErrors().get());
	}
}
