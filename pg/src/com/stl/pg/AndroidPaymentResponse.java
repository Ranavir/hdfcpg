package com.stl.pg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.stl.dao.DBUtils;
import com.stl.dto.PaymentDetailsDto;

/**
 * Servlet implementation class AndroidPaymentResponse
 */
@WebServlet("/androidPaymentResponse")
public class AndroidPaymentResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String methodname = "" ;
	private String user = "Ranvir" ;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidPaymentResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.methodname="doPost";
		System.out.println("Entry--->"+this.getClass()+" method :  "+methodname);
		
		PrintWriter out = response.getWriter();
		/*
		 * This will store the  payment details from database
		 */
		PaymentDetailsDto paymentDto = null ;
		/*
		 * This will store the android response JSON Values
		 */
		JSONObject play = null ;
		
		/*
		 * Get the application_no and amount from mobile request
		 */
		String application_no = request.getParameter("application_no");
		String  strApplicationNumber =  (null != application_no) ? application_no.trim() : "" ;
		String amount = request.getParameter("amount") ;// Amount validation should be in previous page
		Double amount_paid = (amount != null)? Double.parseDouble(amount) : 0.0 ;
		
		if( application_no != "" && amount_paid != 0 ){//Valid Request
			/*
			 * Get the payment details from database
			 */
			paymentDto = getPaymentDetails(strApplicationNumber,amount_paid);
		}
		/*
		 * Prepare response
		 */
		try {
			if( paymentDto != null ){
				/*
				 * THIS object stores the final response value to mobile
				 */
				play = new JSONObject();
				play.put("status", "SUCCESS");
				play.put("application_no",paymentDto.getStrApplicationNumber());
				play.put("order_no",paymentDto.getStrOrderNumber());
				play.put("amount",paymentDto.getStrAmount());
				play.put("payment_id",paymentDto.getStrPaymentId());
				play.put("payment_status",paymentDto.getStrPaymentStatus());
				
				out.print(play);
			}//end of If block
			else{
				out.print("FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("FAILED");
		} 
		
		System.out.println("Exit-->"+this.getClass()+" method : "+methodname);
	}

	private PaymentDetailsDto getPaymentDetails(String strApplicationNumber,
			Double amount_paid) {
		this.methodname="getPaymentDetails";
		System.out.println("Entry--->"+this.getClass()+" method :  "+methodname);
		
		PaymentDetailsDto paymentDto = null ;
		try {
			paymentDto = DBUtils.getPaymentDetails(strApplicationNumber,amount_paid) ;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Exit-->"+this.getClass()+" method : "+methodname);
		return paymentDto;
	}

}
