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
package fr.landel.utils.assertor.commons;

import java.io.Serializable;

import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.builder.ToStringBuilder;
import fr.landel.utils.commons.builder.ToStringStyles;

/**
 * Assertor parameter DTO
 *
 * @since Feb 4, 2017
 * @author Gilles
 *
 * @param <T>
 *            the type of object
 */
public class ParameterAssertor<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private final T object;
    private final EnumType type;
    private final boolean checked;

    /**
     * Constructor (for parameter under validation)
     *
     * @param object
     *            The object
     * @param type
     *            The type of object
     * @param checked
     *            if the object is checked
     */
    public ParameterAssertor(final T object, final EnumType type, final boolean checked) {
        this.object = object;
        this.type = type;
        this.checked = checked;
    }

    /**
     * Constructor (for parameter used to check)
     *
     * @param object
     *            The object
     * @param type
     *            The type of object
     */
    public ParameterAssertor(final T object, final EnumType type) {
        this(object, type, false);
    }

    /**
     * Constructor (for parameter used to check)
     *
     * @param object
     *            The object
     */
    public ParameterAssertor(final T object) {
        this(object, EnumType.getType(object), false);
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

    @Override
    public String toString() {
        // @formatter:off
        return new ToStringBuilder(this, ToStringStyles.JSON_SPACED)
                .append("object", this.object)
                .append("type", this.type)
                .append("checked", this.checked)
                .build();
        // @formatter:on
    }
}
