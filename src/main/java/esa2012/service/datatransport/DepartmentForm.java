package esa2012.service.datatransport;

import esa2012.model.Department;
import esa2012.service.customchecks.NotInDepartments;
import net.sf.oval.constraint.*;

import java.io.Serializable;



public class DepartmentForm implements Serializable{
    private Integer id;

    @NotBlank(message = "Departmen name not specified")
    @Length(min = 3, max = 64, message = "Department name must be between 3 and 64 characters")
    @NotInDepartments(message = "Department with the same name already exists")
    private String depName;

    public DepartmentForm(Department department) {
        this.depName = department.getDepName();
        this.id = department.getId();
    }

    public Department buildDepartment() {
        Department department = new Department();
        department.setId(this.id);
        department.setDepName(this.depName);
        return department;
    }

    public Integer getId() {
        return id;
    }

    public DepartmentForm() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
