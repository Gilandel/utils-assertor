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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
public class AssertorLongestPerf extends AbstractMicrobenchmark {

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 100_000d;
    }

    /**
     * Test method for {@link Assertor}.
     */
    @Benchmark
    public void assertorCombiningPerf() {
        Assertor.that(true).isTrue().and().not().isFalse().and(new String[] {"array"}).contains("arra").or("text").startsWith("t").and()
                .not().endsWith("e").and(Assertor.class).hasSimpleName("Assertor").and(12.56f).isGT(256f).xor().isLTE(23f)
                .and(Arrays.asList("re", "tr")).contains("tr").and().contains("re").and(new Date()).isNotNull().and()
                .isAround(new Date(), Calendar.MINUTE, 1).isOK();
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
