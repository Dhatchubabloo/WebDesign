package com.zoho.registartionform.contoller;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import com.zoho.registartionform.models.AccountInfo;
import com.zoho.registartionform.models.CustomerInfo;
import com.zoho.registartionform.models.ExceptionHandling;
import com.zoho.registartionform.models.Helper;
import com.zoho.registartionform.models.Persistance;
import com.zoho.registartionform.models.QueryHandler;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Persistance db = new QueryHandler();
    
//    public RegistrationServlet() {
//    	try {
//            FileReader reader = new FileReader("dbConnector.properties");
//            Properties props = new Properties();
//            props.load(reader);
//            String path = (String) props.get("dbName");
//            db = (Persistance) Class.forName(path).newInstance();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
	CustomerInfo customerInfo = new CustomerInfo();
	AccountInfo accountInfo = new AccountInfo();
    ArrayList customerList = new ArrayList();
    Helper logic = new Helper();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String type = request.getParameter("link");
		//String value = request.getParameter("value");
		//System.out.println(value);
		if(type.equalsIgnoreCase("customer")) {
			 ArrayList<CustomerInfo>  customerDetailsList= db.wholeCustomer_details();
			request.setAttribute("customerDetailsList", customerDetailsList);
			 request.getRequestDispatcher("view/CustomerDisplay.jsp").forward(request, response);
		}
		else if(type.equalsIgnoreCase("account")) {
				ArrayList<AccountInfo> accountList = db.wholeAccount();
				System.out.println(accountList);
				request.setAttribute("accountList",accountList);
			request.getRequestDispatcher("view/AccountDisplay.jsp").forward(request, response);
		}
		else if(type.equalsIgnoreCase("Transaction")) {
			 RequestDispatcher dispatcher= request.getRequestDispatcher("view/Transaction.jsp");
			 dispatcher.forward(request, response);
		}
		else if(type.equalsIgnoreCase("Add")) {
			 request.getRequestDispatcher("view/Customer.jsp").forward(request, response);
			
		}
		
		else if(type.equalsIgnoreCase("submitcustomer")) {
			String name = (String) request.getParameter("Name");
			String city = (String)request.getParameter("City");
			double value  = Double.parseDouble(request.getParameter("Balance"));
			BigDecimal num = BigDecimal.valueOf(value);
			String branch = (String) request.getParameter("Branch");
			customerInfo.setName(name);
			customerInfo.setCity(city);
			System.out.print(customerInfo);
			customerList.add(customerInfo);			
			accountInfo.setBalance(num);
			accountInfo.setBranch(branch);
			System.out.print(accountInfo);
			customerList.add(accountInfo);
			try {
				logic.caseNewUser(customerList, 1);
			} catch (ExceptionHandling e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println("Data Inserted Successfully");
		}		
	}

}
