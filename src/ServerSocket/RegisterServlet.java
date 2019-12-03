package ServerSocket;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegServ
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       //not yet adapted to towN oF SC . put the ${RegError} line in Register.jsp
	protected void service (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		String passwordconf = request.getParameter("pass2");
		String nxtjsp = "";
		HttpSession session = request.getSession();
		
		if(((!username.isEmpty()) && !password.isEmpty()) && passwordconf.equals(password)) { //move on to checking if user exists	
			Connection conn = null;
		    ResultSet rs = null;
		    PreparedStatement ps = null;
			
		    try {
		    	Class.forName("com.mysql.jdbc.Driver");
		        conn = DriverManager.getConnection(LoginServlet.CREDENTIALS);

		        ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
		        ps.setString(1, username);
		        rs = ps.executeQuery();
		        if(rs.next()) {//user exists -> error
		        	nxtjsp="Register.jsp";
		        	request.setAttribute("RegError", "This username already exists, please choose a different one.");
		        } else {//user doesn't exist and passwords matched, so register new user.
		        	nxtjsp="HomePage.jsp";
		        	PreparedStatement ps2 = conn.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		        	ps2.setString(1, username);
		        	ps2.setString(2, password);
		        	ps2.executeUpdate();
		        	
		        	int uid = 0;
		        	ResultSet rs2 = ps2.getGeneratedKeys();
		        	if(rs2.next()) {
		        		uid = rs2.getInt(1);
		        	}
		        	ps2.close();
		        	rs2.close();
		        	session.setAttribute("u", username);
		        	//https://stackoverflow.com/questions/17112852/get-the-new-record-primary-key-id-from-mysql-insert-query
		        	//https://stackoverflow.com/questions/4246646/mysql-java-get-id-of-the-last-inserted-value-jdbc
		        	session.setAttribute("uid", uid);
		        	request.setAttribute("HomeMessage", "Successfully registered new user! Thank you, "+username+"!");
		        }
		        
		    } catch (SQLException sqle) {
		    	sqle.printStackTrace();
		    } catch (ClassNotFoundException cnfe) {
		    	cnfe.printStackTrace();
		    } finally {
		    	try {
		    		if (rs != null) {
		    			rs.close();
		    		}
		    		if (ps != null) {
		    			ps.close();
		    		}
		    		if (conn != null) {
		    			conn.close();
		    		}
		    	} catch (SQLException sqle) {
		    		System.out.println(sqle.getMessage());
		    	}
		    }  
		    
		} else { //passwords don't match :(
			nxtjsp="Register.jsp";
			if(password.isEmpty() || username.isEmpty()) {
				request.setAttribute("RegError", "Please fill in all fields.");
			} else {
				request.setAttribute("RegError", "Passwords do not match, please try again.");
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nxtjsp);
		dispatcher.forward(request,response);
	}

}
