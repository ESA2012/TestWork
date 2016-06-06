package esa2012.servlets;

import esa2012.service.datatransport.EmployeeForm;
import esa2012.service.datatransport.ErrorMessages;
import esa2012.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by snake on 05.06.16.
 */
public class EmpEditServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Service service = Service.INSTANCE;

        String url = "emplist";

        String depIdStr = req.getParameter("dep");

        if (depIdStr!=null && !depIdStr.equals("")) {
            int depId = Integer.valueOf(req.getParameter("dep"));
            req.setAttribute("depobj", service.getDepartment(depId));
        }

        boolean update = req.getParameter("upd")!=null;
        boolean insert = req.getParameter("add")!=null;

        EmployeeForm empObj = getFormData(req);


        ErrorMessages messages = service.validate(empObj);
        if (messages.hasErrors()) {
            insert = false;
            update = false;
            req.setAttribute("errorlist", messages);
            req.setAttribute("empobj", empObj);
            url = "/pages/empedit.jsp";
        }

        if (insert) {
            service.addEmployee(empObj.buildEmployee());
        }
        if (update) {
            service.updateEmployee(empObj.buildEmployee());
        }

        req.getRequestDispatcher(url).forward(req, res);
    }


    private EmployeeForm getFormData(HttpServletRequest req) {
        EmployeeForm newemp = new EmployeeForm();
        String id = req.getParameter("emp_id");
        if (id!=null && !"".equals(id)) {
            newemp.setId(Integer.valueOf(id));
        }
        String depid = req.getParameter("dep_id");
        if (depid!=null && !"".equals(depid)) {
            newemp.setDepId(Integer.valueOf(depid));
        }
        newemp.setFirstName(req.getParameter("emp_firstname"));
        newemp.setLastName(req.getParameter("emp_lastname"));
        newemp.setEmail(req.getParameter("emp_email"));
        newemp.setPosition(req.getParameter("emp_position"));
        newemp.setSalary(req.getParameter("emp_salary"));
        newemp.setDateOfBirth(req.getParameter("emp_dateofbirth"));
        return newemp;
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }
}
