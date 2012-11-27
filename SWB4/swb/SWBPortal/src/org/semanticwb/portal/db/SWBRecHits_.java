/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBRecHits_.
 */
public class SWBRecHits_
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBRecHits.class);
    
    /** The instance. */
    static private SWBRecHits_ instance;

    /**
     * Instantiates a new sWB rec hits_.
     */
    private SWBRecHits_() {
    }

    /**
     * Destroy.
     */
    public void destroy() {
        instance=null;
    }

    /**
     * Inits the.
     */
    public void init() {
    }

    /**
     * Gets the single instance of SWBRecHits_.
     * 
     * @return single instance of SWBRecHits_
     */
    static synchronized public SWBRecHits_ getInstance() {
        if (instance == null) {
            instance = new SWBRecHits_();
            instance.init();
        }
        return instance;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the res hits log
     */
    public List getResHitsLog(String modelid, int type, int year, int month, int day) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            try {
                //if(objid != null) {
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits)as hits from swb_reshits");
                query.append(" where hits_modelid=? and hits_type=?	and (hits_date>=? and hits_date<=?)");
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()), rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch(Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @return the res hits log
     */
    public List getResHitsLog(String modelid, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            try {
                //if(objid != null) {
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits)as hits from swb_reshits");
                query.append(" where hits_modelid=? and hits_type=?	and (hits_date>=? and hits_date<=?)");
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()), rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch(Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        int mi=0, mf=0;

        if (year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                for(int i=mi; i<=mf; i++) {
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setInt(2, type);
                    date = new GregorianCalendar(year,i,1,0,0,0);
                    st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,i+1,1,0,0,0);
                    st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                    if(objid != null) {
                        st.setString(5, objid);
                    }
                    SWBRecHit detail;
                    rs = st.executeQuery();
                    while(rs.next()) {
                        date = new GregorianCalendar(year,i,1);
                        detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        resumeRecHits.add(detail);
                    }
                    st.clearParameters();
                    st.close();
                    rs.close();
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year, int month, int day) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            try {
                //if(objid != null) {
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if( objid!=null && objid.length()>0 ) {
                    query.append(" and hits_objid=?");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                if(objid != null) {
                    st.setString(5, objid);
                }
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch(Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                if(objid != null) {
                    st.setString(5, objid);
                }
                rs = st.executeQuery();

                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

        /**
         * Gets the res hits log.
         * 
         * @param modelid the modelid
         * @param objid the objid
         * @param type the type
         * @param year the year
         * @return the res hits log
         * @return
         */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        int mi=0, mf=0;

        if (year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                for(int i=mi; i<=mf; i++) {
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setInt(2, type);
                    date = new GregorianCalendar(year,i,1,0,0,0);
                    st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,i+1,1,0,0,0);
                    st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                    SWBRecHit detail;
                    rs = st.executeQuery();
                    while(rs.next()) {
                        date = new GregorianCalendar(year,i,1);
                        detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        resumeRecHits.add(detail);
                    }
                    st.clearParameters();
                    st.close();
                    rs.close();
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year, int month, int day) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            try {
                //if(objid != null) {
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch(Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year1, int month1, int day1, int year2, int month2, int day2) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        int mi=0, mf=0;

        if(year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                for(int i=mi; i<=mf; i++) {
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setInt(2, type);
                    date = new GregorianCalendar(year,i,1,0,0,0);
                    st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,i+1,1,0,0,0);
                    st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                    SWBRecHit detail;
                    rs = st.executeQuery();
                    while(rs.next()) {
                        date = new GregorianCalendar(year,i,1);
                        detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        detail.setItem((String)items.get(rs.getString("hits_objid")));
                        resumeRecHits.add(detail);
                    }
                    st.clearParameters();
                    st.close();
                    rs.close();
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year, int month, int day, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem((String)items.get(rs.getString("hits_objid")));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, Iterator<String> objid, int type, int year1, int month1, int day1, int year2, int month2, int day2, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if( objid!=null && objid.hasNext()) {
                    query.append(" and hits_objid in(");
                    while(objid.hasNext()) {
                        query.append("'"+objid.next()+"'");
                        if(objid.hasNext()) {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));                
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem((String)items.get(rs.getString("hits_objid")));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }











    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        int mi=0, mf=0;

        if(year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                /*if(objid != null) {*/
                query.append("select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" group by hits_modelid,hits_objid,hits_type order by hits_objid");
                for(int i=mi; i<=mf; i++) {
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);                    
                    st.setInt(2, type);
                    date = new GregorianCalendar(year,i,1,0,0,0);
                    st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,i+1,1,0,0,0);
                    st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                    if(objid != null) {
                        st.setString(5, objid);
                    }

                    SWBRecHit detail;
                    rs = st.executeQuery();
                    while(rs.next()) {
                        date = new GregorianCalendar(year,i,1);
                        detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        detail.setItem((String)items.get(rs.getString("hits_objid")));
                        resumeRecHits.add(detail);
                    }
                    st.clearParameters();
                    st.close();
                    rs.close();
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year, int month, int day, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        GregorianCalendar date;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                /*if(objid != null) {*/
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);                
                st.setInt(2, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                if(objid != null) {
                    st.setString(5, objid);
                }
                /*}else {
                    query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?) order by hits_date");
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setInt(2, type);
                    date = new GregorianCalendar(year,month-1,day,0,0,0);
                    st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,month-1,day,23,59,59);
                    st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                }*/

                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem((String)items.get(rs.getString("hits_objid")));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @param items the items
     * @return the res hits log
     * @return
     */
    public List getResHitsLog(String modelid, String objid, int type, int year1, int month1, int day1, int year2, int month2, int day2, HashMap items) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" order by hits_date, hits_objid");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                if(objid != null) {
                    st.setString(5, objid);
                }
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem((String)items.get(rs.getString("hits_objid")));
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }














    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param item the item
     * @param iditem the iditem
     * @return the res hits log
     */
    public List getResHitsLog(String modelid, String objid, int type, int year, String item, int iditem) {
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        int mi=0, mf=0;

        if(year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                if(objid != null) {
                    String query = "select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_objid=? and hits_type=? and (hits_date>=? and hits_date<?) group by hits_modelid,hits_objid,hits_type order by hits_date";
                    for(int i=mi; i<=mf; i++) {
                        st = con.prepareStatement(query.toString());
                        st.setString(1, modelid);
                        st.setString(2, objid);
                        st.setInt(3, type);

                        date = new GregorianCalendar(year,i,1,0,0,0);
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));

                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                            detail.setItem(item);
                            detail.setIditem(iditem);
                            resumeRecHits.add(detail);
                        }
                        st.clearParameters();
                        st.close();
                        rs.close();
                    }
                }else {
                    String query = "select hits_modelid,hits_objid,hits_type,sum(hits) as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?) group by hits_modelid,hits_objid,hits_type order by hits_date";
                    for(int i=mi; i<=mf; i++) {
                        st = con.prepareStatement(query);
                        st.setString(1, modelid);
                        st.setInt(2, type);

                        date = new GregorianCalendar(year,i,1,0,0,0);
                        st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));

                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()) {
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                            detail.setItem(item);
                            resumeRecHits.add(detail);
                        }
                        st.clearParameters();
                        st.close();
                        rs.close();
                    }
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else{
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @param item the item
     * @param iditem the iditem
     * @return the res hits log
     */
    public List getResHitsLog(String modelid, String objid, int type, int year, int month, int day, String item, int iditem) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;
        Timestamp fecha = null;
        Timestamp fecha2 = null;        
        GregorianCalendar date;

        if(year > 0) {
            if(month > 0) {
                if(day > 0) {
                    //para cuando envian year,month y day
                    date = new GregorianCalendar(year,month-1,day,0,0,0);
                    fecha = new Timestamp(date.getTimeInMillis());
                    date = new GregorianCalendar(year,month-1,day,23,59,59);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
                if(fecha == null) {
                    //para cuando envian year y month
                    date = new GregorianCalendar(year,month-1,1,0,0,0);
                    fecha = new Timestamp(date.getTimeInMillis());
                    date = new GregorianCalendar(year,month,1,0,0,0);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
            }
            if(fecha == null) {
                //para cuando envian solamente year
                date = new GregorianCalendar(year,0,1,0,0,0);
                fecha = new Timestamp(date.getTimeInMillis());
                date = new GregorianCalendar(year+1,0,1,0,0,0);
                fecha2 = new Timestamp(date.getTimeInMillis());
            }
        }else {
            date = new GregorianCalendar(2000,0,1,0,0,0);
            fecha = new Timestamp(date.getTimeInMillis());
            date = new GregorianCalendar(2022,0,1,0,0,0);
            fecha2 = new Timestamp(date.getTimeInMillis());
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            try {
                if(objid != null) {
                    if(year > 0 && month > 0 && day > 0) {
                        query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid=? and hits_type=? and (hits_date>=? and hits_date<=?) order by hits_date");
                    }else {
                        query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid=? and hits_type=? and (hits_date>=? and hits_date<?) order by hits_date");
                    }
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setString(2, objid);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                }else {
                    if (year > 0 && month > 0 && day > 0)
                        query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?) order by hits_date");
                    else
                        query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<?) order by hits_date");
                    st = con.prepareStatement(query.toString());
                    st.setString(1, modelid);
                    st.setInt(2, type);
                    st.setTimestamp(3, fecha);
                    st.setTimestamp(4, fecha2);
                }
                if(st != null) {
                    rs = st.executeQuery();
                    SWBRecHit detail;
                    while(rs.next()) {
                        detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        detail.setItem(item);
                        detail.setIditem(iditem);
                        resumeRecHits.add(detail);
                    }
                }
            }catch (Exception e) {
                log.error("Error_DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * Gets the res hits log.
     * 
     * @param modelid the modelid
     * @param objid the objid
     * @param type the type
     * @param year1 the year1
     * @param month1 the month1
     * @param day1 the day1
     * @param year2 the year2
     * @param month2 the month2
     * @param day2 the day2
     * @param item the item
     * @param iditem the iditem
     * @return the res hits log
     */
    public List getResHitsLog(String modelid, String objid, int type, int year1, int month1, int day1, int year2, int month2, int day2, String item, int iditem) {
        List resumeRecHits = new ArrayList();
        StringBuilder query = new StringBuilder();
        ResultSet rs = null;
        PreparedStatement st = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            try {
                GregorianCalendar date;
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)");
                if(objid != null) {
                    query.append(" and hits_objid=?");
                }
                query.append(" order by hits_date");
                st = con.prepareStatement(query.toString());
                st.setString(1, modelid);
                st.setInt(2, type);

                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));

                if(objid != null)
                    st.setString(5, objid);

                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem(item);
                    detail.setIditem(iditem);
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }
}
