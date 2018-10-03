package daggerok.framework.command;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface CommandHandler { }
