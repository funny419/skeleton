package com.funny.utils.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExport {
    String fileType() default "xlsx";

    String[] sheetNames() default {};

    boolean[] isHiddenSheets() default {};

    boolean[] isIncludeHeaders() default {};

    int[] startRowIndexes() default {};

    short[] startColumnIndexes() default {};
}
