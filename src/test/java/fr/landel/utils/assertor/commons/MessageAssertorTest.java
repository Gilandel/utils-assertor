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
package fr.landel.utils.assertor.commons;

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
        assertEquals("{precondition: false, not: false}", MessageAssertor.of(null, null).toString());
        assertEquals("{precondition: false, not: false, locale: fr_FR}", MessageAssertor.of(Locale.FRANCE, null).toString());
        assertEquals("{precondition: false, not: false, locale: fr_FR, message: message}",
                MessageAssertor.of(Locale.FRANCE, "message").toString());
        assertEquals("{precondition: false, not: false, locale: fr_FR, arguments: [arg1, arg2]}",
                MessageAssertor.of(Locale.FRANCE, null, "arg1", "arg2").toString());
        assertEquals("{precondition: false, not: false, message: message}", MessageAssertor.of(null, "message").toString());
        assertEquals("{precondition: false, not: false, locale: fr_FR, message: message, arguments: [arg1, arg2]}",
                MessageAssertor.of(Locale.FRANCE, "message", "arg1", "arg2").toString());
        assertEquals("{precondition: false, not: false, message: message, arguments: [arg1, arg2]}",
                MessageAssertor.of(null, "message", "arg1", "arg2").toString());
        assertEquals("{precondition: false, not: false, arguments: [arg1, arg2]}",
                MessageAssertor.of(null, null, "arg1", "arg2").toString());
    }
}
