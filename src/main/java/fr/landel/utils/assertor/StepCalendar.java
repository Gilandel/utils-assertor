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

import java.util.Calendar;

import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see {@link Step}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface StepCalendar extends Step<StepCalendar, Calendar> {

    default StepCalendar get(final StepAssertor<Calendar> result) {
        return () -> result;
    }

    @Override
    default AssertorStepCalendar and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepCalendar or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepCalendar xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepCalendar nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepCalendar nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
