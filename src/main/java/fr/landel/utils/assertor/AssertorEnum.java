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
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class to prepare the check of {@link Enum}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorEnum extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the {@link Enum} has the specified
     * name (insensitive case)
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the previous step
     * @param name
     *            the enumeration property name
     * @param message
     *            the message if invalid
     * @param <T>
     *            the enumeration type
     * @return the next step
     */
    protected static <T extends Enum<T>> StepAssertor<T> hasNameIgnoreCase(final StepAssertor<T> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> object.name().equalsIgnoreCase(name.toString());

        return AssertorEnum.hasName(step, name, MSG.ENUM.NAME, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Enum} has the specified
     * name
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the previous step
     * @param name
     *            the enumeration property name
     * @param message
     *            the message if invalid
     * @param <T>
     *            the enumeration type
     * @return the next step
     */
    protected static <T extends Enum<T>> StepAssertor<T> hasName(final StepAssertor<T> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> object.name().equals(name);

        return AssertorEnum.hasName(step, name, MSG.ENUM.NAME, checker, message);
    }

    private static <T extends Enum<T>> StepAssertor<T> hasName(final StepAssertor<T> step, final CharSequence name, final CharSequence key,
            final BiPredicate<T, Boolean> checker, final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(name);

        return new StepAssertor<>(step, preChecker, checker, false, message, key, false,
                new ParameterAssertor<>(name, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link Enum} has the specified
     * ordinal
     * 
     * <p>
     * precondition: the {@code ordinal} cannot be lower than 0
     * </p>
     * 
     * @param step
     *            the previous step
     * @param ordinal
     *            the enumeration property ordinal
     * @param message
     *            the message if invalid
     * @param <T>
     *            the enumeration type
     * @return the next step
     */
    protected static <T extends Enum<T>> StepAssertor<T> hasOrdinal(final StepAssertor<T> step, final int ordinal,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && ordinal >= 0;

        final BiPredicate<T, Boolean> checker = (object, not) -> object.ordinal() == ordinal;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ENUM.ORDINAL, false,
                new ParameterAssertor<>(ordinal, EnumType.NUMBER_INTEGER));
    }
}
