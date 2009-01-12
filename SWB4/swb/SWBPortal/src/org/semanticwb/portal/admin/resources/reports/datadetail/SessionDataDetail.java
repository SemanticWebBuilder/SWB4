
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;

public class SessionDataDetail extends SWBDataDetail{

    public SessionDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
        
    /*public URL getJasperResource(){
        URL urlJR = null;        
        urlJR = this.getClass().getResource("templates/dailyRepSessions.jasper");
        return urlJR;
    }*/
        
    public List doDataList(String repository, String rfilter, int type) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type);
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int year) throws IncompleteFilterException {
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, rfilter, type, year);
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type,year,month,day);
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF);
        return resumeRecHits;
    }
}