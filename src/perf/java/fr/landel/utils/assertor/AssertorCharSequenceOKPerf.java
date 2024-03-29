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

import org.apache.commons.lang3.StringUtils;
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
public class AssertorCharSequenceOKPerf {

	private static final Pattern P1 = Pattern.compile("[ft]");
	private static final Pattern P2 = Pattern.compile("t[a-f]x[tT]");

//    @Override
//    protected double getExpectedMinNbOpsPerSeconds() {
//        return 25_000d;
//    }

	/**
	 * Test method for {@link AssertorCharSequence}.
	 */
	@Benchmark
	public void assertorCharSequenceOKAssertorPerf() {
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

	/**
	 * Test method for {@link AssertorCharSequence}.
	 */
	@Benchmark
	public void assertorCharSequenceOKBasicPerf() {
		StringUtils.isNotEmpty("text");
		"text".contains("ex");
		StringUtils.isNotEmpty("text");
		"text".contains("ex");
		String.format("%s", "test");
		StringUtils.isNotEmpty("text");
		"text".contains("ex");

		StringUtils.isNotEmpty("text");
		"text".startsWith("ex");
		StringUtils.isNotEmpty("text");
		"text".toUpperCase().startsWith("ex".toUpperCase());

		StringUtils.isNotEmpty("text");
		"text".endsWith("ex");
		StringUtils.isNotEmpty("text");
		"text".toUpperCase().endsWith("ex".toUpperCase());

		StringUtils.isNotEmpty("text");
		Pattern.compile("[ft]").matcher("text").find();
		StringUtils.isNotEmpty("text");
		P1.matcher("text").find();

		StringUtils.isNotEmpty("text");
		Pattern.compile("t[a-f]x[tT]").matcher("text").matches();
		StringUtils.isNotEmpty("text");
		P2.matcher("text").matches();
	}
}
