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

import java.util.Map;

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
public interface PredicateStepMap<K, V> extends PredicateStep<PredicateStepMap<K, V>, Map<K, V>> {

    default PredicateStepMap<K, V> get(final StepAssertor<Map<K, V>> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorStepMap<K, V> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default PredicateAssertorStepMap<K, V> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default PredicateAssertorStepMap<K, V> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default PredicateAssertorStepMap<K, V> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default PredicateAssertorStepMap<K, V> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
