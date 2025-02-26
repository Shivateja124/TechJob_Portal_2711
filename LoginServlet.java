package com.login;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username= request.getParameter("username");
		String password =request.getParameter("password");
		//jdbc connection 
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root","root");
			Statement st=con.createStatement();
			String query=" select * from users where username= '"+username+"' and password ='"+password+"' "; 
			ResultSet rs=st.executeQuery(query);
			if(rs.next()) {
				 response.sendRedirect("home.html");
			}else {
				out.print("<h1>"+username+":Invalid details</h1><br>");
				out.print("<h1>"+username+":Login Falied</h1><br>");
				
			}
			rs.close();
			st.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			out.print("<h1>"+username+":Login Falied because of server exception</h1><br>");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("<h1>"+username+":Login Falied because of server exception</h1><br>");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
