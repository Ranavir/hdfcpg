package com.stl.dto;
/**
 * This class stores the payment details of a Transaction 
 * @author Ranvir Dash
 *
 */
public class PaymentDetailsDto {
	//This String stores the application number for the payment
	private String strApplicationNumber ;
	//This String stores the unique order number for the payment
	private String strOrderNumber ;
	//This String stores the amount of the transaction
	private String strAmount ;
	//This String stores the paymentId for the transaction
	private String strPaymentId ;
	//This String stores the deposit status of the payment
	private String strPaymentStatus ;
	public PaymentDetailsDto() {
		super();
		this.strApplicationNumber = "";
		this.strOrderNumber = "";
		this.strAmount = "";
		this.strPaymentId = "";
		this.strPaymentStatus = "";
	}
	
	public String getStrApplicationNumber() {
		return strApplicationNumber;
	}
	public void setStrApplicationNumber(String strApplicationNumber) {
		this.strApplicationNumber = strApplicationNumber;
	}
	public String getStrOrderNumber() {
		return strOrderNumber;
	}
	public void setStrOrderNumber(String strOrderNumber) {
		this.strOrderNumber = strOrderNumber;
	}
	public String getStrAmount() {
		return strAmount;
	}
	public void setStrAmount(String strAmount) {
		this.strAmount = strAmount;
	}
	public String getStrPaymentId() {
		return strPaymentId;
	}
	public void setStrPaymentId(String strPaymentId) {
		this.strPaymentId = strPaymentId;
	}
	public String getStrPaymentStatus() {
		return strPaymentStatus;
	}
	public void setStrPaymentStatus(String strPaymentStatus) {
		this.strPaymentStatus = strPaymentStatus;
	}
	@Override
	public String toString() {
		return "PaymentDetailsDto [strApplicationNumber="
				+ strApplicationNumber + ", strOrderNumber=" + strOrderNumber
				+ ", strAmount=" + strAmount + ", strPaymentId=" + strPaymentId
				+ ", strPaymentStatus=" + strPaymentStatus + "]";
	}
	
	
}
