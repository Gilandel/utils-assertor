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
package fr.landel.utils.assertor.expect;

/**
 * Expect exception
 *
 * @since Jul 19, 2016
 * @author Gilles
 */
public class ExpectException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3829486658567119898L;

    /**
     * Constructs a new expect exception with the specified detail message.
     *
     * @param message
     *            the detail message. The detail message is saved for later
     *            retrieval by the {@link #getMessage()} method.
     */
    public ExpectException(final String message) {
        super(message);
    }
}
