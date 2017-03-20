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
 * @param <I>
 *            the {@link Iterable} type
 * @param <T>
 *            the {@link Iterable} elements type
 */
@FunctionalInterface
public interface PredicateStepIterable<I extends Iterable<T>, T> extends PredicateStep<PredicateStepIterable<I, T>, I> {

    default PredicateStepIterable<I, T> get(final StepAssertor<I> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorIterable<I, T> and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorIterable<I, T> or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorIterable<I, T> xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }

    @Override
    default PredicateAssertorIterable<I, T> nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorIterable<I, T> nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }
}
