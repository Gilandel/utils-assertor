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

import java.util.ArrayList;
import java.util.List;

/**
 * To store the assertor result
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class ResultAssertor {

    private final boolean precondition;
    private final boolean valid;
    private final String message;
    private final List<ParameterAssertor<?>> parameters;

    /**
     * Constructor
     *
     * @param precondition
     *            the precondition status
     * @param valid
     *            the validity status
     * @param message
     *            the result message
     * @param parameters
     *            the complete list of parameters
     */
    public ResultAssertor(boolean precondition, boolean valid, String message, final List<ParameterAssertor<?>> parameters) {
        this.precondition = precondition;
        this.valid = valid;
        this.message = message;
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
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the parameters
     */
    public List<ParameterAssertor<?>> getParameters() {
        return this.parameters;
    }
}
