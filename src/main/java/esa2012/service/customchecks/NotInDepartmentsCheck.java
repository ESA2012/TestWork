package esa2012.service.customchecks;

import esa2012.model.Department;
import esa2012.service.datatransport.DepartmentForm;
import esa2012.service.Service;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by snake on 04.06.16.
 */
public class NotInDepartmentsCheck extends AbstractAnnotationCheck<NotInDepartments>{

    public boolean isSatisfied(Object validatedObject, Object valueToValidate, OValContext context, Validator validator)
    {
        if (!(validatedObject instanceof DepartmentForm)) return false;

        if (valueToValidate == null) return true;

        List<Department> employeeDTOs = Service.INSTANCE.getDepartments();

        List<String> strings = employeeDTOs.stream().map(Department::getDepName).collect(Collectors.toList());

        return !strings.contains(((DepartmentForm)validatedObject).getDepName());

    }


}
