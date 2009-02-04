
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
    
    public List doDataList(String repository, Iterator rfilter, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = null;
        return resumeRecHits;
    }
}
