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
 * Utility class to prepare the check of {@link Class}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorClass extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the {@link Class} is assignable from
     * the specified super type
     * 
     * <p>
     * precondition: neither classes can be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param superType
     *            the super type
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> isAssignableFrom(final StepAssertor<Class<T>> step, final Class<?> superType,
            final MessageAssertor message) {

        final Predicate<Class<T>> preChecker = (type) -> type != null && superType != null;

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> superType.isAssignableFrom(type);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CLASS.ASSIGNABLE, false,
                new ParameterAssertor<>(superType, EnumType.CLASS));
    }

    /**
     * Prepare the next step to validate if the {@link Class} has the specified
     * name
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param name
     *            the name to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> hasName(final StepAssertor<Class<T>> step, final CharSequence name, final MessageAssertor message) {

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> type.getName().equals(name);

        return AssertorClass.has(step, name, MSG.CLASS.NAME, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Class} has the specified
     * simple name
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param name
     *            the name to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> hasSimpleName(final StepAssertor<Class<T>> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> type.getSimpleName().equals(name);

        return AssertorClass.has(step, name, MSG.CLASS.SIMPLE_NAME, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Class} has the specified
     * canonical name
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param name
     *            the name to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> hasCanonicalName(final StepAssertor<Class<T>> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> type.getCanonicalName().equals(name);

        return AssertorClass.has(step, name, MSG.CLASS.CANONICAL_NAME, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Class} has the specified
     * type name
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param name
     *            the name to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> hasTypeName(final StepAssertor<Class<T>> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> type.getTypeName().equals(name);

        return AssertorClass.has(step, name, MSG.CLASS.TYPE_NAME, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Class} has the specified
     * package name
     * 
     * <p>
     * precondition: {@link Class} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param name
     *            the name to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T> StepAssertor<Class<T>> hasPackageName(final StepAssertor<Class<T>> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<Class<T>, Boolean> checker = (type, not) -> type.getPackage().getName().equals(name);

        return AssertorClass.has(step, name, MSG.CLASS.PACKAGE_NAME, checker, message);
    }

    private static <T> StepAssertor<Class<T>> has(final StepAssertor<Class<T>> step, final CharSequence name, final CharSequence key,
            final BiPredicate<Class<T>, Boolean> checker, final MessageAssertor message) {

        final Predicate<Class<T>> preChecker = (type) -> type != null && StringUtils.isNotEmpty(name);

        return new StepAssertor<>(step, preChecker, checker, false, message, key, false, new ParameterAssertor<>(name, EnumType.CHAR_SEQUENCE));
    }
}
