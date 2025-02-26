// Source code is decompiled from a .class file using FernFlower decompiler.
package com.jobdetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet({"/JobDetailsServlet"})
public class JobDetailsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public JobDetailsServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         Statement st = con.createStatement();
         String query = "select * from jobs where deadline>=CURDATE()";
         ResultSet rs = st.executeQuery(query);
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head><title>Job Details</title></head>");
         out.println("<body>");
         out.println("<h2>Available Job Opportunities</h2>");
         out.println("<table border='1' style='border-collapse: collapse; width: 100%; border: 2px solid #4CAF50; background-color: #f9f9f9;'>");
         out.println("<tr style='background-color: #4CAF50; color: white;'>");
         out.println("<th>Job ID</th><th>Job Title</th><th>Company</th><th>Qualification</th><th>Location</th><th>Deadline</th>");
         out.println("</tr>");

         while(rs.next()) {
            int jobId = rs.getInt("id");
            String title = rs.getString("jobtitle");
            String company = rs.getString("companyname");
            String qualification = rs.getString("qualification");
            String location = rs.getString("location");
            String deadline = rs.getString("deadline");
            out.println("<tr style='text-align: center;'>");
            out.println("<td>" + jobId + "</td>");
            out.println("<td>" + title + "</td>");
            out.println("<td>" + company + "</td>");
            out.println("<td>" + qualification + "</td>");
            out.println("<td>" + location + "</td>");
            out.println("<td>" + deadline + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");
         out.println("</body></html>");
         rs.close();
         st.close();
         con.close();
      } catch (ClassNotFoundException var14) {
         var14.printStackTrace();
      } catch (SQLException var15) {
         var15.printStackTrace();
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }
}
