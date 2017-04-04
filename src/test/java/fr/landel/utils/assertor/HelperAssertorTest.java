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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.Test;

/**
 * Check {@link HelperAssertor}
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class HelperAssertorTest extends AbstractTest {

    /**
     * Test method for {@link HelperAssertor#HelperAssertor()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new HelperAssertor());
    }

    /**
     * Test method for {@link HelperAssertor#combine} .
     */
    @Test
    public void testCombine() {
        final Predicate<String> apTrue = (obj) -> true;
        final Predicate<String> apFalse = (obj) -> false;

        final BiPredicate<String, Boolean> aTrue = (obj, not) -> true;
        // final BiPredicate<String, Boolean> aFalse = (obj, not) -> false;

        final Predicate<Boolean> bpTrue = (obj) -> true;
        // final Predicate<Boolean> bpFalse = (obj) -> false;

        final BiPredicate<Boolean, Boolean> bTrue = (obj, not) -> true;
        final BiPredicate<Boolean, Boolean> bFalse = (obj, not) -> false;

        final StepAssertor<String> a = new StepAssertor<>("test", EnumType.CHAR_SEQUENCE, null);
        final StepAssertor<Boolean> b = new StepAssertor<>(true, EnumType.BOOLEAN, null);

        // precondition: true & true, valid: true & true
        StepAssertor<String> step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.CONTAINS, false);
        StepAssertor<Boolean> step2 = new StepAssertor<>(b, bpTrue, bTrue, false, MessageAssertor.of(null, "test", new Object[0]),
                MSG.BOOLEAN.TRUE, false);

        StepAssertor<String> step = new StepAssertor<>(step1, step2, EnumOperator.AND);

        ResultAssertor result = HelperAssertor.combine(step, false);

        assertTrue(result.isPrecondition());
        assertTrue(result.isValid());

        // ALL PRECONDITIONS KO

        step1 = new StepAssertor<>(a, apFalse, aTrue, false, null, MSG.CSQ.CONTAINS, false);
        step2 = new StepAssertor<>(b, bpTrue, bTrue, false, null, MSG.BOOLEAN.TRUE, false);

        step = new StepAssertor<>(step1, step2, EnumOperator.AND);

        result = HelperAssertor.combine(step, true);

        assertFalse(result.isPrecondition());
        assertEquals("the char sequence cannot be null and the searched substring cannot be null or empty", result.getMessage());

        // INVALID SUB

        step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.BLANK, false);
        step2 = new StepAssertor<>(b, bpTrue, bFalse, false, null, MSG.BOOLEAN.TRUE, false);

        step = new StepAssertor<>(step1, step2, EnumOperator.AND);

        result = HelperAssertor.combine(step, true);

        assertTrue(result.isPrecondition());
        assertFalse(result.isValid());
        assertEquals("(the boolean should be true)", result.getMessage().toString());

        // OPERATOR NULL (== AND)

        step = new StepAssertor<>(step1, step2, null);

        result = HelperAssertor.combine(step, true);

        assertTrue(result.isPrecondition());
        assertFalse(result.isValid());
        assertEquals("(the boolean should be true)", result.getMessage().toString());

        assertFalse(Assertor.that("test").isNotBlank().and(Assertor.that((CharSequence) null).contains("test")).isOK());

        // SUB NULL

        step = new StepAssertor<>(step1, null, EnumOperator.AND);

        result = HelperAssertor.combine(step, true);

        assertTrue(result.isPrecondition());
        assertTrue(result.isValid());

        // STEP PREDICATE NULL

        PredicateStepCharSequence<CharSequence> step5 = () -> (StepAssertor<CharSequence>) null;
        assertTrue(Assertor.that("test").isNotBlank().and(step5).isOK());

        // MATCHER (with creation)

        assertException(() -> {
            HelperAssertor.combine(a, "test", true, false);
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for {@link HelperAssertor#getLastChecked} .
     */
    @Test
    public void testGetLastChecked() {
        final List<ParameterAssertor<?>> params = new ArrayList<>();

        assertNull(HelperAssertor.getLastChecked(params));

        params.add(new ParameterAssertor<>(true, EnumType.BOOLEAN));

        assertNull(HelperAssertor.getLastChecked(params));

        params.add(new ParameterAssertor<>(true, EnumType.BOOLEAN, true));

        assertTrue(HelperAssertor.getLastChecked(params));
    }
}
