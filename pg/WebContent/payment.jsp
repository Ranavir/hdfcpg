<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PAYMENT GATEWAY</title>
    <link href="./css/styles.css" rel="stylesheet" type="text/css"/>
    
   <script src="./js/jQuery-2.1.4.min.js"></script>
   <script src="./js/payment.js"></script>
</head>
<body>
	<div class='payment_heading'>
		<label>Payment Gateway Web Application</label>
	</div>
	<form  name="PaymentDetailsForm" action="./initiatePayment" method="post">
		<div class='payment_div'>
			<p style="color: blue;font-size: 20px;">Application Number: &nbsp;<input type="text" name="application_no" size="10"><br><br>
			<p style="color: blue;font-size: 20px;">Enter Amount to Pay: &nbsp;<input type="text" name="amount" size="5"><br><br>
			<button class='payment' style="background-color: burlywood;" >Pay Now</button>
			<p style="color: blue;font-size: 20px;">${param.message}</p>
		</div>
	</form>
</body>
</html>