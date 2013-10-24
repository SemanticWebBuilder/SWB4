/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import java.sql.Connection;
import java.sql.SQLException;
import org.semanticwb.base.db.PoolConnection;

/**
 *
 * @author javier.solis.g
 */
public class SWBPoolConection extends PoolConnection
{
    boolean canClose=true;
    
    /**
     *
     * @param con
     * @param canClose
     */
    public SWBPoolConection(Connection con, boolean canClose)
    {
        super(con,null);
        this.canClose=canClose;
    }

    @Override
    public void close() throws SQLException
    {
        if(canClose)super.close(); 
    }
    
    public void sclose() throws SQLException
    {
        super.close();
    }
}
