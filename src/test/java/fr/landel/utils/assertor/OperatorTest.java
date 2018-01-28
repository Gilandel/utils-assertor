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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.commons.DateUtils;
import fr.landel.utils.commons.MapUtils2;

/**
 * Test operator class
 *
 * @since Jul 17, 2016
 * @author Gilles
 *
 */
public class OperatorTest extends AbstractTest {

    /**
     * Test method for {@link Operator#and()}.
     */
    @Test
    public void testAnd() {
        final String text = "text";
        assertTrue(Assertor.that(text).isNotEmpty().and().isNotBlank().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(true).isTrue().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(true).isFalse().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(text.getClass()).isAssignableFrom(CharSequence.class).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(text.getClass()).isAssignableFrom(StringBuilder.class).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(Calendar.getInstance()).isAfter(DateUtils.getCalendar(new Date(0))).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Calendar.getInstance()).isBefore(DateUtils.getCalendar(new Date(0))).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(new Date()).isAfter(new Date(0)).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(new Date()).isBefore(new Date(0)).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(LocalDateTime.now()).isAfter(LocalDateTime.MIN).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(LocalDateTime.now()).isAfter(LocalDateTime.MAX).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(2).isGT(1).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(2).isLT(1).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and("tt").isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and("tt").isEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(new String[] {}).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(new String[] {}).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(new String[] {}, EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(new String[] {}, EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(new String[] {}, EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(new String[] {}, EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyList()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyList(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyList(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyMap()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyMap(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyMap(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(new Exception()).isNotNull().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(new Exception()).isNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNull().and().isEqual(Color.black).isOK());
        assertFalse(Assertor.that(Color.BLACK).isNull().and((Object) 0).isNotNull().isOK());

        // SUB

        assertTrue(Assertor.that(true).isTrue().and(Assertor.that("text").isEmpty().or().contains("e")).isOK());
        // left part error
        assertEquals("the boolean should be false",
                Assertor.that(true).isFalse().and(Assertor.that("text").isEmpty().or().contains("e")).getErrors().get());
        // right part error
        assertEquals("(the char sequence 'text' should contain 's')",
                Assertor.that(true).isTrue().and(Assertor.that("text").contains("s")).getErrors().get());
        assertFalse(Assertor.that(true).isTrue().and(Assertor.that("text").contains("s")).isOK());
        assertTrue(Assertor.that(true).isTrue().or(Assertor.that("text").contains("s")).isOK());
        assertFalse(Assertor.that(true).isTrue().or(Assertor.that("text").contains("s")).getErrors().isPresent());
        // both parts error
        assertEquals("the boolean should be false",
                Assertor.that(true).isFalse().and(Assertor.that("text").isEmpty().or().contains("s")).getErrors().get());
        assertEquals("the combination 'true' and ' NAND ' is invalid",
                Assertor.that(true).isTrue().nand(Assertor.that("text").isEmpty().or().contains("s")).getErrors().get());
        assertEquals("the boolean should be false OR (the char sequence 'text' should contain 's')",
                Assertor.that(true).isFalse().or(Assertor.that("text").isNotEmpty().and().contains("s")).getErrors().get());
        // precondition error
        assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty",
                Assertor.that(true).isTrue().and(Assertor.that("text").contains((String) null)).getErrors().get());

        // SUB ASSERTOR

        assertTrue(Assertor.that(text).isNotEmpty().andAssertor(t -> Assertor.that(t.length()).isGT(3)).isOK());
        // left part error
        assertEquals("the char sequence 'text' should be null or empty",
                Assertor.that(text).isEmpty().andAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());
        // right part error
        assertEquals("(the number '4' should be greater than '4')",
                Assertor.that(text).isNotEmpty().andAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());
        // precondition error
        assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty",
                Assertor.that(text).isNotEmpty().andAssertor(t -> Assertor.that(t.substring(0)).contains((String) null)).getErrors().get());
        // null
        assertFalse(Assertor.that((String) null).isEmpty().andAssertor(t -> {
            if (t != null) {
                return Assertor.that(t.substring(1)).contains("e");
            } else {
                return Assertor.that((String) null).isNull();
            }
        }).getErrors().isPresent());

        assertException(() -> Assertor.that(text).isNotEmpty().andAssertor((Function<String, StepCharSequence<String>>) null).isOK(),
                IllegalStateException.class, "sub assertor cannot be null");

        // MAPPER

        assertTrue(Assertor.that(true).isTrue().andObject(b -> b.toString()).hasHashCode(Objects.hashCode("true")).isOK());
        assertTrue(Assertor.that(true).isTrue().andCharSequence(b -> b.toString()).contains("ue").isOK());
        assertTrue(Assertor.that("test").isNotEmpty().andArray(s -> ArrayUtils.toObject(s.getBytes(StandardCharsets.UTF_8)))
                .contains((byte) 'e').isOK());
        assertTrue(Assertor.that(true).isTrue().andBoolean(b -> !b).isFalse().isOK());
        assertTrue(Assertor.that(true).isTrue().andClass(b -> b.getClass()).hasSimpleName("Boolean").isOK());
        assertTrue(Assertor.that(true).isTrue().andDate(b -> new Date(1464475553641L)).isAfter(new Date(1464475553640L)).isOK());
        assertTrue(Assertor.that(true).isTrue().andCalendar(b -> DateUtils.getCalendar(new Date(1464475553641L)))
                .isBefore(Calendar.getInstance()).isOK());
        assertTrue(Assertor.that(true).isTrue().andTemporal(b -> DateUtils.getLocalDateTime(new Date(1464475553641L)))
                .isBefore(LocalDateTime.now()).isOK());
        assertTrue(Assertor.that(true).isTrue().andEnum(b -> EnumOperator.AND).hasName("AND").isOK());
        assertTrue(Assertor.that(true).isTrue().andIterable(b -> Arrays.asList('t', 'r')).contains('t').isOK());
        assertTrue(Assertor.that(true).isTrue().andMap(b -> MapUtils2.newHashMap("key", b)).contains("key", true).isOK());
        assertTrue(Assertor.that(true).isTrue().andNumber(b -> b.hashCode()).isGT(0).isOK()); // 1231
        assertTrue(Assertor.that(true).isTrue().andThrowable(b -> new IOException(b.toString()))
                .validates(e -> e.getMessage().contains("true")).isOK());
    }

    /**
     * Test method for {@link Operator#or()}.
     */
    @Test
    public void testOr() {
        final String text = "text";
        assertTrue(Assertor.that(text).isEmpty().or().isNotBlank().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(true).isTrue().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(true).isFalse().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(text.getClass()).isAssignableFrom(CharSequence.class).isOK());
        assertFalse(Assertor.that(text).isEmpty().or(text.getClass()).isAssignableFrom(StringBuilder.class).isOK());

        assertTrue(Assertor.that(text).isEmpty().or(Calendar.getInstance()).isAfter(DateUtils.getCalendar(new Date(0))).isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Calendar.getInstance()).isBefore(DateUtils.getCalendar(new Date(0))).isOK());

        assertTrue(Assertor.that(text).isEmpty().or(new Date()).isAfter(new Date(0)).isOK());
        assertFalse(Assertor.that(text).isEmpty().or(new Date()).isBefore(new Date(0)).isOK());

        assertTrue(Assertor.that(text).isEmpty().or(LocalDateTime.now()).isAfter(LocalDateTime.MIN).isOK());
        assertFalse(Assertor.that(text).isEmpty().or(LocalDateTime.now()).isAfter(LocalDateTime.MAX).isOK());

        assertTrue(Assertor.that(text).isEmpty().or(2).isGT(1).isOK());
        assertFalse(Assertor.that(text).isEmpty().or(2).isLT(1).isOK());

        assertTrue(Assertor.that(text).isEmpty().or("tt").isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or("tt").isEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(new String[] {}).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(new String[] {}).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(new String[] {}, EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(new String[] {}, EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(new String[] {}, EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(new String[] {}, EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyList()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyList(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyList(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyMap()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyMap(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyMap(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().or((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(new Exception()).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(new Exception()).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().or().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().or((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().or(Assertor.that(text).isEmpty()).isOK());

        assertTrue(Assertor.that(true).isFalse().or(Assertor.that("text").startsWith("t").and().contains("e")).isOK());

        // SUB ASSERTOR

        assertTrue(Assertor.that(text).isNotEmpty().orAssertor(t -> Assertor.that(t.length()).isGT(4)).isOK());
        assertEquals("the char sequence 'text' should be null or empty OR (the number '4' should be greater than '4')",
                Assertor.that(text).isEmpty().orAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());

        assertException(() -> Assertor.that(text).isNotEmpty().orAssertor((Function<String, StepCharSequence<String>>) null).isOK(),
                IllegalStateException.class, "sub assertor cannot be null");

        // MAPPER

        assertTrue(Assertor.that(true).isTrue().orObject(b -> b.toString()).hasHashCode(Objects.hashCode("true")).isOK());
        assertTrue(Assertor.that(true).isTrue().orCharSequence(b -> b.toString()).contains("ue").isOK());
        assertTrue(Assertor.that("test").isNotEmpty().orArray(s -> ArrayUtils.toObject(s.getBytes(StandardCharsets.UTF_8)))
                .contains((byte) 'e').isOK());
        assertTrue(Assertor.that(true).isTrue().orBoolean(b -> !b).isFalse().isOK());
        assertTrue(Assertor.that(true).isTrue().orClass(b -> b.getClass()).hasSimpleName("Boolean").isOK());
        assertTrue(Assertor.that(true).isTrue().orDate(b -> new Date(1464475553641L)).isAfter(new Date(1464475553640L)).isOK());
        assertTrue(Assertor.that(true).isTrue().orCalendar(b -> DateUtils.getCalendar(new Date(1464475553641L)))
                .isBefore(Calendar.getInstance()).isOK());
        assertTrue(Assertor.that(true).isTrue().orTemporal(b -> DateUtils.getLocalDateTime(new Date(1464475553641L)))
                .isBefore(LocalDateTime.now()).isOK());
        assertTrue(Assertor.that(true).isTrue().orEnum(b -> EnumOperator.AND).hasName("AND").isOK());
        assertTrue(Assertor.that(true).isTrue().orIterable(b -> Arrays.asList('t', 'r')).contains('t').isOK());
        assertTrue(Assertor.that(true).isTrue().orMap(b -> MapUtils2.newHashMap("key", b)).contains("key", true).isOK());
        assertTrue(Assertor.that(true).isTrue().orNumber(b -> b.hashCode()).isGT(0).isOK()); // 1231
        assertTrue(Assertor.that(true).isTrue().orThrowable(b -> new IOException(b.toString()))
                .validates(e -> e.getMessage().contains("true")).isOK());
    }

    /**
     * Test method for {@link Operator#xor()}.
     */
    @Test
    public void testXor() {
        final String text = "text";
        assertTrue(Assertor.that(text).isEmpty().xor().isNotBlank().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(true).isTrue().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(true).isFalse().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(text.getClass()).isAssignableFrom(CharSequence.class).isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(text.getClass()).isAssignableFrom(StringBuilder.class).isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(Calendar.getInstance()).isAfter(DateUtils.getCalendar(new Date(0))).isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Calendar.getInstance()).isBefore(DateUtils.getCalendar(new Date(0))).isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(new Date()).isAfter(new Date(0)).isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(new Date()).isBefore(new Date(0)).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().xor(LocalDateTime.now()).isAfter(LocalDateTime.MAX).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().xor(LocalDateTime.now()).isAfter(LocalDateTime.MIN).isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(2).isGT(1).isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(2).isLT(1).isOK());

        assertTrue(Assertor.that(text).isEmpty().xor("tt").isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor("tt").isEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(new String[] {}).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(new String[] {}).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(new String[] {}, EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(new String[] {}, EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(new String[] {}, EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(new String[] {}, EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyList()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyList(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyList(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyMap()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyMap(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyMap(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(new Exception()).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(new Exception()).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().xor().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().xor((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().xor(Assertor.that(text).isEmpty()).isOK());

        // SUB ASSERTOR

        assertTrue(Assertor.that(text).isNotEmpty().xorAssertor(t -> Assertor.that(t.length()).isGT(4)).isOK());
        assertEquals("the char sequence 'text' should be null or empty XOR (the number '4' should be greater than '4')",
                Assertor.that(text).isEmpty().xorAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());

        assertException(() -> Assertor.that(text).isNotEmpty().xorAssertor((Function<String, StepCharSequence<String>>) null).isOK(),
                IllegalStateException.class, "sub assertor cannot be null");

        // MAPPER

        assertTrue(Assertor.that(false).isTrue().xorObject(b -> b.toString()).hasHashCode(Objects.hashCode("false")).isOK());
        assertTrue(Assertor.that(false).isTrue().xorCharSequence(b -> b.toString()).contains("se").isOK());
        assertTrue(Assertor.that("test").isNotEmpty().xorArray(s -> ArrayUtils.toObject(s.getBytes(StandardCharsets.UTF_8)))
                .contains((byte) 'x').isOK());
        assertTrue(Assertor.that(false).isTrue().xorBoolean(b -> b).isFalse().isOK());
        assertTrue(Assertor.that(false).isTrue().xorClass(b -> b.getClass()).hasSimpleName("Boolean").isOK());
        assertTrue(Assertor.that(false).isTrue().xorDate(b -> new Date(1464475553641L)).isAfter(new Date(1464475553640L)).isOK());
        assertTrue(Assertor.that(false).isTrue().xorCalendar(b -> DateUtils.getCalendar(new Date(1464475553641L)))
                .isBefore(Calendar.getInstance()).isOK());
        assertTrue(Assertor.that(false).isTrue().xorTemporal(b -> DateUtils.getLocalDateTime(new Date(1464475553641L)))
                .isBefore(LocalDateTime.now()).isOK());
        assertTrue(Assertor.that(false).isTrue().xorEnum(b -> EnumOperator.AND).hasName("AND").isOK());
        assertTrue(Assertor.that(false).isTrue().xorIterable(b -> Arrays.asList('t', 'r')).contains('t').isOK());
        assertTrue(Assertor.that(false).isTrue().xorMap(b -> MapUtils2.newHashMap("key", b)).contains("key", false).isOK());
        assertTrue(Assertor.that(false).isTrue().xorNumber(b -> b.hashCode()).isGT(0).isOK()); // 1231
        assertTrue(Assertor.that(false).isTrue().xorThrowable(b -> new IOException(b.toString()))
                .validates(e -> e.getMessage().contains("false")).isOK());
    }

    /**
     * Test method for {@link Operator#nand()}.
     */
    @Test
    public void testNand() {
        final String text = "text";
        assertFalse(Assertor.that(text).isEmpty().nand().isNotBlank().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(true).isTrue().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(true).isFalse().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(text.getClass()).isAssignableFrom(CharSequence.class).isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(text.getClass()).isAssignableFrom(StringBuilder.class).isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(Calendar.getInstance()).isAfter(DateUtils.getCalendar(new Date(0))).isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Calendar.getInstance()).isBefore(DateUtils.getCalendar(new Date(0))).isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(new Date()).isAfter(new Date(0)).isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new Date()).isBefore(new Date(0)).isOK());

        assertFalse(Assertor.that(text).isNotEmpty().nand(LocalDateTime.now()).isAfter(LocalDateTime.MAX).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().nand(LocalDateTime.now()).isAfter(LocalDateTime.MIN).isOK());

        assertFalse(Assertor.that(text).isNotEmpty().nandNumber(t -> t.length()).isGT(Integer.MAX_VALUE).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().nandNumber(t -> t.length()).isGT(Integer.MIN_VALUE).isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(2).isGT(1).isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(2).isLT(1).isOK());

        assertFalse(Assertor.that(text).isEmpty().nand("tt").isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand("tt").isEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(new String[] {}).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new String[] {}).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(new String[] {}, EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new String[] {}, EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(new String[] {}, EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new String[] {}, EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyList()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyList()).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyList(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyList(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyMap()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyMap()).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyMap(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyMap(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand((Object) 0).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand((Object) 0).isNull().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(new Exception()).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new Exception()).isNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNull().nand().isEqual(Color.black).isOK());
        assertFalse(Assertor.that(Color.BLACK).isNull().nand((Object) 0).isNotNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNotNull().nand(Assertor.that(text).isEmpty()).isOK());
        assertEquals("the combination 'true' and ' NAND ' is invalid",
                Assertor.that(Color.BLACK).isNotNull().nand(Assertor.that(text).isEmpty()).getErrors().get());

        // SUB ASSERTOR

        assertTrue(Assertor.that(text).isEmpty().nandAssertor(t -> Assertor.that(t.length()).isGT(4)).isOK());
        assertEquals("the combination 'true' and ' NAND ' is invalid",
                Assertor.that(text).isNotEmpty().nandAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());

        assertException(() -> Assertor.that(text).isNotEmpty().nandAssertor((Function<String, StepCharSequence<String>>) null).isOK(),
                IllegalStateException.class, "sub assertor cannot be null");

        // MAPPER

        assertException(() -> Assertor.that(text).isNotEmpty().nandNumber((Function<String, Integer>) null).isGT(Integer.MAX_VALUE).isOK(),
                IllegalStateException.class, "property cannot be null");

        assertTrue(Assertor.that(false).isTrue().nandObject(b -> b.toString()).hasHashCode(0).isOK());
        assertTrue(Assertor.that(false).isTrue().nandCharSequence(b -> b.toString()).contains("ue").isOK());
        assertTrue(Assertor.that("test").isEmpty().nandArray(s -> ArrayUtils.toObject(s.getBytes(StandardCharsets.UTF_8)))
                .contains((byte) 'x').isOK());
        assertTrue(Assertor.that(false).isTrue().nandBoolean(b -> !b).isFalse().isOK());
        assertTrue(Assertor.that(false).isTrue().nandClass(b -> b.getClass()).hasSimpleName("Bool").isOK());
        assertTrue(Assertor.that(false).isTrue().nandDate(b -> new Date(1464475553641L)).isBefore(new Date(1464475553640L)).isOK());
        assertTrue(Assertor.that(false).isTrue().nandCalendar(b -> DateUtils.getCalendar(new Date(1464475553641L)))
                .isAfter(Calendar.getInstance()).isOK());
        assertTrue(Assertor.that(false).isTrue().nandTemporal(b -> DateUtils.getLocalDateTime(new Date(1464475553641L)))
                .isAfter(LocalDateTime.now()).isOK());
        assertTrue(Assertor.that(false).isTrue().nandEnum(b -> EnumOperator.OR).hasName("AND").isOK());
        assertTrue(Assertor.that(false).isTrue().nandIterable(b -> Arrays.asList('t', 'r')).contains('u').isOK());
        assertTrue(Assertor.that(false).isTrue().nandMap(b -> MapUtils2.newHashMap("key", b)).contains("key", true).isOK());
        assertTrue(Assertor.that(false).isTrue().nandNumber(b -> b.hashCode()).isGT(Integer.MAX_VALUE).isOK()); // 1231
        assertTrue(Assertor.that(false).isTrue().nandThrowable(b -> new IOException(b.toString()))
                .validates(e -> e.getMessage().contains("true")).isOK());
    }

    /**
     * Test method for {@link Operator#nor()}.
     */
    @Test
    public void testNor() {
        final String text = "text";
        assertTrue(Assertor.that(text).isEmpty().nor().isNotBlank().isOK());
        assertTrue(Assertor.that(text).isNotEmpty().nor().isBlank().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor().isBlank().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(true).isTrue().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(true).isFalse().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(text.getClass()).isAssignableFrom(CharSequence.class).isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(text.getClass()).isAssignableFrom(StringBuilder.class).isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(Calendar.getInstance()).isAfter(DateUtils.getCalendar(new Date(0))).isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Calendar.getInstance()).isBefore(DateUtils.getCalendar(new Date(0))).isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(new Date()).isAfter(new Date(0)).isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new Date()).isBefore(new Date(0)).isOK());

        assertTrue(Assertor.that(text).isNotEmpty().nor(LocalDateTime.now()).isAfter(LocalDateTime.MAX).isOK());
        assertFalse(Assertor.that(text).isNotEmpty().nor(LocalDateTime.now()).isAfter(LocalDateTime.MIN).isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(2).isGT(1).isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(2).isLT(1).isOK());

        assertTrue(Assertor.that(text).isEmpty().nor("tt").isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor("tt").isEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}, EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}, EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}, EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new String[] {}, EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap()).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap(), EnumAnalysisMode.STREAM).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap(), EnumAnalysisMode.STREAM).isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap(), EnumAnalysisMode.PARALLEL).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor((Object) 0).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(new Exception()).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(new Exception()).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().nor().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().nor((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().nor(Assertor.that(text).isEmpty()).isOK());

        // SUB ASSERTOR

        assertTrue(Assertor.that(text).isNotEmpty().norAssertor(t -> Assertor.that(t.length()).isGT(4)).isOK());
        assertEquals("the char sequence 'text' should be null or empty",
                Assertor.that(text).isEmpty().norAssertor(t -> Assertor.that(t.length()).isGT(4)).getErrors().get());

        assertException(() -> Assertor.that(text).isNotEmpty().norAssertor((Function<String, StepCharSequence<String>>) null).isOK(),
                IllegalStateException.class, "sub assertor cannot be null");

        // MAPPER

        assertTrue(Assertor.that(false).isTrue().norObject(b -> b.toString()).hasHashCode(Objects.hashCode("true")).isOK());
        assertTrue(Assertor.that(false).isTrue().norCharSequence(b -> b.toString()).contains("ue").isOK());
        assertTrue(Assertor.that("test").isNotEmpty().norArray(s -> ArrayUtils.toObject(s.getBytes(StandardCharsets.UTF_8)))
                .contains((byte) 'x').isOK());
        assertTrue(Assertor.that(false).isTrue().norBoolean(b -> !b).isFalse().isOK());
        assertTrue(Assertor.that(false).isTrue().norClass(b -> b.getClass()).hasSimpleName("Boolean").isOK());
        assertTrue(Assertor.that(false).isTrue().norDate(b -> new Date(1464475553641L)).isAfter(new Date(1464475553640L)).isOK());
        assertTrue(Assertor.that(false).isTrue().norCalendar(b -> DateUtils.getCalendar(new Date(1464475553641L)))
                .isBefore(Calendar.getInstance()).isOK());
        assertTrue(Assertor.that(false).isTrue().norTemporal(b -> DateUtils.getLocalDateTime(new Date(1464475553641L)))
                .isBefore(LocalDateTime.now()).isOK());
        assertTrue(Assertor.that(false).isTrue().norEnum(b -> EnumOperator.AND).hasName("AND").isOK());
        assertTrue(Assertor.that(false).isTrue().norIterable(b -> Arrays.asList('t', 'r')).contains('t').isOK());
        assertTrue(Assertor.that(false).isTrue().norMap(b -> MapUtils2.newHashMap("key", b)).contains("key", true).isOK());
        assertTrue(Assertor.that(false).isTrue().norNumber(b -> b.hashCode()).isGT(0).isOK()); // 1231
        assertTrue(Assertor.that(false).isTrue().norThrowable(b -> new IOException(b.toString()))
                .validates(e -> e.getMessage().contains("true")).isOK());
    }

    /**
     * Test combination.
     */
    @Test
    public void test() {
        // "true" and "OR" => true (collections preconditions are invalid, but
        // aren't checked because of previous check)
        try {
            Assertor.that(true).isTrue().or(Collections.emptyList()).contains("test").orElseThrow(JUNIT_THROWABLE);
        } catch (AssertionError e) {
            fail(e.getMessage());
        }
    }
}
