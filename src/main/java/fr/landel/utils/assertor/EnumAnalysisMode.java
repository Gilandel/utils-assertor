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
 * Analysis mode.
 * 
 * Some assertion methods provide different analysis modes to improve
 * performance following specific use case (like on
 * {@link AssertorIterable#contains}).
 *
 * @since Mar 28, 2017
 * @author Gilles
 */
public enum EnumAnalysisMode {

    /**
     * In standard mode, loops are preferred
     */
    STANDARD,

    /**
     * In stream mode, streams are preferred for checking
     */
    STREAM,

    /**
     * In stream mode, parallel streams are preferred for checking
     */
    PARALLEL;
}
