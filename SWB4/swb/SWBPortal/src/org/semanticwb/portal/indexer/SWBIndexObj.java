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
 * SWBIndexObj.java
 *
 * Created on 24 de mayo de 2006, 12:17 PM
 */

package org.semanticwb.portal.indexer;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBIndexObj
{
    public static final String TYPE_FILE="file";
    public static final String TYPE_TOPIC="topic";
    public static final String TYPE_CONTENT="content";
    public static final String TYPE_URL="url";
    public static final String TYPE_DATA="data";
    public static final String TYPE_HTML="html";
    public static final String TYPE_USER="user";
    
    private String url;
    private String type;
    private String tmid;
    private String tpid;
    private String resid;
    private String lang;
    private String id;
    private String title;
    private String summary;
    private String category;
    private String data;
    private float score;
    
    private int index;
    private int page;
    private int pindex;
    
    private boolean remove=false;
    
    /** Creates a new instance of WBIndexObj */
    public SWBIndexObj()
    {
    }
    
    /**
     * Getter for property url.
     * @return Value of property url.
     */
    public java.lang.String getUrl()
    {
        return url;
    }
    
    /**
     * Setter for property url.
     * @param url New value of property url.
     */
    public void setUrl(java.lang.String url)
    {
        this.url = url;
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getType()
    {
        return type;
    }
    
    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(java.lang.String type)
    {
        this.type = type;
    }
    
    /**
     * Getter for property tmid.
     * @return Value of property tmid.
     */
    public java.lang.String getTopicMapID()
    {
        return tmid;
    }
    
    /**
     * Setter for property tmid.
     * @param tmid New value of property tmid.
     */
    public void setTopicMapID(java.lang.String tmid)
    {
        this.tmid = tmid;
    }
    
    /**
     * Getter for property tpid.
     * @return Value of property tpid.
     */
    public java.lang.String getTopicID()
    {
        return tpid;
    }
    
    /**
     * Setter for property tpid.
     * @param tpid New value of property tpid.
     */
    public void setTopicID(java.lang.String tpid)
    {
        this.tpid = tpid;
    }
    
    /**
     * Getter for property lang.
     * @return Value of property lang.
     */
    public java.lang.String getLang()
    {
        return lang;
    }
    
    /**
     * Setter for property lang.
     * @param lang New value of property lang.
     */
    public void setLang(java.lang.String lang)
    {
        this.lang = lang;
    }    
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId()
    {
        return id;
    }
    
    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }
    
    /**
     * Getter for property title.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return title;
    }
    
    /**
     * Setter for property title.
     * @param title New value of property title.
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }
    
    /**
     * Getter for property summary.
     * @return Value of property summary.
     */
    public java.lang.String getSummary()
    {
        return summary;
    }
    
    /**
     * Setter for property summary.
     * @param summary New value of property summary.
     */
    public void setSummary(java.lang.String summary)
    {
        this.summary = summary;
    }
    
    /**
     * Getter for property category.
     * @return Value of property category.
     */
    public java.lang.String getCategory()
    {
        return category;
    }
    
    /**
     * Setter for property category.
     * @param category New value of property category.
     */
    public void setCategory(java.lang.String category)
    {
        this.category = category;
    }    
    
    /**
     * Getter for property data.
     * @return Value of property data.
     */
    public java.lang.String getData()
    {
        return data;
    }
    
    /**
     * Setter for property data.
     * @param data New value of property data.
     */
    public void setData(java.lang.String data)
    {
        this.data = data;
    }    
    
    public String toString()
    {
        return "id("+id+"), type("+type+")";
    }
    
    /**
     * Getter for property score.
     * @return Value of property score.
     */
    public float getScore()
    {
        return score;
    }    

    /**
     * Setter for property score.
     * @param score New value of property score.
     */
    public void setScore(float score)
    {
        this.score = score;
    }    
    
    protected void set4Remove(boolean remove)
    {
        this.remove=remove;
    }
    
    protected boolean is4Remove()
    {
        return remove;
    }
    
    /**
     * Getter for property page.
     * @return Value of property page.
     */
    public int getPage()
    {
        return page;
    }
    
    /**
     * Setter for property page.
     * @param page New value of property page.
     */
    public void setPage(int page)
    {
        this.page = page;
    }
    
    /**
     * Getter for property pindex.
     * @return Value of property pindex.
     */
    public int getPageIndex()
    {
        return pindex;
    }
    
    /**
     * Setter for property pindex.
     * @param pindex New value of property pindex.
     */
    public void setPageIndex(int pindex)
    {
        this.pindex = pindex;
    }
    
    /**
     * Getter for property index.
     * @return Value of property index.
     */
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Setter for property index.
     * @param index New value of property index.
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    
    /**
     * Getter for property resid.
     * @return Value of property resid.
     */
    public java.lang.String getResId()
    {
        return resid;
    }
    
    /**
     * Setter for property resid.
     * @param resid New value of property resid.
     */
    public void setResId(java.lang.String resid)
    {
        this.resid = resid;
    }
    
}
