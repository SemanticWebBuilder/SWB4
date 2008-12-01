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
//import org.semanticwb.portal.services.SWBServices;

/**
 *
 * @author jorge.jimenez
 */
public class SWBRecHits 
{
    private static Logger log = SWBUtils.getLogger(SWBRecHits.class);

    public Iterator getResGlobalHitsLog(String webSite) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");

        if (con != null) {
            try {
                String query = "select * from swb_reshits where website=? and type=0";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, webSite);
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
     * @param webSite
     * @param object
     * @param type
     * @return  */
    public Iterator getResHitsLog(String webSite, String object, int type) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");
        if (con != null) {
            try {
                String query = "select * from swb_reshits where website=? and type=?";
                if (object != null) {
                    query = query + " and object=?";
                }
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, webSite);
                st.setInt(2, type);
                if (object != null) {
                    st.setString(3, object);
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
     * @param webSite
     * @param type
     * @return  */
    public Iterator getResHitsLog(String webSite, int type) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResGlobalHitsLog()");
        if (con != null) {
            try {
                String query = "select wbdate,webSite,object, sum(hits) hits,type from swb_reshits where website=? and type=? group by object order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, webSite);
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
     * @param webSite
     * @param object
     * @param type
     * @param year
     * @param month
     * @param day
     * @return  */
    public Iterator getResHitsLog(String website, String object, int type, int year, int month, int day) {
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
                if (object != null) {
                    if (year > 0 && month > 0 && day > 0) {
                        query = "select * from swb_reshits where website=? and object=? and type=? and (wbdate=? or wbdate=?) order by wbdate";
                    } else {
                        query = "select * from swb_reshits where website=? and object=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, website);
                    st.setString(2, object);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                } else {
                    if (year > 0 && month > 0 && day > 0) {
                        query = "select * from swb_reshits where website=? and type=? and (wbdate=? or wbdate=?) order by wbdate";
                    } else {
                        query = "select * from swb_reshits where website=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, website);
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
     * @param webSite   
     * @param object
     * @param type
     * @param year1
     * @param month1
     * @param day1
     * @param year2
     * @param month2
     * @param day2
     * @return  */
    public Iterator getResHitsLog(String webSite, String object, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        fecha = new Timestamp(year1 - 1900, month1 - 1, day1, 00, 00, 00, 00);
        fecha2 = new Timestamp(year2 - 1900, month2 - 1, day2, 00, 00, 00, 00);
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResHitsLog()");
        if (con != null) {
            try {
                String query = "select * from swb_reshits where website=? and type=? and (wbdate>=? and wbdate<=?)";
                if (object != null) {
                    query = query + " and object=?";
                }
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, webSite);
                st.setInt(2, type);
                st.setTimestamp(3, fecha);
                st.setTimestamp(4, fecha2);
                if (object != null) {
                    st.setString(5, object);
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
     * @param webSite
     * @param type
     * @param year1
     * @param month1
     * @param day1
     * @param year2
     * @param month2
     * @param day2
     * @return  */
    public Iterator getResHitsLog(String website, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        fecha = new Timestamp(year1 - 1900, month1 - 1, day1, 00, 00, 00, 00);
        fecha2 = new Timestamp(year2 - 1900, month2 - 1, day2, 00, 00, 00, 00);
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("DBResHits.getResHitsLog()");
        if (con != null) {
            try {
                String query = "select wbdate,website,object, sum(hits) hits,type from swb_reshits where website=? and type=? and (wbdate>=? and wbdate<=?) group by object order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, website);
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
