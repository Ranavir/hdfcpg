/*Copyright (c) 2011 Tcube Solution Pvt Ltd.  All rights reserved. 			           *	
	 * This document is the property of Tcube Solution Pvt Ltd..                          *
	 * All ideas and information contained in this document are the intellectual property (IP) *
	 * rights of Tcube Solution Pvt Ltd.. This document is not for reference or general   *
	 * distribution and is meant for use only for Tcube. This document shall not             *
	 * be loaned to or shared with anyone, within or outside Tcube, including its customers. * 
	 * Copying or unauthorized distribution of this document, in any form or means             *
	 * including electronic, mechanical, photocopying or otherwise is illegal.                 * 
	 * Use is subject to license terms only.                                                   *  
	  *****************************************************************************************

	*****************************************************************************************
	*    Ver         Author                  Date        			Description		        *
	*    1.0         Nibedita               16-October-2011          	Initial Version		    *
	*****************************************************************************************
*/
package com.stl.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




/**
 * @author Ranavir
 * @description
 * @date Dec 26, 2016
 *
 */
public class MyPostgresqlDaoFactory extends DaoFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method is used to get Oracle Connection
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {

			DriverManager.registerDriver(new org.postgresql.Driver());
			
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pg", "postgres", "techlab");
			if (conn != null) {
				System.out.println("Connection Established Successfully");
			}

		} catch (SQLException sqe) {
			System.out.println("Not able to Established Connection ");
		} catch (Exception exe) {
			System.out.println("Not able to Established Connection ");
		}
		return conn;
	}


		
}
