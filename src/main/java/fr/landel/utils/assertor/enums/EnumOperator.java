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
package fr.landel.utils.assertor.enums;

import fr.landel.utils.assertor.commons.ConstantsAssertor;

/**
 * 
 * List of operators
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
public enum EnumOperator {

    /**
     * And operator
     * 
     * <pre>
     * AND = bool1 &amp;&amp; bool2
     * 
     * true AND true = true
     * true AND false = false
     * false AND true = false
     * false AND false = false
     * </pre>
     */
    AND("operator.and"),

    /**
     * Or operator
     * 
     * <pre>
     * OR = bool1 || bool2
     * 
     * true OR true = true
     * true OR false = true
     * false OR true = true
     * false OR false = false
     * </pre>
     */
    OR("operator.or"),

    /**
     * Xor operator
     * 
     * <pre>
     * XOR = bool1 ^ bool2
     * 
     * true XOR true = false
     * true XOR false = true
     * false XOR true = true
     * false XOR false = false
     * </pre>
     */
    XOR("operator.xor"),

    /**
     * Nand operator (negative and)
     * 
     * <pre>
     * NAND = !bool1 &amp;&amp; !bool2
     * 
     * true NAND true = false
     * true NAND false = false
     * false NAND true = false
     * false NAND false = true
     * </pre>
     */
    NAND("operator.nand"),

    /**
     * Nor operator (negative or)
     * 
     * <pre>
     * NOR = !bool1 || !bool2
     * 
     * true NOR true = false
     * true NOR false = true
     * false NOR true = true
     * false NOR false = true
     * </pre>
     */
    NOR("operator.nor");

    private final String key;

    private EnumOperator(final String key) {
        this.key = key;
    }

    /**
     * @return the operator message key
     */
    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return ConstantsAssertor.getProperty(this.key, null).toString();
    }
}
