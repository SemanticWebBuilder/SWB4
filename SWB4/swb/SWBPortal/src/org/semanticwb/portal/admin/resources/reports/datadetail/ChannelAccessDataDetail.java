
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator; 
import java.util.ArrayList;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;

public abstract class ChannelAccessDataDetail extends SWBDataDetail{
        
    public ChannelAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    public List doDataList(String site, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }

    public List doDataList(String site, String rfilter, int type, int year, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }

    public List doDataList(String site, String rfilter, int type, int year, int month, int day, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }

    public List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }
}
