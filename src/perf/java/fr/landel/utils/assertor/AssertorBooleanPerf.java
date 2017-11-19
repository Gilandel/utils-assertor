/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import fr.landel.utils.assertor.utils.AssertorBoolean;
import fr.landel.utils.microbenchmark.AbstractMicrobenchmark;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@State(Scope.Benchmark)
public class AssertorBooleanPerf extends AbstractMicrobenchmark {

    @Override
    protected double getExpectedMinNbOpsPerSeconds() {
        return 30_000d;
    }

    /**
     * Perf method for {@link AssertorBoolean}.
     */
    @Benchmark
    public void assertorBasicPerf1() {
        Assertor.that(true).isTrue().isOK();
        Assertor.that(true).isTrue().getErrors();
        Assertor.that(true).isTrue().orElseThrow();

        Assertor.that(false).isFalse().isOK();
        Assertor.that(false).isFalse().getErrors();
        Assertor.that(false).isFalse().orElseThrow();

        Assertor.that(true).isFalse().isOK();
        Assertor.that(true).isFalse().getErrors();
        try {
            Assertor.that(true).isFalse().orElseThrow();
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        Assertor.that(false).isTrue().isOK();
        Assertor.that(false).isTrue().getErrors();
        try {
            Assertor.that(false).isTrue().orElseThrow();
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    @Test
    public void testPerf() throws IOException, RunnerException {
        assertNotNull(super.run());
    }
}
