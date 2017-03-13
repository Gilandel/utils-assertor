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
package fr.landel.utils.assertor.memory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;

/**
 * Speed test to just have a preview of memory used / memory leaks
 *
 * @since Aug 9, 2016
 * @author Gilles
 *
 */
public class MemoryTest extends AbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTest.class);

    @Test
    public void testMemory() {
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.freeMemory();

        Assertor.that("text").contains("ex").and().endsWithIgnoreCase("T").isOK();

        memory = memory - runtime.freeMemory();

        LOGGER.info(String.format("Memory used: %,d bytes", memory));

        memory = runtime.freeMemory();

        for (int i = 0; i < 1_000; i++) {
            Assertor.that(MemoryTest.class).isAssignableFrom(AbstractTest.class).isOK();

            Assertor.that("text").contains("ex").and().endsWithIgnoreCase("T").isOK();
            Assertor.that("text").contains("ex").or().endsWithIgnoreCase("E").isOK();
            Assertor.that("text").contains("ex").xor().endsWithIgnoreCase("X").isOK();

            Assertor.that("text").contains("ex").and(12.3f).isGT(10.25f).isOK();
            Assertor.that("text").contains("ex").or(12.3f).isGT(10.25f).isOK();
            Assertor.that("text").contains("ex").xor(12.3f).isGT(13.25f).isOK();

            Assertor.that("text").contains("ex").and(Assertor.that(12.3f).isNotNull().and().isGT(10.25f)).isOK();
            Assertor.that("text").contains("ex").or(Assertor.that(12.3f).isNull().and().isGT(10.25f)).isOK();
            Assertor.that("text").contains("ex").xor(Assertor.that(12.3f).isNotNull().and().isGT(13.25f)).isOK();
        }

        memory = memory - runtime.freeMemory();

        LOGGER.info(String.format("Memory used: %,d bytes", memory));
        assertTrue(memory < 30_000_000);
    }
}
