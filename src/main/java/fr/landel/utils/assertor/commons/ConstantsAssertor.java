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
package fr.landel.utils.assertor.commons;

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

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.commons.EnumChar;
import fr.landel.utils.commons.StringUtils;

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
    public static final Logger LOGGER = LoggerFactory.getLogger(Assertor.class);

    // ---------- DEFAULT

    /**
     * Default assertion
     */
    public static final String DEFAULT_ASSERTION = "Assertion failed";

    // ---------- PROPERTIES / MESSAGES

    /**
     * Messages properties (for now doesn't support locale, maybe later)
     */
    public static final Properties PROPS = loadProperties("assertor_messages.properties");

    // ---------- OTHERS

    public static final Map<Integer, String> CALENDAR_FIELDS;
    static {
        final Map<Integer, String> map = new HashMap<>();
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

    private static Properties loadProperties(final String path) {
        final Properties properties = new Properties();
        if (StringUtils.isNotEmpty(path)) {
            try (final InputStream is = Assertor.class.getClassLoader().getResourceAsStream(path)) {
                properties.load(is);
            } catch (final IOException | IllegalArgumentException | NullPointerException e) {
                LOGGER.error("Cannot load the assertor configuration file", e);
            }
        }
        return properties;
    }

    /**
     * Returns the property associated to the key with replaced arguments or the
     * default string if not found {@link ConstantsAssertor#DEFAULT_ASSERTION}.
     * 
     * @param key
     *            The property key
     * @param values
     *            The property values applied before arguments
     * @param arguments
     *            The arguments to replace
     * @return The property
     */
    public static String getProperty(final CharSequence key, final CharSequence[] values, final CharSequence... arguments) {
        String property = PROPS.getProperty(String.valueOf(key));
        if (property != null) {
            if (ArrayUtils.isNotEmpty(values)) {
                property = StringUtils.inject(property, (Object[]) values);
            }
            if (ArrayUtils.isNotEmpty(arguments)) {
                for (int i = 0; i < arguments.length; ++i) {
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
    public static interface MSG {

        /**
         * Suffix for properties of type NOT
         */
        String NOT = ".not";

        /**
         * Suffix for properties of type prerequisites
         */
        String PRE = ".pre";

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

            /**
             * Message key for number between left hand number and right hand
             * number
             */
            String BETWEEN = "number.between";
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
             * Message key for char sequence length greater than
             */
            String LENGTH_GT = "csq.length.gt";

            /**
             * Message key for char sequence length greater than or equal to
             */
            String LENGTH_GTE = "csq.length.gte";

            /**
             * Message key for char sequence length lower than
             */
            String LENGTH_LT = "csq.length.lt";

            /**
             * Message key for char sequence length lower than or equal to
             */
            String LENGTH_LTE = "csq.length.lte";

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
             * Message key for date between
             */
            String BETWEEN = "date.between";

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
             * Message key for temporal between
             */
            String BETWEEN = "temporal.between";

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
             * Message key for array length greater than
             */
            String LENGTH_GT = "array.length.gt";

            /**
             * Message key for array length greater than or equal to
             */
            String LENGTH_GTE = "array.length.gte";

            /**
             * Message key for array length lower than
             */
            String LENGTH_LT = "array.length.lt";

            /**
             * Message key for array length lower than or equal to
             */
            String LENGTH_LTE = "array.length.lte";

            /**
             * Message key for empty array
             */
            String EMPTY = "array.empty";

            /**
             * Message key for all array elements match predicate
             */
            String MATCH_ALL = "array.match.all";

            /**
             * Message key for any array element matches predicate
             */
            String MATCH_ANY = "array.match.any";

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

            /**
             * Message key for array contains values in a specified order
             */
            String CONTAINS_IN_ORDER = "array.contains.array.inOrder";
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
             * Message key for iterable size greater than
             */
            String SIZE_GT = "iterable.size.gt";

            /**
             * Message key for iterable size greater than or equal to
             */
            String SIZE_GTE = "iterable.size.gte";

            /**
             * Message key for iterable size lower than
             */
            String SIZE_LT = "iterable.size.lt";

            /**
             * Message key for iterable size lower than or equal to
             */
            String SIZE_LTE = "iterable.size.lte";

            /**
             * Message key for empty iterable
             */
            String EMPTY = "iterable.empty";

            /**
             * Message key for all iterable elements match predicate
             */
            String MATCH_ALL = "iterable.match.all";

            /**
             * Message key for any iterable element matches predicate
             */
            String MATCH_ANY = "iterable.match.any";

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

            /**
             * Message key for iterable contains values in a specified order
             */
            String CONTAINS_IN_ORDER = "iterable.contains.iterable.inOrder";
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
             * Message key for map size greater than
             */
            String SIZE_GT = "map.size.gt";

            /**
             * Message key for map size greater than or equal to
             */
            String SIZE_GTE = "map.size.gte";

            /**
             * Message key for map size lower than
             */
            String SIZE_LT = "map.size.lt";

            /**
             * Message key for map size lower than or equal to
             */
            String SIZE_LTE = "map.size.lte";

            /**
             * Message key for empty map
             */
            String EMPTY = "map.empty";

            /**
             * Message key for all map elements match predicate
             */
            String MATCH_ALL = "map.match.all";

            /**
             * Message key for any map element matches predicate
             */
            String MATCH_ANY = "map.match.any";

            /**
             * Message key for map contains key
             */
            String CONTAINS_KEY = "map.contains.key";

            /**
             * Message key for map contains value
             */
            String CONTAINS_VALUE = "map.contains.value";

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
             * Message key for map contains entries in a specified order
             */
            String CONTAINS_KEYS_IN_ORDER = "map.contains.keys.inOrder";

            /**
             * Message key for map contains all values
             */
            String CONTAINS_VALUES_ALL = "map.contains.values.all";

            /**
             * Message key for map contains any values
             */
            String CONTAINS_VALUES_ANY = "map.contains.values.any";

            /**
             * Message key for map contains entries in a specified order
             */
            String CONTAINS_VALUES_IN_ORDER = "map.contains.values.inOrder";

            /**
             * Message key for map contains all map entries
             */
            String CONTAINS_MAP_ALL = "map.contains.map.all";

            /**
             * Message key for map contains any map entries
             */
            String CONTAINS_MAP_ANY = "map.contains.map.any";

            /**
             * Message key for map contains entries in a specified order
             */
            String CONTAINS_MAP_IN_ORDER = "map.contains.map.inOrder";
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
