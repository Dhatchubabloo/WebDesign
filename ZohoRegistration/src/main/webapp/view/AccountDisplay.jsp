<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>account Page</title>
        <style>
        table{
			border-collapse :collapse;
			        
        }
        .centre{
        margin-left:auto;
        margin-right:auto;
        }
        h3{
        text-align:center;
        }
        </style>
    </head>
    <body>
    <h3> Account Page</h3>
    <table class = "centre" cellspacing='10px'  border  = '1px'; cellpadding = '10px'>
     <thead>
                <tr>
                    <th>Id</th>
                    <th>Account No</th>
                    <th>Balance</th>
                    <th>Branch</th>
                </tr>
            </thead>
            <tbody>
    <c:forEach items="${accountList}" var="account">
        <tr>
            <td><c:out value="${account.customer_id}" /></td>
            <td><c:out value="${account.account_no}" /></td>
            <td><c:out value="${account.balance}"/></td>
            <td><c:out value="${account.branch}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

        <button><a href='http://localhost:8080/ZohoRegistration/view/Account.jsp' alt='Broken Link'>Add</a></button>
        <br>
        <button>Delete</button>
        
    </body>
</html>
