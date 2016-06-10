package com.stl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.stl.dto.PaymentDetailsDto;

public class DBUtils {
	/**
	 * This method returns the next value of the order_seq as order no
	 * @param void
	 * @author Ranvir
	 */
	public static int getOrderNo() throws SQLException{
		String methodname = "getOrderNo" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		int iorderNo = 0 ;
		Connection con = null;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement("select nextval('order_seq')");

				//pstmt.setString(1, loyalityId);
				
				rs = ps.executeQuery();
				if(rs.next()){
		        	iorderNo = rs.getInt(1);
		        }
			}
			rs.close();
			ps.close();
			con.close(); 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
	return iorderNo ;
	}//end of getOrderNo
	/**
	 * This method checks if payment already done for the application_no or not
	 * @param HashMap
	 * @author Ranvir
	 */
	public static boolean checkPayment(String appno) throws SQLException{
		String methodname = "checkPayment" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		
		boolean existFlag = false ;
		/*
		 * Check already the payment Done or not
		 */
		Connection con = null;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement("select * from online_payment_details where application_no = ?  and deposit_status = 'SUCCESS'") ;
				ps.setString(1, appno);
				rs = ps.executeQuery() ;
				if(rs.next()){
					existFlag =  true ;
				}
				
			}
			 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
		return existFlag ;
	}//end of checkPayment
	/**
	 * This method saves payment initiation details to database
	 * @param HashMap
	 * @author Ranvir
	 */
	public static void savePaymentInit(HashMap hmRequest) throws SQLException{
		String methodname = "savePaymentInit" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		
		String strapplicationNo = hmRequest.get("application_no").toString() ;
		Double amount = Double.parseDouble(hmRequest.get("amount").toString()) ;
		String userId = hmRequest.get("userId").toString() ;
		Integer iorderNo = Integer.parseInt(hmRequest.get("order_no").toString()) ;
		/*
		 * Check already the payment initiated for this appno or not if yes then donot create another initiation
		 */
		
		
		Connection con = null;
		PreparedStatement ps1 = null ;
		PreparedStatement ps2 = null ;
		ResultSet rs = null ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps1 = con.prepareStatement("select * from online_payment_details where application_no = ? and order_no = ?") ;
				ps1.setString(1, strapplicationNo);
				ps1.setInt(2,iorderNo);
				rs = ps1.executeQuery() ;
				if(!rs.next()){
					ps2 = con.prepareStatement("INSERT INTO online_payment_details(application_no, deposit_mode, amount, deposit_status, request_time,"
							+ "created_by, created_on, order_no) VALUES (?, 'ONLINE', ?, 'INITIATED', current_timestamp, ?, current_timestamp, ?);");
	
					ps2.setString(1, strapplicationNo);
					ps2.setDouble(2, amount);
					ps2.setString(3,userId);
					ps2.setInt(4,iorderNo);
					
					ps2.execute() ;
				}
				
			}
			 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(ps1 != null){
				ps1.close();
			}
			if(ps2 != null){
				ps2.close();
			}
			if(rs != null){
				rs.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
	}//end of savePaymentInit
	/**
	 * This method updates payment response details to database
	 * @param HashMap
	 * @author Ranvir
	 */
	public static void savePaymentResponse(HashMap hmRequest) throws SQLException{
		String methodname = "savePaymentResponse" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		Connection con = null;
		PreparedStatement ps = null ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement("UPDATE online_payment_details set deposit_status = ?,request_time = current_timestamp,payment_id = ? "
						+ "where order_no = ? and amount = ?");
				
				ps.setString(1,hmRequest.get("payment_status").toString());
				ps.setString(2,hmRequest.get("payment_id").toString());
				ps.setInt(3,Integer.parseInt(hmRequest.get("order_no").toString()));
				ps.setDouble(4, Double.parseDouble(hmRequest.get("amount").toString()));
				
				ps.executeUpdate() ;
				
			}
			ps.close();
			con.close(); 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
	}//end of savePaymentResponse
	/**
	 * This method retrives the payment Details for an order and amount
	 * @param HashMap
	 * @author Ranvir
	 */
	public static String getAppliationNumber(HashMap<String, Object> hmRequest) throws SQLException {
		String methodname = "getAppliationNumber" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		
		String strapp_no = "" ;
		/*
		 * Check already the payment Done or not
		 */
		Connection con = null;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement("select application_no from online_payment_details where order_no = ? and amount = ? and deposit_status = 'SUCCESS'") ;
				ps.setInt(1,Integer.parseInt(hmRequest.get("order_no").toString()));
				ps.setDouble(2, Double.parseDouble(hmRequest.get("amount").toString()));
				rs = ps.executeQuery() ;
				if(rs.next()){
					strapp_no =  rs.getString(1) ;
				}
				
			}
			 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
		return strapp_no ;
	}//end of getAppliationNumber
	/**
	 * This method retrives the payment Details for an application_number and amount
	 * @param HashMap
	 * @author Ranvir
	 * @throws SQLException 
	 */
	public static PaymentDetailsDto getPaymentDetails(
			String strApplicationNumber, Double amount_paid) throws SQLException {
		
		String methodname = "getPaymentDetails" ;
		System.out.println("Entry--->DBUtils method :  "+methodname);
		
		PaymentDetailsDto paymentDto  = null ;
		Connection con = null;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		String query = "SELECT DEPOSIT_STATUS,ORDER_NO,PAYMENT_ID FROM ONLINE_PAYMENT_DETAILS WHERE REQUEST_TIME = (SELECT MAX(REQUEST_TIME) "
		+"FROM ONLINE_PAYMENT_DETAILS WHERE APPLICATION_NO = ? AND AMOUNT= ?)" ;
		try {
			con = MyPostgresqlDaoFactory.getConnection();
			if (con != null) {
				ps = con.prepareStatement(query);
				
				ps.setString(1,strApplicationNumber);
				ps.setDouble(2, amount_paid);
				
				rs = ps.executeQuery() ;
				if(rs.next()){
					//Populate Dto Object
					paymentDto = new PaymentDetailsDto() ;
					paymentDto.setStrApplicationNumber(strApplicationNumber);
					paymentDto.setStrAmount(amount_paid+"");
					paymentDto.setStrPaymentStatus(rs.getString(1));
					paymentDto.setStrOrderNumber(rs.getString(2));
					paymentDto.setStrPaymentId(rs.getString(3));
				}
				
			}
			ps.close();
			con.close(); 
		} catch (Exception e) {
			System.out.println("Exception in DAO");
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			if(con != null){
				con.close();
			}
		}
		System.out.println("Exit--->DBUtils method :  "+methodname);
		
		return paymentDto;
	}
	
	
}
