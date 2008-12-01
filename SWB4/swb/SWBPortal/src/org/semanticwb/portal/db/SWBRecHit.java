/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.db;

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
//import org.semanticwb.portal.services.SWBServices;

/**
 *
 * @author jorge.jimenez
 */
public class SWBRecHit 
{
    private static Logger log = SWBUtils.getLogger(SWBRecHit.class);

    private Timestamp date;
    private String model;
    private String idaux;
    private int type;
    private long hits;

    public SWBRecHit(ResultSet rs) {
        this(rs, true);
    }

    /**
     * @param rs  */
    public SWBRecHit(ResultSet rs, boolean hasWBDate) {
        try {
            if (hasWBDate) {
                this.date = rs.getTimestamp("wbdate");
            }
            this.model = rs.getString("model");
            this.idaux = rs.getString("idaux");
            this.type = rs.getInt("type");
            this.hits = rs.getLong("hits");
        } catch (Exception e) {
            log.error("Error en method SWBRecHit:SWBRecHit",e);
        }
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getIdaux() {
        return idaux;
    }

    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }   
    
    
}
