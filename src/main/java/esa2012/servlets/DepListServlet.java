package esa2012.servlets;

import esa2012.datalayer.DBConnections;
import esa2012.datalayer.DBUtils;
import esa2012.service.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by snake on 04.06.16.
 */
public class DepListServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Service service = Service.INSTANCE;

        String url = "/pages/deplist.jsp";

        String toDelete = req.getParameter("del");
        String toUpdate = req.getParameter("edt");

        if (toDelete != null) {
            service.removeDepartment(Integer.valueOf(toDelete));
            req.setAttribute("deplist", service.getDepartments());
        }


        if (toUpdate != null) {
            int dep = Integer.valueOf(toUpdate);
            if (dep > 0) {
                req.setAttribute("depobj", service.getDepartment(dep));
            }
            url = "/pages/depedit.jsp";
        }

        if (toUpdate == null & toDelete == null) {
            req.setAttribute("deplist", service.getDepartments());
        }
        req.getRequestDispatcher(resp.encodeURL(url)).forward(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File fileDB = new File(classLoader.getResource("tables.sql").getFile());
            File fileDemoData = new File(classLoader.getResource("data.sql").getFile());
            DBUtils.executeSQLfile(fileDB, DBConnections.getConnection());
            DBUtils.executeSQLfile(fileDemoData, DBConnections.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to initialize data base");
        }

    }

}
