<html>
    <head>
        <title>
            Account Registration
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
    <h3> Account Creation</h3>
    <form action="register" method="post">
     <input type="hidden" name="link" value="submitaccount">
        <table class = "align"cellspacing='20px'>
        <tr>
        	<td>
     <label for="">Customer Id</label></td>
        <td><input type="text" name="Id" placeholder="Enter customerId" required></td>
        </tr>
    <tr>
       
        <td><label for="">Deposite Amount</label></td>
        <td><input type="text" name="Amount" placeholder="Enter Amonut" required></td>

    </tr>
    <tr><td>
        <label for="">Branch</label></td>
        <td><input type="text" name="Branch" placeholder="Enter Branch" required></td>

    </tr>
    </table>
   <input type="submit" value="submit">
    </form>
    </body>
</html>