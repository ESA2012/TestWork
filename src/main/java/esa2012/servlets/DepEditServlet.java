package esa2012.servlets;

import esa2012.service.Service;
import esa2012.service.datatransport.DepartmentForm;
import esa2012.service.datatransport.ErrorMessages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet-controller for adding and updating Department info
 * Created by ESA2012 on 05.06.16.
 */
public class DepEditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Service service = Service.INSTANCE;

        boolean update = req.getParameter("upd")!=null;
        boolean insert = req.getParameter("add")!=null;

        String url = "/deplist";

        DepartmentForm depObj = getFormData(req);

        ErrorMessages messages = service.validate(depObj);
        if (messages.hasErrors()) {
            insert = false;
            update = false;
            req.setAttribute("errorlist", messages);
            req.setAttribute("depobj", depObj);
            url = "pages/depedit.jsp";
        }

        if (insert) {
            service.addDepartment(depObj.buildDepartment());
        }
        if (update) {
            service.updateDepartment(depObj.buildDepartment());
        }

        req.getRequestDispatcher(resp.encodeURL(url)).forward(req, resp);

    }


    private DepartmentForm getFormData(HttpServletRequest request) {
        DepartmentForm departmentForm = new DepartmentForm();

        if (!request.getParameter("dep_id").equals("")) {
            departmentForm.setId(Integer.valueOf(request.getParameter("dep_id")));
        }

        departmentForm.setDepName(request.getParameter("dep_name"));
        return departmentForm;
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }


}
