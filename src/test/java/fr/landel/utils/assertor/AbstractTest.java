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

import org.junit.ComparisonFailure;

import fr.landel.utils.commons.function.TriFunction;

/**
 * Abstract for tests
 *
 * @since Jul 31, 2016
 * @author Gilles
 *
 */
public abstract class AbstractTest extends ConstantsAssertor {

    /**
     * Function to manage the creation of Junit exception
     */
    public static final TriFunction<Boolean, String, String, AssertionError> JUNIT_ERROR = (catched, expected, actual) -> {
        if (catched) {
            return new ComparisonFailure("The exception message don't match.", expected, actual);
        } else {
            return new AssertionError("The expected exception never comes up.");
        }
    };
}
