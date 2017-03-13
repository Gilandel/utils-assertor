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

import java.util.Map;

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateStepMap<K, V> extends PredicateStep<PredicateStepMap<K, V>, Map<K, V>> {

    default PredicateStepMap<K, V> get(final StepAssertor<Map<K, V>> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorMap<K, V> and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorMap<K, V> or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorMap<K, V> xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }

    @Override
    default PredicateAssertorMap<K, V> nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorMap<K, V> nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }
}
