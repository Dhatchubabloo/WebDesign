<html>
  <head>
    <title>Zoho Registaration Form</title>
    <style>
      *{
    padding: 0;
    margin: 0; 
    box-sizing: border-box;  
  }
body{
    background-image: url(https://mir-s3-cdn-cf.behance.net/project_modules/disp/7f1b1647755313.5608166ac6915.jpg);
    height: 100%;
    background-position:center;
    background-repeat: no-repeat;
    background-size: cover;
}
.menu{
    background: cadetblue;
    text-align: center;
    height: 1050px;
    width: 200px;
}.menu ul{
    display: inline;
    list-style: none;
    color: white;
}
.menu ul li{
    width: 120px;
    margin: 15px;
    padding: 15px;
}
.menu ul li a{
    text-decoration: none;
    color: white;
}
    </style>
  </head>
  <body><form action="register" method="post"><div class="menu">
    <font size='5px'>
    <ul><li>
      <a href="register?link=customer">Customer</a>
    </li>
    <li>
      <a href="register?link=account">Acconut</a>
    </li>
    <li>
      <a href="register?link=transaction">Transaction</a>
    </li></ul>
  </font>
  </div></form>
  </body>
</html>