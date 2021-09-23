
<html>
    <head>
        <title>
            customer Registration
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
    <h3>Customer Creation</h3>
    <form action="register" method="post">
    <input type="hidden" name="link" value="submitcustomer">
        <table class ="align"cellspacing='20px'>
            
    <tr>
        <td><label>Name</label></td>
        <td><input type="text" name="Name" placeholder="Enter Name" required></td>    
    </tr>
	<tr>
        <td><label>City</label></td>
        <td><input type="text" name="City" placeholder="Enter city" required></td>
    </tr>
    <tr>
        <td><label for="">Balance</label></td>
        <td><input type="text" name="Balance"  required></td>
    </tr>
    <tr>
        <td><label for="">Branch</label></td>
        <td><input type="text" name="Branch" required></td>
    </tr>
	</table>
    <input type="submit" value="submit">
    </form>
    </body>
</html>