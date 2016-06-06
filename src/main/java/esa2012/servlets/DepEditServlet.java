package esa2012.servlets;

import esa2012.service.datatransport.DepartmentDTO;
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
public class DepEditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Service service = Service.INSTANCE;

        boolean update = req.getParameter("upd")!=null;
        boolean insert = req.getParameter("add")!=null;

        String url = "/deplist";

        DepartmentDTO depObj = buildObject(req);

        ErrorMessages messages = service.validate(depObj);
        if (messages.hasErrors()) {
            insert = false;
            update = false;
            req.setAttribute("errorlist", messages);
            req.setAttribute("depobj", depObj);
            url = "pages/depedit.jsp";
        }

        if (insert) {
            service.addDepartment(depObj);
        }
        if (update) {
            service.updateDepartment(depObj);
        }

        req.getRequestDispatcher(resp.encodeURL(url)).forward(req, resp);

    }


    private DepartmentDTO buildObject(HttpServletRequest request) {
        DepartmentDTO departmentDTO = new DepartmentDTO();

        if (!request.getParameter("dep_id").equals("")) {
            departmentDTO.setId(Integer.valueOf(request.getParameter("dep_id")));
        }

        departmentDTO.setDepName(request.getParameter("dep_name"));
        return departmentDTO;
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }


}
