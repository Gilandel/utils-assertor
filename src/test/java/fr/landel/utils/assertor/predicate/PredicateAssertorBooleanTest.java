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
package fr.landel.utils.assertor.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.utils.AssertorBoolean;

/**
 * Check {@link AssertorBoolean}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class PredicateAssertorBooleanTest extends AbstractTest {

    /**
     * Test method for {@link AssertorBoolean} .
     */
    @Test
    public void testPredicateGet() {
        assertFalse(Assertor.ofBoolean().hasHashCode(0).that(true).isOK());
        assertTrue(Assertor.ofBoolean().hasHashCode(Objects.hashCode(true)).that(true).isOK());
    }

    /**
     * Test method for {@link AssertorBoolean#isFalse()} .
     */
    @Test
    public void testIsFalse() {
        StepAssertor<Boolean> assertorResult = new StepAssertor<>(true, EnumType.BOOLEAN, null);
        assertFalse(AssertorBoolean.isFalse(assertorResult, null).getChecker().test(true, false));

        assertTrue(Assertor.ofBoolean().isFalse().or(Assertor.ofBoolean().isTrue()).that(true).isOK());

        try {
            Assertor.ofBoolean().isFalse().that(false).orElseThrow("not false");
            Assertor.ofBoolean().not().isTrue().that(false).orElseThrow("not false");
            Assertor.ofBoolean().isTrue().that(true).orElseThrow(new IllegalArgumentException(), true);
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofBoolean().isFalse().that(true).orElseThrow("not false");
            fail();
        }, IllegalArgumentException.class, "not false");

        assertException(() -> {
            Assertor.ofBoolean().isFalse("%s, '%s*'", "not false").that(true).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not false, 'true'");

        assertException(() -> {
            Assertor.ofBoolean().isFalse().that(true).orElseThrow(new IOException("not false"), true);
            fail();
        }, IOException.class, "not false");
    }

    /**
     * Test method for {@link AssertorBoolean#isTrue()} .
     */
    @Test
    public void testIsTrue() {
        try {
            assertTrue(Assertor.ofBoolean().isTrue().that(true).orElseThrow());

            assertFalse(Assertor.ofBoolean().isTrue().or().not().isTrue().that(false).orElseThrow("not true"));
            assertTrue(Assertor.ofBoolean().isTrue().that(true).orElseThrow(new IllegalArgumentException(), true));
            assertTrue(Assertor.ofBoolean().isTrue().and().not().isFalse().that(true).orElseThrow());

            assertTrue(Assertor.ofBoolean().isTrue().and().not().isFalse().that(true).orElseThrow());
            assertTrue(Assertor.ofBoolean().isTrue().or().isFalse().that(true).orElseThrow());
            assertTrue(Assertor.ofBoolean().isTrue().xor().isFalse().that(true).orElseThrow());
            assertFalse(Assertor.ofBoolean().isTrue().nand().isFalse().that(true).isOK());
            assertTrue(Assertor.ofBoolean().isTrue().nor().isFalse().that(true).orElseThrow());
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.ofBoolean().isTrue().that(false).orElseThrow("not true");
            fail();
        }, IllegalArgumentException.class, "not true");

        assertException(() -> {
            Assertor.ofBoolean().isTrue("%s, '%s*'", "not true").that(false).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not true, 'false'");

        assertException(() -> {
            Assertor.ofBoolean().isTrue().that(false).orElseThrow(new IOException("not true"), true);
            fail();
        }, IOException.class, "not true");
    }
}
