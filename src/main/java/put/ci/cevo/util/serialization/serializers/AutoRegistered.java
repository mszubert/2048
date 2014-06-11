package put.ci.cevo.util.serialization.serializers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes defining a default serialization logic for a certain type should be marked with this annotation followed by
 * the property <code>defaultSerializer = true</code>. Any deprecated but still usable serializers should also be
 * marked, however the property should be set to false which is a default setting.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoRegistered {
	boolean defaultSerializer() default false;
}
