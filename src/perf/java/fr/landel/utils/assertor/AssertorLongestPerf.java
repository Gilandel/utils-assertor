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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * Checks assertor performance
 *
 * @since Aug 8, 2016
 * @author Gilles
 *
 */
@Fork(1)
@State(Scope.Benchmark)
public class AssertorLongestPerf {

//    @Override
//    protected double getExpectedMinNbOpsPerSeconds() {
//        return 50_000d;
//    }

	/**
	 * Test method for {@link Assertor}.
	 */
	@Benchmark
	public void assertorCombiningPerf() {
		Assertor.that(true).isTrue().and().not().isFalse().and(new String[] { "array" }).contains("arra").or("text")
				.startsWith("t").and().not().endsWith("e").and(Assertor.class).hasSimpleName("Assertor").and(12.56f)
				.isGT(256f).xor().isLTE(23f).and(Arrays.asList("re", "tr")).contains("tr").and().contains("re")
				.and(new Date()).isNotNull().and().isAround(new Date(), Calendar.MINUTE, 1).isOK();
	}
}
