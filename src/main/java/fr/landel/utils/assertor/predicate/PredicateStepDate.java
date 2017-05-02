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

import java.util.Date;

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
public interface PredicateStepDate extends PredicateStep<PredicateStepDate, Date> {

    default PredicateStepDate get(final StepAssertor<Date> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorStepDate and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default PredicateAssertorStepDate or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default PredicateAssertorStepDate nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default PredicateAssertorStepDate nor() {
        return () -> HelperStep.nor(this.getStep());
    }

    @Override
    default PredicateAssertorStepDate xor() {
        return () -> HelperStep.xor(this.getStep());
    }
}
