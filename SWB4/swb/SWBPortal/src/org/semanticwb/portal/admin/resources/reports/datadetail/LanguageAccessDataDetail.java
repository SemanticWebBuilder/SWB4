
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.GregorianCalendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHit;
import org.semanticwb.portal.db.SWBRecHits;
/*import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.core.db.DBCatalogs;
import com.infotec.wb.core.db.RecLanguage;
import com.infotec.wb.core.db.SWBRecHit;
import com.infotec.wb.core.db.SWBRecHits;*/


public class LanguageAccessDataDetail extends SWBDataDetail {
    private static Logger log = SWBUtils.getLogger(LanguageAccessDataDetail.class);
    
    public LanguageAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    /*public URL getJasperResource(){
        URL urlJR = null;
        urlJR = this.getClass().getResource("templates/dailyRepLanguages.jasper");
        return urlJR;
    }*/
    
    public List getUnknownHits(String topicmap, int type, String item){
        List resumeUnRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        GregorianCalendar date;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {
            try
            {
                String query = "select * from wbreshits where topicmap=? and idaux not in(select lang from wblanguage where idtm=?) and type=? order by wbdate";
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setString(2, topicmap);
                st.setInt(3, type);
                
                if (st != null)
                {
                    rs = st.executeQuery();                    
                    SWBRecHit detail;                    
                    while(rs.next()){                        
                        detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                        detail.setItem(item);
                        resumeUnRecHits.add(detail);
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
        return resumeUnRecHits;
    }
    
    public List getUnknownHits(String topicmap, int type, int year, String item){
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
                for(int i=mi; i<=mf; i++){                    
                    query = "select topicmap,idaux,type,sum(hits) as hits from wbreshits where topicmap=? and idaux not in(select lang from wblanguage where idtm=?) and type=? and (wbdate>=? and wbdate<?) group by topicmap,idaux,type";
                    st = con.prepareStatement(query);
                    st.setString(1, topicmap);
                    st.setString(2, topicmap);
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
            } catch (Exception e){
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            } finally{
                try{
                    if(rs!=null) rs.close();
                    if(st!=null) st.close();
                    if(con!=null) con.close();
                } catch(SQLException e){
                }
            }
        } else{
            //tira una exception
        }
        return resumeRecHits;
    }
    
    public List getUnknownHits(String topicmap, int type, int year, int month, int day, String item){
        List resumeUnRecHits = new ArrayList();
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
                String query;                
                if (year > 0 && month > 0 && day > 0)
                {                    
                    query = "select * from wbreshits where topicmap=? and idaux not in(select lang from wblanguage where idtm=?) and type=? and (wbdate=? or wbdate=?) order by wbdate asc";
                } else
                {
                    query = "select * from wbreshits where topicmap=? and idaux not in(select lang from wblanguage where idtm=?) and type=? and (wbdate>=? and wbdate<?) order by wbdate asc";
                }
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setString(2, topicmap);
                st.setInt(3, type);
                st.setTimestamp(4, fecha);
                st.setTimestamp(5, fecha2);
                
                if (st != null)
                {
                    rs = st.executeQuery();                    
                    SWBRecHit detail;                    
                    while(rs.next()){                        
                        detail = new SWBRecHit(rs.getTimestamp("wbdate"),rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getLong("hits"));
                        detail.setItem(item);
                        resumeUnRecHits.add(detail);
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
        return resumeUnRecHits;
    }
    
    public List getUnknownHits(String topicmap, int type, int year1, int month1, int day1, int year2, int month2, int day2, String item){
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null)
        {               
            try
            {
                GregorianCalendar date;
                String query = "select * from wbreshits where topicmap=? and idaux not in(select lang from wblanguage where idtm=?) and type=? and (wbdate>=? and wbdate<=?) order by wbdate asc";
                st = con.prepareStatement(query);
                st.setString(1, topicmap);
                st.setInt(2, type);
                
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);                    
                st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
               
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
            
    public List doDataList(String site, String rfilter, int type) throws IncompleteFilterException{
        List resumeRecHits = null;
//        Enumeration enum_language = DBCatalogs.getInstance().getLanguages(site).elements();
//        String languageName = null;
//        RecLanguage recLanguage;
//        
//        if(rfilter!=null){
//            while(enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage)enum_language.nextElement();                
//                if (rfilter.equalsIgnoreCase(recLanguage.getLang())) {
//                    languageName = recLanguage.getTitle();
//                    break;
//                }
//            }
//            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type, languageName);
//        }else{
//            resumeRecHits = new ArrayList();
//            while (enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage) enum_language.nextElement();
//                rfilter = recLanguage.getLang();
//                languageName = recLanguage.getTitle();
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type, languageName));
//            }
//        }
//        if(resumeRecHits != null){
//            resumeRecHits.addAll(getUnknownHits(site,type, "Desconocido"));
//        }        
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = null;
//        Enumeration enum_language = DBCatalogs.getInstance().getLanguages(site).elements();
//        String languageName = null;
//        RecLanguage recLanguage;
//        
//        if(rfilter!=null){
//            while (enum_language.hasMoreElements()) {
//                 recLanguage = (RecLanguage) enum_language.nextElement();
//                if (rfilter.equalsIgnoreCase(recLanguage.getLang())) {
//                    languageName = recLanguage.getTitle();
//                    break;
//                }
//            }
//            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year, languageName);
//        }else{
//            resumeRecHits = new ArrayList();
//            while (enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage) enum_language.nextElement();
//                rfilter = recLanguage.getLang();
//                languageName = recLanguage.getTitle();
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year, languageName));
//            }
//        }
//        if(resumeRecHits != null){
//            resumeRecHits.addAll(getUnknownHits(site,type,year, "Desconocido"));
//        }
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        List resumeRecHits = null;
//        Enumeration enum_language = DBCatalogs.getInstance().getLanguages(site).elements();
//        String languageName = null;
//        RecLanguage recLanguage;
//        
//        if(rfilter!=null){
//            while(enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage)enum_language.nextElement();                
//                if (rfilter.equalsIgnoreCase(recLanguage.getLang())) {
//                    languageName = recLanguage.getTitle();
//                    break;
//                }
//            }
//            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year,month,day, languageName);
//        }else{
//            resumeRecHits = new ArrayList();
//            while (enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage) enum_language.nextElement();
//                rfilter = recLanguage.getLang();
//                languageName = recLanguage.getTitle();
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year,month,day, languageName));
//            }
//        }
//        if(resumeRecHits != null){
//            resumeRecHits.addAll(getUnknownHits(site,type,year,month,day, "Desconocido"));
//        }
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        List resumeRecHits = null;
//        Enumeration enum_language = DBCatalogs.getInstance().getLanguages(site).elements();
//        String languageName = null;
//        RecLanguage recLanguage;
//        
//        if(rfilter!=null){
//            while (enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage) enum_language.nextElement();
//                if (rfilter.equalsIgnoreCase(recLanguage.getLang())) {
//                    languageName = recLanguage.getTitle();
//                    break;
//                }
//            }
//            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF, languageName);
//        }else{
//            resumeRecHits = new ArrayList();
//            while (enum_language.hasMoreElements()) {
//                recLanguage = (RecLanguage) enum_language.nextElement();
//                rfilter = recLanguage.getLang();
//                languageName = recLanguage.getTitle();
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF, languageName));
//            }
//        }
//        
//        if(resumeRecHits != null){
//            resumeRecHits.addAll(getUnknownHits(site,type,yearI,monthI,dayI,yearF,monthF,dayF, "Desconocido"));
//        }
        return resumeRecHits;        
    }
}
