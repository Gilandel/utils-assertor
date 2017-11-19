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
import java.util.function.Predicate;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorObject;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.function.PredicateThrowable;

/**
 * This class define methods that can be applied on the checked object. To
 * provide a result, it's also provide a chain builder by returning a
 * {@link Step}. The chain looks like:
 * 
 * <pre>
 * {@link AssertorStep} &gt; {@link Step} &gt; {@link AssertorStep} &gt; {@link Step}...
 * </pre>
 * 
 * This chain always starts with a {@link AssertorStep} and ends with
 * {@link Step}. If multiple values are checked, following to their types, the
 * chain can be (checked values: "text", 3):
 *
 * <pre>
 * {@link AssertorStepCharSequence} &gt; {@link AssertorStepCharSequence} &gt; {@link AssertorStepNumber} &gt; {@link StepNumber}...
 * </pre>
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
public interface AssertorStep<S extends Step<S, T>, T> {

    /**
     * Get the net step.
     * 
     * @return the assertor step
     */
    StepAssertor<T> getStep();

    /**
     * The only purpose of this is to avoid the copy of basic methods into
     * children interfaces. This is an indirect way to create specific
     * {@link Step} by overriding this interface. All children class has to
     * override this method.
     * 
     * @param result
     *            The result
     * @return The predicate step
     */
    default S get(final StepAssertor<T> result) {
        return CastUtils.cast((Step<S, T>) () -> result);
    }

    /**
     * Add the NOT operator on the next assertion
     * 
     * <pre>
     * Assertor.that(object).not().isInstanceOf(Exception.class).orElseThrow();
     * </pre>
     * 
     * @return an assertor based on the current one
     */
    default AssertorStep<S, T> not() {
        return () -> HelperStep.not(this.getStep());
    }

    /**
     * Check if the checked object is {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNull().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default S isNull() {
        return this.isNull(null);
    }

    /**
     * Check if the checked object is {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNull("The object must be null").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isNull(final CharSequence message, final Object... arguments) {
        return this.isNull(null, message, arguments);
    }

    /**
     * Check if the checked object is {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNull(Locale.US, "The object must be null").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isNull(final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isNull(this.getStep(), MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked object is NOT {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNotNull().orElseThrow();
     * </pre>
     * 
     * @return the assertor step
     * @category no_message
     */
    default S isNotNull() {
        return this.isNotNull(null);
    }

    /**
     * Check if the checked object is NOT {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNotNull("The object cannot be null").orElseThrow();
     * </pre>
     * 
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isNotNull(final CharSequence message, final Object... arguments) {
        return this.isNotNull(null, message, arguments);
    }

    /**
     * Check if the checked object is NOT {@code null}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isNotNull(Locale.US, "The object cannot be null").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isNotNull(final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isNotNull(this.getStep(), MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked object is equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isEqual(object2).orElseThrow();
     * </pre>
     * 
     * @param object
     *            the object to compare
     * @return the assertor step
     * @category no_message
     */
    default S isEqual(final Object object) {
        return this.isEqual(object, null);
    }

    /**
     * Check if the checked object is equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isEqual(object2, "The object must be equal").orElseThrow();
     * </pre>
     * 
     * @param object
     *            the object to compare
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isEqual(final Object object, final CharSequence message, final Object... arguments) {
        return this.isEqual(object, null, message, arguments);
    }

    /**
     * Check if the checked object is equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(object).isEqual(object2, Locale.US, "The object must be equal").orElseThrow();
     * </pre>
     * 
     * @param object
     *            the object to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isEqual(final Object object, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isEqual(this.getStep(), object, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked object is NOT equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param object
     *            the object to compare
     * @return the assertor step
     * @category no_message
     */
    default S isNotEqual(final Object object) {
        return this.isNotEqual(object, null);
    }

    /**
     * Check if the checked object is NOT equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param object
     *            the object to compare
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isNotEqual(final Object object, final CharSequence message, final Object... arguments) {
        return this.isNotEqual(object, null, message, arguments);
    }

    /**
     * Check if the checked object is NOT equal to the specified {@code object}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param object
     *            the object to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isNotEqual(final Object object, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isNotEqual(this.getStep(), object, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked object is an instance of the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the instance class
     * @return the assertor step
     * @category no_message
     */
    default S isInstanceOf(final Class<?> clazz) {
        return this.isInstanceOf(clazz, null);
    }

    /**
     * Check if the checked object is an instance of the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the instance class
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isInstanceOf(final Class<?> clazz, final CharSequence message, final Object... arguments) {
        return this.isInstanceOf(clazz, null, message, arguments);
    }

    /**
     * Check if the checked object is an instance of the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the instance class
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isInstanceOf(final Class<?> clazz, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isInstanceOf(this.getStep(), clazz, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked object is assignable from the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the class (super class or interface)
     * @return the assertor step
     * @category no_message
     */
    default S isAssignableFrom(final Class<?> clazz) {
        return this.isAssignableFrom(clazz, null);
    }

    /**
     * Check if the checked object is assignable from the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the class (super class or interface)
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S isAssignableFrom(final Class<?> clazz, final CharSequence message, final Object... arguments) {
        return this.isAssignableFrom(clazz, null, message, arguments);
    }

    /**
     * Check if the checked object is assignable from the specified
     * {@code clazz}.
     * 
     * <p>
     * precondition: neither {@link Object} and {@code clazz} can be
     * {@code null}
     * </p>
     * 
     * @param clazz
     *            the class (super class or interface)
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S isAssignableFrom(final Class<?> clazz, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.isAssignableFrom(this.getStep(), clazz, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Check if the checked value matches the specified {@code hashCode}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param hashCode
     *            the expected hash code
     * @return the assertor step
     * @category no_message
     */
    default S hasHashCode(final int hashCode) {
        return this.hasHashCode(hashCode, null);
    }

    /**
     * Check if the checked value matches the specified {@code hashCode}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param hashCode
     *            the expected hash code
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S hasHashCode(final int hashCode, final CharSequence message, final Object... arguments) {
        return this.hasHashCode(hashCode, null, message, arguments);
    }

    /**
     * Check if the checked value matches the specified {@code hashCode}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param hashCode
     *            the expected hash code
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S hasHashCode(final int hashCode, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.hasHashCode(this.getStep(), hashCode, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @param <E>
     *            The type of the exception
     * @return the assertor step
     * @category no_message
     */
    default <E extends Throwable> S validates(final PredicateThrowable<T, E> predicate) {
        return this.validates(predicate, null);
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @param <E>
     *            The type of the exception
     * @return the assertor step
     * @category message
     */
    default <E extends Throwable> S validates(final PredicateThrowable<T, E> predicate, final CharSequence message,
            final Object... arguments) {
        return this.validates(predicate, null, message, arguments);
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @param <E>
     *            The type of the exception
     * @return the assertor step
     * @category localized_message
     */
    default <E extends Throwable> S validates(final PredicateThrowable<T, E> predicate, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return this.get(AssertorObject.validates(this.getStep(), predicate, MessageAssertor.of(locale, message, arguments)));
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @return the assertor step
     * @category no_message
     */
    default S validates(final Predicate<T> predicate) {
        return this.validates(predicate, null);
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category message
     */
    default S validates(final Predicate<T> predicate, final CharSequence message, final Object... arguments) {
        return this.validates(predicate, null, message, arguments);
    }

    /**
     * Validates that the checked object matches the {@code predicate}.
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param predicate
     *            the predicate that validates the object
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on hash code not equal
     * @param arguments
     *            the message arguments
     * @return the assertor step
     * @category localized_message
     */
    default S validates(final Predicate<T> predicate, final Locale locale, final CharSequence message, final Object... arguments) {
        return this.get(AssertorObject.validates(this.getStep(), predicate, MessageAssertor.of(locale, message, arguments)));
    }
}
