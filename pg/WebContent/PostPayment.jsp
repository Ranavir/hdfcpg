<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PAYMENT GATEWAY</title>
    <link href="./css/styles.css" rel="stylesheet" type="text/css"/>
    
   <script src="./js/jQuery-2.1.4.min.js"></script>
</head>
<body>
	<div class='payment_heading'>
		<label>Payment Gateway Web Application</label>
	</div>
	<div class='post_payment_div'>
		<u><label>Payment Details</label></u><br><br>
		<p style="color: blue;font-size: 15px;">Application Number: &nbsp;${requestScope.application_no}${param.application_no}<br>
		<p style="color: blue;font-size: 15px;">Order Number: &nbsp;${requestScope.order_no}${param.order_no}<br>
		<p style="color: blue;font-size: 15px;">Amount: &nbsp;${requestScope.amount}${param.amount}<br>
		<p style="color: blue;font-size: 15px;">PaymentID: &nbsp;${requestScope.paymentId}${param.paymentId}<br>
		<p style="color: blue;font-size: 15px;">PaymentStatus: &nbsp;${requestScope.paymentStatus}${param.paymentStatus}<br>
		
	</div>
</body>
</html>