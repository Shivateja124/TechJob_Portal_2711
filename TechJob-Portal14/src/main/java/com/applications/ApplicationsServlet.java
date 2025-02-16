// Source code is decompiled from a .class file using FernFlower decompiler.
package com.applications;

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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/ApplicationsServlet"})
public class ApplicationsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public ApplicationsServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String companyname = request.getParameter("companyname");
      String jobtitle = request.getParameter("jobtitle");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         String query = "select * from applied WHERE jobtitle = ? and companyname = ?";
         PreparedStatement ps = con.prepareStatement(query);
         ps.setString(1, jobtitle);
         ps.setString(2, companyname);
         ResultSet rs = ps.executeQuery();
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head><title>Job Details</title></head>");
         out.println("<body>");
         out.println("<h2>Available Job Opportunities</h2>");
         out.println("<table border='1' style='border-collapse: collapse; width: 100%; border: 2px solid #4CAF50; background-color: #f9f9f9;'>");
         out.println("<tr style='background-color: #4CAF50; color: white;'>");
         out.println("<th>SNo</th><th>Companay_Name</th><th>Job_Name</th><th>Name</th><th>Contact</th><th>Resume</th>");
         out.println("</tr>");

         while(rs.next()) {
            int sno = rs.getInt("Sno");
            String company = rs.getString("companyname");
            String jobname = rs.getString("jobtitle");
            String name = rs.getString("name");
            String contact = rs.getString("contact");
            out.println("<tr style='text-align: center;'>");
            out.println("<td>" + sno + "</td>");
            out.println("<td>" + company + "</td>");
            out.println("<td>" + jobname + "</td>");
            out.println("<td>" + name + "</td>");
            out.println("<td>" + contact + "</td>");
            out.println("<td>Uploaded Resume</td>");
            out.println("</tr>");
         }

         out.println("</table>");
         out.println("</body></html>");
         rs.close();
         ps.close();
         con.close();
      } catch (ClassNotFoundException var15) {
         var15.printStackTrace();
      } catch (SQLException var16) {
         var16.printStackTrace();
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }
}
