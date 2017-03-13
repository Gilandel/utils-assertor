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

import java.util.Locale;

import org.junit.Test;

/**
 * Check {@link MessageAssertor}
 *
 * @since Aug 11, 2016
 * @author Gilles
 *
 */
public class MessageAssertorTest {

    /**
     * Check {@link MessageAssertor#toString()}
     */
    @Test
    public void testToString() {
        assertEquals("{}", MessageAssertor.of(null, null, null).toString());
        assertEquals("{locale: fr_FR}", MessageAssertor.of(Locale.FRANCE, null, null).toString());
        assertEquals("{locale: fr_FR, message: message}", MessageAssertor.of(Locale.FRANCE, "message", null).toString());
        assertEquals("{locale: fr_FR, arguments: arg1, arg2}",
                MessageAssertor.of(Locale.FRANCE, null, new String[] {"arg1", "arg2"}).toString());
        assertEquals("{message: message}", MessageAssertor.of(null, "message", null).toString());
        assertEquals("{locale: fr_FR, message: message, arguments: arg1, arg2}",
                MessageAssertor.of(Locale.FRANCE, "message", new String[] {"arg1", "arg2"}).toString());
        assertEquals("{message: message, arguments: arg1, arg2}",
                MessageAssertor.of(null, "message", new String[] {"arg1", "arg2"}).toString());
        assertEquals("{arguments: arg1, arg2}", MessageAssertor.of(null, null, new String[] {"arg1", "arg2"}).toString());
    }
}
