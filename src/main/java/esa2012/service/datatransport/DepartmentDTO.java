package esa2012.service.datatransport;

import esa2012.service.customchecks.NotInDepartments;
import net.sf.oval.constraint.*;

import java.io.Serializable;

/**
 * Created by snake on 02.06.16.
 */
public class DepartmentDTO implements Serializable{
    private Integer id;

    @NotBlank(message = "Departmen name not specified")
    @Length(min = 3, max = 64, message = "Department name must be between 3 and 64 characters")
    @NotInDepartments(message = "Department with the same name already exists")
    private String depName;

    public DepartmentDTO(String depName) {
        this.depName = depName;
    }

    public Integer getId() {
        return id;
    }

    public DepartmentDTO() {
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
