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

import java.util.Locale;
import java.util.regex.Pattern;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorCharSequence;

/**
 * This class define methods that can be applied on the checked
 * {@link CharSequence} object. To provide a result, it's also provide a chain
 * builder by returning a {@link PredicateStepCharSequence}. The chain looks
 * like:
 * 
 * <pre>
 * {@link PredicateAssertorStepCharSequence} &gt; {@link PredicateStepCharSequence} &gt; {@link PredicateAssertorStepCharSequence} &gt; {@link PredicateStepCharSequence}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepCharSequence} and
 * ends with {@link PredicateStepCharSequence}.
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <T>
 *            The type of checked object
 */
@FunctionalInterface
public interface PredicateAssertorStepCharSequence<T extends CharSequence> extends PredicateAssertorStep<PredicateStepCharSequence<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepCharSequence<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorStepCharSequence<T> not() {
        return () -> HelperStep.not(this.getStep());
    }

    /**
     * Asserts that the given char sequence has the specified length. The input
     * cannot not be {@code null} and the length cannot be lower than 0 (returns
     * false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLength(5).orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> hasLength(final int length) {
        return this.hasLength(length, null);
    }

    /**
     * Asserts that the given char sequence has the specified length. The input
     * cannot not be {@code null} and the length cannot be lower than 0 (returns
     * false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLength(5, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> hasLength(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLength(length, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence has the specified length. The input
     * cannot not be {@code null} and the length cannot be lower than 0 (returns
     * false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLength(5, Locale.US, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> hasLength(final int length, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.hasLength(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence has a length greater than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGT(5).orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> hasLengthGT(final int length) {
        return this.hasLengthGT(length, null);
    }

    /**
     * Asserts that the given char sequence has a length greater than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGT(5, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> hasLengthGT(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLengthGT(length, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence has a length greater than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGT(5, Locale.US, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> hasLengthGT(final int length, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.hasLengthGT(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence has a length greater than or equal
     * to {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGTE(5).orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @return The next step
     * @category no_message
     */
    default PredicateStepCharSequence<T> hasLengthGTE(final int length) {
        return this.hasLengthGTE(length, null);
    }

    /**
     * Asserts that the given char sequence has a length greater than or equal
     * to {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGTE(5, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The next step
     * @category message
     */
    default PredicateStepCharSequence<T> hasLengthGTE(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLengthGTE(length, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence has a length greater than or equal
     * to {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthGTE(5, Locale.US, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> hasLengthGTE(final int length, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.hasLengthGTE(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence has a length lower than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLT(5).orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @return The next step
     * @category no_message
     */
    default PredicateStepCharSequence<T> hasLengthLT(final int length) {
        return this.hasLengthLT(length, null);
    }

    /**
     * Asserts that the given char sequence has a length lower than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLT(5, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The next step
     * @category message
     */
    default PredicateStepCharSequence<T> hasLengthLT(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLengthLT(length, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence has a length lower than
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLT(5, Locale.US, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The next step
     * @category localized_message
     */
    default PredicateStepCharSequence<T> hasLengthLT(final int length, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.hasLengthLT(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence has a length lower than or equal to
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLTE(5).orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> hasLengthLTE(final int length) {
        return this.hasLengthLTE(length, null);
    }

    /**
     * Asserts that the given char sequence has a length lower than or equal to
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLTE(5, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> hasLengthLTE(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLengthLTE(length, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence has a length lower than or equal to
     * {@code length}. The input cannot not be {@code null} and the length
     * cannot be lower than 0 (returns false).
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be null and {@code length} must
     * be a positive number
     * </p>
     * 
     * <pre>
     * Assertor.that(name).hasLengthLTE(5, Locale.US, "not the good length").orElseThrow();
     * </pre>
     * 
     * @param length
     *            The length (cannot be lower than 0)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> hasLengthLTE(final int length, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.hasLengthLTE(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isEmpty() {
        return this.isEmpty(null);
    }

    /**
     * Asserts that the given char sequence is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEmpty("not empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isEmpty(final CharSequence message, final Object... arguments) {
        return this.isEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEmpty(Locale.US, "not empty").orElseThrow();
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
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEmpty().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotEmpty() {
        return this.isNotEmpty(null);
    }

    /**
     * Asserts that the given char sequence is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEmpty("cannot be empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotEmpty(final CharSequence message, final Object... arguments) {
        return this.isNotEmpty(null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEmpty(Locale.US, "cannot be empty").orElseThrow();
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
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isNotEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is {@code null}, empty or blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isBlank().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isBlank() {
        return this.isBlank(null);
    }

    /**
     * Asserts that the given char sequence is {@code null}, empty or blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isBlank("not blank").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isBlank(final CharSequence message, final Object... arguments) {
        return this.isBlank(null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is {@code null}, empty or blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isBlank(Locale.US, "not blank").orElseThrow();
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
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isBlank(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isBlank(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT {@code null}, NOT empty and
     * NOT blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotBlank().orElseThrow();
     * </pre>
     * 
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotBlank() {
        return this.isNotBlank(null);
    }

    /**
     * Asserts that the given char sequence is NOT {@code null}, NOT empty and
     * NOT blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotBlank("cannot be blank").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotBlank(final CharSequence message, final Object... arguments) {
        return this.isNotBlank(null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT {@code null}, NOT empty and
     * NOT blank.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotBlank(Locale.US, "cannot be blank").orElseThrow();
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
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotBlank(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isNotBlank(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqual(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isEqual(final CharSequence string) {
        return this.isEqual(string, null);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqual(title, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isEqual(final CharSequence string, final CharSequence message, final Object... arguments) {
        return this.isEqual(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqual(title, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isEqual(final CharSequence string, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.isEqual(this.getStep(), string, false, false, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCase(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCase(final CharSequence string) {
        return this.isEqualIgnoreCase(string, null);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCase(title, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCase(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isEqualIgnoreCase(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCase(title, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCase(final CharSequence string, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.isEqual(this.getStep(), string, true, false, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreLineReturns(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreLineReturns(final CharSequence string) {
        return this.isEqualIgnoreLineReturns(string, null);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreLineReturns(title, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreLineReturns(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isEqualIgnoreLineReturns(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreLineReturns(title, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreLineReturns(final CharSequence string, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isEqual(this.getStep(), string, false, true, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCaseAndLineReturns(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCaseAndLineReturns(final CharSequence string) {
        return this.isEqualIgnoreCaseAndLineReturns(string, null);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCaseAndLineReturns(title, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCaseAndLineReturns(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isEqualIgnoreCaseAndLineReturns(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isEqualIgnoreCaseAndLineReturns(title, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isEqualIgnoreCaseAndLineReturns(final CharSequence string, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isEqual(this.getStep(), string, true, true, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqual(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotEqual(final CharSequence string) {
        return this.isNotEqual(string, null);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqual(title, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotEqual(final CharSequence string, final CharSequence message, final Object... arguments) {
        return this.isNotEqual(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqual(title, Locale.US, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotEqual(final CharSequence string, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.isNotEqual(this.getStep(), string, false, false, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCase(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCase(final CharSequence string) {
        return this.isNotEqualIgnoreCase(string, null);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCase(title, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCase(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isNotEqualIgnoreCase(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case considerations.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCase(title, Locale.US, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCase(final CharSequence string, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.isNotEqual(this.getStep(), string, true, false, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreLineReturns(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreLineReturns(final CharSequence string) {
        return this.isNotEqualIgnoreLineReturns(string, null);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreLineReturns(title, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreLineReturns(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isNotEqualIgnoreLineReturns(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring line returns considerations (characters '\r' and
     * '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreLineReturns(title, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreLineReturns(final CharSequence string, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isNotEqual(this.getStep(), string, false, true, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCaseAndLineReturns(title).orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCaseAndLineReturns(final CharSequence string) {
        return this.isNotEqualIgnoreCaseAndLineReturns(string, null);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCaseAndLineReturns(title, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCaseAndLineReturns(final CharSequence string, final CharSequence message,
            final Object... arguments) {
        return this.isNotEqualIgnoreCaseAndLineReturns(string, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence is NOT equal to the specified
     * {@code string} ignoring case and line returns considerations (characters
     * '\r' and '\n' are ignored).
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(name).isNotEqualIgnoreCaseAndLineReturns(title, Locale.US, "cannot be equal").orElseThrow();
     * </pre>
     * 
     * @param string
     *            The {@link CharSequence} to compare
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> isNotEqualIgnoreCaseAndLineReturns(final CharSequence string, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorCharSequence.isNotEqual(this.getStep(), string, true, true, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code character}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code character} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(character).orElseThrow();
     * </pre>
     * 
     * @param character
     *            The {@link Character} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> contains(final Character character) {
        return this.contains(character, null);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code character}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code character} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(character, "character not found").orElseThrow();
     * </pre>
     * 
     * @param character
     *            The {@link Character} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> contains(final Character character, final CharSequence message, final Object... arguments) {
        return this.contains(character, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code character}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code character} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(character, Locale.US, "character not found").orElseThrow();
     * </pre>
     * 
     * @param character
     *            The {@link Character} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> contains(final Character character, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.contains(this.getStep(), character, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word).orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> contains(final CharSequence substring) {
        return this.contains(substring, null);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word, "word not found").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> contains(final CharSequence substring, final CharSequence message, final Object... arguments) {
        return this.contains(substring, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word, Locale.US, "word not found").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> contains(final CharSequence substring, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.contains(this.getStep(), substring, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word).orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> startsWith(final CharSequence substring) {
        return this.startsWith(substring, null);
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word, "'%1$s*' doesn't start with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> startsWith(final CharSequence substring, final CharSequence message, final Object... arguments) {
        return this.startsWith(substring, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).contains(word, Locale.US, "'%1$s*' doesn't start with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> startsWith(final CharSequence substring, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.startsWith(this.getStep(), substring, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).startsWithIgnoreCase(word).orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> startsWithIgnoreCase(final CharSequence substring) {
        return this.startsWithIgnoreCase(substring, null);
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).startsWithIgnoreCase(word, "'%1$s*' doesn't start with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> startsWithIgnoreCase(final CharSequence substring, final CharSequence message,
            final Object... arguments) {
        return this.startsWithIgnoreCase(substring, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence starts with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).startsWithIgnoreCase(word, Locale.US, "'%1$s*' doesn't start with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> startsWithIgnoreCase(final CharSequence substring, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.startsWithIgnoreCase(this.getStep(), substring, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWith(word).orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> endsWith(final CharSequence substring) {
        return this.endsWith(substring, null);
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWith(word, "'%1$s*' doesn't end with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> endsWith(final CharSequence substring, final CharSequence message, final Object... arguments) {
        return this.endsWith(substring, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWith(word, Locale.US, "'%1$s*' doesn't end with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> endsWith(final CharSequence substring, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.endsWith(this.getStep(), substring, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWithIgnoreCase(word).orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> endsWithIgnoreCase(final CharSequence substring) {
        return this.endsWithIgnoreCase(substring, null);
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWithIgnoreCase(word, "'%1$s*' doesn't end with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> endsWithIgnoreCase(final CharSequence substring, final CharSequence message,
            final Object... arguments) {
        return this.endsWithIgnoreCase(substring, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence ends with the specified
     * {@code substring}, ignoring case considerations.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).endsWithIgnoreCase(word, Locale.US, "'%1$s*' doesn't end with word '%2$s*'").orElseThrow();
     * </pre>
     * 
     * @param substring
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> endsWithIgnoreCase(final CharSequence substring, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.endsWithIgnoreCase(this.getStep(), substring, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence matches the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(pattern).orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to match
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> matches(final Pattern pattern) {
        return this.matches(pattern, null);
    }

    /**
     * Asserts that the given char sequence matches the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(pattern, "'%1$s*' doesn't match the pattern").orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to match
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> matches(final Pattern pattern, final CharSequence message, final Object... arguments) {
        return this.matches(pattern, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence matches the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(pattern, Locale.US, "'%1$s*' doesn't match the pattern").orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to match
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> matches(final Pattern pattern, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.matches(this.getStep(), pattern, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence matches the specified {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(regex).orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to match
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> matches(final CharSequence regex) {
        return this.matches(regex, null);
    }

    /**
     * Asserts that the given char sequence matches the specified {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(regex, "'%1$s*' doesn't match the regular expression").orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to match
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> matches(final CharSequence regex, final CharSequence message, final Object... arguments) {
        return this.matches(regex, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence matches the specified {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).matches(regex, Locale.US, "'%1$s*' doesn't match the regular expression").orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to match
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> matches(final CharSequence regex, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.matches(this.getStep(), regex, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(pattern).orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> find(final Pattern pattern) {
        return this.find(pattern, null);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(pattern, "'%1$s*' doesn't contain the pattern").orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> find(final Pattern pattern, final CharSequence message, final Object... arguments) {
        return this.find(pattern, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code pattern}.
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(pattern, Locale.US, "'%1$s*' doesn't contain the pattern").orElseThrow();
     * </pre>
     * 
     * @param pattern
     *            The {@link Pattern} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> find(final Pattern pattern, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.find(this.getStep(), pattern, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(regex).orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to find
     * @return The operator
     * @category no_message
     */
    default PredicateStepCharSequence<T> find(final CharSequence regex) {
        return this.find(regex, null);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(regex, "'%1$s*' doesn't contain the regular expression").orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to find
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category message
     */
    default PredicateStepCharSequence<T> find(final CharSequence regex, final CharSequence message, final Object... arguments) {
        return this.find(regex, null, message, arguments);
    }

    /**
     * Asserts that the given char sequence contains the specified
     * {@code regex}.
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(name).find(regex, Locale.US, "'%1$s*' doesn't contain the regular expression").orElseThrow();
     * </pre>
     * 
     * @param regex
     *            The {@link CharSequence} to find
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     * @category localized_message
     */
    default PredicateStepCharSequence<T> find(final CharSequence regex, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorCharSequence.find(this.getStep(), regex, MessageAssertor.of(locale, message, arguments));
    }
}
