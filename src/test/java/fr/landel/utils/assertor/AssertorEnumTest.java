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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.utils.AssertorEnum;
import fr.landel.utils.commons.EnumChar;

/**
 * Check {@link AssertorEnum}
 *
 * @since Jul 18, 2016
 * @author Gilles
 *
 */
public class AssertorEnumTest extends AbstractTest {

    /**
     * Test method for {@link AssertorEnum#AssertorEnum()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorEnum());
    }

    /**
     * Test method for {@link AssertorEnum} .
     */
    @Test
    public void testValues() {
        Assertor.that(EnumType.values())
                .containsAll(new EnumType[] {EnumType.BOOLEAN, EnumType.NUMBER_INTEGER, EnumType.NUMBER_DECIMAL, EnumType.ARRAY,
                        EnumType.ENUMERATION, EnumType.ITERABLE, EnumType.MAP, EnumType.DATE, EnumType.CHAR_SEQUENCE, EnumType.CLASS,
                        EnumType.CHARACTER, EnumType.UNKNOWN})
                .orElseThrow();

        assertTrue(Assertor.that(EnumType.BOOLEAN).hasName("BOOLEAN").isOK());
    }

    /**
     * Test method for {@link AssertorEnum#hasName} .
     */
    @Test
    public void testHasName() {
        StepAssertor<EnumChar> assertorResult = new StepAssertor<>(EnumChar.ASTERISK, EnumType.ENUMERATION, null);
        assertTrue(AssertorEnum.hasName(assertorResult, "ASTERISK", null).getChecker().test(EnumChar.ASTERISK, false));

        try {
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").orElseThrow("not found");
            Assertor.that(EnumChar.ASTERISK).not().hasName("ASTERIS").orElseThrow("not found");
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").orElseThrow(new IllegalArgumentException(), true);
            Assertor.that(EnumChar.ASTERISK).hasNameIgnoreCase("asTerisK").orElseThrow("not found");
            Assertor.that(EnumChar.ASTERISK).not().hasNameIgnoreCase("asTeris").orElseThrow();

            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").and(EnumOperator.AND).hasName("AND").orElseThrow();
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").or(EnumOperator.AND).hasName("AND").orElseThrow();
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").xor(EnumOperator.AND).hasName("XOR").orElseThrow();
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERIS").nand(EnumOperator.AND).hasName("XOR").orElseThrow();
            Assertor.that(EnumChar.ASTERISK).hasName("ASTERISK").nor(EnumOperator.AND).hasName("XOR").orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(EnumChar.ASTERISK).hasName("asterisk").orElseThrow("not found");
            fail();
        }, IllegalArgumentException.class, "not found");

        assertException(() -> {
            Assertor.that(EnumChar.ASTERISK).hasName("asterisk", "%s, '%s*'", "not found").orElseThrow();
            fail();

            // to string = unicode character
        }, IllegalArgumentException.class, "not found, '*'");

        assertException(() -> {
            Assertor.that(EnumChar.ASTERISK).hasName("asterisk").orElseThrow(new IOException("not found"), true);
            fail();
        }, IOException.class, "not found");

        assertException(() -> {
            Assertor.that((EnumChar) null).hasName("asterisk").orElseThrow(new IOException("not found"), true);
            fail();
        }, IOException.class, "not found");

        assertException(() -> {
            Assertor.that(EnumChar.ASTERISK).hasName("").orElseThrow(new IOException("not found"), true);
            fail();
        }, IOException.class, "not found");
    }

    /**
     * Test method for {@link AssertorEnum#hasOrdinal} .
     */
    @Test
    public void testHasOrdinal() {
        try {
            assertTrue(Assertor.that(EnumOperator.OR).hasOrdinal(1).isOK());

            Assertor.that(EnumOperator.OR).isNotNull().orElseThrow();

            Assertor.that(EnumOperator.OR).hasOrdinal(1).orElseThrow();
            Assertor.that(EnumOperator.OR).hasOrdinal(1).and(false).not().isTrue().orElseThrow("not true");
            Assertor.that(EnumOperator.OR).hasOrdinal(1).orElseThrow(new IllegalArgumentException(), true);

            Assertor.that(EnumOperator.OR).hasOrdinal(1).and().not().hasName("xor").orElseThrow();
            Assertor.that(EnumOperator.OR).hasOrdinal(1).or().hasName("xor").orElseThrow();
            Assertor.that(EnumOperator.OR).hasOrdinal(1).xor().hasName("xor").orElseThrow();
            Assertor.that(EnumOperator.OR).hasOrdinal(2).nand().hasName("xor").orElseThrow();
            Assertor.that(EnumOperator.OR).hasOrdinal(1).nor().hasName("xor").orElseThrow();

            Assertor.that(EnumOperator.OR).hasOrdinal(1).and(Assertor.that("").isBlank().or().isEqual("r")).orElseThrow();
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.that(EnumOperator.OR).hasOrdinal(100).orElseThrow("not correct");
            fail();
        }, IllegalArgumentException.class, "not correct");

        assertException(() -> {
            Assertor.that(EnumOperator.OR).hasOrdinal(0).orElseThrow("not correct");
            fail();
        }, IllegalArgumentException.class, "not correct");

        assertException(() -> {
            Assertor.that(EnumOperator.OR).hasOrdinal(0, "%s, '%s*'", "not correct").orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not correct, ' OR '");

        assertException(() -> {
            Assertor.that((EnumOperator) null).hasOrdinal(0).orElseThrow(new IOException("not correct"), true);
            fail();
        }, IOException.class, "not correct");

        assertException(() -> {
            Assertor.that(EnumOperator.OR).hasOrdinal(-1).orElseThrow(new IOException("not correct"), true);
            fail();
        }, IOException.class, "not correct");
    }
}
