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

import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorArray;

/**
 * Check {@link AssertorArray}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class PredicateAssertorArrayTest extends AbstractTest {

    /**
     * Test method for {@link AssertorArray} .
     */
    @Test
    public void testPredicateGet() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.ofArray().hasHashCode(0).that(array).isOK());
        assertTrue(Assertor.ofArray().hasHashCode(Objects.hashCode((Object[]) array)).that(array).isOK());
    }

    /**
     * Test method for {@link AssertorArray#hasLengthGT} .
     */
    @Test
    public void testHasLengthGT() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.<String> ofArray().hasLengthGT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().hasLengthGT(1).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthGT(1).that((String[]) null).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthGT(-1).that(array).isOK());

        assertFalse(Assertor.<String> ofArray().isNotEmpty().and().hasLengthGT(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().or().hasLengthGT(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().xor().hasLengthGT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nand().hasLengthGT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nor().hasLengthGT(2).that(array).isOK());

        assertException(() -> Assertor.<String> ofArray().hasLengthGT(12, Locale.US, "the array has not the specified length %2$d*")
                .that(array).orElseThrow(), IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasLengthGTE} .
     */
    @Test
    public void testHasLengthGTE() {
        String[] array = new String[] {null, "2"};

        assertTrue(Assertor.<String> ofArray().hasLengthGTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().hasLengthGTE(1).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthGTE(1).that((String[]) null).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthGTE(-1).that(array).isOK());

        assertTrue(Assertor.<String> ofArray().isNotEmpty().and().hasLengthGTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().or().hasLengthGTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().xor().hasLengthGTE(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().nand().hasLengthGTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nor().hasLengthGTE(2).that(array).isOK());

        assertException(() -> Assertor.<String> ofArray().hasLengthGTE(12, Locale.US, "the array has not the specified length %2$d*")
                .that(array).orElseThrow(), IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasLengthLT} .
     */
    @Test
    public void testHasLengthLT() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.<String> ofArray().hasLengthLT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().hasLengthLT(3).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthLT(1).that((String[]) null).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthLT(-1).that(array).isOK());

        assertFalse(Assertor.<String> ofArray().isNotEmpty().and().hasLengthLT(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().or().hasLengthLT(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().xor().hasLengthLT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nand().hasLengthLT(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nor().hasLengthLT(2).that(array).isOK());

        assertException(() -> Assertor.<String> ofArray().hasLengthLT(1, Locale.US, "the array has not the specified length %2$d*")
                .that(array).orElseThrow(), IllegalArgumentException.class, "the array has not the specified length 1");
    }

    /**
     * Test method for {@link AssertorArray#hasLengthLTE} .
     */
    @Test
    public void testHasLengthLTE() {
        String[] array = new String[] {null, "2"};

        assertTrue(Assertor.<String> ofArray().hasLengthLTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().hasLengthLTE(3).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthLTE(1).that((String[]) null).isOK());
        assertFalse(Assertor.<String> ofArray().hasLengthLTE(-1).that(array).isOK());

        assertTrue(Assertor.<String> ofArray().isNotEmpty().and().hasLengthLTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().or().hasLengthLTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().xor().hasLengthLTE(2).that(array).isOK());
        assertFalse(Assertor.<String> ofArray().isEmpty().nand().hasLengthLTE(2).that(array).isOK());
        assertTrue(Assertor.<String> ofArray().isEmpty().nor().hasLengthLTE(2).that(array).isOK());

        assertException(() -> Assertor.<String> ofArray().hasLengthLTE(1, Locale.US, "the array has not the specified length %2$d*")
                .that(array).orElseThrow(), IllegalArgumentException.class, "the array has not the specified length 1");
    }

    /**
     * Test method for {@link AssertorArray#hasLength} .
     */
    @Test
    public void testHasLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.ofArray().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().hasLength(1).that(array).isOK());
        assertFalse(Assertor.ofArray().hasLength(1).that((String[]) null).isOK());
        assertFalse(Assertor.ofArray().hasLength(-1).that(array).isOK());

        assertTrue(Assertor.ofArray().hasLength(2).and(Assertor.ofArray().contains(null).or().validates(a -> "2".equals(a[1]))).that(array)
                .isOK());
        assertTrue(Assertor.ofArray().isNotEmpty().and().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().or().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().xor().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().isEmpty().nand().hasLength(2).that(array).isOK());
        assertTrue(Assertor.ofArray().isEmpty().nor().hasLength(2).that(array).isOK());

        assertTrue(Assertor.ofArray().hasLength(2).and(Assertor.ofArray().contains("2")).xor().contains("1").that(array).isOK());

        assertException(
                () -> Assertor.ofArray().hasLength(12, Locale.US, "the array has not the specified length %2$d*").that(array).orElseThrow(),
                IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasNotLength} .
     */
    @Test
    public void testHasNotLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.ofArray().not().hasLength(1).that(array).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(2).that(array).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(1).that((String[]) null).isOK());
        assertFalse(Assertor.ofArray().not().hasLength(-1).that(array).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isEmpty()} .
     */
    @Test
    public void testIsEmpty() {
        assertFalse(Assertor.ofArray().isEmpty().that(new String[] {null, "2"}).isOK());
        assertTrue(Assertor.ofArray().isEmpty().that(new String[] {}).isOK());
        assertTrue(Assertor.ofArray().isEmpty().that((String[]) null).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isNotEmpty} .
     */
    @Test
    public void testIsNotEmpty() {
        try {
            Assertor.ofArray().not().isNotEmpty().that(new String[0]).orElseThrow("not empty array");
            Assertor.ofArray().isNotEmpty().that(new String[] {""}).orElseThrow("empty array");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().isNotEmpty().that(new Object[0]).orElseThrow("empty array");
            fail();
        }, IllegalArgumentException.class, "empty array");

        assertException(() -> {
            Assertor.ofArray().not().isNotEmpty().that(new String[] {"z"}).orElseThrow("not empty array");
            fail();
        }, IllegalArgumentException.class, "not empty array");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContainsNull() {
        try {
            String[] array = new String[] {null, "2"};
            Assertor.ofArray().contains(null).that(array).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().contains(null).that((Object[]) null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the array cannot be null or empty");

        assertException(() -> {
            Assertor.ofArray().contains(null).that(new String[] {"1", "3"}).orElseThrow("array hasn't null element");
            fail();
        }, IllegalArgumentException.class, "array hasn't null element");
    }

    /**
     * Test method for {@link AssertorArray#doesNotContainNull} .
     */
    @Test
    public void testDoesNotContainNull() {
        try {
            String[] array = new String[] {"1", "2"};
            Assertor.ofArray().not().contains(null).that(array).orElseThrow("array has null element");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofArray().not().contains(null).that((Object[]) null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");

        assertException(() -> {
            Assertor.ofArray().not().contains(null).that(new String[] {"", null}).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.ofArray().contains("2").that(new String[] {null, "2"}).isOK());
        assertFalse(Assertor.ofArray().contains((String) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().contains("").that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAll((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().containsAll(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().containsAny(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray().containsAny(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAny(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().containsAny((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().containsAny(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(
                Assertor.ofArray(EnumAnalysisMode.STREAM).containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray(EnumAnalysisMode.PARALLEL).containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"})
                .isOK());
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testDoesNotContain() {
        assertFalse(Assertor.ofArray().not().contains("2").that(new String[] {null, "2"}).isOK());
        assertFalse(Assertor.ofArray().not().contains((String) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().contains("").that((String[]) null).isOK());

        assertTrue(Assertor.ofArray().not().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().containsAll(new String[] {null, "3"}).that((String[]) null).isOK());

        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "4"}).that(new String[] {null, "2", "3"}).isOK());
        assertTrue(Assertor.ofArray().not().containsAny(new String[] {"4"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "3"}).that(new String[] {null, "2", "3"}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny((String[]) null).that(new String[] {}).isOK());
        assertFalse(Assertor.ofArray().not().containsAny(new String[] {null, "3"}).that((String[]) null).isOK());

        assertTrue(Assertor.ofArray(EnumAnalysisMode.STREAM).not().containsAll(new String[] {null, "4"}).that(new String[] {null, "2", "3"})
                .isOK());
        assertTrue(Assertor.ofArray(EnumAnalysisMode.PARALLEL).not().containsAll(new String[] {null, "4"})
                .that(new String[] {null, "2", "3"}).isOK());
    }

    /**
     * Check {@link AssertorArray#containsInOrder}
     */
    @Test
    public void testContainsInOrder() {
        String[] arrayTU = {"t", "u"};
        String[] arrayTUClone = {"t", "u"};
        String[] arrayUT = {"u", "t"};
        String[] arrayU = {"u"};
        String[] arrayTVTUV = {"t", "v", "t", "u", "v"};
        String[] arrayXTUTUV = {"x", "t", "u", "t", "u", "v"};
        String[] arrayTUV = {"t", "u", "v"};
        String[] arrayUV = {"u", "v"};
        String[] arrayZ = {"z"};
        String[] arrayTNull = {"t", null};

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {
            PredicateAssertorStepArray<String> predicate = Assertor.<String> ofArray(mode);

            assertTrue(predicate.containsInOrder(arrayTUClone).that(arrayTU).isOK());
            assertFalse(predicate.not().containsInOrder(arrayTUClone).that(arrayTU).isOK());

            assertFalse(predicate.containsInOrder(arrayUT).that(arrayTU).isOK());
            assertTrue(predicate.not().containsInOrder(arrayUT).that(arrayTU).isOK());

            assertTrue(predicate.containsInOrder(arrayU).that(arrayTU).isOK());
            assertFalse(predicate.not().containsInOrder(arrayU).that(arrayTU).isOK());

            assertTrue(predicate.containsInOrder(arrayTU).that(arrayTVTUV).isOK());
            assertTrue(predicate.containsInOrder(arrayTU).that(arrayXTUTUV).isOK());
            assertTrue(predicate.containsInOrder(arrayTU).that(arrayTU).isOK());
            assertTrue(predicate.containsInOrder(arrayTNull).that(arrayTNull).isOK());
            assertTrue(predicate.containsInOrder(arrayTU).that(arrayTUV).isOK());
            assertTrue(predicate.containsInOrder(arrayUV).that(arrayTUV).isOK());
            assertFalse(predicate.containsInOrder(arrayTUV).that(arrayTU).isOK());
            assertFalse(predicate.containsInOrder(arrayUT).that(arrayTU).isOK());
            assertFalse(predicate.containsInOrder(arrayZ).that(arrayTU).isOK());

            assertFalse(predicate.not().containsInOrder(arrayTU).that(arrayTVTUV).isOK());
            assertFalse(predicate.not().containsInOrder(arrayTU).that(arrayXTUTUV).isOK());
            assertFalse(predicate.not().containsInOrder(arrayTU).that(arrayTU).isOK());
            assertFalse(predicate.not().containsInOrder(arrayTNull).that(arrayTNull).isOK());
            assertFalse(predicate.not().containsInOrder(arrayTU).that(arrayTUV).isOK());
            assertFalse(predicate.not().containsInOrder(arrayUV).that(arrayTUV).isOK());
            assertTrue(predicate.not().containsInOrder(arrayTUV).that(arrayTU).isOK());
            assertTrue(predicate.not().containsInOrder(arrayUT).that(arrayTU).isOK());
            assertTrue(predicate.not().containsInOrder(arrayZ).that(arrayTU).isOK());

            assertFalse(predicate.containsInOrder(arrayU).that((String[]) null).isOK());
            assertFalse(predicate.containsInOrder((String[]) null).that(arrayTU).isOK());

            assertException(() -> predicate.containsInOrder(arrayTUV).that(arrayTU).orElseThrow(), IllegalArgumentException.class,
                    "the array '[t, u]' should contain all values of '[t, u, v]' in the same order");

            assertException(() -> predicate.not().containsInOrder(arrayTU).that(arrayTVTUV).orElseThrow(), IllegalArgumentException.class,
                    "the array '[t, v, t, u, v]' should NOT contain all values of '[t, u]' or should be in an other order");

            assertException(() -> predicate.containsInOrder(arrayU).that((String[]) null).orElseThrow(), IllegalArgumentException.class,
                    "neither arrays can be null or empty");

            assertException(() -> predicate.containsInOrder((String[]) null).that(arrayTU).orElseThrow(), IllegalArgumentException.class,
                    "neither arrays can be null or empty");
        }
    }

    /**
     * Check {@link AssertorArray#anyMatch}
     */
    @Test
    public void testAnyMatch() {
        String[] arraytu = {"t", "u"};
        String[] arrayTu = {"T", "u"};
        String[] arrayTU = {"T", "U"};
        String[] arraytNull = {"t", null};

        Predicate<String> predicate = e -> Objects.equals(e, StringUtils.lowerCase(e));

        assertTrue(Assertor.<String> ofArray().anyMatch(predicate).that(arraytu).isOK());

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

            PredicateAssertorStepArray<String> predicateAssertor = Assertor.<String> ofArray(mode);
            PredicateStepArray<String> predicateStep = predicateAssertor.anyMatch(predicate);

            assertTrue(predicateStep.that(arraytu).isOK());
            assertTrue(predicateStep.that(arrayTu).isOK());
            assertFalse(predicateStep.that(arrayTU).isOK());
            assertTrue(predicateStep.that(arraytNull).isOK());

            assertTrue(predicateAssertor.not().anyMatch(predicate).that(arrayTU).isOK());
            assertFalse(predicateAssertor.not().anyMatch(predicate).that(arrayTu).isOK());

            assertException(() -> predicateStep.that(new String[0]).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that((String[]) null).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateAssertor.anyMatch(null).that(arrayTu).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that(arrayTU).orElseThrow(), IllegalArgumentException.class,
                    "any array element '[T, U]' should match the predicate");
        }
    }

    /**
     * Check {@link AssertorArray#allMatch}
     */
    @Test
    public void testAllMatch() {
        String[] arraytu = {"t", "u"};
        String[] arrayTu = {"T", "u"};
        String[] arrayTU = {"T", "U"};
        String[] arraytNull = {"t", null};

        Predicate<String> predicate = e -> Objects.equals(e, StringUtils.lowerCase(e));

        assertTrue(Assertor.<String> ofArray().allMatch(predicate).that(arraytu).isOK());

        for (EnumAnalysisMode mode : EnumAnalysisMode.values()) {

            PredicateAssertorStepArray<String> predicateAssertor = Assertor.<String> ofArray(mode);
            PredicateStepArray<String> predicateStep = predicateAssertor.allMatch(predicate);

            assertTrue(predicateStep.that(arraytu).isOK());
            assertFalse(predicateStep.that(arrayTu).isOK());
            assertFalse(predicateStep.that(arrayTU).isOK());
            assertTrue(predicateStep.that(arraytNull).isOK());

            assertException(() -> predicateStep.that(new String[0]).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that((String[]) null).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateAssertor.allMatch(null).that(arrayTu).orElseThrow(), IllegalArgumentException.class,
                    "the array cannot be null or empty and predicate cannot be null");
            assertException(() -> predicateStep.that(arrayTU).orElseThrow(), IllegalArgumentException.class,
                    "all the array elements '[T, U]' should match the predicate");
        }
    }
}
