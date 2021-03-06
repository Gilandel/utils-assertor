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

import java.util.Locale;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorBoolean;

/**
 * This class define methods that can be applied on the checked {@link Boolean}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link StepBoolean}. The chain looks like:
 * 
 * <pre>
 * {@link AssertorStepBoolean} &gt; {@link StepBoolean} &gt; {@link AssertorStepBoolean} &gt; {@link StepBoolean}...
 * </pre>
 * 
 * This chain always starts with a {@link AssertorStepBoolean} and ends with
 * {@link StepBoolean}.
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface AssertorStepBoolean extends AssertorStep<StepBoolean, Boolean> {

    /**
     * {@inheritDoc}
     */
    @Override
    default StepBoolean get(final StepAssertor<Boolean> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default AssertorStepBoolean not() {
        return () -> HelperStep.not(this.getStep());
    }

    /**
     * Check if the {@link Boolean} is {@code true}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isTrue().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default StepBoolean isTrue() {
        return this.isTrue(null);
    }

    /**
     * Check if the {@link Boolean} is {@code true}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isTrue("not true").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on incorrect length
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepBoolean isTrue(final CharSequence message, final Object... arguments) {
        return this.isTrue(null, message, arguments);
    }

    /**
     * Check if the {@link Boolean} is {@code true}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isTrue(Locale.US, "not true").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on incorrect length
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepBoolean isTrue(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorBoolean.isTrue(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the {@link Boolean} is {@code false}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isFalse().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default StepBoolean isFalse() {
        return this.isFalse(null);
    }

    /**
     * Check if the {@link Boolean} is {@code false}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isFalse("not false").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on incorrect length
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepBoolean isFalse(final CharSequence message, final Object... arguments) {
        return this.isFalse(null, message, arguments);
    }

    /**
     * Check if the {@link Boolean} is {@code false}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(bool).isFalse(Locale.US, "not false").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on incorrect length
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepBoolean isFalse(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorBoolean.isFalse(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }
}
