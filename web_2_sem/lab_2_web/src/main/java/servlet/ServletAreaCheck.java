package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import model.Point;
import model.TablePoint;

public class ServletAreaCheck extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = req.getSession();
        context.log("ServletAreaCheck: doGet");

        Double x = Double.parseDouble(req.getParameter("x"));
        Double y = Double.parseDouble(req.getParameter("y"));
        Double r = Double.parseDouble(req.getParameter("r"));
        
        if (r < 2 || r > 5) {
            req.setAttribute("warning", "Value of R must be in range [2; 5]");
            req.getRequestDispatcher("./views/table.jsp").forward(req, resp);
            return;
        } else if (x < -5 || x > 3) {
            req.setAttribute("warning", "Value of X must be in range [-5; 3]");
            req.getRequestDispatcher("./views/table.jsp").forward(req, resp);
            return;
            // resp.sendRedirect(req.getContextPath() + "/");
        } else if (y < -5 || y > 3) {
            req.setAttribute("warning", "Value of Y must be in range [-5; 3]");
            // resp.sendRedirect(req.getContextPath() + "/");
            req.getRequestDispatcher("./views/table.jsp").forward(req, resp);
            return;
        }

        long startTime = System.nanoTime();
        
        Point point = new Point(x, y, r, checkHit(x, y, r), LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss", Locale.US)));
        
        TablePoint table;
        if (session.getAttribute("points") == null) {
            table = new TablePoint();
        } else {
            table = (TablePoint) session.getAttribute("points");
        }
        point.setDuration((System.nanoTime() - startTime) / 1000000.0F);
        table.addPoint(point);
        session.setAttribute("points", table);
        PrintWriter writer = resp.getWriter();
        writer.print(gson.toJson(table.getPoints()));
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    }

    private boolean checkHit(double x, double y, double r) {
        if (x > 0 && y > 0) {
            return false;
        } else if (x <= 0 && y >= 0 && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= r) {
            return true;
        } else if (x <= 0 && y < 0 && x >= -r && y >= -r) {
            return true;
        } else if (x > 0 && y <= 0 && (2 * x - r) <= y) {
            return true;
        }
        return false;
    }
}
