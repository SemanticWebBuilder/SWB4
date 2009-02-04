
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator; 
import java.util.ArrayList;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;


public class LoggedUserDataDetail extends SWBDataDetail{
    
    public LoggedUserDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
        
    protected List doDataList(String repository, Iterator rfilter, int type) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {        
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, Iterator rfilter, int type, int year) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, year);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, Iterator rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, year, month, day);
        }
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, Iterator rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, yearI, monthI, dayI, yearF, monthF, dayF);
        }
        return resumeRecHits;
    }
}