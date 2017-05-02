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

    SUB_ASSERTOR,

    PROPERTY;
}
