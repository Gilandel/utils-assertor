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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.utils.AssertorCharSequence;

/**
 * Check {@link AssertorCharSequence}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class PredicateAssertorCharSequenceTest extends AbstractTest {

    /**
     * Test method for {@link AssertorCharSequence} .
     */
    @Test
    public void testPredicateGet() {
        assertFalse(Assertor.ofCharSequence().hasHashCode(0).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().hasHashCode(Objects.hashCode("text")).that("text").isOK());

        assertTrue(Assertor.ofCharSequence().contains("ex").and().hasHashCode(Objects.hashCode("text")).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().contains("ex").or().hasHashCode(Objects.hashCode("text")).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().contains("ex").xor().hasHashCode(Objects.hashCode("text")).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().contains("ex").nand().hasHashCode(Objects.hashCode("text")).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().contains("ex").nor().hasHashCode(Objects.hashCode("text")).that("text").isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#hasLength(int)} .
     */
    @Test
    public void testHasLength() {
        assertTrue(Assertor.ofCharSequence().hasLength(4).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().hasLength(3).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().hasLength(-1).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().hasLength(1).that((String) null).isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#hasLength(int)} .
     */
    @Test
    public void testHasNotLength() {
        assertFalse(Assertor.ofCharSequence().not().hasLength(4).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(3).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().not().hasLength(-1).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().not().hasLength(1).that((String) null).isOK());

        assertFalse(Assertor.ofCharSequence().not().hasLength(4).and().contains("ex").that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).or().contains("ex").that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).xor().contains("ex").that("text").isOK());
        assertFalse(Assertor.ofCharSequence().not().hasLength(4).nand().contains("ex").that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).nor().contains("ex").that("text").isOK());

        assertFalse(Assertor.ofCharSequence().not().hasLength(4).and(Assertor.ofCharSequence().contains("ex")).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).or(Assertor.ofCharSequence().contains("ex")).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).xor(Assertor.ofCharSequence().contains("ex")).that("text").isOK());
        assertFalse(Assertor.ofCharSequence().not().hasLength(4).nand(Assertor.ofCharSequence().contains("ex")).that("text").isOK());
        assertTrue(Assertor.ofCharSequence().not().hasLength(4).nor(Assertor.ofCharSequence().contains("ex")).that("text").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test
    public void testIsNotEmptyOKStringString() {
        try {
            Assertor.ofCharSequence().isNotEmpty().that("a").orElseThrow("empty string");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOStringString() {
        Assertor.ofCharSequence().isNotEmpty().that("").orElseThrow("empty string");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKONot() {
        Assertor.ofCharSequence().not().isNotEmpty().that("z").orElseThrow("empty string");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKO2StringString() {
        Assertor.ofCharSequence().isNotEmpty().that((String) null).orElseThrow("empty string");
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotEmpty(java.lang.String)}
     * .
     */
    @Test
    public void testIsNotEmptyOKString() {
        try {
            Assertor.ofCharSequence().isNotEmpty().that("z").orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotEmpty(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKOString() {
        Assertor.ofCharSequence().isNotEmpty().that("").orElseThrow();
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotEmpty(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKO2String() {
        Assertor.ofCharSequence().isNotEmpty().that((String) null).orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEmpty(String, String, Object...)} .
     */
    @Test
    public void testIsEmptyOKStringString() {
        try {
            Assertor.ofCharSequence().isEmpty().that((String) null).orElseThrow("not empty or null");
            Assertor.ofCharSequence().isEmpty().that("").orElseThrow("not empty");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsEmptyKOStringString() {
        Assertor.ofCharSequence().isEmpty().that("r").orElseThrow("not empty");
    }

    /**
     * Test method for {@link AssertorCharSequence#isEmpty(java.lang.String)} .
     */
    @Test
    public void testIsEmptyOKString() {
        try {
            Assertor.ofCharSequence().isEmpty().that((String) null).orElseThrow();
            Assertor.ofCharSequence().isEmpty().that("").orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isEmpty(java.lang.String)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsEmptyKOString() {
        Assertor.ofCharSequence().isEmpty().that("e").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotBlank(String, String, Object...)} .
     */
    @Test
    public void testIsNotBlankOKStringString() {
        try {
            Assertor.ofCharSequence().isNotBlank().that("   \t sds  ").orElseThrow("blank");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotBlank(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotBlankKOStringString() {
        Assertor.ofCharSequence().isNotBlank().that("   \t    ").orElseThrow("blank");
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotBlank(java.lang.String)}
     * .
     */
    @Test
    public void testIsNotBlankOKString() {
        try {
            Assertor.ofCharSequence().isNotBlank().that("    \t  e ").orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotBlank(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotBlankKOString() {
        Assertor.ofCharSequence().isNotBlank().that("    \t   ").orElseThrow();
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotBlank(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotBlankKOnot() {
        Assertor.ofCharSequence().not().isNotBlank().that("    \t   a").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isBlank(String, String, Object...)} .
     */
    @Test
    public void testIsBlankOKStringString() {
        try {
            Assertor.ofCharSequence().isBlank().that("   \t   ").orElseThrow("not blank");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isBlank(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsBlankKOStringString() {
        Assertor.ofCharSequence().isBlank().that("   \t d   ").orElseThrow("not blank");
    }

    /**
     * Test method for {@link AssertorCharSequence#isBlank(java.lang.String)} .
     */
    @Test
    public void testIsBlankOKString() {
        try {
            Assertor.ofCharSequence().isBlank().that("   \t   ").orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isBlank(java.lang.String)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsBlankKOString() {
        Assertor.ofCharSequence().isBlank().that("      j ").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqual() {
        assertTrue(Assertor.ofCharSequence().isEqual("test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isEqual("Test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isEqual("tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isEqual("Tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isEqual(new StringBuilder("test")).that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqual(null).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqual("test").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqual(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqual() {
        assertFalse(Assertor.ofCharSequence().isNotEqual("test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqual("Test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqual("tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqual("Tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqual(new StringBuilder("test")).that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqual(null).that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqual("test").that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqual(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreCase(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreCase() {
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCase("test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCase("Test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCase("tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCase("Tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCase(new StringBuilder("test")).that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCase(null).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCase("test").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCase(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqualIgnoreCase(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreCase() {
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCase("test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCase("Test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCase("tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCase("Tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCase(new StringBuilder("test")).that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCase(null).that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCase("test").that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCase(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreLineReturns() {
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreLineReturns("test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreLineReturns("Test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreLineReturns("tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreLineReturns("Tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreLineReturns(new StringBuilder("test")).that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreLineReturns(null).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreLineReturns("test").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreLineReturns(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqualIgnoreLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreLineReturns() {
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns("test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns("Test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns("tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns("Tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns(new StringBuilder("test")).that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns(null).that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns("test").that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreLineReturns(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreCaseAndLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreCaseAndLineReturns() {
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns("test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns("Test").that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns("tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns("Tes\nt").that("te\r\nst").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns(new StringBuilder("test")).that("test").isOK());
        assertTrue(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns(null).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns("test").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().isEqualIgnoreCaseAndLineReturns(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreCaseAndLineReturns() {
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns("test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns("Test").that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns("tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns("Tes\nt").that("te\r\nst").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns(new StringBuilder("test")).that("test").isOK());
        assertFalse(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns(null).that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns("test").that((String) null).isOK());
        assertTrue(Assertor.ofCharSequence().isNotEqualIgnoreCaseAndLineReturns(null).that("test").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test
    public void testDoesNotContainOKStringStringString() {
        try {
            Assertor.ofCharSequence().not().contains("toto").that("titi part en vacances").orElseThrow("not found");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDoesNotContainKOStringStringString() {
        Assertor.ofCharSequence().not().contains("titi").that("titi part en vacances").orElseThrow("not found");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#not().contains(java.lang.String,
     * java.lang.String)} .
     */
    @Test
    public void testDoesNotContain() {
        assertTrue(Assertor.ofCharSequence().not().contains("toto part en vacances").that("totos").isOK());
        assertTrue(Assertor.ofCharSequence().not().contains("totu").that("toto").isOK());
        assertTrue(Assertor.ofCharSequence().not().contains('x').that("toto").isOK());
        assertFalse(Assertor.ofCharSequence().not().contains("toto").that("toto part en vacances").isOK());
        assertFalse(Assertor.ofCharSequence().not().contains("toto part en vacances").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().not().contains((CharSequence) null).that("toto").isOK());
        assertFalse(Assertor.ofCharSequence().not().contains((Character) null).that("toto").isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDoesNotContainKOStringString() {
        Assertor.ofCharSequence().not().contains("tata").that("tata part en vacances").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.ofCharSequence().contains("toto").that("toto part en vacances").isOK());
        assertTrue(Assertor.ofCharSequence().contains('t').that("toto").isOK());
        assertTrue(Assertor.ofCharSequence().contains("toto").that("toto").isOK());
        assertTrue(Assertor.ofCharSequence().contains("toto").that("toti et toto part en vacances").isOK());
        assertFalse(Assertor.ofCharSequence().contains("toto").that("toti part en vacances en moto").isOK());
        assertFalse(Assertor.ofCharSequence().contains("toto part en vacances").that("toto").isOK());
        assertFalse(Assertor.ofCharSequence().contains("toto part en vacances").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().contains((CharSequence) null).that("toto").isOK());
        assertFalse(Assertor.ofCharSequence().contains((Character) null).that("toto").isOK());
        assertFalse(Assertor.ofCharSequence().contains((Character) null).that((String) null).isOK());

        assertException(() -> {
            Assertor.ofCharSequence().contains("toto").and().contains("voyage").that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.ofCharSequence().contains("toto").and().contains("voyage")
                    .and(Assertor.ofCharSequence().isBlank().or().contains("text")).that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.ofCharSequence().contains("toto").and().contains("voyage")
                    .or(Assertor.ofCharSequence().isBlank().or().not().contains("text")).that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.ofCharSequence().contains('t').and().contains('y').that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.ofCharSequence().contains('t').and().contains('y').and(Assertor.ofCharSequence().isBlank().or().contains("text"))
                    .that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.ofCharSequence().contains('t').and().contains('y').or(Assertor.ofCharSequence().isBlank().or().not().contains('t'))
                    .that("toto part en vacances").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.ofCharSequence().contains('t').and().contains((Character) null).that((CharSequence) null).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence cannot be null and the searched substring cannot be null or empty");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsKOStringString() {
        Assertor.ofCharSequence().contains("tutu").that("tata part en vacances").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test
    public void testContainsOKStringStringString() {
        try {
            Assertor.ofCharSequence().contains("toto").that("toto part en vacances").orElseThrow("text not found");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsKOStringStringString() {
        Assertor.ofCharSequence().contains("tutu").that("tata part en vacances").orElseThrow("text not found");
    }

    /**
     * Test method for {@link AssertorCharSequence#startsWith} and
     * {@link AssertorCharSequence#startsWithIgnoreCase}.
     */
    @Test
    public void testStartsWith() {
        assertTrue(Assertor.ofCharSequence().startsWith("Tex").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("tex").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("ext").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("").that("").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("Texte").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWith("Tex").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().startsWith(null).that("TexT").isOK());

        assertTrue(Assertor.ofCharSequence().startsWithIgnoreCase("tex").that("TexT").isOK());
        assertTrue(Assertor.ofCharSequence().startsWithIgnoreCase("tex").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase("ext").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase("").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase("").that("").isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase("texte").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase("tex").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().startsWithIgnoreCase(null).that("TexT").isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#endsWith} and
     * {@link AssertorCharSequence#endsWithIgnoreCase}.
     */
    @Test
    public void testEndsWith() {
        assertTrue(Assertor.ofCharSequence().endsWith("exT").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("ext").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("tex").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("").that("").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("eTexT").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWith("exT").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().endsWith(null).that("TexT").isOK());

        assertTrue(Assertor.ofCharSequence().endsWithIgnoreCase("exT").that("TexT").isOK());
        assertTrue(Assertor.ofCharSequence().endsWithIgnoreCase("ext").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase("tex").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase("").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase("").that("").isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase("eTexT").that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase("exT").that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().endsWithIgnoreCase(null).that("TexT").isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#matches}.
     */
    @Test
    public void testMatches() {
        final String regex = "[xeT]{4}";
        final Pattern pattern = Pattern.compile(regex);

        assertTrue(Assertor.ofCharSequence().matches(pattern).that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().matches(pattern).that("Text").isOK());
        assertFalse(Assertor.ofCharSequence().matches(pattern).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().matches((Pattern) null).that("Text").isOK());

        assertTrue(Assertor.ofCharSequence().matches(regex).that("TexT").isOK());
        assertFalse(Assertor.ofCharSequence().matches(regex).that("Text").isOK());
        assertFalse(Assertor.ofCharSequence().matches(regex).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().matches((String) null).that("Text").isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#find}.
     */
    @Test
    public void testFind() {
        final String regex = "[xeT]{3}";
        final Pattern pattern = Pattern.compile(regex);

        assertTrue(Assertor.ofCharSequence().find(pattern).that("TexT").isOK());
        assertTrue(Assertor.ofCharSequence().find(pattern).that("Text").isOK());
        assertFalse(Assertor.ofCharSequence().find(pattern).that("Tetxt").isOK());
        assertFalse(Assertor.ofCharSequence().find(pattern).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().find((Pattern) null).that("Text").isOK());

        assertTrue(Assertor.ofCharSequence().find(regex).that("TexT").isOK());
        assertTrue(Assertor.ofCharSequence().find(regex).that("Text").isOK());
        assertFalse(Assertor.ofCharSequence().find(regex).that("Tetxt").isOK());
        assertFalse(Assertor.ofCharSequence().find(regex).that((String) null).isOK());
        assertFalse(Assertor.ofCharSequence().find((String) null).that("Text").isOK());
        assertFalse(Assertor.ofCharSequence().find((String) null).that((String) null).isOK());
    }

    @Test
    public void testSubAssertor() {
        // Assertor.ofCharSequence().isNotNull().and(e ->
        // e.getMessage()).startsWith("er").that(new
        // IOException("error")).orElseThrow();
    }
}
