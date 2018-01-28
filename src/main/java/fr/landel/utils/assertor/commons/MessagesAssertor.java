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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.helper.HelperMessage;
import fr.landel.utils.commons.EnumChar;

/**
 * Class that contains current errors messages and provides build method.
 * 
 * @since Jan 27, 2018
 * @author Gilles
 */
public class MessagesAssertor implements Builder<String> {

    private static final MessageAssertor PARENTHESIS_OPEN = MessageAssertor.of(null, false, null, null, null,
            EnumChar.PARENTHESIS_OPEN.getUnicode());
    private static final MessageAssertor PARENTHESIS_CLOSE = MessageAssertor.of(null, false, null, null, null,
            EnumChar.PARENTHESIS_CLOSE.getUnicode());

    private final List<MessageAssertor> preconditions;
    private final List<MessageAssertor> messages;

    /**
     * Default constructor to initialize preconditions and messages list.
     */
    public MessagesAssertor() {
        this.preconditions = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    /**
     * @return true, if there are currently precondition errors
     */
    public boolean isPreconditionsNotEmpty() {
        return !this.preconditions.isEmpty();
    }

    /**
     * @return true, if there are currently errors
     */
    public boolean isMessagesNotEmpty() {
        return !this.messages.isEmpty();
    }

    /**
     * @return true, if there are currently precondition or standard errors
     */
    public boolean isNotEmpty() {
        return !this.preconditions.isEmpty() || !this.messages.isEmpty();
    }

    /**
     * Append an operator to the current errors list
     * 
     * @param operator
     *            the operator to append
     */
    public void append(final EnumOperator operator) {
        if (this.isPreconditionsNotEmpty()) {
            this.preconditions.add(operator.getMessageAssertor());
        } else if (this.isMessagesNotEmpty()) {
            this.messages.add(operator.getMessageAssertor());
        } else {
            throw new UnsupportedOperationException("An operator can only be applied on a previous error");
        }
    }

    /**
     * Append a sub-messages Assertor to the current list
     * 
     * @param sub
     *            the sub-messages Assertor
     */
    public void append(final MessagesAssertor sub) {
        if (sub.isPreconditionsNotEmpty()) {
            this.preconditions.add(PARENTHESIS_OPEN);
            this.preconditions.addAll(sub.preconditions);
            this.preconditions.add(PARENTHESIS_CLOSE);
        } else if (sub.isMessagesNotEmpty()) {
            this.messages.add(PARENTHESIS_OPEN);
            this.messages.addAll(sub.messages);
            this.messages.add(PARENTHESIS_CLOSE);
        }
    }

    /**
     * Append a standard message
     * 
     * @param key
     *            the default key, used if message is {@code null}
     * @param not
     *            if not is currently applied
     * @param values
     *            the values to inject into the default property's key
     * @param parameters
     *            the Assertor parameters
     * @param message
     *            the user message (may be {@code null})
     */
    public void append(final CharSequence key, final boolean not, final CharSequence[] values, final List<ParameterAssertor<?>> parameters,
            final MessageAssertor message) {
        // standard
        if (message != null) {
            this.messages.add(
                    MessageAssertor.of(key, not, values, parameters, message.getLocale(), message.getMessage(), message.getArguments()));
        } else {
            this.messages.add(MessageAssertor.of(key, not, values, parameters, null, null));
        }
    }

    /**
     * Append a precondition error
     * 
     * @param key
     *            the precondition key
     * @param not
     *            if not is applied
     * @param values
     *            the values to inject into the property's key
     * @param parameters
     */
    public void append(final CharSequence key, final boolean not, final CharSequence[] values,
            final List<ParameterAssertor<?>> parameters) {
        this.preconditions.add(MessageAssertor.of(key, not, values, parameters));
    }

    /**
     * Build the message error
     * 
     * @return the error message or an empty String
     */
    public String build() {
        if (this.isNotEmpty()) {
            final StringBuilder sb = new StringBuilder();

            final List<MessageAssertor> messages;
            if (this.isPreconditionsNotEmpty()) {
                messages = this.preconditions;
            } else {
                messages = this.messages;
            }

            for (final MessageAssertor message : messages) {
                sb.append(HelperMessage.getMessage(message));
            }

            return sb.toString();
        }
        return StringUtils.EMPTY;
    }
}
