package esa2012.service.customchecks;

import esa2012.service.datatransport.DepartmentDTO;
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
        if (!(validatedObject instanceof DepartmentDTO)) return false;

        if (valueToValidate == null) return true;

        List<DepartmentDTO> employeeDTOs = Service.INSTANCE.getDepartments();

        List<String> strings = employeeDTOs.stream().map(DepartmentDTO::getDepName).collect(Collectors.toList());

        return !strings.contains(((DepartmentDTO)validatedObject).getDepName());

    }


}
