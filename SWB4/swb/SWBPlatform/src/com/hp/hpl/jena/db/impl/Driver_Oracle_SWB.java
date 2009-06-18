/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hp.hpl.jena.db.impl;

import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.RDFRDBException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis
 */
public class Driver_Oracle_SWB extends Driver_Oracle
{
    private static Logger log = SWBUtils.getLogger(Driver_Oracle_SWB.class);

    public Driver_Oracle_SWB()
    {
        super();
    }

	/**
	 * Set the database connection
	 */
    @Override
	public void setConnection( IDBConnection dbcon ) {
		m_dbcon = dbcon;

		try {
			DatabaseMetaData dmd = dbcon.getConnection().getMetaData();
			if (dmd == null)
				throw new RDFRDBException("Oracle database metadata not available.");
			TABLE_NAME_LENGTH_MAX =	dmd.getMaxTableNameLength();
			setTableNames(TABLE_NAME_PREFIX);  // need to recheck that table names are not too long
		} catch ( SQLException e ) {
			throw new RDFRDBException("Problem accessing Oracle database metadata.");
		}

        try {
			m_sql = new SQLCache_SWB(SQL_FILE, null, dbcon, ID_SQL_TYPE);
		} catch (Exception e) {
            e.printStackTrace( System.err );
			log.error("Unable to set connection for Driver:", e);
		}
	}

}
