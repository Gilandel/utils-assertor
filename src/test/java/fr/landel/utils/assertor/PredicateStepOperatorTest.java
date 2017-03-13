/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.assertor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

import fr.landel.utils.commons.DateUtils;

/**
 * Test operator class
 *
 * @since Jul 17, 2016
 * @author Gilles
 *
 */
public class PredicateStepOperatorTest extends AbstractTest {

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

        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyList()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and(Collections.emptyMap()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isNotEmpty().and((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isNotEmpty().and((Object) 0).isNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNull().and().isEqual(Color.black).isOK());
        assertFalse(Assertor.that(Color.BLACK).isNull().and((Object) 0).isNotNull().isOK());
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

        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyList()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().or(Collections.emptyMap()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().or((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().or((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().or().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().or((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().or(Assertor.that(text).isEmpty()).isOK());
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

        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyList()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyList()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor(Collections.emptyMap()).isEmpty().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor(Collections.emptyMap()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().xor((Object) 0).isNotNull().isOK());
        assertFalse(Assertor.that(text).isEmpty().xor((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().xor().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().xor((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().xor(Assertor.that(text).isEmpty()).isOK());
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

        assertFalse(Assertor.that(text).isEmpty().nand(2).isGT(1).isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(2).isLT(1).isOK());

        assertFalse(Assertor.that(text).isEmpty().nand("tt").isNotEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand("tt").isEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(new String[] {}).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(new String[] {}).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyList()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyList()).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand(Collections.emptyMap()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand(Collections.emptyMap()).isNotEmpty().isOK());

        assertFalse(Assertor.that(text).isEmpty().nand((Object) 0).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nand((Object) 0).isNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNull().nand().isEqual(Color.black).isOK());
        assertFalse(Assertor.that(Color.BLACK).isNull().nand((Object) 0).isNotNull().isOK());

        assertFalse(Assertor.that(Color.BLACK).isNotNull().nand(Assertor.that(text).isEmpty()).isOK());
    }

    /**
     * Test method for {@link Operator#nor()}.
     */
    @Test
    public void testNor() {
        final String text = "text";
        assertTrue(Assertor.that(text).isEmpty().nor().isNotBlank().isOK());

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

        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyList()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap()).isEmpty().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor(Collections.emptyMap()).isNotEmpty().isOK());

        assertTrue(Assertor.that(text).isEmpty().nor((Object) 0).isNotNull().isOK());
        assertTrue(Assertor.that(text).isEmpty().nor((Object) 0).isNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNull().nor().isEqual(Color.black).isOK());
        assertTrue(Assertor.that(Color.BLACK).isNull().nor((Object) 0).isNotNull().isOK());

        assertTrue(Assertor.that(Color.BLACK).isNotNull().nor(Assertor.that(text).isEmpty()).isOK());
    }
}
