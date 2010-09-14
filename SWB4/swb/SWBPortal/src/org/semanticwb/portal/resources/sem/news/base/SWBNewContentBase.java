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
package org.semanticwb.portal.resources.sem.news.base;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBNewContentBase.
 */
public abstract class SWBNewContentBase extends org.semanticwb.portal.resources.sem.HTMLContent implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    
    /** The Constant swb_VersionInfo. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    
    /** The Constant swb_actualVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    
    /** The Constant swbnews_sendTwitter. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_sendTwitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sendTwitter");
    
    /** The Constant swb_lastVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    
    /** The Constant swbnews_originalTitle. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#originalTitle");
    
    /** The Constant swbnews_image. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#image");
    
    /** The Constant swbnews_homeShow. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#homeShow");
    
    /** The Constant swbnews_publishDate. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#publishDate");
    
    /** The Constant swb_Country. */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    
    /** The Constant swbnews_country. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#country");
    
    /** The Constant swbnews_source. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#source");
    
    /** The Constant swbnews_author. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#author");
    
    /** The Constant swbnews_sourceURL. */
    public static final org.semanticwb.platform.SemanticProperty swbnews_sourceURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sourceURL");
    
    /** The Constant swbnews_SWBNewContent. */
    public static final org.semanticwb.platform.SemanticClass swbnews_SWBNewContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");

    /**
     * Instantiates a new sWB new content base.
     */
    public SWBNewContentBase()
    {
    }

    /**
     * Instantiates a new sWB new content base.
     * 
     * @param base the base
     */
    public SWBNewContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#setActualVersion(org.semanticwb.model.VersionInfo)
     */
    public void setActualVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_actualVersion, value.getSemanticObject());
        }else
        {
            removeActualVersion();
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#removeActualVersion()
     */
    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#getActualVersion()
     */
    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Checks if is send twitter.
     * 
     * @return true, if is send twitter
     */
    public boolean isSendTwitter()
    {
        return getSemanticObject().getBooleanProperty(swbnews_sendTwitter);
    }

    /**
     * Sets the send twitter.
     * 
     * @param value the new send twitter
     */
    public void setSendTwitter(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_sendTwitter, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#setLastVersion(org.semanticwb.model.VersionInfo)
     */
    public void setLastVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_lastVersion, value.getSemanticObject());
        }else
        {
            removeLastVersion();
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#removeLastVersion()
     */
    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.sem.base.HTMLContentBase#getLastVersion()
     */
    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the original title.
     * 
     * @return the original title
     */
    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(swbnews_originalTitle);
    }

    /**
     * Sets the original title.
     * 
     * @param value the new original title
     */
    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(swbnews_originalTitle, value);
    }

    /**
     * Gets the image.
     * 
     * @return the image
     */
    public String getImage()
    {
        return getSemanticObject().getProperty(swbnews_image);
    }

    /**
     * Sets the image.
     * 
     * @param value the new image
     */
    public void setImage(String value)
    {
        getSemanticObject().setProperty(swbnews_image, value);
    }

    /**
     * Checks if is home show.
     * 
     * @return true, if is home show
     */
    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(swbnews_homeShow);
    }

    /**
     * Sets the home show.
     * 
     * @param value the new home show
     */
    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_homeShow, value);
    }

    /**
     * Gets the publish date.
     * 
     * @return the publish date
     */
    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(swbnews_publishDate);
    }

    /**
     * Sets the publish date.
     * 
     * @param value the new publish date
     */
    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbnews_publishDate, value);
    }

    /**
     * Sets the country.
     * 
     * @param value the new country
     */
    public void setCountry(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swbnews_country, value.getSemanticObject());
        }else
        {
            removeCountry();
        }
    }

    /**
     * Removes the country.
     */
    public void removeCountry()
    {
        getSemanticObject().removeProperty(swbnews_country);
    }

    /**
     * Gets the country.
     * 
     * @return the country
     */
    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbnews_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the source.
     * 
     * @return the source
     */
    public String getSource()
    {
        return getSemanticObject().getProperty(swbnews_source);
    }

    /**
     * Sets the source.
     * 
     * @param value the new source
     */
    public void setSource(String value)
    {
        getSemanticObject().setProperty(swbnews_source, value);
    }

    /**
     * Gets the author.
     * 
     * @return the author
     */
    public String getAuthor()
    {
        return getSemanticObject().getProperty(swbnews_author);
    }

    /**
     * Sets the author.
     * 
     * @param value the new author
     */
    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(swbnews_author, value);
    }

    /**
     * Gets the source url.
     * 
     * @return the source url
     */
    public String getSourceURL()
    {
        return getSemanticObject().getProperty(swbnews_sourceURL);
    }

    /**
     * Sets the source url.
     * 
     * @param value the new source url
     */
    public void setSourceURL(String value)
    {
        getSemanticObject().setProperty(swbnews_sourceURL, value);
    }
}
