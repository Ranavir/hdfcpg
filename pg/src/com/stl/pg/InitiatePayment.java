package com.stl.pg;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
 * Servlet implementation class InitiatePayment
 */
@WebServlet("/initiatePayment")
public class InitiatePayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String methodname = "" ;
	private String user = "Ranvir" ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitiatePayment() {
        super();
        // TODO Auto-generated constructor stub
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
		RequestDispatcher rd = null ;
		/*
		 * Create the session for payment
		 */
		HttpSession sess = request.getSession(true) ;
		/*
		 * Extra data declared
		 */
		final String HASHING_METHOD = "sha512"; // sha1,sha512,md5
		final String ACTION_URL = "https://secure.ebs.in/pg/ma/payment/request/";
		//final String ACTION_URL = "./TestServlet";
		/*
		 * Get values from requesting page
		 */
		String amount = request.getParameter("amount") ;// Amount validation should be in previous page
		Double amount_to_pay = (null != amount) ? Double.parseDouble(amount) : 0.0 ;
		System.out.println("amount_to_pay---->"+amount_to_pay);
		/*
		 * Get the reference_no for which payment to be made same as application_no
		 */
		String strappno = request.getParameter("application_no") ;
		String application_no =  (null != strappno) ? strappno.trim() : "" ;
		System.out.println("application_no---->"+application_no);
		//Check for application_no in database with success payment status if not present then do it
		/*boolean boolPaymentFlag = false ;
		try {
			boolPaymentFlag = DBUtils.checkPayment(application_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		if( application_no != "" && amount_to_pay != 0 ){
			/*
			 * Declare required variables
			 */
			HashMap<String,Object> hmRequest = new HashMap<String, Object>() ;//Used for database operation request object
			int iorderNo = 0 ;
			String hashData = "" ;
			String secureHash = "" ;
			TreeMap<String,String> tm_post_data = null ;
			HashMap<String,String> hm_post_data = new HashMap<String, String>() ;
			hm_post_data.put("channel", "10");
			hm_post_data.put("account_id", "16931");//$account_id
			hm_post_data.put("secretkey", "472ae234a85627d3e921301910330e51");//$secret_key,
			
			hm_post_data.put("reference_no", "");//$order_id
			hm_post_data.put("amount", "");
			
			hm_post_data.put("description", "TEST PAYMENT");//$delivery_cust_notes
			
			hm_post_data.put("return_url", "http://182.156.93.61:8080/pg/paymentResponse");//http://silicontechlab.com/esadmission/payonline/response.php
			
			hm_post_data.put("mode", "LIVE");
			hm_post_data.put("name", "Ranavir Dash");//$billing_cust_name
			hm_post_data.put("address", "Pippala");//$billing_cust_address
			hm_post_data.put("city", "Kamakhya Nagar");//$billing_city
			hm_post_data.put("state", "Odisha");//$billing_cust_state
			hm_post_data.put("postal_code", "759018");//$billing_zip
			hm_post_data.put("country", "IN");//$billing_cust_country
			hm_post_data.put("email", "ranavir.dash@stlindia.com");//$billing_cust_email
			hm_post_data.put("phone", "9658602048");//$billing_cust_tel
			hm_post_data.put("ship_name", "Ranavir Dash");//$delivery_cust_name
			hm_post_data.put("ship_address", "Pippala");//$delivery_cust_address
			hm_post_data.put("ship_city", "Kamakhya Nagar");//$delivery_city
			hm_post_data.put("ship_state", "Odisha");//$delivery_cust_state
			hm_post_data.put("ship_postal_code", "759018");//$delivery_zip
			hm_post_data.put("ship_country", "IN");//$delivery_cust_country
			hm_post_data.put("ship_phone", "9658602048");//$delivery_cust_tel
			
			
			
			
			try{
				iorderNo = DBUtils.getOrderNo() ;
				System.out.println("iorderNo==="+iorderNo);
			}catch(SQLException se){
				se.printStackTrace(); 
			}
			/*
			 * Update the hash map values
			 */
			hm_post_data.put("reference_no", iorderNo+"");//$order_id
			hm_post_data.put("amount", amount_to_pay+"");
			
			/*
			 * Generate SecureHash value by initializing with secret key
			 */
			hashData = hm_post_data.get("secretkey") ;
			
			//sort the map values as ksort in php using TreeMap with String key in Java
			//ksort($POST_DATA);
			tm_post_data = new TreeMap<String, String>(hm_post_data) ;
			
			//Get the concatenated hashData from the map
			
			Collection<String> tmValues = (Collection<String>) tm_post_data.values() ;
			for (String value : tmValues) {
				if(value.length() > 0){
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
			 * Set values in session
			 */
			sess.setAttribute("amount_to_pay", amount_to_pay);
			sess.setAttribute("POST_DATA", tm_post_data);
			sess.setAttribute("ACTION_URL", ACTION_URL);
			sess.setAttribute("secure_hash", secureHash);
			//request.setAttribute("keySet",tm_post_data.keySet());
			/*
			 * Insert initiated status to database for the application_no
			 */
			hmRequest.put("application_no",application_no);
			hmRequest.put("amount",amount_to_pay);
			hmRequest.put("userId","STL");
			hmRequest.put("order_no",iorderNo);
			try{
				DBUtils.savePaymentInit(hmRequest);
			}catch(SQLException se){
				se.printStackTrace(); 
			}
			/*
			 * Make the original payment request
			 */
			rd = getServletContext().getRequestDispatcher("/post.jsp") ;
			rd.forward(request,response);
		}/*else if(boolPaymentFlag == true && application_no != ""){
			//rd = getServletContext().getRequestDispatcher("/payment.jsp") ;
			//request.setAttribute("message", "Payment Already done for application no: "+application_no);
			response.sendRedirect(request.getContextPath()+"/payment.jsp?message=" + "Payment Already done");
		}*/
		else{
			//rd = getServletContext().getRequestDispatcher("/payment.jsp") ;
			//request.setAttribute("message", "Invalid application no: "+application_no);
			System.out.println(getServletContext().getContextPath()+"/payment.jsp?message=" + "Invalid application no");
			
			StringBuffer sb = new StringBuffer("");
			
			  
			sb.append(request.getContextPath()+"/payment.jsp");
			sb.append("?message=InvalidRequest");
			
			  
			  
			String url = sb.toString();
			  
			String urlEncoded = response.encodeRedirectURL(url) ;
			System.out.println(urlEncoded);
			response.sendRedirect(urlEncoded);
			
			
		}
		
		
		System.out.println("Exit-->"+this.getClass()+" method : "+methodname);
	}

	

}
