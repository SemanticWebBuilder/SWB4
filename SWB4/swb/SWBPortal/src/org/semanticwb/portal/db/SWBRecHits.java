
/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/** Objeto: Almacena resumenes de hits diarios:
 *
 * Se tienen 6 tipos de reportes diarios con la siguiente informacion:
 *
 * ---------- date, topicmap, idaux, hits, type  --------------------
 *
 * tipo
 * 0	registro diario global, 		idaux=_
 * 1	registro diario por dispositivo, 	idaux=disp,    ejemplo: web
 * 2	registro diario por lenguaje,		idaux=lang,    ejemplo: en
 * 3	registro diario por topico		idaux=topicid, ejemplo: Egobierno
 * 4	registro diario por tipousu		idaux=tipousu, ejemplo: estudiante, si es nulo=_
 * 5	registro diario num sesiones	        topicmap=repid idaux=_
 * 6    registro diario login                   topicmap=repid idaux=_
 * 7    registro diario login unicos            topicmap=repid idaux=_
 *
 * Object: It stores daily summaries of hits:
 *
 * 6 types of daily reports with the following information are had.
 *
 *
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBRecHits //implements AFAppObject, AFObserver
{
    private static Logger log = SWBUtils.getLogger(SWBRecHits.class);
    
    static private SWBRecHits instance;

    private SWBRecHits() {
        log.error("log_DBResHits_DBResHits_initialized");
    }

    public void destroy() {
        instance=null;
        log.error("log_DBResHits_destroy_finalized");
    }

    static synchronized public SWBRecHits getInstance() {
        if (instance == null) {
            instance = new SWBRecHits();
            instance.init();
        }
        return instance;
    }

    public void init() {
    }

    public Iterator getResGlobalHitsLog(String topicmap) {
        Iterator ret = new ArrayList().iterator();
//        Connection con = SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                String query = "select * from wbreshits where topicmap=? and type=0";
//                PreparedStatement st = con.prepareStatement(query);
//                st.setString(1, topicmap);
//                ResultSet rs = st.executeQuery();
//                ret = new IterResHits(con, st, rs);
//            } catch (Exception e)
//            {
//                log.error("Error DBResHits_getResGlobalHitsLog_getLogerror", e);
//            }
//        } else
//        {
//            //tira una exception
//        }
        return ret;
    }

    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @return  */
    public List getResHitsLog(String topicmap, String idaux, int type)
    {
        List resumeRecHits = new ArrayList();
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=?";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                if (idaux != null)
                    st.setString(3, idaux);
                ResultSet rs = st.executeQuery();
                SWBRecHit detail;                    
                while(rs.next()){                        
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }

    /**
     * @param topicmap
     * @param type
     * @return  */
    public Iterator getResHitsLog(String topicmap, int type)
    {
        Iterator ret = new ArrayList().iterator();
//        Connection con = SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                String query = "select wbdate,topicmap,idaux, sum(hits) hits,type from wbreshits where topicmap=? and type=? group by idaux order by wbdate";
//                PreparedStatement st = con.prepareStatement(query);
//                st.setString(1, topicmap);
//                st.setInt(2, type);
//                ResultSet rs = st.executeQuery();
//                ret = new IterResHits(con, st, rs);
//            } catch (Exception e)
//            {
//                log.error("Error DBResHits_getResHitsLog_getLogError", e);
//            }
//        } else
//        {
//            //tira una exception
//        }
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
    public List getResHitsLog(String topicmap, String idaux, int type, int year, int month, int day)
    {
        List resumeRecHits = new ArrayList();
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        GregorianCalendar date;
        
        if (year > 0)
        {
            if (month > 0)
            {
                if (day > 0)
                { //para cuando envian year,month y day
                    date = new GregorianCalendar(year,month-1,day,0,0,0);
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month-1,day,23,59,59);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
                if (fecha == null)
                { //para cuando envian year y month
                    date = new GregorianCalendar(year,month-1,1,0,0,0);                    
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month,1,0,0,0);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
            }
            if (fecha == null)
            { //para cuando envian solamente year
                date = new GregorianCalendar(year,0,1,0,0,0);                
                fecha = new Timestamp(date.getTimeInMillis());
                date = new GregorianCalendar(year+1,0,1,0,0,0);                
                fecha2 = new Timestamp(date.getTimeInMillis());
            }
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = null;
                if (idaux != null)
                {
                    if (year > 0 && month > 0 && day > 0)
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    } else
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setString(2, idaux);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                } else
                {
                    if (year > 0 && month > 0 && day > 0)
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    else
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setInt(2, type);
                    st.setTimestamp(3, fecha);
                    st.setTimestamp(4, fecha2);
                }
                if (st != null)
                {
                    rs = st.executeQuery();                    
                    SWBRecHit detail;                    
                    while(rs.next()){                        
                        detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                        resumeRecHits.add(detail);
                    }
                }
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
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
    public List getResHitsLog(String topicmap, String idaux, int type, int year1, int month1, int day1, int year2, int month2, int day2)
    {
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {               
            try
            {
                GregorianCalendar date;
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?)";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);                    
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));

                if (idaux != null)
                    st.setString(5, idaux);
                rs = st.executeQuery();
                
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){                   
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
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
    public Iterator getResHitsLog(String topicmap, int type, int year1, int month1, int day1, int year2, int month2, int day2)
    {
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        fecha = new Timestamp(year1 - 1900, month1 - 1, day1, 00, 00, 00, 00);
        fecha2 = new Timestamp(year2 - 1900, month2 - 1, day2, 00, 00, 00, 00);
        Iterator ret = new ArrayList().iterator();
//        Connection con = SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                String query = "select wbdate,topicmap,idaux, sum(hits) hits,type from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?) group by idaux order by wbdate";
//                PreparedStatement st = con.prepareStatement(query);
//                st.setString(1, topicmap);
//                st.setInt(2, type);
//                st.setTimestamp(3, fecha);
//                st.setTimestamp(4, fecha2);
//                ResultSet rs = st.executeQuery();
//                ret = new IterResHits(con, st, rs);
//            } catch (Exception e)
//            {
//                log.error("Error DBResHits_getResHitsLog_getLogError", e);
//            }
//        } else
//        {
//            //tira una exception
//        }
        return ret;
    }


    public void refresh()
    {
    }

    /** Avisa al observador de que se ha producido un cambio.
     * @param s
     * @param obj  */
    public void sendDBNotify(String s, Object obj)
    {
        if (s.equals("remove"))
        {
        }
        if (s.equals("create"))
        {
        }
    }
    
    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @param year
     * @param month
     * @param day
     * @return  */
    public List getResHitsLog(String topicmap, String idaux, int type, int year)
    {
        List resumeRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        int mi=0, mf=0;        
        
        if (year > 0)            
        {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query;                
                GregorianCalendar date;
                if (idaux != null)
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setString(2, idaux);
                        st.setInt(3, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            resumeRecHits.add(detail);
                        }                        
                    }
                }else
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setInt(2, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            resumeRecHits.add(detail);
                        }                        
                    }
                }
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else{
            //tira una exception
        }
        return resumeRecHits;
    }
    
    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @return  */
    public List getResHitsLog(String topicmap, String idaux, int type, String item)
    {
        List resumeRecHits = new ArrayList();
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=?";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                if (idaux != null)
                    st.setString(3, idaux);
                ResultSet rs = st.executeQuery();
                SWBRecHit detail;                    
                while(rs.next()){                        
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    detail.setItem(item);
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @param year
     * @param month
     * @param day
     * @return  */
    public List getResHitsLog(String topicmap, String idaux, int type, int year, String item)
    {
        List resumeRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        int mi=0, mf=0;        
        
        if (year > 0)            
        {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query;                
                GregorianCalendar date;
                if (idaux != null)
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setString(2, idaux);
                        st.setInt(3, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            detail.setItem(item);
                            resumeRecHits.add(detail);
                        }                        
                    }
                }else
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setInt(2, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            detail.setItem(item);                            
                            resumeRecHits.add(detail);
                        }                        
                    }
                }
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else{
            //tira una exception
        }
        return resumeRecHits;
    }
    
    /**
     * @param topicmap
     * @param idaux
     * @param type
     * @param year
     * @param month
     * @param day
     * @return  */
    public List getResHitsLog(String topicmap, String idaux, int type, int year, int month, int day, String item)
    {
        List resumeRecHits = new ArrayList();
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        GregorianCalendar date;
        
        if (year > 0)
        {
            if (month > 0)
            {
                if (day > 0)
                { //para cuando envian year,month y day
                    date = new GregorianCalendar(year,month-1,day,0,0,0);
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month-1,day,23,59,59);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
                if (fecha == null)
                { //para cuando envian year y month
                    date = new GregorianCalendar(year,month-1,1,0,0,0);                    
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month,1,0,0,0);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
            }
            if (fecha == null)
            { //para cuando envian solamente year
                date = new GregorianCalendar(year,0,1,0,0,0);                
                fecha = new Timestamp(date.getTimeInMillis());
                date = new GregorianCalendar(year+1,0,1,0,0,0);                
                fecha2 = new Timestamp(date.getTimeInMillis());
            }
        }else{
            date = new GregorianCalendar(2000,0,1,0,0,0);                
            fecha = new Timestamp(date.getTimeInMillis());
            date = new GregorianCalendar(2022,0,1,0,0,0);                
            fecha2 = new Timestamp(date.getTimeInMillis());
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = null;
                if (idaux != null)
                {
                    if (year > 0 && month > 0 && day > 0)
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    } else
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setString(2, idaux);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                } else
                {
                    if (year > 0 && month > 0 && day > 0)
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    else
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setInt(2, type);
                    st.setTimestamp(3, fecha);
                    st.setTimestamp(4, fecha2);
                }
                if (st != null)
                {
                    rs = st.executeQuery();                    
                    SWBRecHit detail;                    
                    while(rs.next()){                        
                        detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                        detail.setItem(item);
                        resumeRecHits.add(detail);
                    }
                }
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
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
    public List getResHitsLog(String topicmap, String idaux, int type, int year1, int month1, int day1, int year2, int month2, int day2, String item)
    {
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {               
            try
            {
                GregorianCalendar date;
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?)";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);                    
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));

                if (idaux != null)
                    st.setString(5, idaux);
                
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){                   
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    detail.setItem(item);
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    public List getResHitsLog(String topicmap, String idaux, int type, String item, int iditem)
    {
        List resumeRecHits = new ArrayList();
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=?";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                if (idaux != null)
                    st.setString(3, idaux);
                ResultSet rs = st.executeQuery();
                SWBRecHit detail;                    
                while(rs.next()){                        
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    detail.setItem(item);
                    detail.setIditem(iditem);
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    public List getResHitsLog(String topicmap, String idaux, int type, int year, String item, int iditem)
    {
        List resumeRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        int mi=0, mf=0;        
        
        if (year > 0)            
        {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query;                
                GregorianCalendar date;
                if (idaux != null)
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setString(2, idaux);
                        st.setInt(3, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            detail.setItem(item);
                            detail.setIditem(iditem);
                            resumeRecHits.add(detail);
                        }                        
                    }
                }else
                {
                    for(int i=mi; i<=mf; i++){
                        query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type order by wbdate";
                        st = con.prepareStatement(query);
                        st.setString(1, topicmap);
                        st.setInt(2, type);
                                                
                        date = new GregorianCalendar(year,i,1,0,0,0);                    
                        st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                        date = new GregorianCalendar(year,i+1,1,0,0,0);
                        st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
                      
                        SWBRecHit detail;
                        rs = st.executeQuery();
                        while(rs.next()){
                            date = new GregorianCalendar(year,i,1);
                            detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                            detail.setItem(item);                            
                            resumeRecHits.add(detail);
                        }                        
                    }
                }
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else{
            //tira una exception
        }
        return resumeRecHits;
    }
    
    public List getResHitsLog(String topicmap, String idaux, int type, int year, int month, int day, String item, int iditem)
    {
        List resumeRecHits = new ArrayList();
        Timestamp fecha = null;
        Timestamp fecha2 = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        GregorianCalendar date;
        
        if (year > 0)
        {
            if (month > 0)
            {
                if (day > 0)
                { //para cuando envian year,month y day
                    date = new GregorianCalendar(year,month-1,day,0,0,0);
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month-1,day,23,59,59);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
                if (fecha == null)
                { //para cuando envian year y month
                    date = new GregorianCalendar(year,month-1,1,0,0,0);                    
                    fecha = new Timestamp(date.getTimeInMillis());                    
                    date = new GregorianCalendar(year,month,1,0,0,0);
                    fecha2 = new Timestamp(date.getTimeInMillis());
                }
            }
            if (fecha == null)
            { //para cuando envian solamente year
                date = new GregorianCalendar(year,0,1,0,0,0);                
                fecha = new Timestamp(date.getTimeInMillis());
                date = new GregorianCalendar(year+1,0,1,0,0,0);                
                fecha2 = new Timestamp(date.getTimeInMillis());
            }
        }else{
            date = new GregorianCalendar(2000,0,1,0,0,0);                
            fecha = new Timestamp(date.getTimeInMillis());
            date = new GregorianCalendar(2022,0,1,0,0,0);                
            fecha2 = new Timestamp(date.getTimeInMillis());
        }

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = null;
                if (idaux != null)
                {
                    if (year > 0 && month > 0 && day > 0)
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    } else
                    {
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and idaux=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    }
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setString(2, idaux);
                    st.setInt(3, type);
                    st.setTimestamp(4, fecha);
                    st.setTimestamp(5, fecha2);
                } else
                {
                    if (year > 0 && month > 0 && day > 0)
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?) order by wbdate";
                    else
                        query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<?) order by wbdate";
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setInt(2, type);
                    st.setTimestamp(3, fecha);
                    st.setTimestamp(4, fecha2);
                }
                if (st != null)
                {
                    rs = st.executeQuery();                    
                    SWBRecHit detail;                    
                    while(rs.next()){                        
                        detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                        detail.setItem(item);
                        detail.setIditem(iditem);
                        resumeRecHits.add(detail);
                    }
                }
            } catch (Exception e){
                log.error("Error_DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    public List getResHitsLog(String topicmap, String idaux, int type, int year1, int month1, int day1, int year2, int month2, int day2, String item, int iditem)
    {
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {               
            try
            {
                GregorianCalendar date;
                String query = "select wbdate,topicmap,idaux,type,hits from wbreshits where topicmap=? and type=? and (wbdate>=? and wbdate<=?)";
                if (idaux != null)
                    query = query + " and idaux=?";
                query = query + " order by wbdate";
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);                    
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));

                if (idaux != null)
                    st.setString(5, idaux);
                
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){                   
                    detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                    detail.setItem(item);
                    detail.setIditem(iditem);
                    resumeRecHits.add(detail);
                }
            } catch (Exception e)
            {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null)rs.close();
                    if(st!=null)st.close();
                    if(con!=null)con.close();
                } catch(SQLException e){
                }
            }
        } else
        {
            //tira una exception
        }
        return resumeRecHits;
    }
}