
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.util.db.SWBRecHits;

public class GlobalAccessDataDetail extends SWBDataDetail{
    
    public GlobalAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    public List doDataList(String site, String rfilter, int type) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type);
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, rfilter, type, year);        
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year,month,day);
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF);
        return resumeRecHits;
    }
}
