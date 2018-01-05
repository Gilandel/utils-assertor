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

import fr.landel.utils.assertor.utils.AssertorIterable;

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
