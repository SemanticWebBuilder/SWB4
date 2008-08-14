/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.services.SWBServices;

/**
 *
 * @author jorge.jimenez
 */
public class SWBRecHits 
{
    private static Logger log = SWBUtils.getLogger(SWBServices.class);

    public Iterator getResGlobalHitsLog(SemanticModel model) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");

        if (con != null) {
            try {
                String query = "select * from swb_reshits where model=? and type=0";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, model.getName());
                ResultSet rs = st.executeQuery();
                ret = new IterRecHits(con, st, rs);
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResGlobalHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @return  */
    public Iterator getResHitsLog(String topicmap, String idaux, int type) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");
        if (con != null) {
            try {
                String query = "select * from wbreshits where topicmap=? and type=?";
                if (idaux != null) {
                    query = query + " and idaux=?";
                }
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                if (idaux != null) {
                    st.setString(3, idaux);
                }
                ResultSet rs = st.executeQuery();
                ret = new IterRecHits(con, st, rs);
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    /**
     * @param topicmap
     * @param type
     * @return  */
    public Iterator getResHitsLog(String topicmap, int type) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");
        if (con != null) {
            try {
                String query = "select wbdate,topicmap,idaux, sum(hits) hits,type from wbreshits where topicmap=? and type=? group by idaux order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                ResultSet rs = st.executeQuery();
                ret = new IterRecHits(con, st, rs);
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @param year
     * @param month
     * @param day
     * @return  */
    public Iterator getResHitsLog(String topicmap, String idaux, int type, int year, int month, int day) {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        if (year > 0) {
            if (month > 0) {
                if (day > 0) { //para cuando envian year,month y day
                    fecha = new Timestamp(year - 1900, month - 1, day, 00, 00, 00, 00);
                    fecha2 = new Timestamp(year - 1900, month - 1, day, 00, 00, 00, 00);
                }
                if (fecha == null) { //para cuando envian year y month
                    fecha = new Timestamp(year - 1900, month - 1, 01, 00, 00, 00, 00);
                    fecha2 = new Timestamp(year - 1900, month, 01, 00, 00, 00, 00);
                }
            }
            if (fecha == null) { //para cuando envian solamente year
                fecha = new Timestamp(year - 1900, 00, 01, 00, 00, 00, 00);
                fecha2 = new Timestamp(year - 1899, 00, 01, 00, 00, 00, 00);
            }
        }
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResHitsLog()");
        if (con != null) {
            try {
                String query = null;
                PreparedStatement st = null;
                if (idaux != null) {
                    if (year > 0 && month > 0 && day > 0) {
                        query = "select * from wbreshits where topicmap=? and idaux=? and type=? and (wbdate=? or wbdate=?) order by wbdate";
                    } else {
                        query = "select * from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setString(2, idaux);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                } else {
                    if (year > 0 && month > 0 && day > 0) {
                        query = "select * from wbreshits where topicmap=? and type=? and (wbdate=? or wbdate=?) order by wbdate";
                    } else {
                        query = "select * from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setInt(2, type);
                    st.setTimestamp(3, fecha);
                    st.setTimestamp(4, fecha2);
                }
                if (st != null) {
                    ResultSet rs = st.executeQuery();
                    ret = new IterRecHits(con, st, rs);
                }
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @param year1
     * @param month1
     * @param day1
     * @param year2
     * @param month2
     * @param day2
     * @return  */
    public Iterator getResHitsLog(String topicmap, String idaux, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        fecha = new Timestamp(year1 - 1900, month1 - 1, day1, 00, 00, 00, 00);
        fecha2 = new Timestamp(year2 - 1900, month2 - 1, day2, 00, 00, 00, 00);
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResHitsLog()");
        if (con != null) {
            try {
                String query = "select * from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?)";
                if (idaux != null) {
                    query = query + " and idaux=?";
                }
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                st.setTimestamp(3, fecha);
                st.setTimestamp(4, fecha2);
                if (idaux != null) {
                    st.setString(5, idaux);
                }
                ResultSet rs = st.executeQuery();
                ret = new IterRecHits(con, st, rs);
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    /**
     * @param topicmap
     * @param type
     * @param year1
     * @param month1
     * @param day1
     * @param year2
     * @param month2
     * @param day2
     * @return  */
    public Iterator getResHitsLog(String topicmap, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        fecha = new Timestamp(year1 - 1900, month1 - 1, day1, 00, 00, 00, 00);
        fecha2 = new Timestamp(year2 - 1900, month2 - 1, day2, 00, 00, 00, 00);
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResHitsLog()");
        if (con != null) {
            try {
                String query = "select wbdate,topicmap,idaux, sum(hits) hits,type from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?) group by idaux order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                st.setTimestamp(3, fecha);
                st.setTimestamp(4, fecha2);
                ResultSet rs = st.executeQuery();
                ret = new IterRecHits(con, st, rs);
            } catch (Exception e) {
                log.error("Error in method SWBRecHits:getResHitsLog",e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }
}
