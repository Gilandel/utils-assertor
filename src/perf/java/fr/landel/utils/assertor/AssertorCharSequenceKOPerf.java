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
import java.util.regex.Pattern;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import fr.landel.utils.assertor.utils.AssertorCharSequence;
import fr.landel.utils.microbenchmark.AbstractMicrobenchmark;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@State(Scope.Benchmark)
public class AssertorCharSequenceKOPerf extends AbstractMicrobenchmark {

    private static final Pattern P1 = Pattern.compile("[fa]");
    private static final Pattern P2 = Pattern.compile("t[a-f]x");

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 30_000d;
    }

    /**
     * Test method for {@link AssertorCharSequence}.
     */
    @Benchmark
    public void assertorCharSequenceKOPerf() {
        Assertor.that("text").contains("re").isOK();
        Assertor.that("text").contains("re").getErrors();
        try {
            Assertor.that("text").contains("ex").orElseThrow();
        } catch (IllegalArgumentException e) {
            // Do nothing
        }

        Assertor.that("text").startsWith("ex").isOK();
        Assertor.that("text").startsWithIgnoreCase("Tx").isOK();

        Assertor.that("text").endsWith("ex").isOK();
        Assertor.that("text").endsWithIgnoreCase("eT").isOK();

        Assertor.that("text").find("[fa]").isOK();
        Assertor.that("text").find(P1).isOK();

        Assertor.that("text").matches("t[a-f]x").isOK();
        Assertor.that("text").matches(P2).isOK();
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
