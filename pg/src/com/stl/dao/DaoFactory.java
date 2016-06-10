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

/**
 * @author Ranavir
 * @description
 * @date Dec 26, 2016
 *
 */
public abstract class DaoFactory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ORACLE = 2;
	public static final int POSTGRESQL = 1;
	
	/**
	 * To get all bCODE list
	 * 
	 * @author Ranavir
	 * @return List<String>
	 */
	
	
	
	/**
	 * To store bCODE sms
	 * 
	 * @author Ranavir
	 * @return Integer
	 */

	
	
		/**
	 * To get Oracle Connection object
	 * 
	 * @author Ranavir
	 * @param whichFactory
	 * @return DaoFactory
	 */
	
	public static DaoFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case POSTGRESQL:
			return new MyPostgresqlDaoFactory();
		case ORACLE:
			return new MyOracleDaoFactory();
		default:
			return null;
		}
	}


	
}
