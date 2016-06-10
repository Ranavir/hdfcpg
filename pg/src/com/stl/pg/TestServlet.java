package com.stl.pg;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String methodname = "" ;
	private String user = "Ranvir" ;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.methodname="doPost";
		System.out.println("Entry--->"+this.getClass()+" method :  "+methodname);
		
		/*Enumeration<String> params = request.getParameterNames() ;
		String paramName = "" ;
		while(params.hasMoreElements()){
			System.out.print(paramName = params.nextElement());
			System.out.println(" : "+request.getParameter(paramName));
			
		}*/
		/*request.setAttribute("application_no","123");
		request.setAttribute("order_no","2");
		request.setAttribute("amount","44.44");
		request.setAttribute("PaymentID","123DS");
		request.setAttribute("paymentStatus","SUCCESS");
		RequestDispatcher rd = request.getRequestDispatcher("/PostPayment.jsp") ;
		rd.forward(request,response);*/
		
		StringBuffer sb = new StringBuffer("");
		  
		String application_no = "123" ;
		String order_no = "2" ;
		String amount = "44.44" ;
		String PaymentID = "123ds" ;
		String paymentStatus = "SUCCESS" ;
		  
		sb.append(request.getContextPath()+"/PostPayment.jsp");
		sb.append("?application_no=" + application_no);
		sb.append("&order_no=" + order_no);
		
		
		sb.append("&amount=" + amount);
		sb.append("&PaymentId=" + PaymentID);
		sb.append("&paymentStatus=" + paymentStatus);
		  
		  
		String url = sb.toString();
		  
		String urlEncoded = response.encodeRedirectURL(url) ;
		System.out.println(urlEncoded);
		response.sendRedirect(urlEncoded);
		//response.sendRedirect(request.getContextPath()+"/PostPayment.jsp");
		System.out.println("Exit-->"+this.getClass()+" method : "+methodname);
	}

}
