/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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
package fr.landel.utils.assertor;

import java.util.Date;

import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see {@link Step}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface StepDate extends Step<StepDate, Date> {

    default StepDate get(final StepAssertor<Date> result) {
        return () -> result;
    }

    @Override
    default AssertorStepDate and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepDate or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepDate nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepDate nor() {
        return () -> HelperStep.nor(this.getStep());
    }

    @Override
    default AssertorStepDate xor() {
        return () -> HelperStep.xor(this.getStep());
    }
}
