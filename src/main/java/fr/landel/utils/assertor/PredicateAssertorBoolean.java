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
 * This class define methods that can be applied on the checked {@link Boolean}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepBoolean}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorBoolean} &gt; {@link PredicateStepBoolean} &gt; {@link PredicateAssertorBoolean} &gt; {@link PredicateStepBoolean}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorBoolean} and ends
 * with {@link PredicateStepBoolean}.
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorBoolean extends PredicateAssertor<PredicateStepBoolean, Boolean> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepBoolean get(final StepAssertor<Boolean> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorBoolean not() {
        return () -> HelperAssertor.not(this.getStep());
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
     */
    default PredicateStepBoolean isTrue() {
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
     */
    default PredicateStepBoolean isTrue(final CharSequence message, final Object... arguments) {
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
     */
    default PredicateStepBoolean isTrue(final Locale locale, final CharSequence message, final Object... arguments) {
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
     */
    default PredicateStepBoolean isFalse() {
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
     */
    default PredicateStepBoolean isFalse(final CharSequence message, final Object... arguments) {
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
     */
    default PredicateStepBoolean isFalse(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorBoolean.isFalse(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }
}
