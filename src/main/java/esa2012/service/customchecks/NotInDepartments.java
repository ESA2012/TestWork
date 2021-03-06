package esa2012.service.customchecks;

/**
 * Created by ESA2012 on 04.06.16.
 **/

import net.sf.oval.configuration.annotation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(checkWith = NotInDepartmentsCheck.class)
public @interface NotInDepartments {
    String message() default "In list!";
}
