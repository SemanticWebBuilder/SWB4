/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.reports.beans;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Administrador
 */
public class WBAFilterReportBean {
    private int dayI;
    private int monthI;
    private int yearI;
    private int dayF;
    private int monthF;
    private int yearF;
    private String site;
    private String idaux;
    private int type;       
    
    private HashMap userTypes;

    private String userLanguage;

    
    public WBAFilterReportBean(){
        dayI   = -1;
        monthI = -1;
        yearI  = -1;
        userLanguage = "es";
    }

    public int getDayI() {
        return dayI;
    }

    public void setDayI(int dayI) {
        this.dayI = dayI;
    }
    
    public String getIdaux() {
        return idaux;
    }

    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    public int getMonthI() {
        return monthI;
    }

    public void setMonthI(int monthI) {
        this.monthI = monthI;
    }

    public int getYearI() {
        return yearI;
    }

    public void setYearI(int yearI) {
        this.yearI = yearI;
    }

    public int getDayF() {
        return dayF;
    }

    public void setDayF(int dayF) {
        this.dayF = dayF;
    }

    public int getMonthF() {
        return monthF;
    }

    public void setMonthF(int monthF) {
        this.monthF = monthF;
    }

    public int getYearF() {
        return yearF;
    }

    public void setYearF(int yearF) {
        this.yearF = yearF;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public HashMap getUserTypes() {
        return userTypes;
    }
    
    public void setUserTypes(HashMap userTypes) {
        this.userTypes = userTypes;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WBAFilterReportBean instance to String\n");
        sb.append("dayI=" + dayI);
        sb.append(", monthI=" + monthI);
        sb.append(", yearI=" + yearI);
        sb.append(", dayF=" + dayF);
        sb.append(", monthF=" + monthF);
        sb.append(", yearF=" + yearF);
        sb.append(", site=" + site);
        sb.append(", idaux=" + idaux);
        sb.append(", type=" + type);
        
        return sb.toString();
    }

    /**
     * @return the userLanguage
     */
    public String getUserLanguage() {
        return userLanguage;
    }

    /**
     * @param userLanguage the userLanguage to set
     */
    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }
}
