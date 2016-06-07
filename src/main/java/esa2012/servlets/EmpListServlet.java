package esa2012.servlets;

import esa2012.service.Service;
import esa2012.service.datatransport.EmployeeForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet-controller for viewing list of Employees and delete Employee
 * Created by ESA2012 on 05.06.16.
 */
public class EmpListServlet extends HttpServlet {


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Service service = Service.INSTANCE;

        String url = "/pages/emplist.jsp";

        String depIdStr = req.getParameter("dep");
        String toDelete = req.getParameter("del");
        String toUpdate = req.getParameter("edt");

        int depId = Integer.valueOf(depIdStr);
        req.setAttribute("depobj", service.getDepartment(depId));

        if (toDelete!=null) {
            int id = Integer.valueOf(toDelete);
            service.removeEmployee(id);
            req.setAttribute("emplist", service.getEmployeesByDep(depId));
        }

        if (toUpdate!=null) {
            int id = Integer.valueOf(toUpdate);
            if (id > 0) {
                req.setAttribute("empobj", new EmployeeForm(service.getEmployee(id)));
            }
            url = "/pages/empedit.jsp";
        }

        if (toDelete == null & toUpdate == null) {
            req.setAttribute("emplist", service.getEmployeesByDep(depId));
         }


        req.getRequestDispatcher(url).forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }


}
