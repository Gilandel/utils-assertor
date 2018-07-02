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

import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;

import fr.landel.utils.commons.ArrayUtils;
import fr.landel.utils.commons.StringUtils;
import fr.landel.utils.commons.builder.ToStringBuilder;
import fr.landel.utils.commons.builder.ToStringStyles;

/**
 * Message DTO
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class MessageAssertor {

    private final boolean precondition;

    private final CharSequence key;
    private final boolean not;
    private final CharSequence[] values;
    private final List<ParameterAssertor<?>> parameters;

    private final Locale locale;
    private final CharSequence message;
    private final Object[] arguments;

    /**
     * Message constructor
     *
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     */
    private MessageAssertor(final Locale locale, final CharSequence message, final Object[] arguments) {

        this.precondition = false;

        this.key = null;
        this.not = false;
        this.values = null;
        this.parameters = null;

        this.locale = locale;
        this.message = message;
        this.arguments = ArrayUtils.clone(arguments);
    }

    /**
     * Message constructor
     *
     * @param key
     *            the message key
     * @param not
     *            true, if not is applied
     * @param values
     *            the values of properties message
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param parameters
     *            the checked parameters
     * @param arguments
     *            the message arguments
     */
    private MessageAssertor(final CharSequence key, final boolean not, final CharSequence[] values,
            final List<ParameterAssertor<?>> parameters, final Locale locale, final CharSequence message, final Object[] arguments) {

        this.precondition = false;

        this.key = key;
        this.not = not;
        this.values = ArrayUtils.clone(values);
        this.parameters = parameters;

        this.locale = locale;
        this.message = message;
        this.arguments = ArrayUtils.clone(arguments);
    }

    /**
     * Message constructor
     *
     * @param key
     *            the message key
     * @param not
     *            true, if not is applied
     * @param values
     *            the values of properties message
     * @param parameters
     *            the checked parameters
     */
    private MessageAssertor(final CharSequence key, final boolean not, final CharSequence[] values,
            final List<ParameterAssertor<?>> parameters) {

        this.precondition = true;

        this.key = key;
        this.not = not;
        this.values = ArrayUtils.clone(values);
        this.parameters = parameters;

        this.locale = null;
        this.message = null;
        this.arguments = null;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * @return the key
     */
    public CharSequence getKey() {
        return this.key;
    }

    /**
     * @return the precondition status
     */
    public boolean isPrecondition() {
        return this.precondition;
    }

    /**
     * @return the not status
     */
    public boolean isNot() {
        return this.not;
    }

    /**
     * @return the message
     */
    public CharSequence getMessage() {
        return this.message;
    }

    /**
     * @return the values
     */
    public CharSequence[] getValues() {
        return ArrayUtils.clone(this.values);
    }

    /**
     * @return the parameters
     */
    public List<ParameterAssertor<?>> getParameters() {
        return this.parameters;
    }

    /**
     * @return the arguments clone
     */
    public Object[] getArguments() {
        return ArrayUtils.clone(this.arguments);
    }

    @Override
    public String toString() {
        // @formatter:off
        return new ToStringBuilder(ToStringStyles.JSON_SPACED)
                .append("precondition", this.precondition)
                .appendIfNotNull("key", this.key)
                .append("not", this.not)
                .appendAndFormatIf("values", this.values, ArrayUtils::isNotEmpty, StringUtils::joinComma)
                .appendAndFormatIf("parameters", this.parameters, CollectionUtils::isNotEmpty, StringUtils::joinComma)
                .appendIfNotNull("locale", this.locale)
                .appendIfNotNull("message", this.message)
                .appendAndFormatIf("arguments", this.arguments, ArrayUtils::isNotEmpty, StringUtils::joinComma)
                .build();
        // @formatter:on
    }

    /**
     * Creates a standard message
     * 
     * 
     * @param key
     *            the message key (use if message is {@code null})
     * @param not
     *            the not mode
     * @param values
     *            the values of properties message
     * @param parameters
     *            the checked parameters
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     * @return the new message instance
     */
    public static MessageAssertor of(final CharSequence key, final boolean not, final CharSequence[] values,
            final List<ParameterAssertor<?>> parameters, final Locale locale, final CharSequence message, final Object... arguments) {
        return new MessageAssertor(key, not, values, parameters, locale, message, arguments);
    }

    /**
     * Creates a precondition message
     * 
     * @param key
     *            the message key
     * @param not
     *            the not mode
     * @param values
     *            the values of properties message
     * @param parameters
     *            the checked parameters
     * @return the new message instance
     */
    public static MessageAssertor of(final CharSequence key, final boolean not, final CharSequence[] values,
            final List<ParameterAssertor<?>> parameters) {
        return new MessageAssertor(key, not, values, parameters);
    }

    public static MessageAssertor of(final Locale locale, final CharSequence message, final Object... arguments) {
        return new MessageAssertor(locale, message, arguments);
    }
}