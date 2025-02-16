// Source code is decompiled from a .class file using FernFlower decompiler.
package com.addjob;

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

@WebServlet({"/AddJobServlet"})
public class AddJobServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public AddJobServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String jobtitle = request.getParameter("jobtitle");
      String companyname = request.getParameter("companyname");
      String qualification = request.getParameter("qualification");
      String location = request.getParameter("location");
      String deadline = request.getParameter("deadline");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         String sql = "INSERT INTO jobs (jobtitle, companyname,qualification, location, deadline) VALUES (?, ?, ?, ?,?)";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setString(1, jobtitle);
         ps.setString(2, companyname);
         ps.setString(3, qualification);
         ps.setString(4, location);
         ps.setString(5, deadline);
         int rowsc = ps.executeUpdate();
         if (rowsc > 0) {
            response.sendRedirect("addsuccess.html");
         } else {
            out.print("<h1>Registration failed. Please try again.</h1>");
         }

         ps.close();
         con.close();
      } catch (ClassNotFoundException var13) {
         out.print("<h1>Server error. Unable to load database driver.</h1>");
         var13.printStackTrace();
      } catch (SQLException var14) {
         out.print("<h1>Database error: " + var14.getMessage() + "</h1>");
         var14.printStackTrace();
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }
}
