<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>account Page</title>
        <style>
        table{
			border-collapse :collapse;
			        
        }
       
        body{
        	background-color:#9FE2BF;
       }
       
        .centre{
        margin-left:auto;
        margin-right:auto;
        }
        h3{
        text-align:center;
        }
           .app-button{
    background-color: #CD5C5C;
    width: 100px;
    margin:30 20px;
    display:inline-block;
    line-height: 40px;
}
.row{
  text-align:center;
  margin-left:-20px;
  marin-right:-20px;
}
        </style>
    </head>
    <body>
    <h3> Account Page</h3>
     <form action="register" method="post">
    <input type="hidden" name="link" value="Deleteaccounts">
    <table class = "centre" cellspacing='10px'  border  = '1px'; cellpadding = '10px'>
     <thead>
                <tr>
                	<th>select</th>
                    <th>Id</th>
                    <th>Account No</th>
                    <th>Balance</th>
                    <th>Branch</th>
                </tr>
            </thead>
            <tbody>
    <c:forEach items="${accountList}" var="account">
        <tr>
        	 <td align="center">  
                <input type="checkbox" name="selectedAccounts"   
                    value= "${account.customer_id},${account.account_no}">  
             </td>  
            <td><c:out value="${account.customer_id}" /></td>
            <td><c:out value="${account.account_no}" /></td>
            <td><c:out value="${account.balance}"/></td>
            <td><c:out value="${account.branch}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

  <div class="row">
  <a href="register?link=Addaccount">
   <input type="button" class="app-button"  value="Add"></input></a>
   <input type="submit" class="app-button"  value="Delete"></input>
 </div>
 </form>
    </body>
</html>
