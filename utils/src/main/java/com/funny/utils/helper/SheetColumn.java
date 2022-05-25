package com.funny.utils.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetColumn {
    String name();

    String format() default "yyyy/m/d h:mm:s";

    int index() default -1;

    int width() default 4;

    boolean required() default false;

    boolean imported() default true;

    boolean exported() default true;
}
