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
import java.util.regex.Pattern;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.StringUtils;

/**
 * Utility class to prepare the check of {@link CharSequence}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorCharSequence extends ConstantsAssertor {

    private static final String CR = "\r";
    private static final String LF = "\n";

    /**
     * Prepare the next step to validate if the {@link CharSequence} has the
     * specified length
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and size cannot
     * be lower than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the expected length
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> hasLength(final StepAssertor<T> step, final int length,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> length >= 0 && object != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> object.length() == length;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.LENGTH, false,
                new ParameterAssertor<>(length, EnumType.NUMBER_INTEGER));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is
     * {@code null} or empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isEmpty(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.isEmpty(object);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.EMPTY, false);
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is NOT
     * {@code null} and NOT empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isNotEmpty(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.isNotEmpty(object);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.EMPTY, true);
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is
     * {@code null}, empty or blank
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isBlank(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.isBlank(object);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.BLANK, false);
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is NOT
     * {@code null}, NOT empty and NOT blank
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isNotBlank(final StepAssertor<T> step, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.isNotBlank(object);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.BLANK, true);
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is equal to
     * {@code CharSequence}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param string
     *            the string to compare
     * @param ignoreCase
     *            {@code true} to ignore the case
     * @param ignoreLineReturns
     *            {@code true} to ignore the line return characters
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isEqual(final StepAssertor<T> step, final CharSequence string,
            final boolean ignoreCase, final boolean ignoreLineReturns, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> isEqualInternal(string, object, ignoreCase, ignoreLineReturns);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.EQUALS, false,
                new ParameterAssertor<>(string, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} is NOT
     * equal to {@code CharSequence}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param string
     *            the string to compare
     * @param ignoreCase
     *            {@code true} to ignore the case
     * @param ignoreLineReturns
     *            {@code true} to ignore the line return characters
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> isNotEqual(final StepAssertor<T> step, final CharSequence string,
            final boolean ignoreCase, final boolean ignoreLineReturns, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (object, not) -> !isEqualInternal(string, object, ignoreCase, ignoreLineReturns);

        return new StepAssertor<>(step, checker, false, message, MSG.CSQ.EQUALS, true,
                new ParameterAssertor<>(string, EnumType.CHAR_SEQUENCE));
    }

    private static <T extends CharSequence> boolean isEqualInternal(final T object1, final CharSequence object2, final boolean ignoreCase,
            final boolean ignoreLineReturns) {
        boolean step = false;
        if (object1 == object2) {
            step = true;
        } else if (object1 != null && object2 != null) {
            final String s1;
            final String s2;

            if (ignoreLineReturns) {
                final StringBuilder sb1 = new StringBuilder(object1);
                final StringBuilder sb2 = new StringBuilder(object2);

                deleteLineReturns(sb1);
                deleteLineReturns(sb2);

                s1 = sb1.toString();
                s2 = sb2.toString();
            } else {
                s1 = object1.toString();
                s2 = object2.toString();
            }

            if (ignoreCase) {
                step = s1.equalsIgnoreCase(s2);
            } else {
                step = s1.equals(s2);
            }
        }
        return step;
    }

    private static void deleteLineReturns(final StringBuilder sb) {
        int pos = 0;
        while ((pos = sb.indexOf(CR, pos)) > -1) {
            sb.deleteCharAt(pos);
        }
        pos = 0;
        while ((pos = sb.indexOf(LF, pos)) > -1) {
            sb.deleteCharAt(pos);
        }
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} contains
     * the specified character
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code character} can be
     * {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param character
     *            the character to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> contains(final StepAssertor<T> step, final Character character,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && character != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> object.toString().indexOf(character) > -1;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.CONTAINS, false,
                new ParameterAssertor<>(character, EnumType.CHARACTER));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} contains
     * the specified substring
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param substring
     *            the substring to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> contains(final StepAssertor<T> step, final CharSequence substring,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(substring);

        final BiPredicate<T, Boolean> checker = (object, not) -> containsCharSequence(object, substring);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.CONTAINS, false,
                new ParameterAssertor<>(substring, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} starts with
     * the specified substring
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param substring
     *            the substring to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> startsWith(final StepAssertor<T> step, final CharSequence substring,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(substring);

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.startsWith(object, substring);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.STARTS, false,
                new ParameterAssertor<>(substring, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} starts with
     * the specified substring (insensitive case)
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param substring
     *            the substring to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> startsWithIgnoreCase(final StepAssertor<T> step, final CharSequence substring,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(substring);

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.startsWithIgnoreCase(object, substring);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.STARTS, false,
                new ParameterAssertor<>(substring, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} ends with
     * the specified substring
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param substring
     *            the substring to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> endsWith(final StepAssertor<T> step, final CharSequence substring,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(substring);

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.endsWith(object, substring);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.ENDS, false,
                new ParameterAssertor<>(substring, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} ends with
     * the specified substring (insensitive case)
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and
     * {@code substring} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param substring
     *            the substring to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> endsWithIgnoreCase(final StepAssertor<T> step, final CharSequence substring,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(substring);

        final BiPredicate<T, Boolean> checker = (object, not) -> StringUtils.endsWithIgnoreCase(object, substring);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.ENDS, false,
                new ParameterAssertor<>(substring, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} matches the
     * specified pattern
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param pattern
     *            the pattern to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> matches(final StepAssertor<T> step, final Pattern pattern,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && pattern != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> pattern.matcher(object).matches();

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.MATCHES, false,
                new ParameterAssertor<>(pattern, EnumType.UNKNOWN));
    }

    /**
     * Prepare the next step to validate if the {@link CharSequence} matches the
     * specified regular expression
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param regex
     *            the regular expression to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> matches(final StepAssertor<T> step, final CharSequence regex,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(regex);

        final BiPredicate<T, Boolean> checker = (object, not) -> Pattern.matches(regex.toString(), object);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.MATCHES, false,
                new ParameterAssertor<>(regex, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Prepare the next step to validate if the specified pattern can be found
     * in the {@link CharSequence}
     * 
     * <p>
     * precondition: neither {@link CharSequence} or {@code pattern} can be
     * {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param pattern
     *            the pattern to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> find(final StepAssertor<T> step, final Pattern pattern,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && pattern != null;

        final BiPredicate<T, Boolean> checker = (object, not) -> pattern.matcher(object).find();

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.FIND, false,
                new ParameterAssertor<>(pattern, EnumType.UNKNOWN));
    }

    /**
     * Prepare the next step to validate if the specified regular expression can
     * be found in the {@link CharSequence}
     * 
     * <p>
     * precondition: {@link CharSequence} cannot be {@code null} and the regular
     * expression cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param regex
     *            the regular expression to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the char sequence type
     * @return the next step
     */
    public static <T extends CharSequence> StepAssertor<T> find(final StepAssertor<T> step, final CharSequence regex,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (object) -> object != null && StringUtils.isNotEmpty(regex);

        final BiPredicate<T, Boolean> checker = (object, not) -> Pattern.compile(regex.toString()).matcher(object).find();

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.CSQ.FIND, false,
                new ParameterAssertor<>(regex, EnumType.CHAR_SEQUENCE));
    }

    /**
     * Search in char sequence, if the specified sub sequence exists in.
     * {@code null} values have to be checked first.
     * 
     * @param textToSearch
     *            where to search
     * @param substring
     *            chat to search
     * @return {@code true} if found, {@code false} otherwise
     */
    private static boolean containsCharSequence(final CharSequence textToSearch, final CharSequence substring) {
        int p = 0;
        int l = substring.length();
        for (int i = 0; i < textToSearch.length() && p < l; i++) {
            if (textToSearch.charAt(i) == substring.charAt(p)) {
                p++;
            } else {
                p = 0;
            }
        }
        return p == l;
    }
}
