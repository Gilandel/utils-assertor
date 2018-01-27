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

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;

/**
 * Check {@link ConstantsAssertor}
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class ConstantsAssertorTest extends AbstractTest {

    /**
     * Test method for {@link ConstantsAssertor#getProperty} .
     */
    @Test
    public void testGetProperty() {
        assertEquals(DEFAULT_ASSERTION, ConstantsAssertor.getProperty(null, null, ""));
        assertEquals("the boolean should be true", ConstantsAssertor.getProperty("boolean.true", null));
        assertEquals("the boolean should be true", ConstantsAssertor.getProperty("boolean.true", null, "arg"));
        assertEquals("the object '{0}' should be null", ConstantsAssertor.getProperty("object.null", null));
        assertEquals("the object 'arg' should be null", ConstantsAssertor.getProperty("object.null", null, "arg"));
        assertEquals("the object 'arg' should be null", ConstantsAssertor.getProperty("object.null", null, "arg", ""));
    }
}
