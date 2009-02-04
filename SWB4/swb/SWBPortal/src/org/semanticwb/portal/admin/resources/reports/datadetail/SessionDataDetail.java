
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
        
    public List doDataList(String repository, Iterator rfilter, int type) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type);
        }
        return resumeRecHits;
    }
    
    public List doDataList(String repository, Iterator rfilter, int type, int year) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, year);
        }
        return resumeRecHits;
    }
    
    public List doDataList(String repository, Iterator rfilter, int type, int year, int month, int day) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, year, month, day);
        }
        return resumeRecHits;
    }
    
    public List doDataList(String repository, Iterator rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        if(rfilter.hasNext()) {
            resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository, (String)rfilter.next(), type, yearI, monthI, dayI, yearF, monthF, dayF);
        }
        return resumeRecHits;
    }
}