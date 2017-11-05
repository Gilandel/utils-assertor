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
package fr.landel.utils.assertor.commons;

import java.util.Locale;

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
        this.locale = locale;
        this.message = message;
        this.arguments = ArrayUtils.clone(arguments);
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * @return the message
     */
    public CharSequence getMessage() {
        return this.message;
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
                .appendIfNotNull("locale", this.locale)
                .appendIfNotNull("message", this.message)
                .appendAndFormatIf("arguments", this.arguments, ArrayUtils::isNotEmpty, StringUtils::joinComma)
                .build();
        // @formatter:on
    }

    /**
     * Creates an immutable message
     * 
     * @param locale
     *            the message locale
     * @param message
     *            the message
     * @param arguments
     *            the message arguments
     * @return the new message instance
     */
    public static MessageAssertor of(final Locale locale, final CharSequence message, final Object[] arguments) {
        return new MessageAssertor(locale, message, arguments);
    }
}
