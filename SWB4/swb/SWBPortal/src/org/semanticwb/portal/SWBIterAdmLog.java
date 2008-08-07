/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.sql.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
/**
 *
 * @author jorge.jimenez
 */
public class SWBIterAdmLog implements java.util.Iterator
{

    private static Logger log = SWBUtils.getLogger(SWBIterAdmLog.class);
    
    private ResultSet rs;
    private Connection con;
    private Statement st;
    private boolean closed = false;
    private boolean next = false;
    private boolean checked = false;


    /** Creates a new instance of IterAdmLog
     * @param con
     * @param st
     * @param rs  */
    public SWBIterAdmLog(Connection con, Statement st, ResultSet rs)
    {
        this.rs = rs;
        this.con = con;
        this.st = st;
    }

    /**
     * @return  */
    public boolean hasNext()
    {
        try
        {
            if (!checked)
            {
                next = rs.next();
                checked = true;
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        if (!next) {
            close();
        }
        return next;
    }

    /**
     * @return  */
    public Object next()
    {
        try
        {
            if (checked)
            {
                checked = false;
                return new SWBDBAdmLog(rs);
            } else
            {
                next = rs.next();
                checked = false;
                if (next) return new SWBDBAdmLog(rs);
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        return null;
    }

    public void remove()
    {
    }

    public void close()
    {
        closed = true;
        try
        {
            rs.close();
            st.close();
            con.close();
            //System.out.println("Cerrando iterador...");
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void finalize()
    {
        if (!closed)
        {
            close();
        }
    }   
    
}
