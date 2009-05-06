
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.ArrayList;

import java.util.HashMap;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits_;


public class LoggedUserDataDetail extends SWBDataDetail{
    
    public LoggedUserDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
        
    protected List doDataList(String repository, String rfilter, int type, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        //if(rfilter.hasNext()) {
//            resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(repository, rfilter, type);
//        //}
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, String rfilter, int type, int year, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        UserRepository ur=SWBContext.getUserRepository(rfilter);
        labels.put(ur.getId(), ur.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(repository, rfilter, type, year, labels);
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, String rfilter, int type, int year, int month, int day, String userLanguage) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        UserRepository ur=SWBContext.getUserRepository(rfilter);
        labels.put(ur.getId(), ur.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(repository, rfilter, type, year, month, day, labels);
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String userLanguage) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        UserRepository ur=SWBContext.getUserRepository(rfilter);
        labels.put(ur.getId(), ur.getDisplayTitle(userLanguage));

        resumeRecHits = SWBRecHits_.getInstance().getResHitsLog(repository, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels);
        return resumeRecHits;
    }
}
