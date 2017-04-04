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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Check {@link EnumAnalysisMode}
 *
 * @since Mar 28, 2017
 * @author Gilles
 *
 */
public class EnumAnalysisModeTest extends AbstractTest {

    @Test
    public void test() {
        assertNotNull(EnumAnalysisMode.values());
        assertEquals(3, EnumAnalysisMode.values().length);

        assertEquals(EnumAnalysisMode.STANDARD, EnumAnalysisMode.valueOf("STANDARD"));
        assertEquals(EnumAnalysisMode.STANDARD, EnumAnalysisMode.valueOf(EnumAnalysisMode.class, "STANDARD"));

        assertEquals(0, EnumAnalysisMode.STANDARD.ordinal());
        assertEquals("STANDARD", EnumAnalysisMode.STANDARD.name());
        assertEquals(1, EnumAnalysisMode.STREAM.ordinal());
        assertEquals("STREAM", EnumAnalysisMode.STREAM.name());
        assertEquals(2, EnumAnalysisMode.PARALLEL.ordinal());
        assertEquals("PARALLEL", EnumAnalysisMode.PARALLEL.name());
    }
}