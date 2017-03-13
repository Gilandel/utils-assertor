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

import fr.landel.utils.microbenchmark.AbstractMicrobenchmark;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@State(Scope.Benchmark)
public class AssertorCharSequenceOKPerf extends AbstractMicrobenchmark {

    private static final Pattern P1 = Pattern.compile("[ft]");
    private static final Pattern P2 = Pattern.compile("t[a-f]x[tT]");

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 50_000d;
    }

    /**
     * Test method for {@link AssertorCharSequence}.
     */
    @Benchmark
    public void assertorCharSequenceOKPerf() {
        Assertor.that("text").contains("ex").isOK();
        Assertor.that("text").contains("ex").getErrors();
        Assertor.that("text").contains("ex").orElseThrow();

        Assertor.that("text").startsWith("tex").isOK();
        Assertor.that("text").startsWithIgnoreCase("Tex").isOK();

        Assertor.that("text").endsWith("ext").isOK();
        Assertor.that("text").endsWithIgnoreCase("exT").isOK();

        Assertor.that("text").find("[ft]").isOK();
        Assertor.that("text").find(P1).isOK();

        Assertor.that("text").matches("t[a-f]x[tT]").isOK();
        Assertor.that("text").matches(P2).isOK();
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
