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
package org.semanticwb.portal.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/*import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;*/

/**
 * The Class SWBRecHit.
 */
public class SWBRecHit implements java.io.Serializable, Comparable<SWBRecHit>  {
    
    /** The date. */
    private Timestamp date;
    
    /** The iditem. */
    private int iditem;
    
    /** The item. */
    private String item;
    
    /** The topicmap. */
    private String topicmap;
    
    /** The section. */
    private String section;
    
    /** The type. */
    private int type;
    
    /** The hits. */
    private long hits;
    
    /** The year. */
    private int year;
    
    /** The month. */
    private String month;
    
    /** The day. */
    private int day;

    /** The locale. */
    private Locale locale;
    
    /** The gc. */
    private GregorianCalendar gc;
    
    /**
     * Instantiates a new sWB rec hit.
     */
    public SWBRecHit(){
    }
    
    //new SWBRecHit(date,rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getInt("hits"));
    /**
     * Instantiates a new sWB rec hit.
     * 
     * @param date the date
     * @param topicmap the topicmap
     * @param section the section
     * @param type the type
     * @param hits the hits
     */
    public SWBRecHit(Timestamp date, String topicmap, String section, int type, long hits){
        this(date, topicmap, section, type, hits, "es");
    }

    /**
     * Instantiates a new sWB rec hit.
     * 
     * @param date the date
     * @param topicmap the topicmap
     * @param section the section
     * @param type the type
     * @param hits the hits
     * @param language the language
     */
    public SWBRecHit(Timestamp date, String topicmap, String section, int type, long hits, String language){
        locale = new Locale(language);

        this.date = date;
        if(date!=null){
            gc = new GregorianCalendar(locale);
            gc.setTimeInMillis(date.getTime());
            this.setYear(gc.get(GregorianCalendar.YEAR));
            this.setMonth(Integer.toString(gc.get(GregorianCalendar.MONTH)));
            this.setDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
        }
        this.topicmap = topicmap;
        this.section = section;
        this.type = type;
        this.hits = hits;
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }  

    /**
     * Sets the date.
     * 
     * @param date the new date
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    /**
     * Gets the iditem.
     * 
     * @return the iditem
     */
    public int getIditem() {
        return iditem;
    }

    /**
     * Sets the iditem.
     * 
     * @param iditem the new iditem
     */
    public void setIditem(int iditem) {
        this.iditem = iditem;
    }
    
    /**
     * Gets the item.
     * 
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the item.
     * 
     * @param item the new item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Gets the topicmap.
     * 
     * @return the topicmap
     */
    public String getTopicmap() {
        return topicmap;
    }

    /**
     * Sets the topicmap.
     * 
     * @param topicmap the new topicmap
     */
    public void setTopicmap(String topicmap) {
        this.topicmap = topicmap;
    }

    /**
     * Gets the section.
     * 
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the section.
     * 
     * @param section the new section
     */
    public void setSection(String section) {
        this.section = section;
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
     * Gets the hits.
     * 
     * @return the hits
     */
    public long getHits() {
        return hits;
    }

    /**
     * Sets the hits.
     * 
     * @param hits the new hits
     */
    public void setHits(long hits) {
        this.hits = hits;
    }

    /**
     * Gets the year.
     * 
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year.
     * 
     * @param year the new year
     */
    private void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the month.
     * 
     * @return the month
     */
    public String getMonth() {
        SimpleDateFormat formatter  = new SimpleDateFormat("MMMM", locale);
        return formatter.format(date);
    }

    /**
     * Gets the month.
     * 
     * @param pattern the pattern
     * @return the month
     */
    public String getMonth(String pattern) {
        SimpleDateFormat formatter  = new SimpleDateFormat(pattern, locale);
        return formatter.format(date);
    }

    /**
     * Gets the month to int.
     * 
     * @return the month to int
     */
    public int getMonthToInt() {
        SimpleDateFormat formatter  = new SimpleDateFormat("M");
        try {
            return Integer.parseInt(formatter.format(date), 10);
        }catch(NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Sets the month.
     * 
     * @param month the new month
     */
    private void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the day.
     * 
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day.
     * 
     * @param day the new day
     */
    private void setDay(int day) {
        this.day = day;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("date="+date);
        sb.append(", iditem="+iditem);
        sb.append(", item="+item);
        sb.append(", topicmap="+topicmap);
        sb.append(", section="+section);
        sb.append(", type="+type);
        sb.append(", hits="+hits);
        sb.append(", year="+year);
        sb.append(", month="+month);
        sb.append(", day="+day);
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SWBRecHit o) {
        int compara = 0;
        compara = date.compareTo(o.getDate());
        if(compara!=0)
            return compara;
        compara = topicmap.compareToIgnoreCase(o.getTopicmap());
        if(compara!=0)
            return compara;
        compara = item.compareToIgnoreCase(o.getItem());
        return compara;
    }
}
