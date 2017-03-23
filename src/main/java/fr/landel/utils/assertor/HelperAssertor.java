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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import fr.landel.utils.commons.EnumChar;
import fr.landel.utils.commons.StringUtils;

/**
 * 
 * Assertor helper class, to build exceptions and messages.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class HelperAssertor extends ConstantsAssertor {

    /**
     * Empty {@code String}
     */
    protected static final CharSequence EMPTY_STRING = "";

    protected static final Transformer<ParameterAssertor<?>, Object> PARAM_TRANSFORMER = new Transformer<ParameterAssertor<?>, Object>() {
        @Override
        public Object transform(final ParameterAssertor<?> input) {
            return input.getObject();
        }
    };

    protected static <T> StepAssertor<T> not(final StepAssertor<T> result) {
        return new StepAssertor<>(result);
    }

    protected static <X, T> StepAssertor<T> and(final StepAssertor<X> result, final T object, final EnumType type) {
        return new StepAssertor<>(result, object, type, EnumOperator.AND);
    }

    protected static <X, T> StepAssertor<T> or(final StepAssertor<X> result, final T object, final EnumType type) {
        return new StepAssertor<>(result, object, type, EnumOperator.OR);
    }

    protected static <X, T> StepAssertor<T> xor(final StepAssertor<X> result, final T object, final EnumType type) {
        return new StepAssertor<>(result, object, type, EnumOperator.XOR);
    }

    protected static <X, T> StepAssertor<T> nand(final StepAssertor<X> result, final T object, final EnumType type) {
        return new StepAssertor<>(result, object, type, EnumOperator.NAND);
    }

    protected static <X, T> StepAssertor<T> nor(final StepAssertor<X> result, final T object, final EnumType type) {
        return new StepAssertor<>(result, object, type, EnumOperator.NOR);
    }

    protected static <T> StepAssertor<T> and(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.AND);
    }

    protected static <T> StepAssertor<T> or(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.OR);
    }

    protected static <T> StepAssertor<T> xor(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.XOR);
    }

    protected static <T> StepAssertor<T> nand(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.NAND);
    }

    protected static <T> StepAssertor<T> nor(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.NOR);
    }

    protected static <T, X> StepAssertor<T> and(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.AND);
    }

    protected static <T, X> StepAssertor<T> or(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.OR);
    }

    protected static <T, X> StepAssertor<T> xor(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.XOR);
    }

    protected static <T, X> StepAssertor<T> nand(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.NAND);
    }

    protected static <T, X> StepAssertor<T> nor(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.NOR);
    }

    protected static <T> ResultAssertor combine(final StepAssertor<T> step, final boolean loadMessage) {

        // load all steps and reverse the order
        final List<StepAssertor<?>> steps = new ArrayList<>();
        steps.add(step);
        StepAssertor<?> currentStep = step;
        StepAssertor<?> previousStep;
        while ((previousStep = currentStep.getPreviousStep()) != null) {
            steps.add(previousStep);
            currentStep = previousStep;

        }
        Collections.reverse(steps);

        boolean not = false;
        boolean valid = true;
        EnumOperator operator = null;
        final StringBuilder message = new StringBuilder();
        Object object = null;
        boolean checked = false;
        EnumType type = null;
        ParameterAssertor<?> param = null;

        final List<ParameterAssertor<?>> parameters = new ArrayList<>();

        Optional<Pair<Boolean, String>> dontNeedCheck = Optional.empty();

        for (StepAssertor<?> s : steps) {

            if (dontNeedCheck.isPresent()) {
                return new ResultAssertor(true, dontNeedCheck.get().getKey(), dontNeedCheck.get().getValue(), parameters);
            }

            switch (s.getStepType()) {
            case CREATION:
                object = s.getObject();
                type = s.getType();
                checked = s.isChecked();
                param = new ParameterAssertor<>(object, type, checked);

                parameters.add(param);

                break;
            case ASSERTION:
                parameters.addAll(s.getParameters());

                // if precondition returns false, we end all treatments
                if (!HelperAssertor.preCheck(s, object)) {
                    return HelperAssertor.getPreconditionMessage(s, param, parameters, loadMessage);

                } else {
                    valid = HelperAssertor.validatesAndGetMessage(s, param, object, valid, not, operator, message, loadMessage);
                }

                not = false;

                break;
            case OPERATOR:
                operator = s.getOperator();

                dontNeedCheck = checkValidityAndOperator(valid, operator, message);

                break;
            case NOT:
                not = not ^ s.isNot();

                break;
            case OBJECT:
                operator = s.getOperator();
                object = s.getObject();
                checked = s.isChecked();
                param = new ParameterAssertor<>(object, type, checked);

                parameters.add(param);

                dontNeedCheck = checkValidityAndOperator(valid, operator, message);

                break;
            case SUB:
                if (s.getSubStep().isPresent()) {
                    final Triple<Boolean, EnumOperator, ResultAssertor> output = HelperAssertor.managesSub(s, parameters, valid, operator,
                            message, loadMessage);

                    if (output.getRight() != null) {
                        return output.getRight();
                    } else {
                        valid = output.getLeft();
                        operator = output.getMiddle();

                        dontNeedCheck = checkValidityAndOperator(valid, operator, message);
                    }
                }
                break;
            default:
            }
        }

        return new ResultAssertor(true, valid, message.toString(), parameters);
    }

    private static Optional<Pair<Boolean, String>> checkValidityAndOperator(final boolean valid, final EnumOperator operator,
            final StringBuilder message) {

        Pair<Boolean, String> result = null;

        if ((!valid && EnumOperator.NOR.equals(operator)) || (valid && EnumOperator.OR.equals(operator))) {

            result = Pair.of(true, message.toString());

        } else if ((valid && EnumOperator.NAND.equals(operator)) || (!valid && EnumOperator.AND.equals(operator))) {

            final String formattedMessage;
            if (message.length() > 0) {
                formattedMessage = StringUtils.inject(ConstantsAssertor.getProperty(MSG.INVALID_WITH_MESSAGE), valid, operator, message);
            } else {
                formattedMessage = StringUtils.inject(ConstantsAssertor.getProperty(MSG.INVALID_WITHOUT_MESSAGE), valid, operator);
            }

            result = Pair.of(false, formattedMessage);
        }

        return Optional.ofNullable(result);
    }

    private static <T> ResultAssertor getPreconditionMessage(final StepAssertor<T> step, final ParameterAssertor<?> param,
            final List<ParameterAssertor<?>> parameters, final boolean loadMessage) {

        final List<ParameterAssertor<?>> assertParameters = new ArrayList<>();
        assertParameters.add(param);
        assertParameters.addAll(step.getParameters());

        final String error;
        if (loadMessage) {
            final String preconditionMessage = HelperMessage.getDefaultMessage(step.getMessageKey(), true, false, assertParameters);
            error = String.format(Assertor.getLocale(), preconditionMessage, assertParameters);
        } else {
            error = null;
        }

        return new ResultAssertor(false, false, error, parameters);
    }

    private static <T> boolean validatesAndGetMessage(final StepAssertor<T> step, final ParameterAssertor<?> param, final Object object,
            final boolean valid, final boolean not, final EnumOperator operator, final StringBuilder message, final boolean loadMessage) {

        boolean nextValid = HelperAssertor.isValid(valid, HelperAssertor.check(step, object, not), operator);

        if (!nextValid && loadMessage) {
            if (message.length() > 0 && operator != null) {
                message.append(operator);
            }

            final List<ParameterAssertor<?>> assertParameters = new ArrayList<>();
            assertParameters.add(param);
            assertParameters.addAll(step.getParameters());

            message.append(
                    HelperMessage.getMessage(step.getMessage(), step.getMessageKey(), not ^ step.isMessageKeyNot(), assertParameters));

        }

        return nextValid;
    }

    private static <T> Triple<Boolean, EnumOperator, ResultAssertor> managesSub(final StepAssertor<T> step,
            final List<ParameterAssertor<?>> parameters, final boolean valid, final EnumOperator operator, final StringBuilder message,
            final boolean loadMessage) {

        final StepAssertor<?> subStep = step.getSubStep().get();
        EnumOperator nextOperator = operator;
        boolean nextValid = valid;

        if (!EnumOperator.AND.equals(step.getOperator()) || nextValid) {

            final ResultAssertor subResult = HelperAssertor.combine(subStep, loadMessage);

            parameters.addAll(subResult.getParameters());

            if (!subResult.isPrecondition()) {
                return Triple.of(false, null, subResult);
            } else {
                nextOperator = step.getOperator();

                nextValid = HelperAssertor.isValid(nextValid, subResult.isValid(), nextOperator);

                if (!nextValid && loadMessage && subResult.getMessage() != null) {

                    if (message.length() > 0 && nextOperator != null) {
                        message.append(nextOperator);
                    }

                    message.append(EnumChar.PARENTHESIS_OPEN);
                    message.append(subResult.getMessage());
                    message.append(EnumChar.PARENTHESIS_CLOSE);
                }
            }
        }

        return Triple.of(nextValid, nextOperator, null);
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean preCheck(final StepAssertor<T> step, final Object object) {
        if (step.getPreChecker() != null) {
            return step.getPreChecker().test((T) object);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean check(final StepAssertor<T> step, final Object object, final boolean not) {
        if (step.getChecker() != null) {
            try {
                if (step.isNotAppliedByChecker()) {
                    return step.getChecker().test((T) object, not);
                } else {
                    return not ^ step.getChecker().test((T) object, not);
                }
            } catch (Throwable e) {
                return false;
            }
        }
        return !not;
    }

    protected static boolean isValid(final boolean previousOK, final boolean currentOK, final EnumOperator operator) {
        boolean ok = false;
        if (operator == null) { // AND
            ok = previousOK & currentOK;
        } else {
            switch (operator) {
            case OR:
                ok = previousOK | currentOK;
                break;
            case XOR:
                ok = previousOK ^ currentOK;
                break;
            case NAND:
                ok = !previousOK & !currentOK;
                break;
            case NOR:
                ok = !previousOK | !currentOK;
                break;
            case AND: // intentional fall-through
            default:
                ok = previousOK & currentOK;
            }
        }
        return ok;
    }

    protected static boolean isValid(final boolean all, final boolean not, final int found, final int size) {
        if (all) {
            if (not) { // NOT ALL
                return found > 0 && found < size;
            } else { // ALL
                return found == size;
            }
        } else if (not) { // NOT ANY
            return found == 0;
        } else { // ANY
            return found > 0;
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> T getLastChecked(final List<ParameterAssertor<?>> parameters) {
        final int size = parameters.size();
        T object = null;
        for (int i = size - 1; i >= 0; i--) {
            if (parameters.get(i).isChecked()) {
                object = (T) parameters.get(i).getObject();
                break;
            }
        }
        return object;
    }
}
