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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumStep;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.CastUtils;
import fr.landel.utils.commons.ObjectUtils;
import fr.landel.utils.commons.StringUtils;
import fr.landel.utils.commons.builder.ToStringBuilder;
import fr.landel.utils.commons.builder.ToStringStyles;

/**
 * Class to store results of assertion at each step. It contains the current
 * result and the list of parameters added at each assertion.
 * 
 * <p>
 * What it's stored:
 * </p>
 * <ul>
 * <li>object: the object to check</li>
 * <li>type: the type of object to check</li>
 * <li>operator: the operator applied to the next step</li>
 * <li>not: if not has to be applied to the next step</li>
 * <li>preconditionOK: if preconditions are valid (here we ensured that the
 * check will not throw a {@link NullPointerException} by example)</li>
 * <li>valid: the result of the check (the associated function is only run if
 * preconditions are ok)</li>
 * <li>preconditionMessage: the message applied if preconditions aren't
 * valid</li>
 * <li>message: the message applied if preconditions are valid and check result
 * is ko</li>
 * <li>parameters: the whole list of parameters (includes previous checked
 * object and assertion parameters)</li>
 * </ul>
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <T>
 *            the type of assertor result
 */
public class StepAssertor<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8316517833558101416L;

    private final StepAssertor<?> previousStep;
    private final Optional<StepAssertor<?>> subStep;
    private final Optional<Function<Object, ? extends Step<?, ?>>> subAssertor;
    private final Optional<Function<Object, Object>> mapper;

    private final EnumAnalysisMode analysisMode;

    private final EnumStep stepType;
    private final T object;
    private final EnumType type;
    private final boolean checked;
    private final EnumOperator operator;
    private final boolean not;

    // (object) -> precondition OK?
    private Predicate<T> preChecker;
    // default key message (used id precondition ko or message null)
    private CharSequence messageKey;
    private boolean messageKeyNot;
    // (object, not) -> valid?
    private BiPredicate<T, Boolean> checker;
    // if 'not' is directly applied by checker
    private boolean notAppliedByChecker;
    private MessageAssertor message;

    private List<ParameterAssertor<?>> parameters;

    /**
     * Base constructor
     *
     * @param stepType
     *            the step type
     * @param previousStep
     *            the previous step
     * @param subStep
     *            the sub step
     * @param object
     *            the object under check
     * @param type
     *            the object type
     * @param checked
     *            if the object has to be checked
     * @param operator
     *            the operator for the previous combination (with next step)
     * @param not
     *            if NOT operator is applied on the next checker
     * @param analysisMode
     *            the analysis preferred mode
     * @param <X>
     *            the previous step type
     * @param <Y>
     *            the sub step type
     */
    private <X, Y, S extends Step<S, Y>> StepAssertor(final EnumStep stepType, final StepAssertor<X> previousStep,
            final StepAssertor<Y> subStep, final Function<X, S> subAssertor, final Function<X, Y> mapper, final T object,
            final EnumType type, final boolean checked, final EnumOperator operator, final boolean not,
            final EnumAnalysisMode analysisMode) {

        this.stepType = stepType;
        this.subStep = Optional.ofNullable(subStep);
        this.subAssertor = CastUtils.cast(Optional.ofNullable(subAssertor));
        this.mapper = CastUtils.cast(Optional.ofNullable(mapper));

        this.previousStep = previousStep;

        this.object = object;
        this.type = type;
        this.checked = checked;

        this.operator = operator;
        this.not = not;

        this.analysisMode = ObjectUtils.defaultIfNull(analysisMode, EnumAnalysisMode.STANDARD);
    }

    /**
     * Constructor for each {@link Assertor#that}
     *
     * @param object
     *            the object under check
     * @param type
     *            the type of the object
     * @param analysisMode
     *            the analysis preferred mode
     */
    public StepAssertor(final T object, final EnumType type, final EnumAnalysisMode analysisMode) {
        this(EnumStep.CREATION, null, null, null, null, object, type, true, null, false, analysisMode);
    }

    /**
     * Constructor for predicate {@code Assertor.of...}
     * 
     * @param type
     *            the type of object
     * @param analysisMode
     *            the analysis preferred mode
     */
    public StepAssertor(final EnumType type, final EnumAnalysisMode analysisMode) {
        this(EnumStep.PREDICATE, null, null, null, null, null, type, false, null, false, analysisMode);
    }

    /**
     * Combining constructor, for (NOT operator) (not is set to true)
     *
     * @param previousStep
     *            the previous step
     */
    public StepAssertor(final StepAssertor<T> previousStep) {
        this(EnumStep.NOT, previousStep, null, null, null, null, null, false, null, true, previousStep.getAnalysisMode());
    }

    /**
     * Combining constructor, for (NOT operator) (not is set to true)
     *
     * @param previousStep
     *            the previous step
     * @param object
     *            the object under check
     */
    public StepAssertor(final StepAssertor<T> previousStep, final T object) {
        // the type is not required already loaded from predicate step
        this(EnumStep.PREDICATE_OBJECT, previousStep, null, null, null, object, null, true, null, false, previousStep.getAnalysisMode());
    }

    /**
     * Combining constructor with new object, for (AND, OR and XOR combinations)
     *
     * @param previousStep
     *            the previous step
     * @param object
     *            the object under check
     * @param type
     *            the object type
     * @param operator
     *            the operator for the next combination
     * @param analysisMode
     *            the analysis preferred mode
     * @param <X>
     *            the type of previous step object
     */
    public <X> StepAssertor(final StepAssertor<X> previousStep, final T object, final EnumType type, final EnumOperator operator,
            final EnumAnalysisMode analysisMode) {
        this(EnumStep.OBJECT, previousStep, null, null, null, object, type, true, operator, false, analysisMode);
    }

    /**
     * Combining constructor with new object, for (AND, OR and XOR combinations)
     *
     * @param previousStep
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the object type
     * @param operator
     *            the operator for the next combination
     * @param analysisMode
     *            the analysis preferred mode
     * @param <X>
     *            the type of previous step object
     */
    public <X> StepAssertor(final StepAssertor<X> previousStep, final Function<X, T> mapper, final EnumType type,
            final EnumOperator operator, final EnumAnalysisMode analysisMode) {
        this(EnumStep.PROPERTY, previousStep, null, null, mapper, null, type, true, operator, false, analysisMode);
    }

    /**
     * 
     * Combining constructor, for (AND, OR and XOR combinations)
     *
     * @param previousStep
     *            The previous step
     * @param operator
     *            The operator for the next combination
     */
    public StepAssertor(final StepAssertor<T> previousStep, final EnumOperator operator) {
        this(EnumStep.OPERATOR, previousStep, null, null, null, null, null, false, operator, false, previousStep.getAnalysisMode());
    }

    /**
     * Standard combining constructor for sub Assertor (not is set to false)
     *
     * @param previousStep
     *            the previous step
     * @param subStep
     *            the sub step
     * @param operator
     *            The operator for the next combination
     * @param <Y>
     *            the sub step type
     */
    public <Y> StepAssertor(final StepAssertor<T> previousStep, final StepAssertor<Y> subStep, final EnumOperator operator) {
        this(EnumStep.SUB, previousStep, subStep, null, null, null, null, false, operator, false, previousStep.getAnalysisMode());
    }

    /**
     * Standard combining constructor for sub Assertor (not is set to false)
     *
     * @param previousStep
     *            the previous step
     * @param subAssertor
     *            the sub assertor function
     * @param operator
     *            The operator for the next combination
     * @param <Y>
     *            the sub step type
     * @param <S>
     *            the output step type
     */
    public <Y, S extends Step<S, Y>> StepAssertor(final StepAssertor<T> previousStep, final Function<T, S> subAssertor,
            final EnumOperator operator) {
        this(EnumStep.SUB_ASSERTOR, previousStep, null, subAssertor, null, null, null, false, operator, false,
                previousStep.getAnalysisMode());
    }

    /**
     * Standard combining constructor (not is set to false)
     *
     * @param previousStep
     *            The previous step
     * @param preChecker
     *            the checker for preconditions
     * @param checker
     *            the main checker (only applied if preChecker returns true)
     * @param notAppliedByChecker
     *            if 'not' is directly applied by checker
     * @param message
     *            the error message (only applied if preChecker returns true and
     *            checker returns false)
     * @param messageKey
     *            the default message key (used if preChecker returns false or
     *            if checker returns false and message is null)
     * @param messageKeyNot
     *            if the message key has to be suffixed by NOT
     * @param parameters
     *            the list of parameters for the current step
     */
    @SafeVarargs
    public StepAssertor(final StepAssertor<T> previousStep, final Predicate<T> preChecker, final BiPredicate<T, Boolean> checker,
            final boolean notAppliedByChecker, final MessageAssertor message, final CharSequence messageKey, final boolean messageKeyNot,
            final ParameterAssertor<?>... parameters) {
        this(EnumStep.ASSERTION, previousStep, null, null, null, null, null, false, null, false, previousStep.getAnalysisMode());

        this.messageKey = messageKey;
        this.messageKeyNot = messageKeyNot;
        this.preChecker = preChecker;
        this.checker = checker;
        this.notAppliedByChecker = notAppliedByChecker;
        this.message = message;

        this.parameters = new ArrayList<>(Arrays.asList(parameters));
    }

    /**
     * Standard combining constructor without precondition (not is set to false)
     *
     * @param previousStep
     *            The previous step
     * @param checker
     *            the main checker (only applied if preChecker returns true)
     * @param notAppliedByChecker
     *            if 'not' is directly applied by checker
     * @param message
     *            the error message (only applied if preChecker returns true and
     *            checker returns false)
     * @param messageKey
     *            the default message key (used if preChecker returns false or
     *            if checker returns false and message is null)
     * @param messageKeyNot
     *            if the message key has to be suffixed by NOT
     * @param parameters
     *            the list of parameters for the current step
     */
    @SafeVarargs
    public StepAssertor(final StepAssertor<T> previousStep, final BiPredicate<T, Boolean> checker, final boolean notAppliedByChecker,
            final MessageAssertor message, final CharSequence messageKey, final boolean messageKeyNot,
            final ParameterAssertor<?>... parameters) {
        this(previousStep, null, checker, notAppliedByChecker, message, messageKey, messageKeyNot, parameters);
    }

    /**
     * @return the preChecker
     */
    public Predicate<T> getPreChecker() {
        return this.preChecker;
    }

    /**
     * @return the checker
     */
    public BiPredicate<T, Boolean> getChecker() {
        return this.checker;
    }

    /**
     * @return the messageKey
     */
    public CharSequence getMessageKey() {
        return this.messageKey;
    }

    /**
     * @return the message
     */
    public MessageAssertor getMessage() {
        return this.message;
    }

    /**
     * @return the stepType
     */
    public EnumStep getStepType() {
        return this.stepType;
    }

    /**
     * @return the previousStep
     */
    public StepAssertor<?> getPreviousStep() {
        return this.previousStep;
    }

    /**
     * @return the object
     */
    public T getObject() {
        return this.object;
    }

    /**
     * @return the type
     */
    public EnumType getType() {
        return this.type;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return this.checked;
    }

    /**
     * @return the operator
     */
    public EnumOperator getOperator() {
        return this.operator;
    }

    /**
     * @return the not
     */
    public boolean isNot() {
        return this.not;
    }

    /**
     * @return the subStep
     */
    public Optional<StepAssertor<?>> getSubStep() {
        return this.subStep;
    }

    /**
     * If the NOT is directly managed by the checker
     * 
     * @return the notAppliedByChecker
     */
    public boolean isNotAppliedByChecker() {
        return this.notAppliedByChecker;
    }

    /**
     * @return the preferred analysis mode
     */
    public EnumAnalysisMode getAnalysisMode() {
        return this.analysisMode;
    }

    /**
     * @return the mapper function
     */
    public Optional<Function<Object, Object>> getMapper() {
        return this.mapper;
    }

    /**
     * @return the sub assertor
     */
    public Optional<Function<Object, ? extends Step<?, ?>>> getSubAssertor() {
        return this.subAssertor;
    }

    /**
     * @return the clone of parameters
     */
    public List<ParameterAssertor<?>> getParameters() {
        return this.parameters;
    }

    /**
     * @return the messageKeyNot
     */
    public boolean isMessageKeyNot() {
        return this.messageKeyNot;
    }

    @Override
    public String toString() {
        final ToStringBuilder sb = new ToStringBuilder(ToStringStyles.JSON_SPACED);

        sb.append(this.stepType.name());

        switch (this.stepType) {
        case CREATION:
            sb.append("object", this.object);
            sb.append("type", this.type);
            sb.append("analysisMode", this.analysisMode);
            break;
        case OBJECT:
            sb.append("object", this.object);
            sb.append("type", this.type);
            sb.append("analysisMode", this.analysisMode);
            sb.append("operator", this.operator);
            break;
        case PROPERTY:
            sb.append("type", this.type);
            sb.append("analysisMode", this.analysisMode);
            sb.append("operator", this.operator);
            break;
        case PREDICATE:
            sb.append("type", this.type);
            sb.append("analysisMode", this.analysisMode);
            break;
        case PREDICATE_OBJECT:
            sb.append("object", this.object);
            sb.append("analysisMode", this.analysisMode);
            break;
        case OPERATOR: // intentional fall-through
        case SUB_ASSERTOR: // intentional fall-through
        case SUB:
            sb.append("operator", this.operator);
            break;
        case NOT:
            sb.append("not", this.not);
            break;
        case ASSERTION:
            sb.append("key", this.messageKey);
            sb.append("key not", this.messageKeyNot);
            sb.appendAndFormat("parameters", this.parameters, StringUtils::joinComma);
            sb.append("message", this.message);
            break;
        default:
        }

        return sb.build();
    }
}
