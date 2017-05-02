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
package fr.landel.utils.assertor.predicate;

import java.util.Calendar;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateStepCalendar extends PredicateStep<PredicateStepCalendar, Calendar> {

    default PredicateStepCalendar get(final StepAssertor<Calendar> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorStepCalendar and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default PredicateAssertorStepCalendar or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default PredicateAssertorStepCalendar xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default PredicateAssertorStepCalendar nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default PredicateAssertorStepCalendar nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
