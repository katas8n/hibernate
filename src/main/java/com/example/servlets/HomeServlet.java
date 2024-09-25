package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();

            UserDAO userDAO = new UserDAO();
            User user = new User();
            user.setUsername("john_doe");
            user.setEmail("john.doe@example.com");

            userDAO.saveUser(user, session);

            transaction.commit();

            resp.getWriter().println("<a href='http://localhost:8080/about-us'>About us !</a>");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                System.out.println("Rolling back transaction due to error");
                transaction.rollback();
            }

            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

            resp.getWriter().println("<h1>Error!</h1>");
        } finally {
            if (session != null && session.isOpen()) {
                System.out.println("Closing session");
                session.close();
            }
        }
    }
}
