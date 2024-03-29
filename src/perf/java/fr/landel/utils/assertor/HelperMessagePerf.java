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

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import fr.landel.utils.assertor.commons.ConstantsAssertor.MSG;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.helper.HelperAssertor;
import fr.landel.utils.assertor.helper.HelperMessage;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@Fork(1)
@State(Scope.Benchmark)
public class HelperMessagePerf {

//    @Override
//    protected double getExpectedMinNbOpsPerSeconds() {
//        return 250_000d;
//    }

    /**
     * Perf method for {@link HelperMessage#getMessage} with {@code Boolean}.
     */
    @Benchmark
    public void assertorBasicPerf2() {
        final Predicate<String> apTrue = (obj) -> true;
        final BiPredicate<String, Boolean> aTrue = (obj, not) -> true;
        final Predicate<Boolean> bpTrue = (obj) -> true;
        final BiPredicate<Boolean, Boolean> bTrue = (obj, not) -> true;

        final StepAssertor<String> a = new StepAssertor<>("test", EnumType.CHAR_SEQUENCE, null);
        final StepAssertor<Boolean> b = new StepAssertor<>(true, EnumType.BOOLEAN, null);

        // precondition: true & true, valid: true & false
        StepAssertor<String> step1 = new StepAssertor<>(a, apTrue, aTrue, false, null, MSG.CSQ.CONTAINS, false);
        StepAssertor<Boolean> step2 = new StepAssertor<>(b, bpTrue, bTrue, false, null, MSG.BOOLEAN.TRUE, false);

        StepAssertor<String> result = new StepAssertor<>(step1, step2, EnumOperator.AND);

        HelperAssertor.combine(result, true);
    }
}
