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

import java.util.Locale;
import java.util.Objects;

import org.junit.Test;

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.utils.AssertorArray;

/**
 * Check {@link AssertorArray}
 *
 * @since Dec 10, 2015
 * @author Gilles Landel
 *
 */
public class AssertorArrayTest extends AbstractTest {

    /**
     * Test method for {@link AssertorArray#AssertorArray()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorArray());
    }

    /**
     * Test method for {@link AssertorArray} .
     */
    @Test
    public void testPredicateGet() {
        String[] array = new String[] {null, "2"};

        assertFalse(Assertor.that(array).hasHashCode(0).isOK());
        assertTrue(Assertor.that(array).hasHashCode(Objects.hashCode((Object[]) array)).isOK());
    }

    /**
     * Test method for {@link AssertorArray#hasLength} .
     */
    @Test
    public void testHasLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.that(array).hasLength(2).isOK());
        assertFalse(Assertor.that(array).hasLength(1).isOK());
        assertFalse(Assertor.that((String[]) null).hasLength(1).isOK());
        assertFalse(Assertor.that(array).hasLength(-1).isOK());

        assertTrue(Assertor.that(array).hasLength(2).and(Assertor.that(true).isFalse().or().isTrue()).isOK());
        assertTrue(Assertor.that(array).isNotEmpty().and().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().or().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().xor().hasLength(2).isOK());
        assertFalse(Assertor.that(array).isEmpty().nand().hasLength(2).isOK());
        assertTrue(Assertor.that(array).isEmpty().nor().hasLength(2).isOK());

        assertTrue(Assertor.that(array).hasLength(2).and(Assertor.that("ee").contains("ee")).and().contains("2").isOK());

        assertException(() -> Assertor.that(array).hasLength(12, Locale.US, "the array has not the specified length %2$d*").orElseThrow(),
                IllegalArgumentException.class, "the array has not the specified length 12");
    }

    /**
     * Test method for {@link AssertorArray#hasNotLength} .
     */
    @Test
    public void testHasNotLength() {
        String[] array = new String[] {null, "2"};
        assertTrue(Assertor.that(array).not().hasLength(1).isOK());
        assertFalse(Assertor.that(array).not().hasLength(2).isOK());
        assertFalse(Assertor.that((String[]) null).not().hasLength(1).isOK());
        assertFalse(Assertor.that(array).not().hasLength(-1).isOK());
    }

    /**
     * Test method for {@link AssertorArray#isEmpty()} .
     */
    @Test
    public void testIsEmpty() {
        assertFalse(Assertor.that(new String[] {null, "2"}).isEmpty().isOK());
        assertTrue(Assertor.that(new String[] {}).isEmpty().isOK());
        assertTrue(Assertor.that((String[]) null).isEmpty().isOK());
    }

    /**
     * Test method for {@link AssertorArray#isNotEmpty} .
     */
    @Test
    public void testIsNotEmpty() {
        try {
            Assertor.that(new String[0]).not().isNotEmpty().orElseThrow("not empty array");
            Assertor.that(new String[] {""}).isNotEmpty().orElseThrow("empty array");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(new Object[0]).isNotEmpty().orElseThrow("empty array");
            fail();
        }, IllegalArgumentException.class, "empty array");

        assertException(() -> {
            Assertor.that(new String[] {"z"}).not().isNotEmpty().orElseThrow("not empty array");
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
            Assertor.that(array).contains(null).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that((Object[]) null).contains(null).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "the array cannot be null or empty");

        assertException(() -> {
            Assertor.that(new String[] {"1", "3"}).contains(null).orElseThrow("array hasn't null element");
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
            Assertor.that(array).not().contains(null).orElseThrow("array has null element");
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that((Object[]) null).not().contains(null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");

        assertException(() -> {
            Assertor.that(new String[] {"", null}).not().contains(null).orElseThrow("array has null element");
            fail();
        }, IllegalArgumentException.class, "array has null element");
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testContains() {
        assertTrue(Assertor.that(new String[] {null, "2"}).contains("2").isOK());
        assertFalse(Assertor.that(new String[] {}).contains((String) null).isOK());
        assertFalse(Assertor.that((String[]) null).contains("").isOK());

        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {null, "3", "4", "5"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAll(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {}).containsAll((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).containsAll(new String[] {null, "3"}).isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {null, "3"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).containsAny(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {}).containsAny((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).containsAny(new String[] {null, "3"}).isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.STREAM).containsAll(new String[] {null, "3"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.PARALLEL).containsAll(new String[] {null, "3"}).isOK());
    }

    /**
     * Test method for {@link AssertorArray#contains} .
     */
    @Test
    public void testDoesNotContain() {
        assertFalse(Assertor.that(new String[] {null, "2"}).not().contains("2").isOK());
        assertFalse(Assertor.that(new String[] {}).not().contains((String) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().contains("").isOK());

        assertTrue(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {null, "4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAll(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {}).not().containsAll((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().containsAll(new String[] {null, "3"}).isOK());

        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {null, "4"}).isOK());
        assertTrue(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {"4"}).isOK());
        assertFalse(Assertor.that(new String[] {null, "2", "3"}).not().containsAny(new String[] {null, "3"}).isOK());
        assertFalse(Assertor.that(new String[] {}).not().containsAny((String[]) null).isOK());
        assertFalse(Assertor.that((String[]) null).not().containsAny(new String[] {null, "3"}).isOK());

        assertTrue(
                Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.STREAM).not().containsAll(new String[] {null, "4"}).isOK());
        assertTrue(
                Assertor.that(new String[] {null, "2", "3"}, EnumAnalysisMode.PARALLEL).not().containsAll(new String[] {null, "4"}).isOK());
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
            AssertorStepArray<String> assertorTU = Assertor.that(arrayTU, mode);

            assertTrue(assertorTU.containsInOrder(arrayTUClone).isOK());
            assertFalse(assertorTU.not().containsInOrder(arrayTUClone).isOK());

            assertFalse(assertorTU.containsInOrder(arrayUT).isOK());
            assertTrue(assertorTU.not().containsInOrder(arrayUT).isOK());

            assertTrue(assertorTU.containsInOrder(arrayU).isOK());
            assertFalse(assertorTU.not().containsInOrder(arrayU).isOK());

            assertTrue(Assertor.that(arrayTVTUV, mode).containsInOrder(arrayTU).isOK());
            assertTrue(Assertor.that(arrayXTUTUV, mode).containsInOrder(arrayTU).isOK());
            assertTrue(Assertor.that(arrayTU, mode).containsInOrder(arrayTU).isOK());
            assertTrue(Assertor.that(arrayTNull, mode).containsInOrder(arrayTNull).isOK());
            assertTrue(Assertor.that(arrayTUV, mode).containsInOrder(arrayTU).isOK());
            assertTrue(Assertor.that(arrayTUV, mode).containsInOrder(arrayUV).isOK());
            assertFalse(Assertor.that(arrayTU, mode).containsInOrder(arrayTUV).isOK());
            assertFalse(Assertor.that(arrayTU, mode).containsInOrder(arrayUT).isOK());
            assertFalse(Assertor.that(arrayTU, mode).containsInOrder(arrayZ).isOK());

            assertFalse(Assertor.that(arrayTVTUV, mode).not().containsInOrder(arrayTU).isOK());
            assertFalse(Assertor.that(arrayXTUTUV, mode).not().containsInOrder(arrayTU).isOK());
            assertFalse(Assertor.that(arrayTU, mode).not().containsInOrder(arrayTU).isOK());
            assertFalse(Assertor.that(arrayTNull, mode).not().containsInOrder(arrayTNull).isOK());
            assertFalse(Assertor.that(arrayTUV, mode).not().containsInOrder(arrayTU).isOK());
            assertFalse(Assertor.that(arrayTUV, mode).not().containsInOrder(arrayUV).isOK());
            assertTrue(Assertor.that(arrayTU, mode).not().containsInOrder(arrayTUV).isOK());
            assertTrue(Assertor.that(arrayTU, mode).not().containsInOrder(arrayUT).isOK());
            assertTrue(Assertor.that(arrayTU, mode).not().containsInOrder(arrayZ).isOK());

            assertFalse(Assertor.that((String[]) null, mode).containsInOrder(arrayU).isOK());
            assertFalse(assertorTU.containsInOrder((String[]) null).isOK());

            assertException(() -> Assertor.that(arrayTU, mode).containsInOrder(arrayTUV).orElseThrow(), IllegalArgumentException.class,
                    "the array '[t, u]' should contain all values of '[t, u, v]' in the same order");

            assertException(() -> Assertor.that(arrayTVTUV, mode).not().containsInOrder(arrayTU).orElseThrow(),
                    IllegalArgumentException.class,
                    "the array '[t, v, t, u, v]' should NOT contain all values of '[t, u]' or should be in an other order");

            assertException(() -> Assertor.that((String[]) null, mode).containsInOrder(arrayU).orElseThrow(),
                    IllegalArgumentException.class, "neither arrays can be null or empty");

            assertException(() -> Assertor.that(new String[0], mode).containsInOrder(arrayU).orElseThrow(), IllegalArgumentException.class,
                    "neither arrays can be null or empty");

            assertException(() -> assertorTU.containsInOrder((String[]) null).orElseThrow(), IllegalArgumentException.class,
                    "neither arrays can be null or empty");
        }
    }
}
