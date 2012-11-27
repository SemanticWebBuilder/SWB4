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

import static org.semanticwb.portal.admin.resources.reports.WBADeviceReport.UNKNOW;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.db.SWBRecHits_;
import org.semanticwb.portal.admin.resources.reports.beans.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceAccessDataDetail.
 */
public class DeviceAccessDataDetail extends SWBDataDetail {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(DeviceAccessDataDetail.class);
    
    /**
     * Instantiates a new device access data detail.
     * 
     * @param filterReportBean the filter report bean
     */
    public DeviceAccessDataDetail(WBAFilterReportBean filterReportBean) {
        super(filterReportBean);
    }
           
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail#doDataList(java.lang.String, java.lang.String, int, java.lang.String)
     */
    protected List doDataList(String siteId, String rfilter, int type, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        return resumeRecHits;
    }
    
        /**
         * Do data list.
         * 
         * @param siteId the site id
         * @param rfilter the rfilter
         * @param type the type
         * @param year the year
         * @param userLanguage the user language
         * @return the list
         * @throws IncompleteFilterException the incomplete filter exception
         * @return
         */
    protected List doDataList(String siteId, String rfilter,int type,int year, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", UNKNOW);
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, year, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, labels));
        }
        return resumeRecHits;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail#doDataList(java.lang.String, java.lang.String, int, int, int, int, java.lang.String)
     */
    protected List doDataList(String siteId, String rfilter, int type, int year, int month, int day, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", UNKNOW);
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, year, month, day, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, year, month, day, labels));
        }
        return resumeRecHits;
    }
        
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.datadetail.SWBDataDetail#doDataList(java.lang.String, java.lang.String, int, int, int, int, int, int, int, java.lang.String)
     */
    protected List doDataList(String siteId, String rfilter, int type, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String userLanguage) throws IncompleteFilterException {
        List resumeRecHits = new ArrayList();
        HashMap labels=new HashMap();

        if(rfilter==null) {
            labels.put("_", UNKNOW);
        }
        WebSite ws=SWBContext.getWebSite(siteId);
        Iterator<Device> it=ws.listDevices();
        while(it.hasNext()) {
            Device device=it.next();
            labels.put(device.getId(), device.getDisplayTitle(userLanguage));
        }

        if( rfilter!=null && rfilter.split(",").length>1) {
            List al = Arrays.asList(rfilter.split(","));
            Collections.sort(al);
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, al.iterator(), type, yearI, monthI, dayI, yearF, monthF, dayF, labels));
        }else {
            resumeRecHits.addAll(SWBRecHits_.getInstance().getResHitsLog(siteId, rfilter, type, yearI, monthI, dayI, yearF, monthF, dayF, labels));
        }
        return resumeRecHits;
    }
}
