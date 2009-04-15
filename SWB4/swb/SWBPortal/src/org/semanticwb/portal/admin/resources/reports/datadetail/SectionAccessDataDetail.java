
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.ArrayList;

import java.util.HashMap;
import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits_;

public class SectionAccessDataDetail extends SWBDataDetail{
    
    public SectionAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    /*public URL getJasperResource(){
        URL urlJR = null;
        urlJR = this.getClass().getResource("templates/dailyRepSections.jasper");
        return urlJR;
    }*/    
       
    protected List doDataList(String site, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        HashMap labels=new HashMap();
//        //if(rfilter.hasNext()) {
//            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, labels);
//        //}
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String rfilter, int type, int year, String language) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, year, labels);
        //}
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String rfilter, int type, int year, int month, int day, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, year, month, day, labels);
        //}
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels);
        //}
        return resumeRecHits;
    }
}
