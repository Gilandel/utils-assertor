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
package fr.landel.utils.assertor.enums;

import fr.landel.utils.assertor.Assertor;

/**
 * List of steps
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public enum EnumStep {

    /**
     * First step, generate by {@link Assertor#that}
     */
    CREATION,

    /**
     * Not step (the next step will be prefixed by not)
     */
    NOT,

    /**
     * Combine with another object
     */
    OBJECT,

    /**
     * Combine with another assertion
     */
    OPERATOR,

    /**
     * Assertion step
     */
    ASSERTION,

    /**
     * Include a sub assertion
     */
    SUB,

    /**
     * To create an Assertor chain for predicate
     */
    PREDICATE,

    /**
     * The predicate object
     */
    PREDICATE_OBJECT,

    /**
     * To create a sub assertor from a property of the current object
     */
    SUB_ASSERTOR,

    /**
     * To map the current object
     */
    PROPERTY;
}
