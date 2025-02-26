package com.rlogin;

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

@WebServlet({"/RecruiterLoginServlet"})
public class RecruiterLoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RecruiterLoginServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "root");
         Statement st = con.createStatement();
         String query = " select * from rusers where username= '" + username + "' and password ='" + password + "' ";
         ResultSet rs = st.executeQuery(query);
         if (rs.next()) {
            response.sendRedirect("recruiter-home.html");
         } else {
            out.print("<h1>" + username + ":Invalid details</h1><br>");
            out.print("<h1>" + username + ":Login Falied</h1><br>");
         }

         rs.close();
         st.close();
         con.close();
      } catch (ClassNotFoundException var10) {
         out.print("<h1>" + username + ":Login Falied because of server exception</h1><br>");
         var10.printStackTrace();
      } catch (SQLException var11) {
         out.print("<h1>" + username + ":Login Falied because of server exception</h1><br>");
         var11.printStackTrace();
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }
}
