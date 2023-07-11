package com.BankApplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.BankApplication.helper.Helper;

@WebServlet("/loginvalidate")
public class CustomerLogin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	 {
		String accountNumber=req.getParameter("accountnumber");
	 	String pin=req.getParameter("pin");
		
		long accountnum=Long.parseLong(accountNumber);
		int pin1=Integer.parseInt(pin);
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=Helper.getConnect();
			PreparedStatement ps=con.prepareStatement("select * from account where accountnumber=? and pin=?");
			ps.setLong(1, accountnum);
			ps.setInt(2, pin1);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				PrintWriter pout=resp.getWriter();
				pout.println("<h1>Login Successfull</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("customeroptions.html");
				rd.include(req, resp);
			}
			else
			{
				PrintWriter pout=resp.getWriter();
				pout.println("<h1 align= 'center' style = 'color: red '>Invalid Credentials</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("Login.html");
				rd.include(req, resp);
			}
		} 
		catch (ClassNotFoundException e) 
		{
			
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
	 }
}


