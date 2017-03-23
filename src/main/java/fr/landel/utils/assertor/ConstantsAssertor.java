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

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.landel.utils.commons.EnumChar;

/**
 * Assertor constants
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
public class ConstantsAssertor {

    /**
     * The logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(Assertor.class);

    // ---------- DEFAULT

    /**
     * Default assertion
     */
    protected static final String DEFAULT_ASSERTION = "Assertion failed";

    // ---------- PROPERTIES / MESSAGES

    /**
     * Messages properties (for now doesn't support locale, maybe later)
     */
    protected static final Properties PROPS;
    static {
        PROPS = new Properties();
        try (InputStream is = Assertor.class.getClassLoader().getResourceAsStream("assertor_messages.properties")) {
            PROPS.load(is);
        } catch (IOException e) {
            LOGGER.error("Cannot load the assertor configuration file");
        }
    }

    // ---------- OTHERS

    protected static final Map<Integer, String> CALENDAR_FIELDS;
    static {
        Map<Integer, String> map = new HashMap<>();
        map.put(Calendar.ERA, "ERA");
        map.put(Calendar.YEAR, "YEAR");
        map.put(Calendar.MONTH, "MONTH");
        map.put(Calendar.WEEK_OF_YEAR, "WEEK_OF_YEAR");
        map.put(Calendar.WEEK_OF_MONTH, "WEEK_OF_MONTH");
        map.put(Calendar.DATE, "DATE");
        map.put(Calendar.DAY_OF_MONTH, "DAY_OF_MONTH");
        map.put(Calendar.DAY_OF_YEAR, "DAY_OF_YEAR");
        map.put(Calendar.DAY_OF_WEEK, "DAY_OF_WEEK");
        map.put(Calendar.DAY_OF_WEEK_IN_MONTH, "DAY_OF_WEEK_IN_MONTH");
        map.put(Calendar.AM_PM, "AM_PM");
        map.put(Calendar.HOUR, "HOUR");
        map.put(Calendar.HOUR_OF_DAY, "HOUR_OF_DAY");
        map.put(Calendar.MINUTE, "MINUTE");
        map.put(Calendar.SECOND, "SECOND");
        map.put(Calendar.MILLISECOND, "MILLISECOND");
        CALENDAR_FIELDS = Collections.unmodifiableMap(map);
    }

    // ---------- PROPERTIES LOADER

    /**
     * Returns the property associated to the key with replaced arguments or the
     * default string if not found {@link ConstantsAssertor#DEFAULT_ASSERTION}.
     * 
     * @param key
     *            The property key
     * @param arguments
     *            The arguments to replace
     * @return The property
     */
    protected static String getProperty(final CharSequence key, final CharSequence... arguments) {
        String property = PROPS.getProperty(String.valueOf(key));
        if (property != null) {
            if (ArrayUtils.isNotEmpty(arguments)) {
                for (int i = 0; i < arguments.length; i++) {
                    property = property.replace(new StringBuilder(EnumChar.BRACE_OPEN.toString()).append(i).append(EnumChar.BRACE_CLOSE),
                            arguments[i]);
                }
            }

            return property;
        } else {
            return DEFAULT_ASSERTION;
        }
    }

    // ---------- SUB PROPERTIES

    /**
     * Messages constants
     *
     * @since Aug 10, 2016
     * @author Gilles
     *
     */
    protected static interface MSG {

        /**
         * Suffix for properties of type NOT
         */
        String NOT = ".not";

        /**
         * Suffix for properties of type prerequisites
         */
        String PRE = ".pre";

        /**
         * Invalid combination with message
         */
        String INVALID_WITH_MESSAGE = "invalid.with.message";

        /**
         * Invalid combination without message
         */
        String INVALID_WITHOUT_MESSAGE = "invalid.without.message";

        /**
         * Object constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface OBJECT {

            /**
             * Message key for object null
             */
            String NULL = "object.null";

            /**
             * Message key for object equals
             */
            String EQUALS = "object.equals";

            /**
             * Message key for object instance of
             */
            String INSTANCE = "object.instance";

            /**
             * Message key for object assignable from
             */
            String ASSIGNABLE = "object.assignable";

            /**
             * Message key for object hash code
             */
            String HASH_CODE = "object.hashCode";

            /**
             * Message key for object validates predicate
             */
            String VALIDATES = "object.validates";
        }

        /**
         * Boolean constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface BOOLEAN {

            /**
             * Message key for boolean is true
             */
            String TRUE = "boolean.true";

            /**
             * Message key for boolean is false
             */
            String FALSE = "boolean.false";
        }

        /**
         * Class constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface CLASS {

            /**
             * Message key for class assignable from
             */
            String ASSIGNABLE = "class.assignable";

            /**
             * Message key for class name
             */
            String NAME = "class.name";

            /**
             * Message key for class simple name
             */
            String SIMPLE_NAME = "class.simpleName";

            /**
             * Message key for class canonical name
             */
            String CANONICAL_NAME = "class.canonicalName";

            /**
             * Message key for class type name
             */
            String TYPE_NAME = "class.typeName";

            /**
             * Message key for class package name
             */
            String PACKAGE_NAME = "class.packageName";
        }

        /**
         * Number constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface NUMBER {

            /**
             * Message key for number equals
             */
            String EQUALS = "number.equals";

            /**
             * Message key for number zero
             */
            String ZERO = "number.zero";

            /**
             * Message key for positive number
             */
            String POSITIVE = "number.positive";

            /**
             * Message key for negative number
             */
            String NEGATIVE = "number.negative";

            /**
             * Message key for number greater than
             */
            String GT = "number.gt";

            /**
             * Message key for number greater than or equal to
             */
            String GTE = "number.gte";

            /**
             * Message key for number lower than
             */
            String LT = "number.lt";

            /**
             * Message key for number lower than or equal to
             */
            String LTE = "number.lte";
        }

        /**
         * Char sequence constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface CSQ {

            /**
             * Message key for char sequence length
             */
            String LENGTH = "csq.length";

            /**
             * Message key for empty char sequence
             */
            String EMPTY = "csq.empty";

            /**
             * Message key for char sequence blank
             */
            String BLANK = "csq.blank";

            /**
             * Message key for char sequence equals
             */
            String EQUALS = "csq.equals";

            /**
             * Message key for char sequence contains substring
             */
            String CONTAINS = "csq.contains";

            /**
             * Message key for char sequence starts with
             */
            String STARTS = "csq.starts";

            /**
             * Message key for char sequence ends with
             */
            String ENDS = "csq.ends";

            /**
             * Message key for char sequence matches
             */
            String MATCHES = "csq.matches";

            /**
             * Message key for char sequence find
             */
            String FIND = "csq.find";
        }

        /**
         * Date constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface DATE {

            /**
             * Message key for date equals
             */
            String EQUALS = "date.equals";

            /**
             * Message key for date around
             */
            String AROUND = "date.around";

            /**
             * Message key for date after
             */
            String AFTER = "date.after";

            /**
             * Message key for date after or equals
             */
            String AFTER_OR_EQUALS = "date.afterOrEqual";

            /**
             * Message key for date before
             */
            String BEFORE = "date.before";

            /**
             * Message key for date before or equals
             */
            String BEFORE_OR_EQUALS = "date.beforeOrEqual";
        }

        /**
         * Temporal constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface TEMPORAL {

            /**
             * Message key for temporal equals
             */
            String EQUALS = "temporal.equals";

            /**
             * Message key for temporal around
             */
            String AROUND = "temporal.around";

            /**
             * Message key for temporal after
             */
            String AFTER = "temporal.after";

            /**
             * Message key for temporal after or equals
             */
            String AFTER_OR_EQUALS = "temporal.afterOrEqual";

            /**
             * Message key for temporal before
             */
            String BEFORE = "temporal.before";

            /**
             * Message key for temporal before or equals
             */
            String BEFORE_OR_EQUALS = "temporal.beforeOrEqual";
        }

        /**
         * Array constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface ARRAY {

            /**
             * Message key for array length
             */
            String LENGTH = "array.length";

            /**
             * Message key for empty array
             */
            String EMPTY = "array.empty";

            /**
             * Message key for array contains object
             */
            String CONTAINS_OBJECT = "array.contains.object";

            /**
             * Message key for array contains all values
             */
            String CONTAINS_ALL = "array.contains.array.all";

            /**
             * Message key for array contains any values
             */
            String CONTAINS_ANY = "array.contains.array.any";
        }

        /**
         * Iterable constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface ITERABLE {

            /**
             * Message key for iterable size
             */
            String SIZE = "iterable.size";

            /**
             * Message key for empty iterable
             */
            String EMPTY = "iterable.empty";

            /**
             * Message key for iterable contains object
             */
            String CONTAINS_OBJECT = "iterable.contains.object";

            /**
             * Message key for iterable contains all values
             */
            String CONTAINS_ALL = "iterable.contains.iterable.all";

            /**
             * Message key for iterable contains any values
             */
            String CONTAINS_ANY = "iterable.contains.iterable.any";
        }

        /**
         * MAP constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface MAP {

            /**
             * Message key for map size
             */
            String SIZE = "map.size";

            /**
             * Message key for empty map
             */
            String EMPTY = "map.empty";

            /**
             * Message key for map contains key
             */
            String CONTAINS_KEY = "map.contains.key";

            /**
             * Message key for map contains key/value pair
             */
            String CONTAINS_PAIR = "map.contains.pair";

            /**
             * Message key for map contains all keys
             */
            String CONTAINS_KEYS_ALL = "map.contains.keys.all";

            /**
             * Message key for map contains any keys
             */
            String CONTAINS_KEYS_ANY = "map.contains.keys.any";

            /**
             * Message key for map contains all map entries
             */
            String CONTAINS_MAP_ALL = "map.contains.map.all";

            /**
             * Message key for map contains any map entries
             */
            String CONTAINS_MAP_ANY = "map.contains.map.any";
        }

        /**
         * ENUM constants
         *
         * @since Aug 10, 2016
         * @author Gilles
         *
         */
        static interface ENUM {

            /**
             * Message key for enumeration name
             */
            String NAME = "enum.name";

            /**
             * Message key for enumeration ordinal
             */
            String ORDINAL = "enum.ordinal";
        }

        /**
         * THROWABLE constants
         *
         * @since Mar 21, 2017
         * @author Gilles
         *
         */
        static interface THROWABLE {

            /**
             * Message key for throwable instance of
             */
            String INSTANCE = "throwable.instance";

            /**
             * Message key for throwable instance of
             */
            String INSTANCE_PATTERN = "throwable.instance.pattern";

            /**
             * Message key for throwsable assignable from
             */
            String ASSIGNABLE = "throwable.assignable";

            /**
             * Message key for throwsable assignable from
             */
            String ASSIGNABLE_PATTERN = "throwable.assignable.pattern";

            /**
             * Message key for throwable has cause
             */
            String CAUSE = "throwable.cause";

            /**
             * Message key for throwable cause instance of
             */
            String CAUSE_INSTANCE = "throwable.cause.instance";

            /**
             * Message key for throwable cause instance of
             */
            String CAUSE_INSTANCE_MESSAGE = "throwable.cause.instance.message";

            /**
             * Message key for throwable cause instance of
             */
            String CAUSE_INSTANCE_PATTERN = "throwable.cause.instance.pattern";

            /**
             * Message key for throwable cause assignable from
             */
            String CAUSE_ASSIGNABLE = "throwable.cause.assignable";

            /**
             * Message key for throwable cause assignable from
             */
            String CAUSE_ASSIGNABLE_MESSAGE = "throwable.cause.assignable.message";

            /**
             * Message key for throwable cause assignable from
             */
            String CAUSE_ASSIGNABLE_PATTERN = "throwable.cause.assignable.pattern";
        }
    }
}
