/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.assertor;

import java.util.Locale;

import fr.landel.utils.commons.function.PredicateThrowable;

/**
 * This class define methods that can be applied on the checked object. To
 * provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStep}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertor} &gt; {@link PredicateStep} &gt; {@link PredicateAssertor} &gt; {@link PredicateStep}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertor} and ends with
 * {@link PredicateStep}. If multiple values are checked, following to their
 * types, the chain can be (checked values: "text", 3):
 *
 * <pre>
 * {@link PredicateAssertorCharSequence} &gt; {@link PredicateAssertorCharSequence} &gt; {@link PredicateAssertorNumber} &gt; {@link PredicateStepNumber}...
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
public interface PredicateAssertor<S extends PredicateStep<S, T>, T> {

    /**
     * Get the net step.
     * 
     * @return the assertor step
     */
    StepAssertor<T> getStep();

    /**
     * The only purpose of this is to avoid the copy of basic methods into
     * children interfaces. This is an indirect way to create specific
     * {@link PredicateStep} by overriding this interface. All children class
     * has to override this method.
     * 
     * @param result
     *            The result
     * @return The predicate step
     */
    @SuppressWarnings("unchecked")
    default S get(final StepAssertor<T> result) {
        return (S) (PredicateStep<S, T>) () -> result;
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
    default PredicateAssertor<S, T> not() {
        return () -> HelperAssertor.not(this.getStep());
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
     */
    default <E extends Throwable> S validates(final PredicateThrowable<T, E> predicate, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return this.get(AssertorObject.validates(this.getStep(), predicate, MessageAssertor.of(locale, message, arguments)));
    }
}
