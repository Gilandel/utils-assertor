/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
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

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;

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
    public static <T extends Enum<T>> StepAssertor<T> hasNameIgnoreCase(final StepAssertor<T> step, final CharSequence name,
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
    public static <T extends Enum<T>> StepAssertor<T> hasName(final StepAssertor<T> step, final CharSequence name,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> name.equals(object.name());

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
     * precondition: the enum cannot be {@code null} and {@code ordinal} cannot
     * be lower than 0
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
    public static <T extends Enum<T>> StepAssertor<T> hasOrdinal(final StepAssertor<T> step, final int ordinal,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && ordinal >= 0;

        final BiPredicate<T, Boolean> checker = (object, not) -> object.ordinal() == ordinal;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ENUM.ORDINAL, false,
                new ParameterAssertor<>(ordinal, EnumType.NUMBER_INTEGER));
    }
}
