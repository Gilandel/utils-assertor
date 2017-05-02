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
        return ConstantsAssertor.getProperty(this.key).toString();
    }
}
