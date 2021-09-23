package com.zoho.registartionform.contoller;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.zoho.registartionform.models.TransactionInfo;

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
	TransactionInfo transactionInfo = new TransactionInfo();
    ArrayList customerList = new ArrayList();
    Helper logic = new Helper();
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logic.initialization();
		} catch (ExceptionHandling e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
		else if(type.equalsIgnoreCase("Addcustomer")) {
			 request.getRequestDispatcher("view/Customer.jsp").forward(request, response);
			
		}
		else if(type.equalsIgnoreCase("Addaccount")) {
			 request.getRequestDispatcher("view/Account.jsp").forward(request, response);
			
		}
		else if(type.equalsIgnoreCase("Withdraw")) {
			 request.getRequestDispatcher("view/Withdraw.jsp").forward(request, response);
		}
		else if(type.equalsIgnoreCase("Deposite")) {
			 request.getRequestDispatcher("view/Deposite.jsp").forward(request, response);
		}
		
		else if(type.equalsIgnoreCase("withdrawAmount")) {
			String value = "withdrawl";
			String condition = Helper.amountWithdrawl(getInfo(request), type);
			out.println(condition);
		}
		else if(type.equalsIgnoreCase("depositeAmount")) {
			String value = "deposite";
			String condition = Helper.amountDeposite(getInfo(request), type);
			out.println(condition);
		}
		
		else if(type.equalsIgnoreCase("Deleteaccounts")) {
			String value = "account";
			String [] array = request.getParameterValues("selectedAccounts");
			String status ="";
			for(String i:array) {
				String temp[] = i.split(",");
				int id = Integer.parseInt(temp[0]);
				int account_no = Integer.parseInt(temp[1]);
				System.out.println(id);
				System.out.println(account_no);
				accountInfo.setAccount_no(account_no);
				accountInfo.setCustomer_id(id);
				try {
					status = logic.particularAccountDeletion(accountInfo, value);
				} catch (ExceptionHandling e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			out.println(status);
		}
		else if(type.equalsIgnoreCase("Deletecustomers")) {
			String value = "customer";
			String [] array = request.getParameterValues("selectedcustomers");
			String result="";
			for(String i:array) {
				int id= Integer.parseInt(i);
				accountInfo.setCustomer_id(id);
				try {
					result = logic.entireDeletion(accountInfo, value);
				} catch (ExceptionHandling e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			out.println(result);
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
		else if(type.equalsIgnoreCase("submitaccount")) {
			int id = Integer.parseInt(request.getParameter("Id"));
			double value  = Double.parseDouble(request.getParameter("Amount"));
			BigDecimal num = BigDecimal.valueOf(value);
			String branch = (String) request.getParameter("Branch");
			accountInfo.setCustomer_id(id);
			accountInfo.setBalance(num);
			accountInfo.setBranch(branch);
			String result="";
			try {
				result = logic.caseExistingUser(accountInfo);
			} catch (ExceptionHandling e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(result);
			
		}
		
	}
	TransactionInfo getInfo(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("Id"));
		int account_no = Integer.parseInt(request.getParameter("Account_no"));
		double amount  = Double.parseDouble(request.getParameter("Amount"));
		BigDecimal num = BigDecimal.valueOf(amount);
		transactionInfo.setCustomer_id(id);
		transactionInfo.setAccount_no(account_no);
		transactionInfo.setAmount(num);
		return transactionInfo;
		
	}

}
