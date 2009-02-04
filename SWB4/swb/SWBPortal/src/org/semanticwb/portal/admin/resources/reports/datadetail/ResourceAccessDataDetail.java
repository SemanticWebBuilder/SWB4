
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;


public class ResourceAccessDataDetail extends SWBDataDetail{
    
    public ResourceAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
    
    public List doDataList(String site, Iterator rfilter, int type) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
//        StringTokenizer st_res = new StringTokenizer(rfilter,"|");
//        while(st_res.hasMoreTokens()) {
//            String idRes = (String) st_res.nextToken();
//            String titleRes = null;// = DBResource.getInstance().getResource(site, Long.parseLong(idRes)).getTitle();
//            resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,idRes,type, titleRes, Integer.parseInt(idRes)));
//        }        
        return resumeRecHits;
    }
    
    public List doDataList(String site, Iterator rfilter, int type, int year) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
//        StringTokenizer st_res = new StringTokenizer(rfilter,"|");
//        while(st_res.hasMoreTokens()) {
//            String idRes = (String) st_res.nextToken();
//            String titleRes = null;// = DBResource.getInstance().getResource(site, Long.parseLong(idRes)).getTitle();
//            resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,idRes,type, year, titleRes, Integer.parseInt(idRes)));
//        }        
        return resumeRecHits;        
    }
    
    public List doDataList(String site, Iterator rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
//        StringTokenizer st_res = new StringTokenizer(rfilter,"|");
//        while(st_res.hasMoreTokens()) {
//            String idRes = (String) st_res.nextToken();
//            String titleRes = null;// = DBResource.getInstance().getResource(site, Long.parseLong(idRes)).getTitle();
//            resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,idRes,type, year,month,day, titleRes, Integer.parseInt(idRes)));
//        }        
        return resumeRecHits;
    }
    
    public List doDataList(String site, Iterator rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        List resumeRecHits = new ArrayList();
//        StringTokenizer st_res = new StringTokenizer(rfilter,"|");
//        while(st_res.hasMoreTokens()) {
//            String idRes = (String) st_res.nextToken();
//            String titleRes = null;// = DBResource.getInstance().getResource(site, Long.parseLong(idRes)).getTitle();
//            resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,idRes,type, yearI,monthI,dayI, yearF,monthF,dayF, titleRes, Integer.parseInt(idRes)));
//        }        
        return resumeRecHits;
    }
}
