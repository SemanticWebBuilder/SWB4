
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import org.semanticwb.portal.db.SWBRecHits;
import org.semanticwb.portal.admin.resources.reports.beans.*;

public class SessionDataDetail extends SWBDataDetail{

    public SessionDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
        
    public List doDataList(String repository, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, rfilter, type);
        //}
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int year, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, rfilter, type, year);
        //}
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int year, int month, int day, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, rfilter, type, year, month, day);
        //}
        return resumeRecHits;
    }
    
    public List doDataList(String repository, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        //if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF);
        //}
        return resumeRecHits;
    }
}