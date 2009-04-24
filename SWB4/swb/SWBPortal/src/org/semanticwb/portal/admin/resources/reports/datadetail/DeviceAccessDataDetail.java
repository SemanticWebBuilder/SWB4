
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.db.SWBRecHits_;
import org.semanticwb.portal.admin.resources.reports.beans.*;

public class DeviceAccessDataDetail extends SWBDataDetail {
    private static Logger log = SWBUtils.getLogger(DeviceAccessDataDetail.class);
    
    public DeviceAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
           
    protected List doDataList(String siteId, String rfilter, int type, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }
    
        /**
     * @param siteId
     * @param rfilter
     * @param type
     * @param year
     * @return  */
    protected List doDataList(String siteId, String rfilter,int type,int year, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, year, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, labels));
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String siteId, String rfilter, int type, int year, int month, int day, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, year, month, day, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, month, day, labels));
        }
        return resumeRecHits;
    }
        
    protected List doDataList(String siteId, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", "Desconocido");
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, yearI, monthI, dayI, yearF, monthF, dayF, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels));
        }
        return resumeRecHits;
    }
}
