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
package org.semanticwb.portal.admin.resources.reports.datadetail;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import org.semanticwb.portal.admin.resources.reports.beans.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBDataDetail.
 */
public abstract class SWBDataDetail {
    
    /** The filter report bean. */
    private WBAFilterReportBean filterReportBean;
    
    /** The user types. */
    protected HashMap userTypes;
    
    /**
     * Instantiates a new sWB data detail.
     * 
     * @param filterReportBean the filter report bean
     */
    public SWBDataDetail(WBAFilterReportBean filterReportBean){
        this.filterReportBean = filterReportBean;
        userTypes = this.filterReportBean.getUserTypes();
    }
    
    /**
     * Execute.
     * 
     * @return the list
     * @throws IncompleteFilterException the incomplete filter exception
     */
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
    
    /**
     * Do data list.
     * 
     * @param siteId the site id
     * @param objId the obj id
     * @param type the type
     * @param language the language
     * @return the list
     * @throws IncompleteFilterException the incomplete filter exception
     */
    protected abstract List doDataList(String siteId, String objId, int type, String language) throws IncompleteFilterException;
    
    /**
     * Do data list.
     * 
     * @param siteId the site id
     * @param objId the obj id
     * @param type the type
     * @param year the year
     * @param language the language
     * @return the list
     * @throws IncompleteFilterException the incomplete filter exception
     */
    protected abstract List doDataList(String siteId, String objId, int type, int year, String language) throws IncompleteFilterException;
    
    /**
     * Do data list.
     * 
     * @param siteId the site id
     * @param objId the obj id
     * @param type the type
     * @param year the year
     * @param month the month
     * @param day the day
     * @param language the language
     * @return the list
     * @throws IncompleteFilterException the incomplete filter exception
     */
    protected abstract List doDataList(String siteId, String objId, int type, int year, int month, int day, String language) throws IncompleteFilterException;
    
    /**
     * Do data list.
     * 
     * @param siteId the site id
     * @param objId the obj id
     * @param type the type
     * @param yearI the year i
     * @param monthI the month i
     * @param dayI the day i
     * @param yearF the year f
     * @param monthF the month f
     * @param dayF the day f
     * @param language the language
     * @return the list
     * @throws IncompleteFilterException the incomplete filter exception
     */
    protected abstract List doDataList(String siteId, String objId, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String language) throws IncompleteFilterException;

    /**
     * Gets the filter report bean.
     * 
     * @return the filter report bean
     */
    public WBAFilterReportBean getFilterReportBean() {
        return filterReportBean;
    }
}
