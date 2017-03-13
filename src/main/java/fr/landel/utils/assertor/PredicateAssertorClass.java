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

/**
 * This class define methods that can be applied on the checked {@link Class}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepClass}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorClass} &gt; {@link PredicateStepClass} &gt; {@link PredicateAssertorClass} &gt; {@link PredicateStepClass}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorClass} and ends with
 * {@link PredicateStepClass}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorClass<T> extends PredicateAssertor<PredicateStepClass<T>, Class<T>> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepClass<T> get(final StepAssertor<Class<T>> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorClass<T> not() {
        return () -> HelperAssertor.not(getStep());
    }

    /**
     * Asserts that the given {@link Class} is assignable from the specified
     * super type {@code clazz}.
     * 
     * <p>
     * precondition: neither classes can be null
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).isAssignableFrom(superType).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @return The operator
     */
    @Override
    default PredicateStepClass<T> isAssignableFrom(final Class<?> clazz) {
        return this.isAssignableFrom(clazz, null);
    }

    /**
     * Asserts that the given {@link Class} is assignable from the specified
     * super type {@code clazz}.
     * 
     * <p>
     * precondition: neither classes can be null
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).isAssignableFrom(superType, "not assignable").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    @Override
    default PredicateStepClass<T> isAssignableFrom(final Class<?> clazz, final CharSequence message, final Object... arguments) {
        return this.isAssignableFrom(clazz, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} is assignable from the specified
     * super type {@code clazz}.
     * 
     * <p>
     * precondition: neither classes can be null
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).isAssignableFrom(superType, Locale.US, "not assignable").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    @Override
    default PredicateStepClass<T> isAssignableFrom(final Class<?> clazz, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.isAssignableFrom(this.getStep(), clazz, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @return The operator
     */
    default PredicateStepClass<T> hasName(final CharSequence name) {
        return this.hasName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.hasName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified simple
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasSimpleName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @return The operator
     */
    default PredicateStepClass<T> hasSimpleName(final CharSequence name) {
        return this.hasSimpleName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified simple
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasSimpleName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasSimpleName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasSimpleName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified simple
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasSimpleName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasSimpleName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.hasSimpleName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified canonical
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasCanonicalName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @return The operator
     */
    default PredicateStepClass<T> hasCanonicalName(final CharSequence name) {
        return this.hasCanonicalName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified canonical
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasCanonicalName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasCanonicalName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasCanonicalName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified canonical
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasCanonicalName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasCanonicalName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.hasCanonicalName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified type {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasTypeName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @return The operator
     */
    default PredicateStepClass<T> hasTypeName(final CharSequence name) {
        return this.hasTypeName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified type {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasTypeName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasTypeName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasTypeName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified type {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasTypeName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasTypeName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.hasTypeName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified package
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasPackageName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @return The operator
     */
    default PredicateStepClass<T> hasPackageName(final CharSequence name) {
        return this.hasPackageName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified package
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasPackageName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasPackageName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasPackageName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified package
     * {@code name}.
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(clazz).hasPackageName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The {@link Class} name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepClass<T> hasPackageName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorClass.hasPackageName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }
}
