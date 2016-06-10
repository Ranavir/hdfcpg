<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PAYMENT GATEWAY</title>
    
</head>
<%
	
%>
<body onLoad="document.payment.submit();">
	<h3>Please wait, redirecting to process payment..</h3>
	
	<form  name="payment" action="${sessionScope.ACTION_URL}" method="post">
	<%-- <?php
	foreach($POST_DATA as $key => $value) 
	{
	?>
	<input type="hidden" value="<?php echo $value;?>" name="<?php echo $key;?>"/>
	<?php
		}
	?> --%>
	<%
		HttpSession sess = request.getSession(false) ;
		TreeMap<String,String> tm_post_data = (TreeMap<String,String>)session.getAttribute("POST_DATA") ;
		Set<String> keySet = (Set<String>)tm_post_data.keySet();
		Iterator<String> itr = keySet.iterator() ;
		String key = "" ;
		while(itr.hasNext()){
			if(tm_post_data.containsKey(key = itr.next())){ 
		    //System.out.println(tm_post_data.get(key)+" "+key);
	%>
				<input type="hidden" value="<%=tm_post_data.get(key) %>" name="<%=key%>"/>
	<%
			}
		} 
	%>
		
	
	
		<input type="hidden" value="${sessionScope.secure_hash}" name="secure_hash"/>
	</form>
</body>
</html>