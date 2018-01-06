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

import java.util.Optional;
import java.util.function.Predicate;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.AssertorEnd;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.Default;
import fr.landel.utils.commons.Result;

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateAssertorStep}.
 * 
 * <p>
 * Intermediate by using operators:
 * </p>
 * <ul>
 * <li>{@link PredicateStep#and}: applied the AND operator between the previous
 * and the next assertion</li>
 * <li>{@link PredicateStep#or}: applied the OR operator between the previous
 * and the next assertion</li>
 * <li>{@link PredicateStep#xor}: applied the XOR operator between the previous
 * and the next assertion</li>
 * <li>{@link PredicateStep#nand}: applied the NAND operator between the
 * previous and the next assertion</li>
 * <li>{@link PredicateStep#nor}: applied the NOR operator between the previous
 * and the next assertion</li>
 * <li>{@link PredicateStep#that(Object)}: to inject the object to check (in
 * matcher mode)</li>
 * </ul>
 * 
 * <p>
 * Final by using methods:
 * </p>
 * <ul>
 * <li>{@link AssertorEnd#isOK}: to get the boolean result of the assertion
 * {@code true} or {@code false}.</li>
 * <li>{@link AssertorEnd#getErrors}: to get the error message (precondition
 * message or message depending of error type).</li>
 * <li>{@link AssertorEnd#get}: to get the result as an {@link Optional} object.
 * The result is set to empty if the assertion failed or if the checked value is
 * {@code null}.</li>
 * <li>{@link AssertorEnd#getNullable}: to get the result even if value is
 * {@code null}.</li>
 * <li>{@link AssertorEnd#orElse}: to get the value if not {@code null},
 * otherwise returns the default value.</li>
 * <li>{@link AssertorEnd#orElseThrow}: to throw an exception if assertion is
 * false, or to get the checked value otherwise.</li>
 * <li>{@link AssertorEnd#asResult}: to get the result as a {@link Result}
 * object. The result is set to empty only if the assertion failed.</li>
 * <li>{@link AssertorEnd#asDefault}: to get the result as a {@link Default}
 * object. It's the as {@link Optional} but the default value is already
 * included.</li>
 * </ul>
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <S>
 *            the type of predicate step
 * @param <T>
 *            the type of checked object
 */
@FunctionalInterface
public interface PredicateStep<S extends PredicateStep<S, T>, T>
        extends OperatorAnd<S, T>, OperatorNand<S, T>, OperatorNor<S, T>, OperatorOr<S, T>, OperatorXor<S, T> {

    /**
     * @return the step result
     */
    @Override
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
    @Override
    default S get(final StepAssertor<T> result) {
        return CastUtils.cast((PredicateStep<S, T>) () -> result);
    }

    /**
     * Inject the checked object into an 'end' step.
     * 
     * <p>
     * This method is only compatible with an Assertor starting like this:
     * {@code Assertor.matcher...}. If not this method at end predicate throws
     * an {@link UnsupportedOperationException}.
     * </p>
     *
     * <pre>
     * // Example:
     * PredicateStepNumber&lt;Integer&gt; predicate = Assertor.&lt;Integer&gt; ofNumber().isGT(10);
     * // ...
     * predicate.that(15).orElseThrow(); // -&gt; returns 15
     * predicate.that(5).orElseThrow(); // -&gt; throws an Exception
     * </pre>
     * 
     * @param object
     *            the object to check
     * @return the predicate end step
     */
    default AssertorEnd<T> that(final T object) {
        return () -> HelperStep.object(this.getStep(), object);
    }

    /**
     * Convert the {@link Assertor} predicate into a {@link Predicate}.
     * 
     * <pre>
     * Predicate&lt;Integer&gt; predicate = Assertor.&lt;Integer&gt; ofNumber().isGT(10).asPredicate();
     * // ...
     * predicate.test(15); // -&gt; returns true
     * predicate.test(5); // -&gt; returns false
     * </pre>
     * 
     * @return the predicate object
     */
    default Predicate<T> asPredicate() {
        final PredicateStep<S, T> predicateStep = this;
        return object -> predicateStep.that(object).isOK();
    }
}
