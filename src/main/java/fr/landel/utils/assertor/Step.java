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

import java.util.Optional;

import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.Default;
import fr.landel.utils.commons.Result;

/**
 * This class is an intermediate or final link in chain, see
 * {@link AssertorStep}.
 * 
 * <p>
 * Intermediate by using operators:
 * </p>
 * <ul>
 * <li>{@link Step#and}: applied the AND operator between the previous and the
 * next assertion</li>
 * <li>{@link Step#or}: applied the OR operator between the previous and the
 * next assertion</li>
 * <li>{@link Step#xor}: applied the XOR operator between the previous and the
 * next assertion</li>
 * <li>{@link Step#nand}: applied the NAND operator between the previous and the
 * next assertion</li>
 * <li>{@link Step#nor}: applied the NOR operator between the previous and the
 * next assertion</li>
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
public interface Step<S extends Step<S, T>, T>
        extends AssertorEnd<T>, OperatorAnd<S, T>, OperatorNand<S, T>, OperatorNor<S, T>, OperatorOr<S, T>, OperatorXor<S, T> {

    /**
     * @return the step result
     */
    @Override
    StepAssertor<T> getStep();

    /**
     * {@inheritDoc}
     */
    @Override
    default S get(final StepAssertor<T> result) {
        return CastUtils.cast((Step<S, T>) () -> result);
    }
}
