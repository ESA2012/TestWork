package esa2012.service.customchecks;

import esa2012.model.Employee;
import esa2012.service.datatransport.EmployeeForm;
import esa2012.service.Service;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import java.util.List;

/**
 * Created by snake on 05.06.16.
 */
public class NotInEmailsCheck extends AbstractAnnotationCheck<NotInEmails> {

    public boolean isSatisfied(Object validatedObject, Object valueToValidate, OValContext context, Validator validator)
    {
        if (!(validatedObject instanceof EmployeeForm)) return false;

        if (valueToValidate == null) return true;

        Service service = Service.INSTANCE;

        // used emails list
        List<String> usedEmails = service.getUsedEmails();

        // gets id of violated employee
        int id = ((EmployeeForm)validatedObject).getId()!=null?((EmployeeForm)validatedObject).getId():0;
        // email of violated employee
        String email = ((EmployeeForm)validatedObject).getEmail();

        if (id>0) {
            Employee employeeDTO = service.getEmployee(id);
            if (employeeDTO.getId()==id && employeeDTO.getEmail().equals(email)) {
                usedEmails.remove(email);
            }
        }

        return !usedEmails.contains(email);
    }

}
