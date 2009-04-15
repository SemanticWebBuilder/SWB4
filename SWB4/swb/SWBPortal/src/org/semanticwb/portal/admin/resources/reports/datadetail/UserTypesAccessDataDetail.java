
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import org.semanticwb.portal.admin.resources.reports.beans.*;
import org.semanticwb.portal.db.SWBRecHits;

public class UserTypesAccessDataDetail extends SWBDataDetail{
    
    public UserTypesAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
       
    public List doDataList(String site, String rfilter, int type, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        String userTypeName;
//        if(rfilter != null) {
//            for(int i = 0; i < userTypes.size(); i++) {
//                String s_aux = (String) userTypes.get(Integer.toString(i));
//                int i_pipe = s_aux.indexOf("|");
//                String s_val = s_aux.substring(0,i_pipe);
//                if(rfilter.equalsIgnoreCase(s_val))
//                {
//                    userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                    resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type, userTypeName));
//                }
//            }            
//        } else{
//            for(int j = 0; j < userTypes.size(); j++) {
//                String s_aux = (String) userTypes.get(Integer.toString(j));
//                int i_pipe = s_aux.indexOf("|");
//                rfilter = s_aux.substring(0,i_pipe);
//                userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type, userTypeName));
//            }
//        }
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
//        String userTypeName;        
//        if(rfilter!=null){
//            for(int i = 0; i < userTypes.size(); i++) {
//                String s_aux = (String) userTypes.get(Integer.toString(i));
//                int i_pipe = s_aux.indexOf("|");
//                String s_val = s_aux.substring(0,i_pipe);
//                if(s_val.length() > 0)
//                {
//                    if(rfilter.equalsIgnoreCase(s_val))
//                    {
//                        userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                        resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year, userTypeName));
//                    }
//                }
//            }
//        }else {
//            for(int i = 0; i < userTypes.size(); i++) {
//                String s_aux = (String) userTypes.get(Integer.toString(i));
//                int i_pipe = s_aux.indexOf("|");
//                rfilter = s_aux.substring(0,i_pipe);
//                userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year, userTypeName));                
//            }
//        }        
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int year, int month, int day, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();        
//        String userTypeName;
//        if(rfilter != null) {
//            for(int i = 0; i < userTypes.size(); i++) {
//                String s_aux = (String) userTypes.get(Integer.toString(i));
//                int i_pipe = s_aux.indexOf("|");
//                String s_val = s_aux.substring(0,i_pipe);
//                if(rfilter.equalsIgnoreCase(s_val))
//                {
//                    userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                    resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year,month,day, userTypeName));
//                }
//            }            
//        } else{
//            for(int j = 0; j < userTypes.size(); j++) {
//                String s_aux = (String) userTypes.get(Integer.toString(j));
//                int i_pipe = s_aux.indexOf("|");
//                rfilter = s_aux.substring(0,i_pipe);
//                userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,year,month,day, userTypeName));
//            }
//        }
        return resumeRecHits;
    }
    
    public List doDataList(String site, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();        
//        String userTypeName;
//
//        if(rfilter!=null) {
//            for(int i = 0; i < userTypes.size(); i++) {
//                String s_aux = (String) userTypes.get(Integer.toString(i));
//                int i_pipe = s_aux.indexOf("|");
//                String s_val = s_aux.substring(0,i_pipe);
//                if(rfilter.equalsIgnoreCase(s_val))
//                {
//                    userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                    resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF, userTypeName));
//                }
//            }
//        } else{                        
//            for(int j = 0; j < userTypes.size(); j++) {
//                String s_aux = (String) userTypes.get(Integer.toString(j));
//                int i_pipe = s_aux.indexOf("|");
//                rfilter = s_aux.substring(0,i_pipe);
//                userTypeName = s_aux.substring(i_pipe + 1, s_aux.length());
//                resumeRecHits.addAll(SWBRecHits.getInstance().getResHitsLog(site,rfilter,type,yearI,monthI,dayI,yearF,monthF,dayF, userTypeName));
//            }
//        }
        return resumeRecHits;
    }
}
