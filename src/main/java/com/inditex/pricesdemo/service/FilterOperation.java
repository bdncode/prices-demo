package com.inditex.pricesdemo.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Collection;
import java.util.function.Function;

public class FilterOperation {

    public <T> void and(BooleanBuilder builder, T filterElement, Function<T, BooleanExpression> booleanExpressionFunction) {
        if (isValid(filterElement))
            builder.and(booleanExpressionFunction.apply(filterElement));
    }

    public boolean isValid(Object filterElement) {
        if (filterElement == null) return false;

        if (filterElement instanceof Collection)
            return !((Collection<?>) filterElement).isEmpty();

        return true;
    }
}
