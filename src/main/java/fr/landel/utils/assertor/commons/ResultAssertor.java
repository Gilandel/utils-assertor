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
import java.util.function.Supplier;

/**
 * To store the Assertor result
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class ResultAssertor {

    private final boolean precondition;
    private final boolean valid;
    private final List<Supplier<CharSequence>> messages;
    private final List<ParameterAssertor<?>> parameters;

    /**
     * Constructor
     *
     * @param precondition
     *            the precondition status
     * @param valid
     *            the validity status
     * @param messages
     *            the result message
     * @param parameters
     *            the complete list of parameters
     */
    public ResultAssertor(final boolean precondition, final boolean valid, final List<Supplier<CharSequence>> messages,
            final List<ParameterAssertor<?>> parameters) {

        this.precondition = precondition;
        this.valid = valid;
        this.messages = messages;
        this.parameters = new ArrayList<>(parameters);
    }

    /**
     * @return the precondition
     */
    public boolean isPrecondition() {
        return this.precondition;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * @return the messages
     */
    public List<Supplier<CharSequence>> getMessages() {
        return this.messages;
    }

    /**
     * @return the parameters
     */
    public List<ParameterAssertor<?>> getParameters() {
        return this.parameters;
    }
}
