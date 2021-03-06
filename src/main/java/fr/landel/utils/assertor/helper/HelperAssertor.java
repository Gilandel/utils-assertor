/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.assertor.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import fr.landel.utils.assertor.Step;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessagesAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.commons.ResultAssertor;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumStep;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.ObjectUtils;
import fr.landel.utils.commons.tuple.PairIso;

/**
 * Assertor helper class, to combine steps.
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

    /**
     * Transformer used to extract objects from parameters
     */
    protected static final Transformer<ParameterAssertor<?>, Object> PARAM_TRANSFORMER = new Transformer<ParameterAssertor<?>, Object>() {
        @Override
        public Object transform(final ParameterAssertor<?> input) {
            return input.getObject();
        }
    };

    /**
     * When the previous check is valid followed by operator OR, we don't need
     * to check the next steps, it's OK. Or, when the previous check is invalid
     * followed by operator NOR, we don't need to check the next steps, it's
     * also OK.
     */
    private static final BiPredicate<Boolean, EnumOperator> VALID = (valid, operator) -> (!valid && EnumOperator.NOR.equals(operator))
            || (valid && EnumOperator.OR.equals(operator));

    /**
     * When the previous check is valid followed by operator NAND, we don't need
     * to check the next steps, it's KO. Or, when the previous check is invalid
     * followed by operator AND, we don't need to check the next steps, it's
     * also KO.
     */
    private static final BiPredicate<Boolean, EnumOperator> INVALID = (valid, operator) -> (!valid && EnumOperator.AND.equals(operator))
            || (valid && EnumOperator.NAND.equals(operator));

    private static final String ERROR_CHECKER = "checker is missing";

    /**
     * Validates matcher mode and return the steps list reversed
     * 
     * @param step
     *            the end step
     * @param matcherMode
     *            if in matcher mode
     * @param <T>
     *            the type of current chcecked object
     * @return the steps list reversed
     * @throws UnsupportedOperationException
     *             if in matcher mode and if steps of type
     *             {@link EnumStep#CREATION}, {@link EnumStep#OBJECT} are found
     *             in the chain
     */
    private static <T> List<StepAssertor<?>> validatesAndReverse(final StepAssertor<T> step, final boolean matcherMode) {

        final List<StepAssertor<?>> steps = new ArrayList<>();
        steps.add(step);

        final boolean inMatcherMode = matcherMode || EnumStep.PREDICATE_OBJECT.equals(step.getStepType());

        StepAssertor<?> first = null;
        StepAssertor<?> currentStep = step;
        StepAssertor<?> previousStep;

        while ((previousStep = currentStep.getPreviousStep()) != null) {

            if (inMatcherMode) {
                final EnumStep s = previousStep.getStepType();

                if (EnumStep.PREDICATE.equals(s)) {
                    first = previousStep;

                } else if (EnumStep.CREATION.equals(s) || EnumStep.OBJECT.equals(s)) {
                    throw new UnsupportedOperationException("Creation step cannot be used in Predicate mode");
                }
            }

            steps.add(previousStep);

            currentStep = previousStep;
        }

        if (inMatcherMode && first == null) {
            throw new IllegalArgumentException("StepAssertor chain must contain a matcher step");
        }

        Collections.reverse(steps);

        return steps;
    }

    /**
     * The combine function (the main method). This method is called by each end
     * steps.
     * 
     * @param step
     *            the last step
     * @param loadMessage
     *            if the message has to loaded and formated
     * @param <T>
     *            the type of checked object
     * @return the result
     * @throws IllegalArgumentException
     *             if in matcher mode and object is not set
     */
    public static <T> ResultAssertor combine(final StepAssertor<T> step, final boolean loadMessage) {
        return combine(step, null, false, loadMessage);
    }

    /**
     * The combine function (the main method). This method is called by each end
     * steps.
     * 
     * <p>
     * What is matcher mode: it's an Assertor starting with
     * "{@code Assertor.matcher}...". In this mode, only one variable can be
     * checked through "{@code on(object)}" method. The aim of this is to create
     * the validation steps before in another method or in a static way to
     * improve performance.
     * </p>
     * 
     * @param step
     *            the last step
     * @param object
     *            the object to test (only in matcher mode)
     * @param marcherMode
     *            if it's in matcher mode (set with "on" method)
     * @param loadMessage
     *            if the message has to beloaded and formated
     * @param <T>
     *            the type of checked object
     * @return the result
     * @throws IllegalArgumentException
     *             if in matcher mode and object is not set
     */
    public static <T> ResultAssertor combine(final StepAssertor<T> step, final Object object, final boolean marcherMode,
            final boolean loadMessage) {

        final List<StepAssertor<?>> steps = validatesAndReverse(step, marcherMode);

        boolean not = false;
        boolean valid = true;
        PairIso<Boolean> resultValid;
        EnumOperator operator = null;
        final MessagesAssertor messages = new MessagesAssertor();
        Object obj;
        final Object matcherObject;
        boolean checked = false;
        EnumType type = null;
        ParameterAssertor<?> param = null;

        final List<ParameterAssertor<?>> parameters = new ArrayList<>();

        Optional<Pair<Boolean, MessagesAssertor>> dontNeedCheck = Optional.empty();

        // get the object to check
        // in matcher mode, two ways are available to inject the object:
        // - first: directly through the combine function
        boolean inMatcherMode = marcherMode;
        if (inMatcherMode) {
            obj = object;
            // - second:
        } else if (EnumStep.PREDICATE_OBJECT.equals(step.getStepType())) {
            obj = step.getObject();
            inMatcherMode = true;
        } else {
            obj = null;
        }

        // create a temporary variable for sub steps (in matcher mode)
        matcherObject = obj;

        for (StepAssertor<?> s : steps) {
            if (s.getStepType() == null) {
                continue;
            }

            switch (s.getStepType()) {

            // the first step of an Assertor in predicate mode (ex:
            // Assertor.ofNumber...)
            case PREDICATE:
                type = s.getType();
                if (EnumType.UNKNOWN.equals(type)) {
                    type = EnumType.getType(obj);
                }
                param = new ParameterAssertor<>(obj, type, true);

                parameters.add(param);

                break;
            // the first step of an Assertor (ex: Assertor.that(object)...)
            case CREATION:
                obj = s.getObject();
                type = s.getType();
                checked = s.isChecked();
                param = new ParameterAssertor<>(obj, type, checked);

                parameters.add(param);

                break;
            // the validation step
            case ASSERTION:
                parameters.addAll(s.getParameters());

                // if precondition returns false, we end all treatments
                if (!HelperAssertor.preCheck(s, obj)) {
                    return HelperAssertor.getPreconditionMessage(s, param, parameters, loadMessage);

                } else {
                    resultValid = HelperAssertor.validatesAndGetMessage(s, param, obj, valid, not, operator, messages, loadMessage);
                    valid = resultValid.getRight();
                }

                if (operator != null && !valid) {
                    dontNeedCheck = checkValidityAndOperator(resultValid.getLeft(), operator, messages, loadMessage);
                }

                not = false;

                break;
            // the combining step between two validation steps
            case OPERATOR:
                operator = s.getOperator();

                dontNeedCheck = checkValidityAndOperator(valid, operator, messages, loadMessage);

                break;
            // the not step to reverse the next validation step
            case NOT:
                not = not ^ s.isNot();

                break;
            // the object provided by the mapper
            case PROPERTY:
                if (s.getMapper().isPresent()) {
                    operator = s.getOperator();
                    obj = s.getMapper().get().apply(obj);
                    type = s.getType();
                    checked = s.isChecked();
                    param = new ParameterAssertor<>(obj, type, checked);

                    parameters.add(param);

                    dontNeedCheck = checkValidityAndOperator(valid, operator, messages, loadMessage);
                } else {
                    throw new IllegalStateException("property cannot be null");
                }
                break;
            // the other object to validate
            case OBJECT:
                operator = s.getOperator();
                obj = s.getObject();
                type = s.getType();
                checked = s.isChecked();
                param = new ParameterAssertor<>(obj, type, checked);

                parameters.add(param);

                dontNeedCheck = checkValidityAndOperator(valid, operator, messages, loadMessage);

                break;
            // the sub step to emulate parenthesis in a check (ex:
            // Assertor.that(2).isZero().or(Assertor.that(2).isGTE(1).and().isLTE(10)))
            case SUB:
                if (s.getSubStep().isPresent()) {
                    dontNeedCheck = checkValidityAndOperator(valid, s.getOperator(), messages, loadMessage);

                    if (!dontNeedCheck.isPresent()) {
                        final Triple<Boolean, EnumOperator, ResultAssertor> output = HelperAssertor.managesSub(s, matcherObject,
                                inMatcherMode, parameters, valid, operator, messages, loadMessage);

                        if (output.getRight() != null) {
                            return output.getRight();
                        } else {
                            valid = output.getLeft();
                            operator = output.getMiddle();
                        }
                    }
                }
                break;
            // sub assertor step to check sub properties
            case SUB_ASSERTOR:
                if (s.getSubAssertor().isPresent()) {
                    operator = s.getOperator();

                    dontNeedCheck = checkValidityAndOperator(valid, operator, messages, loadMessage);

                    if (!dontNeedCheck.isPresent()) {
                        final Step<?, ?> stepSubAssertor = Objects.requireNonNull(s.getSubAssertor().get().apply(obj),
                                "Sub assertor mapper cannot be null");
                        final ResultAssertor intermediateResult = combine(stepSubAssertor.getStep(), null, inMatcherMode, loadMessage);

                        valid = intermediateResult.isPrecondition() && isValid(valid, intermediateResult.isValid(), operator);

                        parameters.addAll(intermediateResult.getParameters());

                        if (!valid && loadMessage && intermediateResult.getMessages() != null) {
                            if (messages.isNotEmpty()) {
                                messages.append(operator);
                            }
                            messages.append(intermediateResult.getMessages());
                        }

                        if (intermediateResult.isPrecondition()) {
                            dontNeedCheck = checkValidityAndOperator(intermediateResult.isValid(), operator, messages, loadMessage);
                        } else {
                            dontNeedCheck = Optional.of(Pair.of(false, intermediateResult.getMessages()));
                        }
                    }
                } else {
                    throw new IllegalStateException("sub assertor cannot be null");
                }
                break;
            default: // MATCHER_OBJECT (don't need treatment)
            }

            if (dontNeedCheck.isPresent()) {
                return new ResultAssertor(true, dontNeedCheck.get().getKey(), dontNeedCheck.get().getValue(), parameters);
            }
        }

        return new ResultAssertor(true, valid, messages, parameters);
    }

    private static Optional<Pair<Boolean, MessagesAssertor>> checkValidityAndOperator(final boolean valid, final EnumOperator operator,
            final MessagesAssertor messages, final boolean loadMessage) {

        Pair<Boolean, MessagesAssertor> result = null;

        if (VALID.test(valid, operator)) {

            result = Pair.of(true, messages);

        } else if (INVALID.test(valid, operator)) {

            final MessagesAssertor formattedMessage;
            if (loadMessage) {
                if (messages.isNotEmpty()) {
                    formattedMessage = messages;
                } else {

                    formattedMessage = new MessagesAssertor();
                    formattedMessage.append(MSG.INVALID_WITHOUT_MESSAGE, false,
                            new CharSequence[] {String.valueOf(valid), String.valueOf(operator)}, null);
                }
            } else {
                formattedMessage = null;
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

        final MessagesAssertor error;
        if (loadMessage) {
            error = new MessagesAssertor();
            error.append(step.getMessageKey(), false, null, assertParameters);
        } else {
            error = null;
        }

        return new ResultAssertor(false, false, error, parameters);
    }

    private static <T> PairIso<Boolean> validatesAndGetMessage(final StepAssertor<T> step, final ParameterAssertor<?> param,
            final Object object, final boolean valid, final boolean not, final EnumOperator operator, final MessagesAssertor messages,
            final boolean loadMessage) {

        final boolean currentValid = HelperAssertor.check(step, CastUtils.cast(object), not);
        final boolean nextValid = HelperAssertor.isValid(valid, currentValid, operator);

        if (!nextValid && loadMessage) {
            if (messages.isNotEmpty() && operator != null) {
                messages.append(operator);
            }

            final List<ParameterAssertor<?>> assertParameters = new ArrayList<>();
            assertParameters.add(param);
            assertParameters.addAll(step.getParameters());

            messages.append(step.getMessageKey(), not ^ step.isMessageKeyNot(), null, assertParameters, step.getMessage());
        }

        return PairIso.of(currentValid, nextValid);
    }

    private static Triple<Boolean, EnumOperator, ResultAssertor> managesSub(final StepAssertor<?> step, final Object matcherObject,
            final boolean marcherMode, final List<ParameterAssertor<?>> parameters, final boolean valid, final EnumOperator operator,
            final MessagesAssertor messages, final boolean loadMessage) {

        final StepAssertor<?> subStep = step.getSubStep().get();
        final EnumOperator stepOperator = step.getOperator();
        EnumOperator nextOperator = operator;
        boolean nextValid = valid;

        final ResultAssertor subResult = HelperAssertor.combine(subStep, matcherObject, marcherMode, loadMessage);

        // in matcher mode, the matcher is not required, so we remove it
        final int size = subResult.getParameters().size();
        if (matcherObject != null && size > 1) {
            parameters.addAll(subResult.getParameters().subList(1, size));
        } else {
            parameters.addAll(subResult.getParameters());
        }

        if (!subResult.isPrecondition()) {
            return Triple.of(false, null, subResult);
        } else {
            nextOperator = stepOperator;

            nextValid = HelperAssertor.isValid(nextValid, subResult.isValid(), nextOperator);

            if (!nextValid && loadMessage && subResult.getMessages() != null) {

                if (messages.isNotEmpty() && nextOperator != null) {
                    messages.append(nextOperator);
                }

                messages.append(subResult.getMessages());
            }
        }

        return Triple.of(nextValid, nextOperator, null);
    }

    private static <T> boolean preCheck(final StepAssertor<T> step, final Object object) {
        if (step.getPreChecker() != null) {
            return step.getPreChecker().test(CastUtils.cast(object));
        }
        return true;
    }

    private static <T> boolean check(final StepAssertor<T> step, final T object, final boolean not) {
        Objects.requireNonNull(step.getChecker(), ERROR_CHECKER);

        try {
            if (step.isNotAppliedByChecker()) {
                return step.getChecker().test(object, not);
            } else {
                return not ^ step.getChecker().test(object, not);
            }
        } catch (Throwable e) {
            return false;
        }
    }

    public static boolean isValid(final boolean previousOK, final boolean currentOK, final EnumOperator operator) {
        return ObjectUtils.defaultIfNull(operator, EnumOperator.AND).isValid(previousOK, currentOK);
    }

    public static boolean isValid(final boolean all, final boolean not, final long found, final int size) {
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

    public static <T> boolean isValid(final Stream<T> stream, final Predicate<T> predicate, final boolean all, final boolean not,
            final Supplier<Integer> size) {
        if (all && !not) { // ALL
            return stream.allMatch(predicate);
        } else if (!all && !not) { // ANY
            return stream.anyMatch(predicate);
        } else if (!all) { // NOT ANY
            return stream.noneMatch(predicate);
        } else { // NOT ALL
            return isValid(all, not, stream.filter(predicate).count(), size.get());
        }
    }

    /**
     * Extract the last object (checked variable) from parameters
     * 
     * @param parameters
     *            the parameters list
     * @param <T>
     *            the type of the last object
     * @return a typed object
     */
    public static <T> T getLastChecked(final List<ParameterAssertor<?>> parameters) {
        final int size = parameters.size();
        T object = null;
        for (int i = size - 1; i >= 0; --i) {
            if (parameters.get(i).isChecked()) {
                object = CastUtils.cast(parameters.get(i).getObject());
                break;
            }
        }
        return object;
    }

    /**
     * Combines and gets the message from Assertor result
     * 
     * @param result
     *            the Assertor result
     * @return the message (can be empty)
     */
    public static String getMessage(final ResultAssertor result) {
        if (result.getMessages() != null) {
            return result.getMessages().build();
        } else {
            return StringUtils.EMPTY;
        }
    }
}
