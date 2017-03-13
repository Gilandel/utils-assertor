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

import org.junit.Test;

/**
 * Check {@link StepAssertor}
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
public class StepAssertorTest extends AbstractTest {

    /**
     * Test method for
     * {@link StepAssertor#AssertorResult(java.lang.Object, EnumType)}.
     */
    @Test
    public void testAssertorResultTEnumType() {
        StepAssertor<Boolean> assertorResult = new StepAssertor<>(true, EnumType.BOOLEAN);

        assertNotNull(assertorResult);

        assertEquals(EnumStep.CREATION, assertorResult.getStepType());
        assertEquals(true, assertorResult.getObject());
        assertEquals(EnumType.BOOLEAN, assertorResult.getType());
        assertFalse(assertorResult.isNot());
        assertNull(assertorResult.getPreChecker());
        assertNull(assertorResult.getChecker());
        assertNull(assertorResult.getMessageKey());
        assertNull(assertorResult.getMessage());
        assertNull(assertorResult.getOperator());
        assertNull(assertorResult.getParameters());
    }

    /**
     * Test method for {@link StepAssertor#AssertorResult(StepAssertor)}.
     */
    @Test
    public void testAssertorResultAssertorResultOfT() {
        StepAssertor<Boolean> assertorResult = new StepAssertor<>(new StepAssertor<>(true, EnumType.BOOLEAN));

        assertNotNull(assertorResult);
        assertNotNull(assertorResult.getPreviousStep());

        assertEquals(EnumStep.NOT, assertorResult.getStepType());
        assertEquals(true, assertorResult.getPreviousStep().getObject());
        assertEquals(EnumType.BOOLEAN, assertorResult.getPreviousStep().getType());
        assertTrue(assertorResult.isNot()); // NOT
        assertNull(assertorResult.getPreChecker());
        assertNull(assertorResult.getChecker());
        assertNull(assertorResult.getMessageKey());
        assertNull(assertorResult.getMessage());
        assertNull(assertorResult.getOperator());
        assertNull(assertorResult.getParameters());

        assertorResult = new StepAssertor<>(assertorResult);

        assertTrue(assertorResult.isNot()); // NOT
    }

    /**
     * Test method for {@link StepAssertor#toString()}.
     */
    @Test
    public void testToString() {
        StepAssertor<String> step = new StepAssertor<>("text", EnumType.CHAR_SEQUENCE);
        assertEquals("{CREATION, object: text, type: CHAR_SEQUENCE}", step.toString());

        step = new StepAssertor<>(step);
        assertEquals("{NOT, not: true}", step.toString());

        step = new StepAssertor<>(step, EnumOperator.AND);
        assertEquals("{OPERATOR, operator:  AND }", step.toString());

        StepAssertor<Integer> step2 = new StepAssertor<>(step, 1, EnumType.NUMBER_INTEGER, EnumOperator.AND);
        assertEquals("{OBJECT, object: 1, type: NUMBER_INTEGER, operator:  AND }", step2.toString());

        step = new StepAssertor<>(step, (string) -> true, (string, not) -> true, false, MessageAssertor.of(null, "msg", null), "key",
                false);
        assertEquals("{ASSERTION, key: key, key not: false, parameters: , message: {message: msg}}", step.toString());

        step = new StepAssertor<>(step, step2, EnumOperator.AND);
        assertEquals("{SUB, operator:  AND }", step.toString());
    }
}
