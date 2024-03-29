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
package fr.landel.utils.assertor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.utils.AssertorBoolean;

/**
 * Check {@link AssertorBoolean}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class AssertorBooleanTest extends AbstractTest {

    /**
     * Test method for {@link AssertorBoolean#AssertorBoolean()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorBoolean());
    }

    /**
     * Test method for {@link AssertorBoolean} .
     */
    @Test
    public void testPredicateGet() {
        assertFalse(Assertor.that(true).hasHashCode(0).isOK());
        assertTrue(Assertor.that(true).hasHashCode(Objects.hashCode(true)).isOK());
    }

    /**
     * Test method for {@link AssertorBoolean#isFalse()} .
     */
    @Test
    public void testIsFalse() {
        StepAssertor<Boolean> assertorResult = new StepAssertor<>(true, EnumType.BOOLEAN, null);
        assertFalse(AssertorBoolean.isFalse(assertorResult, null).getChecker().test(true, false));

        assertTrue(Assertor.that(true).isFalse().or(Assertor.that("").isEmpty()).isOK());

        try {
            Assertor.that(false).isFalse().orElseThrow("not false");
            Assertor.that(false).isFalse().and(true).not().isFalse().orElseThrow("not false");
            Assertor.that(false).isFalse().orElseThrow(new IllegalArgumentException(), true);
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(true).isFalse().orElseThrow("not false");
            fail();
        }, IllegalArgumentException.class, "not false");

        assertException(() -> {
            Assertor.that(true).isFalse("%s, '%s*'", "not false").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not false, 'true'");

        assertException(() -> {
            Assertor.that(true).isFalse().orElseThrow(new IOException("not false"), true);
            fail();
        }, IOException.class, "not false");
    }

    /**
     * Test method for {@link AssertorBoolean#isTrue()} .
     */
    @Test
    public void testIsTrue() {
        try {
            assertTrue(Assertor.that(true).isTrue().orElseThrow());

            // get last false
            assertFalse(Assertor.that(true).isTrue().and(false).not().isTrue().orElseThrow("not true"));
            assertTrue(Assertor.that(true).isTrue().orElseThrow(new IllegalArgumentException(), true));

            assertEquals("", Assertor.that(true).isTrue().and("").isEmpty().orElseThrow());

            assertTrue(Assertor.that(true).isTrue().and().not().isFalse().orElseThrow());
            assertTrue(Assertor.that(true).isTrue().or().isFalse().orElseThrow());
            assertTrue(Assertor.that(true).isTrue().xor().isFalse().orElseThrow());
            assertFalse(Assertor.that(true).isTrue().nand().isFalse().isOK());
            assertTrue(Assertor.that(true).isTrue().nor().isFalse().orElseThrow());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(false).isTrue().orElseThrow("not true");
            fail();
        }, IllegalArgumentException.class, "not true");

        assertException(() -> {
            Assertor.that(false).isTrue("%s, '%s*'", "not true").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not true, 'false'");

        assertException(() -> {
            Assertor.that(false).isTrue().orElseThrow(new IOException("not true"), true);
            fail();
        }, IOException.class, "not true");
    }
}
