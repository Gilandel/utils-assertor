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

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Utility class to prepare the check of {@link Throwable}
 *
 * @since Mar 21, 2017
 * @author Gilles
 *
 */
public class AssertorThrowable extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the {@link Throwable} is an instance
     * of {@code type} and has the specified message
     * 
     * <p>
     * precondition: throwable and type cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param type
     *            the exception type
     * @param exceptionMessage
     *            the exception message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> isInstanceOf(final StepAssertor<T> step, final Class<?> type,
            final CharSequence exceptionMessage, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && type != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> type.isInstance(throwable)
                && Objects.equals(exceptionMessage, throwable.getMessage());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.INSTANCE, false,
                new ParameterAssertor<>(type, EnumType.CLASS), new ParameterAssertor<>(exceptionMessage, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} is an instance
     * of {@code type} and matches the specified pattern
     * 
     * <p>
     * precondition: throwable, type and pattern cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param type
     *            the exception type
     * @param pattern
     *            the pattern to check the message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> isInstanceOf(final StepAssertor<T> step, final Class<?> type,
            final Pattern pattern, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && type != null && pattern != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> type.isInstance(throwable) && throwable.getMessage() != null
                && pattern.matcher(throwable.getMessage()).matches();

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.INSTANCE_PATTERN, false,
                new ParameterAssertor<>(type, EnumType.CLASS), new ParameterAssertor<>(pattern, EnumType.UNKNOWN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} is assignable
     * from the {@code type} and has the specified message
     * 
     * <p>
     * precondition: throwable and type cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param type
     *            the exception type
     * @param exceptionMessage
     *            the exception message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> isAssignableFrom(final StepAssertor<T> step, final Class<?> type,
            final CharSequence exceptionMessage, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && type != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> type.isAssignableFrom(throwable.getClass())
                && Objects.equals(exceptionMessage, throwable.getMessage());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.ASSIGNABLE, false,
                new ParameterAssertor<>(type, EnumType.CLASS), new ParameterAssertor<>(exceptionMessage, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} is assignable
     * from the {@code type} and matches the specified pattern
     * 
     * <p>
     * precondition: throwable, type and pattern cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param type
     *            the exception type
     * @param pattern
     *            the pattern to check the message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> isAssignableFrom(final StepAssertor<T> step, final Class<?> type,
            final Pattern pattern, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && type != null && pattern != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> type.isAssignableFrom(throwable.getClass())
                && throwable.getMessage() != null && pattern.matcher(throwable.getMessage()).matches();

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.ASSIGNABLE_PATTERN, false,
                new ParameterAssertor<>(type, EnumType.CLASS), new ParameterAssertor<>(pattern, EnumType.UNKNOWN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has no cause
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseNull(final StepAssertor<T> step, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> throwable.getCause() == null;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE, false);
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has a cause
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseNotNull(final StepAssertor<T> step, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null;

        final BiPredicate<T, Boolean> checker = (throwable, not) -> throwable.getCause() != null;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE, true);
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause with
     * instance of {@code causeType}
     * 
     * <p>
     * precondition: throwable and causeType cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            the specified message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseInstanceOf(final StepAssertor<T> step, final Class<?> causeType,
            final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively, t -> causeType.isInstance(t));

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_INSTANCE, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause with
     * instance of {@code causeType} and has the specified message
     * 
     * <p>
     * precondition: throwable and causeType cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param exceptionMessage
     *            the exception message
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            the specified message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseInstanceOf(final StepAssertor<T> step, final Class<?> causeType,
            final CharSequence exceptionMessage, final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively,
                t -> causeType.isInstance(t) && Objects.equals(exceptionMessage, t.getMessage()));

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_INSTANCE_MESSAGE, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(exceptionMessage, EnumType.CHAR_SEQUENCE),
                new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause with
     * instance of {@code causeType} and matches the specified pattern
     * 
     * <p>
     * precondition: throwable, causeType and pattern cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param pattern
     *            the pattern to check the message
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseInstanceOf(final StepAssertor<T> step, final Class<?> causeType,
            final Pattern pattern, final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null && pattern != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively, throwable -> causeType.isInstance(throwable)
                && throwable.getMessage() != null && pattern.matcher(throwable.getMessage()).matches());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_INSTANCE_PATTERN, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(pattern, EnumType.UNKNOWN),
                new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause
     * assignable from {@code causeType}
     * 
     * <p>
     * precondition: throwable and causeType cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            the specified message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseAssignableFrom(final StepAssertor<T> step, final Class<?> causeType,
            final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively,
                throwable -> causeType.isAssignableFrom(throwable.getClass()));

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_ASSIGNABLE, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause
     * assignable from {@code causeType} and has the specified message
     * 
     * <p>
     * precondition: throwable and causeType cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param exceptionMessage
     *            the exception message
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            the specified message
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseAssignableFrom(final StepAssertor<T> step, final Class<?> causeType,
            final CharSequence exceptionMessage, final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively,
                throwable -> causeType.isAssignableFrom(throwable.getClass()) && Objects.equals(exceptionMessage, throwable.getMessage()));

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_ASSIGNABLE_MESSAGE, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(exceptionMessage, EnumType.CHAR_SEQUENCE),
                new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    /**
     * Prepare the next step to validate if the {@link Throwable} has cause
     * assignable from {@code causeType} and matches the specified pattern
     * 
     * <p>
     * precondition: throwable, causeType and pattern cannot be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param causeType
     *            the exception type of cause
     * @param pattern
     *            the pattern to check the message
     * @param resursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            the message if invalid
     * @param <T>
     *            the class type
     * @return the next step
     */
    protected static <T extends Throwable> StepAssertor<T> hasCauseAssignableFrom(final StepAssertor<T> step, final Class<?> causeType,
            final Pattern pattern, final boolean resursively, final MessageAssertor message) {

        final Predicate<T> preChecker = (throwable) -> throwable != null && causeType != null && pattern != null;

        final BiPredicate<T, Boolean> checker = check(causeType, resursively, throwable -> causeType.isAssignableFrom(throwable.getClass())
                && throwable.getMessage() != null && pattern.matcher(throwable.getMessage()).matches());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.THROWABLE.CAUSE_ASSIGNABLE_PATTERN, false,
                new ParameterAssertor<>(causeType, EnumType.CLASS), new ParameterAssertor<>(pattern, EnumType.UNKNOWN),
                new ParameterAssertor<>(resursively, EnumType.BOOLEAN));
    }

    private static <T extends Throwable> BiPredicate<T, Boolean> check(final Class<?> superType, final boolean resursively,
            final Predicate<Throwable> predicate) {

        final BiPredicate<T, Boolean> checker;
        if (resursively) {
            checker = (throwable, not) -> {
                Throwable t = throwable;
                while ((t = t.getCause()) != null) {
                    if (predicate.test(t)) {
                        return true;
                    }
                }
                return false;
            };
        } else {
            checker = (throwable, not) -> predicate.test(throwable);
        }
        return checker;
    }
}
