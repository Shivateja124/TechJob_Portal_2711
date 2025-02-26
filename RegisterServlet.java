// Source code is decompiled from a .class file using FernFlower decompiler.
package com.register;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet({"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RegisterServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String fullname = request.getParameter("fullname");
      String email = request.getParameter("email");
      long contact = 0L;

      try {
         contact = Long.parseLong(request.getParameter("contact"));
      } catch (NumberFormatException var16) {
         out.print("<h1>Error: Invalid contact number</h1>");
         return;
      }

      String username = request.getParameter("username");
      String password = request.getParameter("password");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         String sql = "INSERT INTO users (fullname, email, contact, username, password) VALUES (?, ?, ?, ?, ?)";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setString(1, fullname);
         ps.setString(2, email);
         ps.setLong(3, contact);
         ps.setString(4, username);
         ps.setString(5, password);
         int rowsAffected = ps.executeUpdate();
         if (rowsAffected > 0) {
            out.print("<h1>Registration successful!</h1>");
         } else {
            out.print("<h1>Registration failed. Please try again.</h1>");
         }

         ps.close();
         con.close();
      } catch (ClassNotFoundException var14) {
         out.print("<h1>Server error. Unable to load database driver.</h1>");
         var14.printStackTrace();
      } catch (SQLException var15) {
         out.print("<h1>Database error: " + var15.getMessage() + "</h1>");
         var15.printStackTrace();
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }
}
