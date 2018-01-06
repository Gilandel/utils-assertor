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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
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
public class PredicateAssertorEnumTest extends AbstractTest {

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
            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").that(EnumChar.ASTERISK).orElseThrow("not found");
            Assertor.<EnumChar> ofEnum().not().hasName("ASTERIS").that(EnumChar.ASTERISK).orElseThrow("not found");
            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").that(EnumChar.ASTERISK).orElseThrow(new IllegalArgumentException(), true);
            Assertor.<EnumChar> ofEnum().hasNameIgnoreCase("asTerisK").that(EnumChar.ASTERISK).orElseThrow("not found");
            Assertor.<EnumChar> ofEnum().not().hasNameIgnoreCase("asTeris").that(EnumChar.ASTERISK).orElseThrow();

            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").and(Assertor.<EnumChar> ofEnum().hasOrdinal(EnumChar.ASTERISK.ordinal()))
                    .that(EnumChar.ASTERISK).orElseThrow(JUNIT_THROWABLE);
            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").or(Assertor.<EnumChar> ofEnum().hasOrdinal(EnumChar.ASTERISK.ordinal()))
                    .that(EnumChar.ASTERISK).orElseThrow(JUNIT_THROWABLE);
            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").xor(Assertor.<EnumChar> ofEnum().hasOrdinal(EnumChar.ACK.ordinal()))
                    .that(EnumChar.ASTERISK).orElseThrow(JUNIT_THROWABLE);
            Assertor.<EnumChar> ofEnum().hasName("ASTERIS").nand(Assertor.<EnumChar> ofEnum().hasOrdinal(EnumChar.ACK.ordinal()))
                    .that(EnumChar.ASTERISK).orElseThrow(JUNIT_THROWABLE);
            Assertor.<EnumChar> ofEnum().hasName("ASTERISK").nor(Assertor.<EnumChar> ofEnum().hasOrdinal(EnumChar.ACK.ordinal()))
                    .that(EnumChar.ASTERISK).orElseThrow(JUNIT_THROWABLE);
        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.<EnumChar> ofEnum().hasName("asterisk").that(EnumChar.ASTERISK).orElseThrow("not found");
            fail();
        }, IllegalArgumentException.class, "not found");

        assertException(() -> {
            Assertor.<EnumChar> ofEnum().hasName("asterisk", "%s, '%s*'", "not found").that(EnumChar.ASTERISK).orElseThrow();
            fail();

            // to string = unicode character
        }, IllegalArgumentException.class, "not found, '*'");

        assertException(() -> {
            Assertor.<EnumChar> ofEnum().hasName("asterisk").that(EnumChar.ASTERISK).orElseThrow(new IOException("not found"), true);
            fail();
        }, IOException.class, "not found");

        assertException(() -> {
            Assertor.<EnumChar> ofEnum().hasName("asterisk").that((EnumChar) null).orElseThrow(new IOException("not found"), true);
            fail();
        }, IOException.class, "not found");

        assertException(() -> {
            Assertor.<EnumChar> ofEnum().hasName("").that(EnumChar.ASTERISK).orElseThrow(new IOException("not found"), true);
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

            Assertor.<EnumOperator> ofEnum().isNotNull().that(EnumOperator.OR).orElseThrow();

            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).that(EnumOperator.OR).orElseThrow();
            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).and().not().hasHashCode(0).that(EnumOperator.OR).orElseThrow("not true");
            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).that(EnumOperator.OR).orElseThrow(new IllegalArgumentException(), true);

            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).and().not().hasName("xor").that(EnumOperator.OR).orElseThrow();
            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).or().hasName("xor").that(EnumOperator.OR).orElseThrow();
            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).xor().hasName("xor").that(EnumOperator.OR).orElseThrow();
            Assertor.<EnumOperator> ofEnum().hasOrdinal(2).nand().hasName("xor").that(EnumOperator.OR).orElseThrow();
            Assertor.<EnumOperator> ofEnum().hasOrdinal(1).nor().hasName("xor").that(EnumOperator.OR).orElseThrow();

        } catch (IllegalArgumentException e) {
            fail("The test isn't correct");
        }

        assertException(() -> {
            Assertor.<EnumOperator> ofEnum().hasOrdinal(100).that(EnumOperator.OR).orElseThrow("not correct");
            fail();
        }, IllegalArgumentException.class, "not correct");

        assertException(() -> {
            Assertor.<EnumOperator> ofEnum().hasOrdinal(0).that(EnumOperator.OR).orElseThrow("not correct");
            fail();
        }, IllegalArgumentException.class, "not correct");

        assertException(() -> {
            Assertor.<EnumOperator> ofEnum().hasOrdinal(0, "%s, '%s*'", "not correct").that(EnumOperator.OR).orElseThrow();
            fail();
        }, IllegalArgumentException.class, "not correct, ' OR '");

        assertException(() -> {
            Assertor.<EnumOperator> ofEnum().hasOrdinal(0).that((EnumOperator) null).orElseThrow(new IOException("not correct"), true);
            fail();
        }, IOException.class, "not correct");

        assertException(() -> {
            Assertor.<EnumOperator> ofEnum().hasOrdinal(-1).that(EnumOperator.OR).orElseThrow(new IOException("not correct"), true);
            fail();
        }, IOException.class, "not correct");
    }
}
