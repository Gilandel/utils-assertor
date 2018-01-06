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
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class define methods that can be applied on the checked {@link Object}.
 * To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepObject}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorStepObject} &gt; {@link PredicateStepObject} &gt; {@link PredicateAssertorStepObject} &gt; {@link PredicateStepObject}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepObject} and ends
 * with {@link PredicateStepObject}.
 *
 * @since Jan 6, 2018
 * @author Gilles
 *
 * @param <T>
 *            The type of checked object
 */
@FunctionalInterface
public interface PredicateAssertorStepObject<T> extends PredicateAssertorStep<PredicateStepObject<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepObject<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorStepObject<T> not() {
        return () -> HelperStep.not(getStep());
    }
}
