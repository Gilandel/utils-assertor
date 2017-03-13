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

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import fr.landel.utils.assertor.memory.MemoryTest;
import fr.landel.utils.microbenchmark.AbstractMicrobenchmark;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@State(Scope.Benchmark)
public class AssertorCombiningPerf extends AbstractMicrobenchmark {

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 30_000d;
    }

    /**
     * Test method for {@link Assertor}.
     */
    @Benchmark
    public void assertorCombiningPerf() {
        Assertor.that(MemoryTest.class).isAssignableFrom(AbstractTest.class).or().isNull().isOK();

        Assertor.that("text").contains("ex").and().endsWithIgnoreCase("T").isOK();
        Assertor.that("text").contains("ex").or().endsWithIgnoreCase("E").isOK();
        Assertor.that("text").contains("ex").xor().endsWithIgnoreCase("X").isOK();

        Assertor.that("text").contains("ex").and(12.3f).isGT(10.25f).isOK();
        Assertor.that("text").contains("ex").or(12.3f).isGT(10.25f).isOK();
        Assertor.that("text").contains("ex").xor(12.3f).isGT(13.25f).isOK();

        Assertor.that("text").contains("ex").and(Assertor.that(12.3f).isNotNull().and().isGT(10.25f)).isOK();
        Assertor.that("text").contains("ex").or(Assertor.that(12.3f).isNull().and().isGT(10.25f)).isOK();
        Assertor.that("text").contains("ex").xor(Assertor.that(12.3f).isNotNull().and().isGT(13.25f)).isOK();
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
