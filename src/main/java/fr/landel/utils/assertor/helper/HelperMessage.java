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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import fr.landel.utils.commons.AsciiUtils;
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

    // The regular expression from String#format
    // (just for info, the original regular expression, it's replaced here cause
    // of performance issues)
    // "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])"

    /**
     * the parameter suffix used to detect, if the expression is a standard or a
     * parameter type
     */
    protected static final char PARAM_SUFFIX = '*';

    /**
     * Empty {@code Object} array
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * Flags in regular expression (sorted for binarySearch)
     */
    private static final char[] FLAGS = StringUtils.toChars(" #(+,-0<\\");

    private static final String EMPTY = "";

    private static final char PERCENT = '%';
    private static final char PREFIX = PERCENT;
    private static final char DOT = '.';
    private static final char INDEX_SUFFIX = '$';
    private static final char TIME_LOWERCASE = 't';
    private static final char TIME_UPPERCASE = 'T';

    private static final int STATE_NOTHING = 0;
    private static final int STATE_NUMBER = 1;
    private static final int STATE_INDEX = 2;
    private static final int STATE_FLAGS = 4;
    private static final int STATE_INTEGER = 8;
    private static final int STATE_DOT = 16;
    private static final int STATE_DECIMAL = 32;
    private static final int STATE_TIME = 64;
    private static final int STATE_TYPE = 128;
    private static final int STATE_SUFFIX = 256;

    private static final int SHIFT_LEFT = 10;

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

    /**
     * Parse the string to find parameters and arguments expressions, changes
     * the index to match the combining of the two arrays and removes expression
     * with unavailable parameters or arguments.
     * 
     * <p>
     * Examples:
     * </p>
     * 
     * <pre>
     * "%s '%s*' '%s*' '%1$.2f' '%1$s*' '%s' '%s'"
     * 
     * // will become with 3 parameters and 2 arguments
     * 
     * "%4$s '%1$s' '%2$s' '%4$.2f' '%1$s' '%5$s' ''"
     * </pre>
     * 
     * @param text
     *            the text where to search
     * @param nbParameters
     *            the number of parameters
     * @param nbArguments
     *            the number of arguments
     * @return the new char sequence
     */
    public static CharSequence prepare(final CharSequence text, final int nbParameters, final int nbArguments) {
        return HelperMessage.prepare(text, nbParameters, 1, nbArguments, 1);
    }

    /**
     * Parse the string to find parameters and arguments expressions, changes
     * the index to match the combining of the two arrays and removes expression
     * with unavailable parameters or arguments.
     * 
     * <p>
     * Examples:
     * </p>
     * 
     * <pre>
     * "%s '%s*' '%s*' '%1$.2f' '%1$s*' '%s' '%s'"
     * 
     * // will become with 3 parameters and 2 arguments
     * 
     * "%4$s '%1$s' '%2$s' '%4$.2f' '%1$s' '%5$s' ''"
     * </pre>
     * 
     * @param text
     *            the text where to search
     * @param nbParameters
     *            the number of parameters
     * @param startParameters
     *            the position of the parameter found
     * @param nbArguments
     *            the number of arguments
     * @param startArguments
     *            the position of the first arguments (after parameters)
     * @return the new char sequence
     */
    public static CharSequence prepare(final CharSequence text, final int nbParameters, final int startParameters, final int nbArguments,
            final int startArguments) {

        final char[] chars = StringUtils.toChars(text);

        int start = -1;
        int state = STATE_NOTHING;

        final Set<Group> groups = new TreeSet<>();

        int i;
        Group group = null;

        for (i = 0; i < chars.length; i++) {
            if (group == null && chars[i] == PREFIX) {
                start = i;
                group = new Group(start);
            } else if (group != null) {
                if (state < STATE_INDEX && AsciiUtils.IS_NUMERIC.test(chars[i])) {
                    // (\\d+\\$)? ; the number
                    if (state == STATE_NOTHING) {
                        state = STATE_NUMBER;
                        group.index = 0;
                    }
                    // shift left from 1 number and add the number (convert char
                    // into number)
                    group.index = group.index * SHIFT_LEFT + chars[i] - AsciiUtils.NUM_FIRST;
                } else if (state < STATE_INDEX && chars[i] == INDEX_SUFFIX) {
                    // (\\d+\\$)? ; the dollar
                    state |= STATE_INDEX;
                } else if (state < STATE_INTEGER && Arrays.binarySearch(FLAGS, chars[i]) > -1) {
                    // ([-#+ 0,(\\<]*)?
                    state |= STATE_FLAGS;
                    group.flags.append((char) chars[i]);
                } else if (state < STATE_DOT && chars[i] == DOT) {
                    // (\\d+)?(\\.\\d+)? ; the dot
                    state |= STATE_DOT;
                    group.number.append((char) chars[i]);
                } else if (state < STATE_TIME && AsciiUtils.IS_NUMERIC.test(chars[i])) {
                    // (\\d+)?(\\.\\d+)? ; 8 (integer) for numbers before dot
                    // and 32 (decimal) for numbers after
                    if ((state & STATE_DOT) == STATE_DOT) {
                        state |= STATE_DECIMAL;
                    } else {
                        state |= STATE_INTEGER;
                    }
                    group.number.append((char) chars[i]);
                } else if (state < STATE_TIME && chars[i] == TIME_UPPERCASE || chars[i] == TIME_LOWERCASE) {
                    // [tT]
                    state |= STATE_TIME;
                    group.time = (char) chars[i];
                } else if (state < STATE_TYPE && (AsciiUtils.IS_ALPHA.test(chars[i]) || chars[i] == PERCENT)) {
                    // [a-zA-Z%]
                    state |= STATE_TYPE;
                    group.type.append((char) chars[i]);
                } else if (state < STATE_SUFFIX && chars[i] == PARAM_SUFFIX) {
                    // to detect internal parameter form
                    state |= STATE_SUFFIX;
                    group.asterisk = true;
                } else {
                    if ((state & STATE_INDEX) != STATE_INDEX && group.index > -1) {
                        // no index, so first number detected is the format, not
                        // a number
                        group.number.insert(0, String.valueOf(group.index));
                        group.index = -1;
                    }
                    if ((state & STATE_TYPE) == STATE_TYPE) {
                        // complete
                        group.end = i;
                        groups.add(group);
                    }
                    if (chars[i] == PREFIX) {
                        // prepare the next expression
                        start = i;
                        state = STATE_NOTHING;
                        group = new Group(start);
                    } else {
                        group = null;
                        state = STATE_NOTHING;
                    }
                }
            }
        }

        if (group != null) {
            if ((state & 2) != 2 && group.index > -1) {
                // no index, so first number detected is the format, not a
                // number
                group.number.insert(0, String.valueOf(group.index));
                group.index = -1;
            }

            group.end = i;
            groups.add(group);
        }

        return replaceAndClear(groups, reindex(groups, text, nbParameters, startParameters, nbArguments, startArguments));
    }

    private static StringBuilder reindex(final Set<Group> groups, final CharSequence text, final int nbParameters,
            final int startParameters, final int nbArguments, final int startArguments) {

        int posArg = startParameters - 1 + nbParameters + startArguments;
        int posParam = startParameters;
        final StringBuilder sb = new StringBuilder(text);

        for (Group group : groups) {
            if (group.index == -1) {
                if (group.asterisk) {
                    if (nbParameters < posParam) {
                        group.remove = true;
                    } else {
                        group.index = posParam;
                        posParam++;
                    }
                } else if (nbArguments < posArg - nbParameters) {
                    group.remove = true;
                } else {
                    group.index = posArg;
                    posArg++;
                }
            } else if (!group.asterisk) {
                if (nbArguments < group.index) {
                    group.remove = true;
                } else {
                    group.index += nbParameters;
                }
            } else if (nbParameters < group.index) {
                group.remove = true;
            }
        }
        return sb;
    }

    private static StringBuilder replaceAndClear(final Set<Group> groups, final StringBuilder sb) {
        groups.stream().sorted(Group.REVERSED_COMPARATOR).forEachOrdered((g) -> {
            if (g.remove) {
                sb.replace(g.start, g.end, EMPTY);
            } else {
                g.asterisk = false;
                sb.replace(g.start, g.end, g.toString());
            }
        });

        return sb;
    }

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
     * Gets the message (the locale can be change through <code>setLocale</code>
     * ). Supports injecting parameters in message by using %s* or %1$s*
     * 
     * @param message
     *            the message object
     * @param defaultKey
     *            the default message key
     * @param not
     *            If not has to be appended to the default message key
     * @param parameters
     *            The list of parameters to inject in message
     * @return The message formatted
     */
    protected static String getMessage(final MessageAssertor message, final CharSequence defaultKey, final boolean not,
            final List<ParameterAssertor<?>> parameters) {

        final Locale locale;
        final String currentMessage;
        final Object[] currentArguments;
        if (message != null && message.getMessage() != null) {
            currentMessage = message.getMessage().toString();
            currentArguments = message.getArguments();
            locale = message.getLocale();
        } else {
            currentMessage = HelperMessage.getDefaultMessage(defaultKey, false, not, parameters).toString();
            currentArguments = null;
            locale = null;
        }

        if (currentMessage.indexOf(PERCENT) > -1) {
            return HelperMessage.getMessage(ConstantsAssertor.DEFAULT_ASSERTION, locale, currentMessage, parameters, currentArguments);
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
     * @return The message formatted
     */
    public static String getMessage(final CharSequence defaultString, final Locale locale, final CharSequence message,
            final List<ParameterAssertor<?>> parameters, final Object[] arguments) {

        String msg;

        if (StringUtils.isNotEmpty(message)) {
            if (message.toString().indexOf(PREFIX) > -1) {
                Object[] params = HelperMessage.convertParams(parameters);
                Object[] args = ObjectUtils.defaultIfNull(arguments, EMPTY_ARRAY);

                msg = HelperMessage.prepare(message, params.length, 1, args.length, 1).toString();

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
     * @param parameters
     *            The parameters
     * @return The loaded property
     */
    protected static String getDefaultMessage(final CharSequence key, final boolean precondition, final boolean not,
            final List<ParameterAssertor<?>> parameters) {
        Objects.requireNonNull(key);

        final StringBuilder keyProperty = new StringBuilder(key);

        if (precondition) {
            keyProperty.append(MSG.PRE);
        } else if (not) {
            // NOT is ignored if precondition mode
            // precondition is the same with or without not
            keyProperty.append(MSG.NOT);
        }

        final CharSequence[] arguments = new CharSequence[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            arguments[i] = HelperMessage.getParam(i + 1, parameters.get(i).getType());
        }

        return getProperty(keyProperty, arguments);
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

    /**
     * Class to manage groups and ordering
     *
     * @since Aug 9, 2016
     * @author Gilles
     *
     */
    private static class Group implements Comparable<Group> {

        private static final Comparator<Group> REVERSED_COMPARATOR = new Comparator<Group>() {
            @Override
            public int compare(final Group o1, final Group o2) {
                return o2.start - o1.start;
            }
        };

        private final int start;
        private int end;
        private int index = -1;
        private StringBuilder flags;
        private StringBuilder number;
        private char time;
        private StringBuilder type;
        private boolean asterisk;

        private boolean remove;

        /**
         * Constructor
         *
         * @param start
         *            the start index
         */
        public Group(final int start) {
            this.start = start;
            this.flags = new StringBuilder();
            this.number = new StringBuilder();
            this.type = new StringBuilder();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(PREFIX_PERCENT);
            sb.append(this.index).append(INDEX_SUFFIX);
            sb.append(this.flags);
            sb.append(this.number);
            if (this.time > 0) {
                sb.append(this.time);
            }
            sb.append(this.type);
            return sb.toString();
        }

        @Override
        public int compareTo(final Group o) {
            return this.start - o.start;
        }
    }
}
