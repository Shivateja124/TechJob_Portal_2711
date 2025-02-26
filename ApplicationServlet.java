// Source code is decompiled from a .class file using FernFlower decompiler.
package com.apply;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet({"/ApplicationServlet"})
@MultipartConfig(
   fileSizeThreshold = 1048576,
   maxFileSize = 52428800L,
   maxRequestSize = 52428800L
)
public class ApplicationServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public ApplicationServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String companyname = request.getParameter("companyname");
      String jobtitle = request.getParameter("jobtitle");
      String name = request.getParameter("name");
      String contact = request.getParameter("contact");
      Part resume = request.getPart("resume");
      InputStream resumeInputStream = resume.getInputStream();

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         String sql = "insert into applied (companyname, jobtitle, name, contact, resume) VALUES (?, ?, ?, ?, ?)";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setString(1, companyname);
         ps.setString(2, jobtitle);
         ps.setString(3, name);
         ps.setString(4, contact);
         ps.setBlob(5, resumeInputStream);
         int rowsc = ps.executeUpdate();
         if (rowsc > 0) {
            response.sendRedirect("appliedsuccess.html");
         } else {
            out.print("<h1>Application submission failed. Please try again.</h1>");
         }

         ps.close();
         con.close();
      } catch (ClassNotFoundException var14) {
         out.print("<h1>Server error: Unable to load database driver.</h1>");
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
