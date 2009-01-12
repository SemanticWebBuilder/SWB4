
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;



public class LoggedUniqueDataDetail extends SWBDataDetail{    
    
    public LoggedUniqueDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }   
        
    /*public URL getJasperResource(){
        URL urlJR = null;        
        urlJR = this.getClass().getResource("templates/dailyRepUniqueUsers.jasper");
        return urlJR;
    }*/
    
    protected List doDataList(String repository, String rfilter, int type) throws IncompleteFilterException{
        /*JRDataSource dataSource = null;*/
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type);
        /*if(!reportRows.isEmpty())
            dataSource = new JRBeanCollectionDataSource(reportRows);*/
        return resumeRecHits;
    }
    
    protected List doDataList(String site, String rfilter, int type, int year) throws IncompleteFilterException {
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(site, rfilter, type, year);
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, String rfilter, int type, int year, int month, int day) throws IncompleteFilterException{
        /*JRDataSource dataSource = null;*/
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type,year,month,day);
        /*if(!reportRows.isEmpty())
            dataSource = new JRBeanCollectionDataSource(reportRows);*/
        return resumeRecHits;
    }
    
    protected List doDataList(String repository, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF) throws IncompleteFilterException{
        /*JRDataSource dataSource = null;*/
        List resumeRecHits = SWBRecHits.getInstance().getResHitsLog(repository,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF);
        /*if(!reportRows.isEmpty())
            dataSource = new JRBeanCollectionDataSource(reportRows);*/
        return resumeRecHits;
    }
}