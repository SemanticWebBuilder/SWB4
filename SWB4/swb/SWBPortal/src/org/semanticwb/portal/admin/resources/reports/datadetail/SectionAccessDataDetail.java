
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;

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
       
    protected List doDataList(String site, String section, int type) throws IncompleteFilterException {
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,section,type);
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String section, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, section, type, year);
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String section, int type, int year, int month, int day) throws IncompleteFilterException {
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,section,type,year,month,day);
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String section, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException {
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,section,type,yearI,monthI,dayI,yearF,monthF,dayF);
        return resumeRecHits;
    }
}
