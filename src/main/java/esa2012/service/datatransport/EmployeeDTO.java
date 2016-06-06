package esa2012.service.datatransport;

import esa2012.service.customchecks.NotInEmails;
import net.sf.oval.constraint.*;

import java.io.Serializable;

/**
 * Created by snake on 02.06.16.
 */
public class EmployeeDTO implements Serializable {
    private Integer id;

    @NotBlank(message = "Employee's first name not valid")
    @Length(min = 2, max = 32, message = "Employee's first name too short or too long")
    private String firstName;

    @NotBlank(message = "Employee's last name not valid")
    @Length(min = 2, max = 32, message = "Employee's first name too short or too long")
    private String lastName;

    private String position;

    @MatchPattern(pattern = "(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((19|20)\\d\\d)|^$", message = "Incorrect date format. Must be YYYY-MM-DD")
    private String dateOfBirth;

    @Email(message = "Not valid e-mail")
    @NotInEmails(message = "Specified e-mail already in use")
    private String email;

//    @Digits(message = "Not valid salary")
    @MatchPattern(pattern = "^(\\d*)(\\.(\\d*))?$|^$", message = "Not valid salary")
    private String salary;

    private Integer depId;

    public EmployeeDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
}
