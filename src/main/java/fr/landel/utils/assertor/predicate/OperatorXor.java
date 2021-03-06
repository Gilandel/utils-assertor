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
package fr.landel.utils.assertor.predicate;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate link in Assertor chain, see
 * {@link PredicateAssertorStep}.
 *
 * @since Mar 28, 2017
 * @author Gilles
 *
 * @param <S>
 *            the type of predicate step
 * @param <T>
 *            the type of checked object
 */
public interface OperatorXor<S extends PredicateStep<S, T>, T> {

    /**
     * @return the step result
     */
    StepAssertor<T> getStep();

    /**
     * The only purpose is to avoid the copy of basic methods into children
     * interfaces. This is an indirect way to create specific
     * {@link PredicateStep} by overriding this interface. All children class
     * has to override this method
     * 
     * @param result
     *            the result
     * @return the predicate step
     */
    S get(StepAssertor<T> result);

    /**
     * Applies a predicate step in the current one with the operator
     * {@link EnumOperator#XOR}. The aim of this is to provide the equivalence
     * of parenthesis in condition expressions.
     * 
     * <pre>
     * // '' empty xor 'text' not empty and contains 'r'
     * Assertor.that("").isEmpty().xor("text").isNotEmpty().and().contains("r").isOK();
     * // -&gt; false (because: true xor true and false =&gt; true xor true = false =&gt;
     * // false and false = false)
     * 
     * // '' empty xor ('text' not empty and contains 'r')
     * Assertor.that("").isEmpty().xor(Assertor.that("text").isNotEmpty().and().contains("r")).isOK();
     * // -&gt; true (because: true xor (true and false) =&gt; (true and false) =
     * // false =&gt; true xor false = true)
     * </pre>
     * 
     * @param other
     *            the other predicate step
     * @return this predicate step with the other injected
     */
    default S xor(final PredicateStep<S, T> other) {
        return this.get(HelperStep.xor(this.getStep(), other.getStep()));
    }

    /**
     * Append an operator '{@link EnumOperator#XOR}' on the current step.
     * 
     * @return the predicate assertor
     */
    default PredicateAssertorStep<S, T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }
}
