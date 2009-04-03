/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;


import org.semanticwb.SWBUtils;

public class Reports {

    @Test
    public void GlobalReport() {
        StringBuilder query = new StringBuilder();
        Connection con = SWBUtils.DB.getDefaultConnection();
        Timestamp fecha = null;
        GregorianCalendar date;

        //(int) (Math.random() * ALPHABETH.length())
        try {
            query.append("insert into swb_reshits(hits_date,hits_modelid,hits_objid,hits_type,hits) values(?,?,?,?,?)");
            PreparedStatement st = con.prepareStatement(query.toString());
            con.setAutoCommit(true);
            for(int c=0; c<101; c++) {
                //System.out.println("incia registro...");
                int year = 2006 + (int)(Math.random()*2);
                int month = (int)(Math.random()*11);
                int day = 1+(int)(Math.random()*27);                
                date = new GregorianCalendar(year, month, day, 10,10,10);
                fecha = new Timestamp(date.getTimeInMillis());
                //System.out.println("fecha="+date.DAY_OF_MONTH+"/"+(date.MONTH+1)+"/"+date.YEAR);

                int hits = (int)(Math.random()*100);
                //System.out.println("hits="+hits);

                st.setTimestamp(1, fecha);
                st.setString(2, "X");
                st.setString(3, "_");
                st.setInt(4, 0);
                st.setInt(5, hits);
                st.executeUpdate();
            }
            System.out.println("FIN");
        }catch(SQLException e) {
            System.out.println("error... "+e);
        }
    }

}
