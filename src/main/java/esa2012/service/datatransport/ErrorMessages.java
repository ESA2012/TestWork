package esa2012.service.datatransport;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by snake on 05.06.16.
 */
public class ErrorMessages implements Serializable {

    private List<String> errors = new LinkedList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
