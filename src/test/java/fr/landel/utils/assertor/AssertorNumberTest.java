/*-
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.junit.Test;

import fr.landel.utils.assertor.predicate.PredicateStepCharSequence;
import fr.landel.utils.assertor.utils.AssertorNumber;
import fr.landel.utils.commons.CollectionUtils2;

/**
 * Check {@link AssertorNumber}
 *
 * @since Jul 16, 2016
 * @author Gilles
 *
 */
public class AssertorNumberTest extends AbstractTest {

    /**
     * Test method for {@link AssertorNumber#AssertorNumber()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorNumber());
    }

    /**
     * Test method for {@link AssertorNumber#isEqual}.
     */
    @Test
    public void testIsEqualN() {
        assertTrue(Assertor.that(2).isEqual(2).isOK());
        assertFalse(Assertor.that(2).isEqual(1).isOK());
        assertFalse(Assertor.that(2).isEqual(null).isOK());
        assertFalse(Assertor.that((Integer) null).isEqual(1).isOK());
        assertTrue(Assertor.that((Integer) null).isEqual(null).isOK());

        assertTrue(Assertor.that(2).isEqual(2, "error1").isOK());
        assertFalse(Assertor.that(2).isEqual(1, "error1").isOK());
        assertFalse(Assertor.that(2).isEqual(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isEqual(1, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isEqual(null, "error1").isOK());

        assertTrue(Assertor.that(2).isEqual(2, "error1").and().isNotNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").or().isNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").xor().isNull().isOK());
        assertFalse(Assertor.that(2).isEqual(2, "error1").nand().isNull().isOK());
        assertTrue(Assertor.that(2).isEqual(2, "error1").nor().isNull().isOK());

        assertEquals("error1", Assertor.that(2).isEqual(1, "error1").getErrors().get());

        Assertor.that(2).isEqual(2).and(Assertor.that(12).isGT(10)).isOK();

        assertException(() -> {
            Assertor.that(2).isEqual(1, "error1 %1$s* %s %2$s*", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0 1");

        assertException(() -> {
            Assertor.that(2).isEqual(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNotEqual}.
     */
    @Test
    public void testIsNotEqualN() {
        assertTrue(Assertor.that(2).isNotEqual(3).isOK());
        assertFalse(Assertor.that(2).isNotEqual(2).isOK());
        assertTrue(Assertor.that(2).isNotEqual(null).isOK());
        assertTrue(Assertor.that((Integer) null).isNotEqual(1).isOK());

        assertTrue(Assertor.that(2).isNotEqual(3, "error1").isOK());
        assertFalse(Assertor.that(2).isNotEqual(2, "error1").isOK());
        assertTrue(Assertor.that(2).isNotEqual(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isNotEqual(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isNotEqual(2, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isNotEqual(2, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isNotEqual(2, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(2).not().isNotEqual(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isZero}.
     */
    @Test
    public void testIsZero() {
        assertTrue(Assertor.that(0).isZero().isOK());
        assertFalse(Assertor.that(12).isZero().isOK());
        assertFalse(Assertor.that(-1).isZero().isOK());

        assertTrue(Assertor.that(0.00).isZero().isOK());
        assertFalse(Assertor.that(0.0001).isZero().isOK());
        assertFalse(Assertor.that(-0.0001).isZero().isOK());

        assertFalse(Assertor.that((Integer) null).isZero().isOK());
        assertTrue(Assertor.that((Integer) null).not().isZero().isOK());

        assertEquals("error1", Assertor.that(2).isZero("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isZero("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(-1).isZero("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(0).not().isZero("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isPositive}.
     */
    @Test
    public void testIsPositive() {
        assertFalse(Assertor.that(0).isPositive().isOK());
        assertTrue(Assertor.that(12).isPositive().isOK());
        assertFalse(Assertor.that(-1).isPositive().isOK());

        assertFalse(Assertor.that(0.00).isPositive().isOK());
        assertTrue(Assertor.that(0.0001).isPositive().isOK());
        assertFalse(Assertor.that(-0.0001).isPositive().isOK());

        assertFalse(Assertor.that((Integer) null).isPositive().isOK());
        assertTrue(Assertor.that((Integer) null).not().isPositive().isOK());

        assertEquals("error1", Assertor.that(0).isPositive("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(0).isPositive("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.that(-1).isPositive("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(0.0001d).not().isPositive("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isNegative}.
     */
    @Test
    public void testIsNegative() {
        assertFalse(Assertor.that(0).isNegative().isOK());
        assertFalse(Assertor.that(12).isNegative().isOK());
        assertTrue(Assertor.that(-1).isNegative().isOK());

        assertFalse(Assertor.that(0.00).isNegative().isOK());
        assertFalse(Assertor.that(0.0001).isNegative().isOK());
        assertTrue(Assertor.that(-0.0001).isNegative().isOK());

        assertFalse(Assertor.that((Integer) null).isNegative().isOK());
        assertTrue(Assertor.that((Integer) null).not().isNegative().isOK());

        assertEquals("error1", Assertor.that(0).isNegative("error1").getErrors().get());

        assertException(() -> {
            Assertor.that(0).isNegative("error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 0 0");

        assertException(() -> {
            Assertor.that(1).isNegative("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");

        assertException(() -> {
            Assertor.that(-0.00001d).not().isNegative("error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGT}.
     */
    @Test
    public void testIsGT() {
        assertTrue(Assertor.that(2).isGT(1).isOK());
        assertFalse(Assertor.that(2).isGT(2).isOK());
        assertFalse(Assertor.that(2).isGT(3).isOK());
        assertTrue(Assertor.that(2).isGT(null).isOK());
        assertFalse(Assertor.that((Integer) null).isGT(1).isOK());

        assertTrue(Assertor.that(2).isGT(1, "error1").isOK());
        assertFalse(Assertor.that(2).isGT(2, "error1").isOK());
        assertFalse(Assertor.that(2).isGT(3, "error1").isOK());
        assertTrue(Assertor.that(2).isGT(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isGT(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isGT(2, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isGT(2, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isGT(2, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isGTE}.
     */
    @Test
    public void testIsGTE() {
        assertTrue(Assertor.that(2).isGTE(1).isOK());
        assertTrue(Assertor.that(2).isGTE(2).isOK());
        assertFalse(Assertor.that(2).isGTE(3).isOK());
        assertTrue(Assertor.that(2).isGTE(null).isOK());
        assertFalse(Assertor.that((Integer) null).isGTE(1).isOK());

        assertTrue(Assertor.that(2).isGTE(1, "error1").isOK());
        assertTrue(Assertor.that(2).isGTE(2, "error1").isOK());
        assertFalse(Assertor.that(2).isGTE(3, "error1").isOK());
        assertTrue(Assertor.that(2).isGTE(null, "error1").isOK());
        assertFalse(Assertor.that((Integer) null).isGTE(1, "error1").isOK());

        assertEquals("error1", Assertor.that(2).isGTE(3, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isGTE(3, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isGTE(3, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLT}.
     */
    @Test
    public void testIsLT() {
        assertTrue(Assertor.that(1).isLT(2).isOK());
        assertFalse(Assertor.that(2).isLT(2).isOK());
        assertFalse(Assertor.that(1).isLT(0).isOK());
        assertFalse(Assertor.that(2).isLT(null).isOK());
        assertTrue(Assertor.that((Integer) null).isLT(1).isOK());

        assertTrue(Assertor.that(1).isLT(2, "error1").isOK());
        assertFalse(Assertor.that(2).isLT(2, "error1").isOK());
        assertFalse(Assertor.that(1).isLT(0, "error1").isOK());
        assertFalse(Assertor.that(2).isLT(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isLT(1, "error1").isOK());
        assertEquals("error1", Assertor.that(2).isLT(1, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isLT(1, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isLT(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isLTE}.
     */
    @Test
    public void testIsLTE() {
        assertTrue(Assertor.that(1).isLTE(2).isOK());
        assertTrue(Assertor.that(2).isLTE(2).isOK());
        assertFalse(Assertor.that(1).isLTE(0).isOK());
        assertFalse(Assertor.that(2).isLTE(null).isOK());
        assertTrue(Assertor.that((Integer) null).isLTE(1).isOK());

        assertTrue(Assertor.that(1).isLTE(2, "error1").isOK());
        assertTrue(Assertor.that(2).isLTE(2, "error1").isOK());
        assertFalse(Assertor.that(1).isLTE(0, "error1").isOK());
        assertFalse(Assertor.that(2).isLTE(null, "error1").isOK());
        assertTrue(Assertor.that((Integer) null).isLTE(1, "error1").isOK());
        assertEquals("error1", Assertor.that(2).isLTE(1, "error1").getErrors().get());

        assertException(() -> {
            Assertor.that(2).isLTE(1, "error1 %1$s* %s", 0).orElseThrow();
        }, IllegalArgumentException.class, "error1 2 0");

        assertException(() -> {
            Assertor.that(2).isLTE(1, "error1").orElseThrow("error2");
        }, IllegalArgumentException.class, "error2");
    }

    /**
     * Test method for {@link AssertorNumber#isBetween}.
     */
    @Test
    public void testIsBetween() {
        Assertor.that(2).isBetween(1, 3).orElseThrow(JUNIT_THROWABLE);

        // precondition
        assertFalse(Assertor.that(2).isBetween(3, 3).isOK());
        assertFalse(Assertor.that(2).isBetween(null, 3).isOK());

        PredicateStepCharSequence<String> predicate = Assertor.<String> ofCharSequence().isNotBlank().and().isEqualIgnoreCase("true");

        assertTrue(Assertor.that(Arrays.asList("test")).containsAny(Arrays.asList("test")).isOK());

        System.out.println(Assertor.that(true).isFalse().getErrors().get());

        List<String> objects = Arrays.asList("a", "b");
        assertThat(CollectionUtils2.toArray(objects), Matchers.arrayContaining(objects.get(0), objects.get(1)));

        Assertor.that(CollectionUtils2.toArray(objects)).containsAll(new String[] {objects.get(0), objects.get(1)}).isOK();

        try {
            assertThat(false, Matchers.is(true));
        } catch (AssertionError e) {
            System.out.println(e.getLocalizedMessage());
        }

        assertTrue(predicate.that("true").isOK());
        assertFalse(predicate.that("false").isOK());
        assertTrue(Assertor.that("true").isNotBlank().andBoolean(Boolean::parseBoolean).isTrue().isOK());

        // check
        assertFalse(Assertor.that(0).isBetween(1, 3).isOK());
        assertFalse(Assertor.that(3).isBetween(1, 3).isOK());

        assertException(() -> {
            Assertor.that(2_111.2).isBetween(1_111.1, 2_111.2, Locale.US, "%1$,.3f* is not between %2$,.3f* and %3$,.3f*").orElseThrow();
        }, IllegalArgumentException.class, "2,111.200 is not between 1,111.100 and 2,111.200");
    }
}
