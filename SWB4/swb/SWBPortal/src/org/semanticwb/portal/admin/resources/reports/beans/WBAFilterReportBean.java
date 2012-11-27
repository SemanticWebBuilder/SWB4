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
package org.semanticwb.portal.admin.resources.reports.beans;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class WBAFilterReportBean.
 * 
 * @author Administrador
 */
public class WBAFilterReportBean {
    
    /** The day i. */
    private int dayI;
    
    /** The month i. */
    private int monthI;
    
    /** The year i. */
    private int yearI;
    
    /** The day f. */
    private int dayF;
    
    /** The month f. */
    private int monthF;
    
    /** The year f. */
    private int yearF;
    
    /** The site. */
    private String site;
    
    /** The idaux. */
    private String idaux;
    
    /** The type. */
    private int type;       
    
    /** The user types. */
    private HashMap userTypes;

    /** The user language. */
    private String userLanguage;

    /** The grouped dates. */
    private boolean groupedDates;

    
    /**
     * Instantiates a new wBA filter report bean.
     */
    public WBAFilterReportBean(){
        dayI   = -1;
        monthI = -1;
        yearI  = -1;
        userLanguage = "es";
    }

    /**
     * Instantiates a new wBA filter report bean.
     * 
     * @param language the language
     */
    public WBAFilterReportBean(String language) {
        dayI   = -1;
        monthI = -1;
        yearI  = -1;
        userLanguage = language;
    }

    /**
     * Gets the day i.
     * 
     * @return the day i
     */
    public int getDayI() {
        return dayI;
    }

    /**
     * Sets the day i.
     * 
     * @param dayI the new day i
     */
    public void setDayI(int dayI) {
        this.dayI = dayI;
    }
    
    /**
     * Gets the idaux.
     * 
     * @return the idaux
     */
    public String getIdaux() {
        return idaux;
    }

    /**
     * Sets the idaux.
     * 
     * @param idaux the new idaux
     */
    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    /**
     * Gets the month i.
     * 
     * @return the month i
     */
    public int getMonthI() {
        return monthI;
    }

    /**
     * Sets the month i.
     * 
     * @param monthI the new month i
     */
    public void setMonthI(int monthI) {
        this.monthI = monthI;
    }

    /**
     * Gets the year i.
     * 
     * @return the year i
     */
    public int getYearI() {
        return yearI;
    }

    /**
     * Sets the year i.
     * 
     * @param yearI the new year i
     */
    public void setYearI(int yearI) {
        this.yearI = yearI;
    }

    /**
     * Gets the day f.
     * 
     * @return the day f
     */
    public int getDayF() {
        return dayF;
    }

    /**
     * Sets the day f.
     * 
     * @param dayF the new day f
     */
    public void setDayF(int dayF) {
        this.dayF = dayF;
    }

    /**
     * Gets the month f.
     * 
     * @return the month f
     */
    public int getMonthF() {
        return monthF;
    }

    /**
     * Sets the month f.
     * 
     * @param monthF the new month f
     */
    public void setMonthF(int monthF) {
        this.monthF = monthF;
    }

    /**
     * Gets the year f.
     * 
     * @return the year f
     */
    public int getYearF() {
        return yearF;
    }

    /**
     * Sets the year f.
     * 
     * @param yearF the new year f
     */
    public void setYearF(int yearF) {
        this.yearF = yearF;
    }

    /**
     * Gets the site.
     * 
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * Sets the site.
     * 
     * @param site the new site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * Gets the user types.
     * 
     * @return the user types
     */
    public HashMap getUserTypes() {
        return userTypes;
    }
    
    /**
     * Sets the user types.
     * 
     * @param userTypes the new user types
     */
    public void setUserTypes(HashMap userTypes) {
        this.userTypes = userTypes;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
     * Gets the user language.
     * 
     * @return the userLanguage
     */
    public String getUserLanguage() {
        return userLanguage;
    }

    /**
     * Sets the user language.
     * 
     * @param userLanguage the userLanguage to set
     */
    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    /**
     * Checks if is grouped dates.
     * 
     * @return the groupedDates
     */
    public boolean isGroupedDates() {
        return groupedDates;
    }

    /**
     * Sets the grouped dates.
     * 
     * @param groupedDates the groupedDates to set
     */
    public void setGroupedDates(boolean groupedDates) {
        this.groupedDates = groupedDates;
    }

    /**
     * Gets the date i.
     * 
     * @return the date i
     */
    public Date getDateI() {
        GregorianCalendar d = new GregorianCalendar(yearI, monthI-1, dayI);
        return d.getTime();
    }

    /**
     * Gets the date f.
     * 
     * @return the date f
     */
    public Date getDateF() {
        GregorianCalendar d = new GregorianCalendar(yearF, monthF-1, dayF);
        return d.getTime();
    }
}
