package com.stl.pg;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stl.dao.DBUtils;
import com.stl.dao.HashingAlgorithm;

/**
 * Servlet implementation class PaymentResponse
 */
@WebServlet("/paymentResponse")
public class PaymentResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String methodname = "" ;
	private String user = "Ranvir" ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentResponse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.methodname="doPost";
		System.out.println("Entry--->"+this.getClass()+" method :  "+methodname);
		/*
		 * Declare the required variables
		 */
		String ResponseCode = "" ;//Will catch ResponseCode from PG
		String PaymentID = "" ;//Will catch paymentId from PG
		String Amount = "" ;//Will catch amount from PG
		String MerchantRefNo = "" ;//Will catch MerchantRefNo  from PG must be same as orderId
		String retrievedSecureHash = "" ;//Will catch the SecureHash Posted Parameter Value from PaymentGateway
		
		StringBuffer sb = null ;//Used for preparing the url for response
		String urlEncoded = "" ;
		
		String paymentStatus = "" ;
		String strApplicationNumber = "" ;//Will get the value after successful transaction from database
		RequestDispatcher rd = null ;
		final String HASHING_METHOD = "sha512"; // sha1,sha512,md5
		final String SECRET_KEY = "472ae234a85627d3e921301910330e51" ;
		String hashData = "" ;//Generate the hashData value for generating secureHash Value for comparison
		String secureHash = "" ;//Generate secureHash Value for comparison
		
		HashMap<String,String> hm_post_data = new HashMap<String, String>() ;//Used for storing posted data to this page
		TreeMap<String,String> tm_post_data = null ;//Used for sorting of the HashMap data in a TreeMap
		HashMap<String, Object> hmRequest = new HashMap<String, Object>() ;
		
		/*
		 * Get the Post data to here in HashMap
		 */
		Enumeration<String> paramNames = request.getParameterNames() ;
		String paramName = "" ;
		String paramValue = "" ;
		while(paramNames.hasMoreElements()){
			paramName = paramNames.nextElement() ;
			paramValue = request.getParameter(paramName) ;
			System.out.println(paramName +" : "+ paramValue);
			if(paramName.equalsIgnoreCase("ResponseCode")){//Store the ResponseCode
				ResponseCode = paramValue ;
				System.out.println("ResponseCode==="+ResponseCode);
			}
			if(paramName.equalsIgnoreCase("Amount")){//Store the Amount
				Amount = paramValue ;
				System.out.println("Amount==="+Amount);
			}
			if(paramName.equalsIgnoreCase("MerchantRefNo")){//Store the MerchantRefNo = orderId
				MerchantRefNo = paramValue ;
				System.out.println("MerchantRefNo==="+MerchantRefNo);
			}
			if(paramName.equalsIgnoreCase("SecureHash")){//Avoid the SecureHash <Key,Value> pair
				retrievedSecureHash = paramValue ;//Initialize the value
				System.out.println("retrievedSecureHash==="+retrievedSecureHash);
			}else{
				hm_post_data.put(paramName,paramValue) ;
			}
			
		}
		
		//Sorting of data in TreeMap
		tm_post_data = new TreeMap<String, String>(hm_post_data) ;
		
		//Initialize hashData with SECRET_KEY
		hashData = SECRET_KEY ;
		
		//Get the concatenated hashData from the TreeMap
		Collection<String> tmValues = (Collection<String>) tm_post_data.values() ;
		for (String value : tmValues) {
			if(value.length() > 0 ){
				hashData += '|' + value ;
			}
		}
		System.out.println("hashData==="+hashData);
		//Get the hashing value by encrypting and making it to upperCase
		/*if (strlen($hashData) > 0) {
			$secureHash = strtoupper(hash($HASHING_METHOD, $hashData));
		}*/
		if( hashData.length() > 0 ){
			secureHash = HashingAlgorithm.hash(hashData,HASHING_METHOD).toUpperCase() ;
		}
		System.out.println("secureHash==="+secureHash);
		/*
		 * Check the validity of the hash value and populate the required data
		 */
		try {
			if(secureHash.equalsIgnoreCase(retrievedSecureHash)){
				/*
				 * Check the ResponseCode
				 */
				if(ResponseCode.equals("0")){
					paymentStatus = "SUCCESS" ;
					//Update database record deposit_status for order_no as MerchantRefNo and amount = Amount
					hmRequest.put("payment_status",paymentStatus ) ;
					hmRequest.put("order_no",MerchantRefNo ) ;
					hmRequest.put("amount",Amount) ;
					hmRequest.put("payment_id",hm_post_data.get("PaymentID")!=null?hm_post_data.get("PaymentID"):"") ;
						DBUtils.savePaymentResponse(hmRequest) ;
						strApplicationNumber = DBUtils.getAppliationNumber(hmRequest) ;
						
					/*
					 * Prepare the url
					 */
						sb = new StringBuffer("");
						sb.append(request.getContextPath()+"/PostPayment.jsp");
						sb.append("?application_no=" + strApplicationNumber);
						sb.append("&order_no=" + MerchantRefNo);
						sb.append("&amount=" + Amount);
						sb.append("&PaymentID=" + PaymentID);
						sb.append("&paymentStatus=" + paymentStatus);
						String url = sb.toString();
						  
						urlEncoded = response.encodeRedirectURL(url) ;
					/*request.setAttribute("application_no",strApplicationNumber);
					request.setAttribute("order_no",MerchantRefNo);
					request.setAttribute("amount",Amount);
					request.setAttribute("PaymentID",hm_post_data.get("PaymentID"));
					request.setAttribute("paymentStatus",paymentStatus);*/
				}
				else{
					paymentStatus = "FAILED" ;
					hmRequest.put("payment_status",paymentStatus ) ;
					hmRequest.put("order_no",MerchantRefNo ) ;
					hmRequest.put("amount",Amount) ;
					hmRequest.put("payment_id",hm_post_data.get("PaymentID")!=null?hm_post_data.get("PaymentID"):"") ;
					DBUtils.savePaymentResponse(hmRequest) ;
					
					sb = new StringBuffer("");
					sb.append(request.getContextPath()+"/payment.jsp");
					sb.append("?message=" + "Payment Transaction Failed");
					String url = sb.toString();
					urlEncoded = response.encodeRedirectURL(url) ;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(urlEncoded);//send redirect is used for tracking the url in android webview
		/*if(paymentStatus.equals("SUCCESS")){
			//rd = getServletContext().getRequestDispatcher("/PostPayment.jsp") ;
			response.sendRedirect(urlEncoded);//send redirect is used for tracking the url in android webview
		}else{
			//response.sendRedirect(request.getContextPath()+"/payment.jsp?message=Payment Transaction Failed");
			rd = getServletContext().getRequestDispatcher("/payment.jsp") ;
			request.setAttribute("message", "Payment Transaction Failed!!! ");
		}*/
		//rd.forward(request,response);
		System.out.println("Exit-->"+this.getClass()+" method : "+methodname);
	}

}
