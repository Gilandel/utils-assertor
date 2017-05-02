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
package fr.landel.utils.assertor.predicate;

import java.util.Locale;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorBoolean;

/**
 * This class define methods that can be applied on the checked {@link Boolean}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepBoolean}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorStepBoolean} &gt; {@link PredicateStepBoolean} &gt; {@link PredicateAssertorStepBoolean} &gt; {@link PredicateStepBoolean}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepBoolean} and ends with
 * {@link PredicateStepBoolean}.
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorStepBoolean extends PredicateAssertorStep<PredicateStepBoolean, Boolean> {

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
    default PredicateAssertorStepBoolean not() {
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
     * @category message
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
     * @category localized_message
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
     * @category no_message
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
     * @category message
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
     * @category localized_message
     */
    default PredicateStepBoolean isFalse(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorBoolean.isFalse(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }
}
