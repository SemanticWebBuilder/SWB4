
package org.semanticwb.portal.admin.resources.reports.jrresources.data;

import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;
        
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JRResAppearedDataDetail implements JRDataSourceable{
    private String topicmap;
    private Locale locale;
    
    public JRResAppearedDataDetail(String site, Locale locale){
        topicmap = site;
        this.locale = locale;
    }
    
    public JRDataSource orderJRReport() throws IncompleteFilterException{
        List dataList = new ArrayList();        
//        Enumeration enuRecs = DBResource.getInstance().getResources(topicmap);
//        
//        while(enuRecs.hasMoreElements()) {            
//            RecResource r_res = (RecResource) enuRecs.nextElement();
//            WBResource tmp_res = ResourceMgr.getInstance().getResource(topicmap,r_res.getId());
//            if(null!=tmp_res)
//            {                
//                Resource res_base = tmp_res.getResourceBase();
//                if(r_res.getHitLog()==1 && null!=res_base.getConfAttribute("hitsEndDate"))
//                {
//                    SWBAppearedHits appearedHits = new SWBAppearedHits();
//                    
//                    String tmp_fecha = res_base.getConfAttribute("hitsEndDate");
//                    int month = Integer.parseInt(tmp_fecha.substring(0,2));
//                    int day = Integer.parseInt(tmp_fecha.substring(3,5));
//                    int year = Integer.parseInt(tmp_fecha.substring(6));
//
//                    GregorianCalendar gc = new GregorianCalendar(year, month-1,day);
//                    Timestamp ts = new Timestamp(gc.getTimeInMillis());
//                    Timestamp ct = new Timestamp(System.currentTimeMillis());
//                    
//                    appearedHits.setId(r_res.getId());
//                    appearedHits.setTitle(r_res.getTitle());
//                    appearedHits.setPriority(r_res.getPriorityName());
//
//                    RecCamp rcamp = DBCatalogs.getInstance().getCamp(topicmap, r_res.getIdCamp());
//                    if(null!=rcamp)
//                        appearedHits.setCampaign(rcamp.getTitle());
//                    else
//                        appearedHits.setCampaign("No");
//                    
//                    appearedHits.setType(r_res.getResourceType().getName());
//                    
//                    if(r_res.getIdSubType()>0){
//                        RecSubType rstype = DBCatalogs.getInstance().getSubType(topicmap,r_res.getIdSubType());
//                        appearedHits.setSubtype(rstype.getTitle());
//                    }else{
//                        appearedHits.setSubtype("");
//                    }
//                    
//                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
//                    appearedHits.setUpdate(df.format(r_res.getLastupdate()));
//                    appearedHits.setNumappear(r_res.getViews());
//                    appearedHits.setHits(r_res.getHits());                    
//                    appearedHits.setAppeardate(res_base.getConfAttribute("hitsEndDate"));
//                    appearedHits.setStatus(Integer.toString(res_base.getActive()));
//                    dataList.add(appearedHits);                    
//                }
//            }
//        }
        
        JRBeanCollectionDataSource dataSource = null;        
        if(!dataList.isEmpty())
            dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;        
    }    
    
    public void prepareJRReport() throws JRException{
    }
    
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
    }
}
