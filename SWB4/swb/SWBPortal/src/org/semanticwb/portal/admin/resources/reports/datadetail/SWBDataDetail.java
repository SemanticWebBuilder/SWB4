
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import org.semanticwb.portal.admin.resources.reports.beans.*;

public abstract class SWBDataDetail {
    private WBAFilterReportBean filterReportBean;
    protected HashMap userTypes;
    
    public SWBDataDetail(WBAFilterReportBean filterReportBean){
        this.filterReportBean = filterReportBean;
        userTypes = this.filterReportBean.getUserTypes();
    }
    
    public final List execute() throws IncompleteFilterException{
        List resumeRecHits = null;        
        if(filterReportBean.getYearF()>0 && filterReportBean.getMonthF()>0 && filterReportBean.getDayF()>0){
            resumeRecHits = doDataList(filterReportBean.getSite(),filterReportBean.getIdaux(),filterReportBean.getType(),filterReportBean.getYearI(),filterReportBean.getMonthI(),filterReportBean.getDayI(),filterReportBean.getYearF(),filterReportBean.getMonthF(),filterReportBean.getDayF(), filterReportBean.getUserLanguage());
        }else if(filterReportBean.getYearI()>0 && filterReportBean.getMonthI()>=0 && filterReportBean.getDayI()>=0){
            resumeRecHits = doDataList(filterReportBean.getSite(),filterReportBean.getIdaux(),filterReportBean.getType(),filterReportBean.getYearI(),filterReportBean.getMonthI(),filterReportBean.getDayI(), filterReportBean.getUserLanguage());
        }else if(filterReportBean.getYearI()>0 && filterReportBean.getMonthI()<0 && filterReportBean.getDayI()<0){
            resumeRecHits = doDataList(filterReportBean.getSite(),filterReportBean.getIdaux(),filterReportBean.getType(),filterReportBean.getYearI(), filterReportBean.getUserLanguage());
        }else{
            resumeRecHits = doDataList(filterReportBean.getSite(),filterReportBean.getIdaux(),filterReportBean.getType(), filterReportBean.getUserLanguage());
        }
        return resumeRecHits;     
    }
    
    protected abstract List doDataList(String siteId, String objId, int type, String language) throws IncompleteFilterException;
    
    protected abstract List doDataList(String siteId, String objId, int type, int year, String language) throws IncompleteFilterException;
    
    protected abstract List doDataList(String siteId, String objId, int type, int year, int month, int day, String language) throws IncompleteFilterException;
    
    protected abstract List doDataList(String siteId, String objId, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException;
}
