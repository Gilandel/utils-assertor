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
package fr.landel.utils.assertor.utils;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.ClassUtils;

/**
 * Utility class to prepare the check of {@link Object}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorObject extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if {@link Object} is {@code null}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isNull(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> object == null;

        return new StepAssertor<>(step, checker, false, message, MSG.OBJECT.NULL, false);
    }

    /**
     * Prepare the next step to validate if {@link Object} is NOT {@code null}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isNotNull(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> object != null;

        return new StepAssertor<>(step, checker, false, message, MSG.OBJECT.NULL, true);
    }

    /**
     * Prepare the next step to validate if {@link Object} is equal to
     * {@code object}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param object
     *            the object to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isEqual(final StepAssertor<T> step, final Object object, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object1, not) -> isEqualInternal(object1, object);

        return new StepAssertor<>(step, checker, false, message, MSG.OBJECT.EQUALS, false, new ParameterAssertor<>(object));
    }

    /**
     * Prepare the next step to validate if {@link Object} is NOT equal to
     * {@code object}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param object
     *            the object to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isNotEqual(final StepAssertor<T> step, final Object object, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object1, not) -> !isEqualInternal(object1, object);

        return new StepAssertor<>(step, checker, false, message, MSG.OBJECT.EQUALS, true, new ParameterAssertor<>(object));
    }

    private static <T> boolean isEqualInternal(final T object1, final Object object2) {
        boolean step = false;
        if (object1 == object2) {
            step = true;
        } else if (object1 != null && object1.equals(object2)) {
            step = true;
        }
        return step;
    }

    /**
     * Prepare the next step to validate if {@link Object} is an instance of
     * {@code type}
     * 
     * <p>
     * precondition: neither {@link Object} and {@code type} can be {@code null}
     * </p>
     * 
     * @param step
     *            the previous step
     * @param type
     *            the type to check
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isInstanceOf(final StepAssertor<T> step, final Class<?> type, final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && type != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> type.isInstance(object);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.OBJECT.INSTANCE, false,
                new ParameterAssertor<>(type, EnumType.CLASS));
    }

    /**
     * Prepare the next step to validate if {@link Object} is assignable from
     * {@code type}
     * 
     * <p>
     * precondition: neither {@link Object} and {@code superType} can be
     * {@code null}
     * </p>
     * 
     * @param step
     *            the previous step
     * @param superType
     *            the type to check against
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> isAssignableFrom(final StepAssertor<T> step, final Class<?> superType,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && superType != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> superType.isAssignableFrom(ClassUtils.getClass(object));

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.OBJECT.ASSIGNABLE, false,
                new ParameterAssertor<>(superType, EnumType.CLASS));
    }

    /**
     * Prepare the next step to validate if {@link Object} has the specified
     * {@code hashCode}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param hashCode
     *            the hashCode to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> hasHashCode(final StepAssertor<T> step, final int hashCode, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> Objects.hashCode(object) == hashCode;

        return new StepAssertor<>(step, checker, false, message, MSG.OBJECT.HASH_CODE, false,
                new ParameterAssertor<>(hashCode, EnumType.NUMBER_INTEGER));
    }

    /**
     * Prepare the next step to validate if {@link Object} validates the
     * {@code predicate}
     * 
     * <p>
     * precondition: {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the previous step
     * @param predicate
     *            the predicate to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            The object type
     * @return the next step
     */
    public static <T> StepAssertor<T> validates(final StepAssertor<T> step, final Predicate<T> predicate, final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> predicate != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> predicate.test(object);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.OBJECT.VALIDATES, false,
                new ParameterAssertor<>(predicate, EnumType.UNKNOWN));
    }
}
