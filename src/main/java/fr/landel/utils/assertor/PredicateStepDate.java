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
    default PredicateAssertorDate and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorDate or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorDate nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorDate nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }

    @Override
    default PredicateAssertorDate xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }
}
