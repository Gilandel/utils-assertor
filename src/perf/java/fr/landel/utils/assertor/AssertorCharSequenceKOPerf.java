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

import java.util.regex.Pattern;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import fr.landel.utils.assertor.utils.AssertorCharSequence;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@Fork(1)
@State(Scope.Benchmark)
public class AssertorCharSequenceKOPerf {

    private static final Pattern P1 = Pattern.compile("[fa]");
    private static final Pattern P2 = Pattern.compile("t[a-f]x");

//    @Override
//    protected double getExpectedMinNbOpsPerSeconds() {
//        return 10_000d;
//    }

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
}
