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
package fr.landel.utils.assertor;

import java.util.Locale;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorArray;

/**
 * This class define methods that can be applied on the checked Array object. To
 * provide a result, it's also provide a chain builder by returning a
 * {@link StepArray}. The chain looks like:
 * 
 * <pre>
 * {@link AssertorStepArray} &gt; {@link StepArray} &gt; {@link AssertorStepArray} &gt; {@link StepArray}...
 * </pre>
 * 
 * This chain always starts with a {@link AssertorStepArray} and ends with
 * {@link StepArray}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface AssertorStepArray<T> extends AssertorStep<StepArray<T>, T[]> {

    /**
     * {@inheritDoc}
     */
    @Override
    default StepArray<T> get(final StepAssertor<T[]> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default AssertorStepArray<T> not() {
        return () -> HelperStep.not(getStep());
    }

    /**
     * Check if the array has the specified {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(array).hasLength(length).orElseThrow();
     * </pre>
     * 
     * @param length
     *            the length to check
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> hasLength(final int length) {
        return this.hasLength(length, null);
    }

    /**
     * Check if the array has the specified {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(array).hasLength(length, "the array has not the specified length %2$d*").orElseThrow();
     * </pre>
     * 
     * @param length
     *            the length to check
     * @param message
     *            the message on incorrect length
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> hasLength(final int length, final CharSequence message, final Object... arguments) {
        return this.hasLength(length, null, message, arguments);
    }

    /**
     * Check if the array has the specified {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(array).hasLength(length, Locale.US, "the array has not the specified length %2$d*").orElseThrow();
     * </pre>
     * 
     * @param length
     *            the length to check
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
    default StepArray<T> hasLength(final int length, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.hasLength(this.getStep(), length, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the array is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isEmpty().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> isEmpty() {
        return this.isEmpty(null);
    }

    /**
     * Check if the array is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isEmpty("the array must be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> isEmpty(final CharSequence message, final Object... arguments) {
        return this.isEmpty(null, message, arguments);
    }

    /**
     * Check if the array is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isEmpty(Locale.US, "the array must be null or empty").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepArray<T> isEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.isEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the array is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isNotEmpty().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> isNotEmpty() {
        return this.isNotEmpty(null);
    }

    /**
     * Check if the array is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isNotEmpty("the array cannot be null or empty").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> isNotEmpty(final CharSequence message, final Object... arguments) {
        return this.isNotEmpty(null, message, arguments);
    }

    /**
     * Check if the array is NOT {@code null} and NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(array).isNotEmpty(Locale.US, "the array cannot be null or empty").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepArray<T> isNotEmpty(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.isNotEmpty(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the array contains the specified object.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).contains(object).orElseThrow();
     * </pre>
     * 
     * @param object
     *            The object to find
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> contains(final T object) {
        return this.contains(object, null);
    }

    /**
     * Check if the array contains the specified object.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).contains(object, "the element cannot be found in the array").orElseThrow();
     * </pre>
     * 
     * @param object
     *            the object to find
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> contains(final T object, final CharSequence message, final Object... arguments) {
        return this.contains(object, null, message, arguments);
    }

    /**
     * Check if the array contains the specified object.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).contains(object, Locale.US, "the element cannot be found in the array").orElseThrow();
     * </pre>
     * 
     * @param object
     *            the object to find
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepArray<T> contains(final T object, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.contains(this.getStep(), object, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the array contains ALL the specified {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAll(subarray).orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> containsAll(final T[] objects) {
        return this.containsAll(objects, null);
    }

    /**
     * Check if the array contains ALL the specified {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAll(subarray, "not all elements can be found in the array").orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> containsAll(final T[] objects, final CharSequence message, final Object... arguments) {
        return this.containsAll(objects, null, message, arguments);
    }

    /**
     * Check if the array contains ALL the specified {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAll(subarray, Locale.US, "not all elements can be found in the array").orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepArray<T> containsAll(final T[] objects, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.containsAll(this.getStep(), objects, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the array contains ANY element of {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAny(subarray).orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @return the assertor step
     * @category no_message
     */
    default StepArray<T> containsAny(final T[] objects) {
        return this.containsAny(objects, null);
    }

    /**
     * Check if the array contains ANY element of {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAny(subarray, "no element found in the array").orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default StepArray<T> containsAny(final T[] objects, final CharSequence message, final Object... arguments) {
        return this.containsAny(objects, null, message, arguments);
    }

    /**
     * Check if the array contains ANY element of {@code objects}.
     * 
     * <p>
     * precondition: neither {@code array} can be {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(array).containsAny(subarray, Locale.US, "no element found in the array").orElseThrow();
     * </pre>
     * 
     * @param objects
     *            the objects to find
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default StepArray<T> containsAny(final T[] objects, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorArray.containsAny(this.getStep(), objects, MessageAssertor.of(locale, message, arguments));
    }
}
