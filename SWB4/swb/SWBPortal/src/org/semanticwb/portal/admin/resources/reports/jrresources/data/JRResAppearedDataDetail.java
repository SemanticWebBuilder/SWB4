/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

// TODO: Auto-generated Javadoc
/**
 * The Class JRResAppearedDataDetail.
 */
public class JRResAppearedDataDetail implements JRDataSourceable{
    
    /** The topicmap. */
    private String topicmap;
    
    /** The locale. */
    private Locale locale;
    
    /**
     * Instantiates a new jR res appeared data detail.
     * 
     * @param site the site
     * @param locale the locale
     */
    public JRResAppearedDataDetail(String site, Locale locale){
        topicmap = site;
        this.locale = locale;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#orderJRReport()
     */
    public JRBeanCollectionDataSource orderJRReport() throws IncompleteFilterException{
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
        
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;        
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#prepareJRReport()
     */
    public void prepareJRReport() throws JRException{
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#exportReport(javax.servlet.http.HttpServletResponse)
     */
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException{
    }
}
