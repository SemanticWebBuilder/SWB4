
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.ArrayList;

import java.util.HashMap;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.db.SWBRecHits_;
import org.semanticwb.portal.admin.resources.reports.beans.*;

public class GlobalAccessDataDetail extends SWBDataDetail{
    
    public GlobalAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    public List doDataList(String site, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        /*if(rfilter.hasNext()) {*/
//            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type);
//        //}
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        WebSite ws=SWBContext.getWebSite(rfilter);
        labels.put(ws.getId(), ws.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, year, labels);
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, int month, int day, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        WebSite ws=SWBContext.getWebSite(rfilter);
        labels.put(ws.getId(), ws.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, year, month, day, labels);
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        WebSite ws=SWBContext.getWebSite(rfilter);
        labels.put(ws.getId(), ws.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(site, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels);
        return resumeRecHits;
    }
}
