package esa2012.service.customchecks;

import esa2012.service.datatransport.EmployeeDTO;
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
        if (!(validatedObject instanceof EmployeeDTO)) return false;

        if (valueToValidate == null) return true;

        Service service = Service.INSTANCE;

        // used emails list
        List<String> usedEmails = service.getUsedEmails();

        // gets id of violated employee
        int id = ((EmployeeDTO)validatedObject).getId()!=null?((EmployeeDTO)validatedObject).getId():0;
        // email of violated employee
        String email = ((EmployeeDTO)validatedObject).getEmail();

        if (id>0) {
            EmployeeDTO employeeDTO = service.getEmployee(id);
            if (employeeDTO.getId()==id && employeeDTO.getEmail().equals(email)) {
                usedEmails.remove(email);
            }
        }

        return !usedEmails.contains(email);
    }

}
