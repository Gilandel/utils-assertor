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
package fr.landel.utils.assertor.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.AbstractTest;

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
