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
package fr.landel.utils.assertor.helper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.CollectionUtils2;
import fr.landel.utils.commons.EnumChar;
import fr.landel.utils.commons.StringUtils;

/**
 * Prepare the {@link CharSequence} message before calling
 * {@link String#format}. This class replaces the parameters and arguments
 * indexes to replace all values in one call. To increase performance no regular
 * expression is used.
 *
 * @since Aug 9, 2016
 * @author Gilles
 *
 */
public final class HelperMessage extends ConstantsAssertor {

    /**
     * Empty {@code Object} array
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private static final char PERCENT = '%';
    private static final char PREFIX = PERCENT;
    private static final String PREFIX_PERCENT = "%";

    private static final String SUFFIX_CHAR_SEQUENCE = "$s*";
    private static final String SUFFIX_BOOLEAN = "$B*";
    private static final String SUFFIX_INTEGER = "$,d*";
    private static final String SUFFIX_DECIMAL = "$,.3f*";
    private static final String SUFFIX_TIME_YEAR = "$tY*/";
    private static final String SUFFIX_TIME_MONTH = "$tm*/";
    private static final String SUFFIX_TIME_DAY = "$td* ";
    private static final String SUFFIX_TIME_HOUR = "$tH*:";
    private static final String SUFFIX_TIME_MINUTE = "$tM*:";
    private static final String SUFFIX_TIME_SECOND = "$tS* ";
    private static final String SUFFIX_TIME_ZONE = "$tZ*";

    private static final String MISSING_DEFAULT_MESSAGE_KEY = "default message key";
    private static final String MISSING_PARAM_TYPE = "parameter type";

    /**
     * Converts parameters list into array and also converts types to improve
     * readability (ex: {@link Calendar} into {@link java.util.Date})
     * 
     * @param parameters
     *            the input list
     * @return the output array
     */
    public static Object[] convertParams(final List<ParameterAssertor<?>> parameters) {
        if (CollectionUtils.isNotEmpty(parameters)) {
            final List<Object> convertedParams = CollectionUtils2.transformIntoList(parameters, HelperAssertor.PARAM_TRANSFORMER);
            // The object, the type and if it's a checked object
            ParameterAssertor<?> param;
            int calendarField = -1;

            // in order for binary search
            final EnumType[] surroundable = new EnumType[] {EnumType.ARRAY, EnumType.ITERABLE, EnumType.MAP};

            for (int i = 0; i < parameters.size(); i++) {
                param = parameters.get(i);
                if (param.getObject() != null) {
                    if (EnumType.DATE.equals(param.getType()) && Calendar.class.isAssignableFrom(param.getObject().getClass())) {
                        convertedParams.set(i, ((Calendar) param.getObject()).getTime());
                    } else if (EnumType.CALENDAR_FIELD.equals(param.getType())) {
                        calendarField = (Integer) param.getObject();
                        if (CALENDAR_FIELDS.containsKey(calendarField)) {
                            convertedParams.set(i, CALENDAR_FIELDS.get(calendarField));
                        }
                    } else if (EnumType.CLASS.equals(param.getType())) {
                        convertedParams.set(i, ((Class<?>) param.getObject()).getSimpleName());
                    } else if (Arrays.binarySearch(surroundable, param.getType()) > -1) {
                        convertedParams.set(i, HelperMessage.surroundByBrackets(param.getObject(), param.getType()));
                    }
                }
            }
            return convertedParams.toArray();
        }
        return new Object[0];
    }

    private static StringBuilder surroundByBrackets(final Object object, final EnumType type) {
        final StringBuilder sb = new StringBuilder(EnumChar.BRACKET_OPEN.getUnicode());
        if (EnumType.ARRAY.equals(type)) {
            sb.append(StringUtils.joinComma((Object[]) object));
        } else if (EnumType.ITERABLE.equals(type)) {
            sb.append(StringUtils.joinComma((Iterable<?>) object));
        } else { // see surroundable
            Map<?, ?> map = (Map<?, ?>) object;
            sb.append(StringUtils.joinComma(map.entrySet()));
        }
        return sb.append(EnumChar.BRACKET_CLOSE.getUnicode());
    }

    /**
     * Gets the formatted message (if the locale is not specified, function uses
     * the locale defined through {@link Assertor#setLocale(Locale)}). Supports
     * injecting parameters and arguments in message by using %s* or %1$s*
     * 
     * @param message
     *            the message object
     * @return The formatted message
     */
    public static String getMessage(final MessageAssertor message) {

        final Locale locale;
        final String currentMessage;
        final Object[] currentArguments;
        if (message.getMessage() != null) {
            currentMessage = message.getMessage().toString();
            currentArguments = message.getArguments();
            locale = message.getLocale();
        } else {
            currentMessage = HelperMessage.getDefaultMessage(message.getKey(), message.isPrecondition(), message.isNot(),
                    message.getValues(), message.getParameters()).toString();

            currentArguments = null;
            locale = null;
        }

        if (currentMessage.indexOf(PERCENT) > -1) {
            return HelperMessage.getMessage(ConstantsAssertor.DEFAULT_ASSERTION, locale, currentMessage, message.getParameters(),
                    currentArguments);
        } else {
            return currentMessage;
        }
    }

    /**
     * Gets the message (the locale can be change through <code>setLocale</code>
     * ). Supports injecting parameters in message by using %s* or %1$s*
     * 
     * @param defaultString
     *            The default message provided by each method
     * @param locale
     *            The message locale
     * @param message
     *            The user message
     * @param parameters
     *            The method parameters
     * @param arguments
     *            The user arguments
     * @return The formatted message
     */
    public static String getMessage(final CharSequence defaultString, final Locale locale, final CharSequence message,
            final List<ParameterAssertor<?>> parameters, final Object[] arguments) {

        String msg;

        if (StringUtils.isNotEmpty(message)) {
            if (message.toString().indexOf(PREFIX) > -1) {
                Object[] params = HelperMessage.convertParams(parameters);
                Object[] args = ObjectUtils.defaultIfNull(arguments, EMPTY_ARRAY);

                msg = StringUtils.prepareFormat(message, params.length, 1, args.length, 1).toString();

                if (msg.indexOf(PERCENT) > -1) {
                    msg = String.format(Assertor.getLocale(locale), msg, ArrayUtils.addAll(params, args));
                }
            } else {
                msg = message.toString();
            }
        } else {
            msg = defaultString.toString();
        }
        return msg;
    }

    /**
     * Get the message and define that the current condition uses a personalized
     * message, not the default one
     * 
     * @param key
     *            The message key (required, not null)
     * @param precondition
     *            If 'precondition' suffix has to be appended
     * @param not
     *            If 'not' suffix has to be appended
     * @param values
     *            the message values
     * @param parameters
     *            The parameters
     * @return The loaded property
     */
    public static String getDefaultMessage(final CharSequence key, final boolean precondition, final boolean not,
            final CharSequence[] values, final List<ParameterAssertor<?>> parameters) {

        Objects.requireNonNull(key, MISSING_DEFAULT_MESSAGE_KEY);

        final StringBuilder keyProperty = new StringBuilder(key);

        if (precondition) {
            if (!MSG.INVALID_WITHOUT_MESSAGE.equals(key)) {
                keyProperty.append(MSG.PRE);
            }
        } else if (not) {
            // NOT is ignored if precondition mode
            // precondition is the same with or without not
            keyProperty.append(MSG.NOT);
        }

        if (CollectionUtils.isNotEmpty(parameters)) {
            final CharSequence[] arguments = new CharSequence[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                arguments[i] = HelperMessage.getParam(i + 1, parameters.get(i).getType());
            }

            return getProperty(keyProperty, values, arguments);
        } else {
            return getProperty(keyProperty, values);
        }
    }

    /**
     * Generates parameter string with default format for each supported types
     * 
     * @param index
     *            The index
     * @param type
     *            The index parameter type
     * @return the parameter string
     */
    protected static StringBuilder getParam(final int index, final EnumType type) {
        Objects.requireNonNull(type, MISSING_PARAM_TYPE);
        final StringBuilder stringBuilder = new StringBuilder();

        final Consumer<String> append = s -> stringBuilder.append(PREFIX_PERCENT).append(index).append(s);

        switch (type) {
        case BOOLEAN:
            append.accept(SUFFIX_BOOLEAN);
            break;
        case NUMBER_INTEGER:
            append.accept(SUFFIX_INTEGER);
            break;
        case NUMBER_DECIMAL:
            append.accept(SUFFIX_DECIMAL);
            break;
        case DATE:
        case CALENDAR:
            append.accept(SUFFIX_TIME_YEAR);
            append.accept(SUFFIX_TIME_MONTH);
            append.accept(SUFFIX_TIME_DAY);
            append.accept(SUFFIX_TIME_HOUR);
            append.accept(SUFFIX_TIME_MINUTE);
            append.accept(SUFFIX_TIME_SECOND);
            append.accept(SUFFIX_TIME_ZONE);
            break;
        default: // CHAR_SEQUENCE, TEMPORAL, THROWABLE...
            append.accept(SUFFIX_CHAR_SEQUENCE);
        }

        return stringBuilder;
    }
}
