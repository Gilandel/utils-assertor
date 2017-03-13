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

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateStepBoolean extends PredicateStep<PredicateStepBoolean, Boolean> {

    default PredicateStepBoolean get(final StepAssertor<Boolean> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorBoolean and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorBoolean or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorBoolean xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }

    @Override
    default PredicateAssertorBoolean nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorBoolean nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }
}
