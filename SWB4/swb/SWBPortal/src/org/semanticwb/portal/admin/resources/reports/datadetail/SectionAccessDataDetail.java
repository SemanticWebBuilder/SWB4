
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;

public class SectionAccessDataDetail extends SWBDataDetail{
    
    public SectionAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    /*public URL getJasperResource(){
        URL urlJR = null;
        urlJR = this.getClass().getResource("templates/dailyRepSections.jasper");
        return urlJR;
    }*/    
       
    protected List doDataList(String site, Iterator rfilter, int type) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, (String)rfilter.next(), type);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String site, Iterator rfilter, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, (String)rfilter.next(), type, year);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String site, Iterator rfilter, int type, int year, int month, int day) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, (String)rfilter.next(), type, year, month, day);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String site, Iterator rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, (String)rfilter.next(), type, yearI, monthI, dayI, yearF, monthF, dayF);
        }
        return resumeRecHits;
    }
}
