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
     * true AND true = true
     * true AND false = true
     * false AND true = true
     * false AND false = false
     * </pre>
     */
    OR("operator.or"),

    /**
     * Xor operator
     * 
     * <pre>
     * true AND true = false
     * true AND false = true
     * false AND true = true
     * false AND false = false
     * </pre>
     */
    XOR("operator.xor"),

    /**
     * Nand operator (negative and)
     * 
     * <pre>
     * true AND true = false
     * true AND false = false
     * false AND true = false
     * false AND false = true
     * </pre>
     */
    NAND("operator.nand"),

    /**
     * Nor operator (negative or)
     * 
     * <pre>
     * true AND true = false
     * true AND false = true
     * false AND true = true
     * false AND false = true
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
