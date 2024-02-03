package sh.miles.pineapple.nms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a feature requires NMS and may not work on multiple new releases immediately
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.RECORD_COMPONENT, ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface NMS {
}
