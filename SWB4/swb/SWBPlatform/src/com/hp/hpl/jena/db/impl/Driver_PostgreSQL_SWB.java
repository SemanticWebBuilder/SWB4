/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hp.hpl.jena.db.impl;

import com.hp.hpl.jena.db.IDBConnection;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis
 */
public class Driver_PostgreSQL_SWB extends Driver_MySQL
{
    private static Logger log = SWBUtils.getLogger(Driver_PostgreSQL_SWB.class);

    public Driver_PostgreSQL_SWB()
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
			// Properties defaultSQL = SQLCache.loadSQLFile(DEFAULT_SQL_FILE, null, ID_SQL_TYPE);
			// m_sql = new SQLCache(SQL_FILE, defaultSQL, dbcon, ID_SQL_TYPE);
            if(SWBPlatform.getEnv("swb/ts_statementsCache","false").equals("false"))
            {
                m_sql = new SQLCache_SWB(SQL_FILE, null, dbcon, ID_SQL_TYPE);
            }else
            {
                m_sql = new SQLCache(SQL_FILE, null, dbcon, ID_SQL_TYPE);
            }
		} catch (Exception e) {
            e.printStackTrace( System.err );
			log.error("Unable to set connection for Driver:", e);
		}
	}

}
