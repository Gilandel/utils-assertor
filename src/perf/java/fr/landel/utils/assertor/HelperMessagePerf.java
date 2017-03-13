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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import fr.landel.utils.assertor.ConstantsAssertor.MSG;
import fr.landel.utils.microbenchmark.AbstractMicrobenchmark;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@State(Scope.Benchmark)
public class HelperMessagePerf extends AbstractMicrobenchmark {

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 1_000_000d;
    }

    /**
     * Perf method for {@link HelperMessage#getMessage} with {@code Boolean}.
     */
    @Benchmark
    public void assertorBasicPerf2() {
        final Predicate<String> apTrue = (obj) -> true;
        final BiPredicate<String, Boolean> aTrue = (obj, not) -> true;
        final Predicate<Boolean> bpTrue = (obj) -> true;
        final BiPredicate<Boolean, Boolean> bTrue = (obj, not) -> true;

        final StepAssertor<String> a = new StepAssertor<>("test", EnumType.CHAR_SEQUENCE);
        final StepAssertor<Boolean> b = new StepAssertor<>(true, EnumType.BOOLEAN);

        // precondition: true & true, valid: true & false
        StepAssertor<String> step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.CONTAINS, false);
        StepAssertor<Boolean> step2 = new StepAssertor<>(b, bpTrue, bTrue, false, null, MSG.BOOLEAN.TRUE, false);

        StepAssertor<String> result = new StepAssertor<>(step1, step2, EnumOperator.AND);

        HelperAssertor.combine(result, true);
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
