package ServerSocket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//CHANGE TO CORRECT CREDENTIALS
	static final String CREDENTIALS = "jdbc:mysql://google/Data?cloudSqlInstance=csci201-lab7-255417:us-central1:townofsc&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=salem&password=salem";
	
	protected void service (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		HttpSession session = request.getSession();
		
		Connection conn = null;
	    ResultSet rs = null;
	    PreparedStatement ps = null;
	    String nxtjsp = "";
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(CREDENTIALS);

	        ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	        if(rs.next()) {//user exists
	        	if(password.matches(rs.getString("password"))) { //username AND password are correct
		    		session.setAttribute("u", username); //save for later
		    		session.setAttribute("uid", rs.getInt("userID"));
		    		
		    		request.setAttribute("LoginError", "CORRECTLY LOGGED IN"); //get rid of this line!!!!!!
		    		nxtjsp="Login.jsp";//CHANGE THIS TO REDIRECRT TO LOBBY.JSP!!!!!!!!!!
	        	} else {
	        		//user exists, incorrect password
	        		nxtjsp="Login.jsp";
	        		request.setAttribute("LoginError", "Incorrect password!");
	        	}
	        } else {
	        	//doesn't exist
	        	nxtjsp="Login.jsp";
	        	request.setAttribute("LoginError", "User doesn't exist, please register!");
	        }
	        
	    } catch (SQLException sqle) {
	    	sqle.printStackTrace();
	    } catch (ClassNotFoundException cnfe) {
	    	cnfe.printStackTrace();
	    } finally {
	    	try {
	    		if (rs != null) {rs.close();}
	    		if (ps != null) {ps.close();}
	    		if (conn != null) {conn.close();}
	    	} catch (SQLException sqle) {System.out.println(sqle.getMessage());}
	    }  
		RequestDispatcher dispatcher = request.getRequestDispatcher(nxtjsp);
		dispatcher.forward(request,response);
	}
	
}
