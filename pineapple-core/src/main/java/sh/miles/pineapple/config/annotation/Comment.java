package sh.miles.pineapple.config.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
@Repeatable(Comments.class)
public @interface Comment {
    /**
     * @return the comment
     */
    String value() default "";
}
