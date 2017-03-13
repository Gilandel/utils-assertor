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

import java.util.function.BiPredicate;

/**
 * Utility class to prepare the check of {@link Boolean}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorBoolean extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the {@link Boolean} is {@code true}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @return the next step
     */
    protected static StepAssertor<Boolean> isTrue(final StepAssertor<Boolean> step, final MessageAssertor message) {

        final BiPredicate<Boolean, Boolean> checker = (bool, not) -> Boolean.TRUE.equals(bool);

        return new StepAssertor<>(step, checker, false, message, MSG.BOOLEAN.TRUE, false);
    }

    /**
     * Prepare the next step to validate if the {@link Boolean} is {@code false}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @return the next step
     */
    protected static StepAssertor<Boolean> isFalse(final StepAssertor<Boolean> step, final MessageAssertor message) {

        final BiPredicate<Boolean, Boolean> checker = (bool, not) -> Boolean.FALSE.equals(bool);

        return new StepAssertor<>(step, checker, false, message, MSG.BOOLEAN.FALSE, false);
    }
}
