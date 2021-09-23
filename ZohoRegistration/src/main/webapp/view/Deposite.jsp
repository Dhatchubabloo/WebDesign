<html>
    <head>
        <title>
            Deposite
        </title>
          <style>
        body{
        background-color:#9FE2BF;
        text-align:center
        }
        .align{
        	  margin-left:auto;
        margin-right:auto;
        }
        </style>
    </head>
    <body>
    <h3>Deposite Page</h3>
    <form action="register" method="post">
     <input type="hidden" name="link" value="depositeAmount">
        <table class = "align" cellspacing='20px'>
        <tr>
        	<td>
     <label for="">Customer Id</label></td>
        <td><input type="text" name="Id" placeholder="Enter customerId" required></td>
        </tr>
    <tr>
       
        <td><label for="">AccountNumber</label></td>
        <td><input type="text" name="Account_no" placeholder="Enter Account No" required></td>

    </tr>
    <tr><td>
        <label for="">Amount</label></td>
        <td><input type="text" name="Amount" placeholder="Enter Amount" required></td>

    </tr>
    </table>
   <input type="submit" value="submit">
    </form>
    </body>
</html>