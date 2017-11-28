package com.github.joostvdg.cab.scm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Encouraged {
    public boolean value() default true;
}
