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
package fr.landel.utils.assertor.predicate;

import java.util.Locale;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorIterable;

/**
 * This class define methods that can be applied on the checked {@link Iterable}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepIterable}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorStepIterable} &gt; {@link PredicateStepIterable} &gt; {@link PredicateAssertorStepIterable} &gt; {@link PredicateStepIterable}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepIterable} and
 * ends with {@link PredicateStepIterable}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 * @param <I>
 *            the {@link Iterable} type
 * @param <T>
 *            the {@link Iterable} elements type
 */
@FunctionalInterface
public interface PredicateAssertorStepIterable<I extends Iterable<T>, T> extends PredicateAssertorStep<PredicateStepIterable<I, T>, I> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepIterable<I, T> get(final StepAssertor<I> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorStepIterable<I, T> not() {
        return () -> HelperStep.not(getStep());
    }

    /**
     * Asserts that the given {@link Iterable} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Iterable} cannot be {@code null} and size cannot be
     * lower than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).hasSize(size).orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @return The operator
     */
    default PredicateStepIterable<I, T> hasSize(final int size) {
        return this.hasSize(size, null);
    }

    /**
     * Asserts that the given {@link Iterable} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Iterable} cannot be {@code null} and size cannot be
     * lower than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).hasSize(size, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> hasSize(final int size, final CharSequence message, final Object... arguments) {
        return this.hasSize(size, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} has the specified {@code size}.
     * 
     * <p>
     * precondition: {@link Iterable} cannot be {@code null} and size cannot be
     * lower than zero
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).hasSize(size, Locale.US, "bad size").orElseThrow();
     * </pre>
     * 
     * @param size
     *            The wanted size
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> hasSize(final int size, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorIterable.hasSize(this.getStep(), size, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default PredicateStepIterable<I, T> isEmpty() {
        return this.isEmpty(null);
    }

    /**
     * Asserts that the given {@link Iterable} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isEmpty("the iterable must be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> isEmpty(final CharSequence message, final Object... arguments) {
        return this.isEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isEmpty(Locale.US, "the iterable must be null or empty").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> isEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorIterable.isEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} is not {@code null} and not
     * empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isNotEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default PredicateStepIterable<I, T> isNotEmpty() {
        return this.isNotEmpty(null);
    }

    /**
     * Asserts that the given {@link Iterable} is not {@code null} and not
     * empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isNotEmpty("the iterable cannot be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> isNotEmpty(final CharSequence message, final Object... arguments) {
        return this.isNotEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} is not {@code null} and not
     * empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).isNotEmpty(Locale.US, "the iterable cannot be null or empty").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> isNotEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorIterable.isNotEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} contains the specified
     * {@code value}.
     * 
     * <p>
     * precondition: the {@link Iterable} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).contains(value).orElseThrow();
     * </pre>
     * 
     * @param value
     *            The value
     * @return The operator
     */
    default PredicateStepIterable<I, T> contains(final T value) {
        return this.contains(value, null);
    }

    /**
     * Asserts that the given {@link Iterable} contains the specified
     * {@code value}.
     * 
     * <p>
     * precondition: the {@link Iterable} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).contains(value, "the element was not found in the iterable").orElseThrow();
     * </pre>
     * 
     * @param value
     *            The value
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> contains(final T value, final CharSequence message, final Object... arguments) {
        return this.contains(value, (Locale) null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} contains the specified
     * {@code value}.
     * 
     * <p>
     * precondition: the {@link Iterable} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).contains(value, Locale.US, "the element was not found in the iterable").orElseThrow();
     * </pre>
     * 
     * @param value
     *            The value
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> contains(final T value, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorIterable.contains(this.getStep(), value, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAll(values).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAll(final Iterable<T> values) {
        return this.containsAll(values, null);
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAll(values, "not all elements can be found in the iterable").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAll(final Iterable<T> values, final CharSequence message, final Object... arguments) {
        return this.containsAll(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL the specified
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAll(values, Locale.US, "not all elements can be found in the iterable").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAll(final Iterable<T> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorIterable.containsAll(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} contains ANY element of
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAny(values).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAny(final Iterable<T> values) {
        return this.containsAny(values, null);
    }

    /**
     * Asserts that the given {@link Iterable} contains ANY element of
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAny(values, "no element found").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAny(final Iterable<T> values, final CharSequence message, final Object... arguments) {
        return this.containsAny(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} contains ANY element of
     * {@code values}.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsAny(values, Locale.US, "no element found").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsAny(final Iterable<T> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorIterable.containsAny(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL elements of
     * {@code values} in the same order.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsInOrder(values).orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsInOrder(final Iterable<T> values) {
        return this.containsInOrder(values, null);
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL elements of
     * {@code values} in the same order.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsInOrder(values, "elements aren't found or in the same order").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsInOrder(final Iterable<T> values, final CharSequence message, final Object... arguments) {
        return this.containsInOrder(values, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Iterable} contains ALL elements of
     * {@code values} in the same order.
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(iterable).containsInOrder(values, Locale.US, "elements aren't found or in the same order").orElseThrow();
     * </pre>
     * 
     * @param values
     *            The {@link Iterable} values
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepIterable<I, T> containsInOrder(final Iterable<T> values, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorIterable.containsInOrder(this.getStep(), values, MessageAssertor.of(locale, message, arguments));
    }
}