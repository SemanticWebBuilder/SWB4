/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.db;

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.services.SWBServices;

/**
 *
 * @author jorge.jimenez
 */
public class IterRecHits implements java.util.Iterator 
{
    private static Logger log = SWBUtils.getLogger(SWBServices.class);
    
    private ResultSet rs;
    private Connection con;
    private Statement st;
    private boolean closed = false;
    private boolean next = false;
    private boolean checked = false;
    private boolean hasWBDate = true;

    /** Creates a new instance of IterResHits
     * @param con
     * @param st
     * @param rs  */
    public IterRecHits(Connection con, Statement st, ResultSet rs) {
        this(con, st, rs, true);
    }

    /** Creates a new instance of IterResHits
     * @param con
     * @param st
     * @param rs  */
    public IterRecHits(Connection con, Statement st, ResultSet rs, boolean hasWBDate) {
        this.rs = rs;
        this.con = con;
        this.st = st;
        this.hasWBDate = hasWBDate;
    }

    /**
     * @return  */
    public boolean hasNext() {
        try {
            if (!checked) {
                next = rs.next();
                checked = true;
            }
        } catch (Exception e) {
            log.error("Error in method IterRecHits:hasNext()",e);
        }
        if (!next) {
            close();
        }
        return next;
    }

    /**
     * @return  */
    public Object next() {
        try {
            if (checked) {
                checked = false;
                return new SWBRecHit(rs, hasWBDate);
            } else {
                next = rs.next();
                checked = false;
                if (next) {
                    return new SWBRecHit(rs, hasWBDate);
                }
            }
        } catch (Exception e) {
            log.error("Error in method IterRecHits:next()");
        }
        return null;
    }

    public void remove() {
    }

    public void close() {
        closed = true;
        try {
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            log.error("Error in method IterRecHits:close()");
        }
    }

    public void finalize() {
        if (!closed) {
            close();
            log.error("Error in method IterRecHits:finalize()");
        }
    }
}
