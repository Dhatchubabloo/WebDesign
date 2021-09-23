<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>customer Page</title>
        <style>
        body{
        background-color:#9FE2BF;
        }
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
    <h3>Customer Page</h3>
     <form action="register" method="post">
    <input type="hidden" name="link" value="Deletecustomers">
    <table class = "centre" cellspacing='10px'  border  = '1px'; cellpadding = '10px'>
     <thead>
                <tr>
                	 <th>Select<th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>City</th>
                   
                </tr>
            </thead>
            <tbody>
    <c:forEach items="${customerDetailsList}" var="customer">
        <tr>
       		 <td align="center">  
                <input type="checkbox" name="selectedcustomers"   
                    value= "${customer.customerId}">  
             </td>  
            <td><c:out value="${customer.customerId}" /></td>
            <td><c:out value="${customer.name}" /></td>
            <td><c:out value="${customer.city}"/></td>
             
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="row">
  <a href="register?link=Addcustomer">
   <input type="button" class="app-button"  value="Add"></input></a>
   <input type="submit" class="app-button"  value="Delete"></input></a>
 </div>
    </body>
</html>