/*
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.landel.utils.assertor.utils.AssertorCharSequence;

/**
 * Check {@link AssertorCharSequence}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class AssertorCharSequenceTest extends AbstractTest {

    /**
     * Test method for {@link AssertorCharSequence#AssertorCharSequence()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorCharSequence());
    }

    /**
     * Test method for {@link AssertorCharSequence#hasLength(int)} .
     */
    @Test
    public void testHasLength() {
        assertTrue(Assertor.that("text").hasLength(4).isOK());
        assertFalse(Assertor.that("text").hasLength(3).isOK());
        assertFalse(Assertor.that("text").hasLength(-1).isOK());
        assertFalse(Assertor.that((String) null).hasLength(1).isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#hasLength(int)} .
     */
    @Test
    public void testHasNotLength() {
        assertFalse(Assertor.that("text").not().hasLength(4).isOK());
        assertTrue(Assertor.that("text").not().hasLength(3).isOK());
        assertFalse(Assertor.that("text").not().hasLength(-1).isOK());
        assertFalse(Assertor.that((String) null).not().hasLength(1).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test
    public void testIsNotEmptyOKStringString() {
        try {
            Assertor.that("a").isNotEmpty().orElseThrow("empty string");
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
        Assertor.that("").isNotEmpty().orElseThrow("empty string");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKONot() {
        Assertor.that("z").not().isNotEmpty().orElseThrow("empty string");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEmpty(String, String, Object...)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKO2StringString() {
        Assertor.that((String) null).isNotEmpty().orElseThrow("empty string");
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotEmpty(java.lang.String)}
     * .
     */
    @Test
    public void testIsNotEmptyOKString() {
        try {
            Assertor.that("z").isNotEmpty().orElseThrow();
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
        Assertor.that("").isNotEmpty().orElseThrow();
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotEmpty(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotEmptyKO2String() {
        Assertor.that((String) null).isNotEmpty().orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEmpty(String, String, Object...)} .
     */
    @Test
    public void testIsEmptyOKStringString() {
        try {
            Assertor.that((String) null).isEmpty().orElseThrow("not empty or null");
            Assertor.that("").isEmpty().orElseThrow("not empty");
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
        Assertor.that("r").isEmpty().orElseThrow("not empty");
    }

    /**
     * Test method for {@link AssertorCharSequence#isEmpty(java.lang.String)} .
     */
    @Test
    public void testIsEmptyOKString() {
        try {
            Assertor.that((String) null).isEmpty().orElseThrow();
            Assertor.that("").isEmpty().orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isEmpty(java.lang.String)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsEmptyKOString() {
        Assertor.that("e").isEmpty().orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotBlank(String, String, Object...)} .
     */
    @Test
    public void testIsNotBlankOKStringString() {
        try {
            Assertor.that("   \t sds  ").isNotBlank().orElseThrow("blank");
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
        Assertor.that("   \t    ").isNotBlank().orElseThrow("blank");
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotBlank(java.lang.String)}
     * .
     */
    @Test
    public void testIsNotBlankOKString() {
        try {
            Assertor.that("    \t  e ").isNotBlank().orElseThrow();
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
        Assertor.that("    \t   ").isNotBlank().orElseThrow();
    }

    /**
     * Test method for {@link AssertorCharSequence#isNotBlank(java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsNotBlankKOnot() {
        Assertor.that("    \t   a").not().isNotBlank().orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isBlank(String, String, Object...)} .
     */
    @Test
    public void testIsBlankOKStringString() {
        try {
            Assertor.that("   \t   ").isBlank().orElseThrow("not blank");
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
        Assertor.that("   \t d   ").isBlank().orElseThrow("not blank");
    }

    /**
     * Test method for {@link AssertorCharSequence#isBlank(java.lang.String)} .
     */
    @Test
    public void testIsBlankOKString() {
        try {
            Assertor.that("   \t   ").isBlank().orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }
    }

    /**
     * Test method for {@link AssertorCharSequence#isBlank(java.lang.String)} .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsBlankKOString() {
        Assertor.that("      j ").isBlank().orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqual() {
        assertTrue(Assertor.that("test").isEqual("test").isOK());
        assertFalse(Assertor.that("test").isEqual("Test").isOK());
        assertFalse(Assertor.that("te\r\nst").isEqual("tes\nt").isOK());
        assertFalse(Assertor.that("te\r\nst").isEqual("Tes\nt").isOK());
        assertTrue(Assertor.that("test").isEqual(new StringBuilder("test")).isOK());
        assertTrue(Assertor.that((String) null).isEqual(null).isOK());
        assertFalse(Assertor.that((String) null).isEqual("test").isOK());
        assertFalse(Assertor.that("test").isEqual(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqual() {
        assertFalse(Assertor.that("test").isNotEqual("test").isOK());
        assertTrue(Assertor.that("test").isNotEqual("Test").isOK());
        assertTrue(Assertor.that("te\r\nst").isNotEqual("tes\nt").isOK());
        assertTrue(Assertor.that("te\r\nst").isNotEqual("Tes\nt").isOK());
        assertFalse(Assertor.that("test").isNotEqual(new StringBuilder("test")).isOK());
        assertFalse(Assertor.that((String) null).isNotEqual(null).isOK());
        assertTrue(Assertor.that((String) null).isNotEqual("test").isOK());
        assertTrue(Assertor.that("test").isNotEqual(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreCase(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreCase() {
        assertTrue(Assertor.that("test").isEqualIgnoreCase("test").isOK());
        assertTrue(Assertor.that("test").isEqualIgnoreCase("Test").isOK());
        assertFalse(Assertor.that("te\r\nst").isEqualIgnoreCase("tes\nt").isOK());
        assertFalse(Assertor.that("te\r\nst").isEqualIgnoreCase("Tes\nt").isOK());
        assertTrue(Assertor.that("test").isEqualIgnoreCase(new StringBuilder("test")).isOK());
        assertTrue(Assertor.that((String) null).isEqualIgnoreCase(null).isOK());
        assertFalse(Assertor.that((String) null).isEqualIgnoreCase("test").isOK());
        assertFalse(Assertor.that("test").isEqualIgnoreCase(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqualIgnoreCase(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreCase() {
        assertFalse(Assertor.that("test").isNotEqualIgnoreCase("test").isOK());
        assertFalse(Assertor.that("test").isNotEqualIgnoreCase("Test").isOK());
        assertTrue(Assertor.that("te\r\nst").isNotEqualIgnoreCase("tes\nt").isOK());
        assertTrue(Assertor.that("te\r\nst").isNotEqualIgnoreCase("Tes\nt").isOK());
        assertFalse(Assertor.that("test").isNotEqualIgnoreCase(new StringBuilder("test")).isOK());
        assertFalse(Assertor.that((String) null).isNotEqualIgnoreCase(null).isOK());
        assertTrue(Assertor.that((String) null).isNotEqualIgnoreCase("test").isOK());
        assertTrue(Assertor.that("test").isNotEqualIgnoreCase(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreLineReturns() {
        assertTrue(Assertor.that("test").isEqualIgnoreLineReturns("test").isOK());
        assertFalse(Assertor.that("test").isEqualIgnoreLineReturns("Test").isOK());
        assertTrue(Assertor.that("te\r\nst").isEqualIgnoreLineReturns("tes\nt").isOK());
        assertFalse(Assertor.that("te\r\nst").isEqualIgnoreLineReturns("Tes\nt").isOK());
        assertTrue(Assertor.that("test").isEqualIgnoreLineReturns(new StringBuilder("test")).isOK());
        assertTrue(Assertor.that((String) null).isEqualIgnoreLineReturns(null).isOK());
        assertFalse(Assertor.that((String) null).isEqualIgnoreLineReturns("test").isOK());
        assertFalse(Assertor.that("test").isEqualIgnoreLineReturns(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqualIgnoreLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreLineReturns() {
        assertFalse(Assertor.that("test").isNotEqualIgnoreLineReturns("test").isOK());
        assertTrue(Assertor.that("test").isNotEqualIgnoreLineReturns("Test").isOK());
        assertFalse(Assertor.that("te\r\nst").isNotEqualIgnoreLineReturns("tes\nt").isOK());
        assertTrue(Assertor.that("te\r\nst").isNotEqualIgnoreLineReturns("Tes\nt").isOK());
        assertFalse(Assertor.that("test").isNotEqualIgnoreLineReturns(new StringBuilder("test")).isOK());
        assertFalse(Assertor.that((String) null).isNotEqualIgnoreLineReturns(null).isOK());
        assertTrue(Assertor.that((String) null).isNotEqualIgnoreLineReturns("test").isOK());
        assertTrue(Assertor.that("test").isNotEqualIgnoreLineReturns(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isEqualIgnoreCaseAndLineReturns(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsEqualIgnoreCaseAndLineReturns() {
        assertTrue(Assertor.that("test").isEqualIgnoreCaseAndLineReturns("test").isOK());
        assertTrue(Assertor.that("test").isEqualIgnoreCaseAndLineReturns("Test").isOK());
        assertTrue(Assertor.that("te\r\nst").isEqualIgnoreCaseAndLineReturns("tes\nt").isOK());
        assertTrue(Assertor.that("te\r\nst").isEqualIgnoreCaseAndLineReturns("Tes\nt").isOK());
        assertTrue(Assertor.that("test").isEqualIgnoreCaseAndLineReturns(new StringBuilder("test")).isOK());
        assertTrue(Assertor.that((String) null).isEqualIgnoreCaseAndLineReturns(null).isOK());
        assertFalse(Assertor.that((String) null).isEqualIgnoreCaseAndLineReturns("test").isOK());
        assertFalse(Assertor.that("test").isEqualIgnoreCaseAndLineReturns(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#isNotEqual(StepAssertor, CharSequence, boolean, boolean, Message)}
     * .
     */
    @Test
    public void testIsNotEqualIgnoreCaseAndLineReturns() {
        assertFalse(Assertor.that("test").isNotEqualIgnoreCaseAndLineReturns("test").isOK());
        assertFalse(Assertor.that("test").isNotEqualIgnoreCaseAndLineReturns("Test").isOK());
        assertFalse(Assertor.that("te\r\nst").isNotEqualIgnoreCaseAndLineReturns("tes\nt").isOK());
        assertFalse(Assertor.that("te\r\nst").isNotEqualIgnoreCaseAndLineReturns("Tes\nt").isOK());
        assertFalse(Assertor.that("test").isNotEqualIgnoreCaseAndLineReturns(new StringBuilder("test")).isOK());
        assertFalse(Assertor.that((String) null).isNotEqualIgnoreCaseAndLineReturns(null).isOK());
        assertTrue(Assertor.that((String) null).isNotEqualIgnoreCaseAndLineReturns("test").isOK());
        assertTrue(Assertor.that("test").isNotEqualIgnoreCaseAndLineReturns(null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test
    public void testDoesNotContainOKStringStringString() {
        try {
            Assertor.that("titi part en vacances").not().contains("toto").orElseThrow("not found");
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
        Assertor.that("titi part en vacances").not().contains("titi").orElseThrow("not found");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#not().contains(java.lang.String,
     * java.lang.String)} .
     */
    @Test
    public void testDoesNotContain() {
        assertTrue(Assertor.that("totos").not().contains("toto part en vacances").isOK());
        assertTrue(Assertor.that("toto").not().contains("totu").isOK());
        assertTrue(Assertor.that("toto").not().contains('x').isOK());
        assertFalse(Assertor.that("toto part en vacances").not().contains("toto").isOK());
        assertFalse(Assertor.that((String) null).not().contains("toto part en vacances").isOK());
        assertFalse(Assertor.that("toto").not().contains((CharSequence) null).isOK());
        assertFalse(Assertor.that("toto").not().contains((Character) null).isOK());
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDoesNotContainKOStringString() {
        Assertor.that("tata part en vacances").not().contains("tata").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.that("toto part en vacances").contains("toto").isOK());
        assertTrue(Assertor.that("toto").contains('t').isOK());
        assertTrue(Assertor.that("toto").contains("toto").isOK());
        assertTrue(Assertor.that("toti et toto part en vacances").contains("toto").isOK());
        assertFalse(Assertor.that("toti part en vacances en moto").contains("toto").isOK());
        assertFalse(Assertor.that("toto").contains("toto part en vacances").isOK());
        assertFalse(Assertor.that((String) null).contains("toto part en vacances").isOK());
        assertFalse(Assertor.that("toto").contains((CharSequence) null).isOK());
        assertFalse(Assertor.that("toto").contains((Character) null).isOK());
        assertFalse(Assertor.that((String) null).contains((Character) null).isOK());

        assertException(() -> {
            Assertor.that("toto part en vacances").contains("toto").and().contains("voyage").orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.that("toto part en vacances").contains("toto").and().contains("voyage")
                    .and(Assertor.that("text").isBlank().or().contains("text")).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.that("toto part en vacances").contains("toto").and().contains("voyage")
                    .or(Assertor.that("text").isBlank().or().not().contains("text")).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'voyage'");

        assertException(() -> {
            Assertor.that("toto part en vacances").contains('t').and().contains('y').orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.that("toto part en vacances").contains('t').and().contains('y')
                    .and(Assertor.that("text").isBlank().or().contains("text")).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.that("toto part en vacances").contains('t').and().contains('y')
                    .or(Assertor.that("text").isBlank().or().not().contains('t')).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence 'toto part en vacances' should contain 'y'");

        assertException(() -> {
            Assertor.that((CharSequence) null).contains('t').and().contains((Character) null).orElseThrow();
        }, IllegalArgumentException.class, "the char sequence cannot be null and the searched substring cannot be null or empty");
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(java.lang.String, java.lang.String)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsKOStringString() {
        Assertor.that("tata part en vacances").contains("tutu").orElseThrow();
    }

    /**
     * Test method for
     * {@link AssertorCharSequence#contains(String, String, String, Object...)}
     * .
     */
    @Test
    public void testContainsOKStringStringString() {
        try {
            Assertor.that("toto part en vacances").contains("toto").orElseThrow("text not found");
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
        Assertor.that("tata part en vacances").contains("tutu").orElseThrow("text not found");
    }

    /**
     * Test method for {@link AssertorCharSequence#startsWith} and
     * {@link AssertorCharSequence#startsWithIgnoreCase}.
     */
    @Test
    public void testStartsWith() {
        assertTrue(Assertor.that("TexT").startsWith("Tex").isOK());
        assertFalse(Assertor.that("TexT").startsWith("tex").isOK());
        assertFalse(Assertor.that("TexT").startsWith("ext").isOK());
        assertFalse(Assertor.that("TexT").startsWith("").isOK());
        assertFalse(Assertor.that("").startsWith("").isOK());
        assertFalse(Assertor.that("TexT").startsWith("Texte").isOK());
        assertFalse(Assertor.that((String) null).startsWith("Tex").isOK());
        assertFalse(Assertor.that("TexT").startsWith(null).isOK());

        assertTrue(Assertor.that("TexT").startsWithIgnoreCase("tex").isOK());
        assertTrue(Assertor.that("TexT").startsWithIgnoreCase("tex").isOK());
        assertFalse(Assertor.that("TexT").startsWithIgnoreCase("ext").isOK());
        assertFalse(Assertor.that("TexT").startsWithIgnoreCase("").isOK());
        assertFalse(Assertor.that("").startsWithIgnoreCase("").isOK());
        assertFalse(Assertor.that("TexT").startsWithIgnoreCase("texte").isOK());
        assertFalse(Assertor.that((String) null).startsWithIgnoreCase("tex").isOK());
        assertFalse(Assertor.that("TexT").startsWithIgnoreCase(null).isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#endsWith} and
     * {@link AssertorCharSequence#endsWithIgnoreCase}.
     */
    @Test
    public void testEndsWith() {
        assertTrue(Assertor.that("TexT").endsWith("exT").isOK());
        assertFalse(Assertor.that("TexT").endsWith("ext").isOK());
        assertFalse(Assertor.that("TexT").endsWith("tex").isOK());
        assertFalse(Assertor.that("TexT").endsWith("").isOK());
        assertFalse(Assertor.that("").endsWith("").isOK());
        assertFalse(Assertor.that("TexT").endsWith("eTexT").isOK());
        assertFalse(Assertor.that((String) null).endsWith("exT").isOK());
        assertFalse(Assertor.that("TexT").endsWith(null).isOK());

        assertTrue(Assertor.that("TexT").endsWithIgnoreCase("exT").isOK());
        assertTrue(Assertor.that("TexT").endsWithIgnoreCase("ext").isOK());
        assertFalse(Assertor.that("TexT").endsWithIgnoreCase("tex").isOK());
        assertFalse(Assertor.that("TexT").endsWithIgnoreCase("").isOK());
        assertFalse(Assertor.that("").endsWithIgnoreCase("").isOK());
        assertFalse(Assertor.that("TexT").endsWithIgnoreCase("eTexT").isOK());
        assertFalse(Assertor.that((String) null).endsWithIgnoreCase("exT").isOK());
        assertFalse(Assertor.that("TexT").endsWithIgnoreCase(null).isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#matches}.
     */
    @Test
    public void testMatches() {
        final String regex = "[xeT]{4}";
        final Pattern pattern = Pattern.compile(regex);

        assertTrue(Assertor.that("TexT").matches(pattern).isOK());
        assertFalse(Assertor.that("Text").matches(pattern).isOK());
        assertFalse(Assertor.that((String) null).matches(pattern).isOK());
        assertFalse(Assertor.that("Text").matches((Pattern) null).isOK());

        assertTrue(Assertor.that("TexT").matches(regex).isOK());
        assertFalse(Assertor.that("Text").matches(regex).isOK());
        assertFalse(Assertor.that((String) null).matches(regex).isOK());
        assertFalse(Assertor.that("Text").matches((String) null).isOK());
    }

    /**
     * Test method for {@link AssertorCharSequence#find}.
     */
    @Test
    public void testFind() {
        final String regex = "[xeT]{3}";
        final Pattern pattern = Pattern.compile(regex);

        assertTrue(Assertor.that("TexT").find(pattern).isOK());
        assertTrue(Assertor.that("Text").find(pattern).isOK());
        assertFalse(Assertor.that("Tetxt").find(pattern).isOK());
        assertFalse(Assertor.that((String) null).find(pattern).isOK());
        assertFalse(Assertor.that("Text").find((Pattern) null).isOK());

        assertTrue(Assertor.that("TexT").find(regex).isOK());
        assertTrue(Assertor.that("Text").find(regex).isOK());
        assertFalse(Assertor.that("Tetxt").find(regex).isOK());
        assertFalse(Assertor.that((String) null).find(regex).isOK());
        assertFalse(Assertor.that("Text").find((String) null).isOK());
        assertFalse(Assertor.that((String) null).find((String) null).isOK());
    }

    @Test
    public void testSubAssertor() {
        Assertor.that(new IOException("error")).isNotNull().andCharSequence(e -> e.getMessage()).startsWith("er").orElseThrow();

        // @formatter:off
        Assertor.that(new IOException("error"))
                .isNotNull()
                .andAssertor(e -> Assertor.that(e.getMessage()).startsWith("er"))
                .andAssertor(e -> Assertor.that(e.getCause()).isNull())
                .orElseThrow();
        // @formatter:on
    }
}
