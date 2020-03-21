package home4;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email=req.getParameter("login");
        String password = req.getParameter("password");

        User_DAO user_dao = new User_DAO();

        Optional<User> user = user_dao.getUserByEmail(email);

        if (!user.isPresent()){
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        User users = user.get();

        if (users.getPassword().equals(password)){
            req.setAttribute("firstName", users.getFirstName());
            req.setAttribute("lastName", users.getLastName());
            req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}