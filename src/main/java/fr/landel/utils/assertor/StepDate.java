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
