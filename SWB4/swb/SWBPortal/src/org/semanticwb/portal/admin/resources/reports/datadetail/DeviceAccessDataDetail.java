
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.db.SWBRecHit;
import org.semanticwb.portal.db.SWBRecHits_;
import org.semanticwb.portal.admin.resources.reports.beans.*;

public class DeviceAccessDataDetail extends SWBDataDetail {
    private static Logger log = SWBUtils.getLogger(DeviceAccessDataDetail.class);
    
    public DeviceAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    /*protected List getUnknownHits(String siteId, int type, String item) {
        List resumeUnRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;

        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {            
            Iterator<Device> itDevices = SWBContext.getWebSite(siteId).listDevices();
            StringBuilder dev = new StringBuilder();
            while(itDevices.hasNext()) {
                dev.append("'");
                dev.append(itDevices.next().getId());
                dev.append("'");
                if(itDevices.hasNext()) {
                    dev.append(",");
                }
            }
            
            try {
                StringBuilder query = new StringBuilder();
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid not in (");
                query.append("select distinct hits_objid from swb_reshits where hits_modelid=? and hits_objid in(");
                query.append(dev);
                query.append(" )and hits_type=? )");
                query.append(" and hits_type=? order by hits_date");
                
                st = con.prepareStatement(query.toString());
                st.setString(1, siteId);
                st.setString(2, siteId);
                st.setInt(3, type);
                st.setInt(4, type);
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()) {
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem(item);
                    resumeUnRecHits.add(detail);
                }
            }catch(Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(con!=null)con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeUnRecHits;
    }
    
    protected List getUnknownHits(String siteId, int type, int year, String item) {
        List resumeRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        int mi=0, mf=0;        
        
        if(year > 0) {
            GregorianCalendar gcf = new GregorianCalendar();
            gcf.set(GregorianCalendar.YEAR, year);
            mi = gcf.getActualMinimum(GregorianCalendar.MONTH);
            mf = gcf.getActualMaximum(GregorianCalendar.MONTH);
        }
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if(con != null) {
            Iterator<Device> itDevices = SWBContext.getWebSite(siteId).listDevices();
            StringBuilder dev = new StringBuilder();
            while(itDevices.hasNext()) {
                dev.append("'");
                dev.append(itDevices.next().getId());
                dev.append("'");
                if(itDevices.hasNext()) {
                    dev.append(",");
                }
            }

            try {         
                GregorianCalendar date;
                SWBRecHit detail;
                StringBuilder query = new StringBuilder();
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid not in (");
                query.append("select distinct hits_objid from swb_reshits where hits_modelid=? and hits_objid in(");
                query.append(dev);
                query.append(" )and hits_type=? )");
                query.append(" and hits_type=?");
                query.append(" and (hits_date>=? and hits_date<?)");
                query.append(" group by hits_modelid,hits_objid,hits_type");
                for(int i=mi; i<=mf; i++) {
                    st = con.prepareStatement(query.toString());
                    st.setString(1, siteId);
                    st.setString(2, siteId);
                    st.setInt(3, type);
                    st.setInt(4, type);
                    date = new GregorianCalendar(year,i,1,0,0,0);                    
                    st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                    date = new GregorianCalendar(year,i+1,1,0,0,0);
                    st.setTimestamp(6, new Timestamp(date.getTimeInMillis()));
                    
                    rs = st.executeQuery();
                    while(rs.next()){
                        date = new GregorianCalendar(year,i,1);
                        detail = new SWBRecHit(new Timestamp(date.getTimeInMillis()),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                        detail.setItem(item);
                        resumeRecHits.add(detail);
                    }
                    st.clearParameters();
                    st.close();
                    rs.close();
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try{
                    if(con!=null) con.close();
                }catch(SQLException e){
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }
    
    protected List getUnknownHits(String siteId, int type, int year, int month, int day, String item) {
        List resumeUnRecHits = new ArrayList();
        PreparedStatement st = null;
        ResultSet rs = null;
        GregorianCalendar date;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            Iterator<Device> itDevices = SWBContext.getWebSite(siteId).listDevices();
            StringBuilder dev = new StringBuilder();
            while(itDevices.hasNext()) {
                dev.append("'");
                dev.append(itDevices.next().getId());
                dev.append("'");
                if(itDevices.hasNext()) {
                    dev.append(",");
                }
            }

            try {
                StringBuilder query = new StringBuilder();
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid not in (");
                query.append("select distinct hits_objid from swb_reshits where hits_modelid=? and hits_objid in(");
                query.append(dev);
                query.append(" )and hits_type=? )");
                query.append(" and hits_type=?");
                query.append(" and (hits_date>=? and hits_date<=?)");
                st = con.prepareStatement(query.toString());
                st.setString(1, siteId);
                st.setString(2, siteId);
                st.setInt(3, type);
                st.setInt(4, type);
                date = new GregorianCalendar(year,month-1,day,0,0,0);
                st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year,month-1,day,23,59,59);
                st.setTimestamp(6, new Timestamp(date.getTimeInMillis()));
                rs = st.executeQuery();
                SWBRecHit detail;
                while(rs.next()){
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem(item);
                    resumeUnRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(con!=null)con.close();
                }catch(SQLException e) {
                }
            }
        }else {
            //tira una exception
        }
        return resumeUnRecHits;
    }
    
    protected List getUnknownHits(String siteId, int type, int year1, int month1, int day1, int year2, int month2, int day2, String item) {
        List resumeRecHits = new ArrayList();
        ResultSet rs = null;
        PreparedStatement st = null;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if (con != null) {
            Iterator<Device> itDevices = SWBContext.getWebSite(siteId).listDevices();
            StringBuilder dev = new StringBuilder();
            while(itDevices.hasNext()) {
                dev.append("'");
                dev.append(itDevices.next().getId());
                dev.append("'");
                if(itDevices.hasNext()) {
                    dev.append(",");
                }
            }

            try {
                GregorianCalendar date;
                StringBuilder query = new StringBuilder();
                query.append("select hits_date,hits_modelid,hits_objid,hits_type,hits from swb_reshits where hits_modelid=? and hits_objid not in (");
                query.append("select distinct hits_objid from swb_reshits where hits_modelid=? and hits_objid in(");
                query.append(dev);
                query.append(" )and hits_type=? )");
                query.append(" and hits_type=?");
                query.append(" and (hits_date>=? and hits_date<=?)");
                st = con.prepareStatement(query.toString());
                st.setString(1, siteId);
                st.setString(2, siteId);
                st.setInt(3, type);
                st.setInt(4, type);
                date = new GregorianCalendar(year1, month1-1, day1, 0, 0, 0);                    
                st.setTimestamp(5, new Timestamp(date.getTimeInMillis()));
                date = new GregorianCalendar(year2, month2-1, day2, 23, 59, 59);
                st.setTimestamp(6, new Timestamp(date.getTimeInMillis()));
               
                SWBRecHit detail;
                rs = st.executeQuery();
                while(rs.next()){                   
                    detail = new SWBRecHit(rs.getTimestamp("hits_date"),rs.getString("hits_modelid"),rs.getString("hits_objid"),rs.getInt("hits_type"),rs.getLong("hits"));
                    detail.setItem(item);
                    resumeRecHits.add(detail);
                }
            }catch (Exception e) {
                log.error("Error DBResHits_getResHitsLog_getLogError", e);
            }finally {
                try {
                    if(con!=null)con.close();
                }catch(SQLException e) {
                }
            }
        }else {
            //tira una exception
        }
        return resumeRecHits;
    }*/
       
    protected List doDataList(String siteId, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        HashMap labels=new HashMap();
//
//        if(rfilter==null) {
//            labels.put("_", "Desconocido");
//        }
//        WebSite ws=SWBContext.getWebSite(siteId);
//        Iterator<Device> it=ws.listDevices();
//        while(it.hasNext()) {
//            Device device=it.next();
//            labels.put(device.getId(), device.getDisplayTitle(language));
//        }
//
//        /*while (rfilter.hasNext()) {
//            device = (Device)rfilter.next();*/
//            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, labels));
//        /*}
//        resumeRecHits.addAll(getUnknownHits(siteId, type, "Desconocido"));*/
        return resumeRecHits;
    }
    
    protected List doDataList(String siteId, String rfilter,int type,int year, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(language));
        }
        /*while (rfilter.hasNext()) {
            device = (Device)rfilter.next();*/
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, labels));
        /*}
        resumeRecHits.addAll(getUnknownHits(siteId, type, year, "Desconocido"));*/
        return resumeRecHits;
    }
    
    protected List doDataList(String siteId, String rfilter, int type, int year, int month, int day, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(language));
        }
        /*while (rfilter.hasNext()) {
            device = (Device)rfilter.next();*/
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, month, day, labels));
        /*}
        resumeRecHits.addAll(getUnknownHits(siteId, type, year, month, day, "Desconocido"));*/
        return resumeRecHits;
    }
        
    protected List doDataList(String siteId, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(language));
        }
        /*while (rfilter.hasNext()) {
            device = (Device)rfilter.next();*/
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels));
        /*}
        resumeRecHits.addAll(getUnknownHits(siteId, type, yearI, monthI, dayI, yearF, monthF, dayF, "Desconocido"));*/
        return resumeRecHits;
    }
}
