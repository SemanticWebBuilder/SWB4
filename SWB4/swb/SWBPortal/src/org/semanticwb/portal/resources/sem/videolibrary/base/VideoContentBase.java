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
package org.semanticwb.portal.resources.sem.videolibrary.base;


// TODO: Auto-generated Javadoc
/**
 * The Class VideoContentBase.
 */
public abstract class VideoContentBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.model.Tagable
{
    
    /** The Constant video_code. */
    public static final org.semanticwb.platform.SemanticProperty video_code=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#code");
    
    /** The Constant video_originalTitle. */
    public static final org.semanticwb.platform.SemanticProperty video_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#originalTitle");
    
    /** The Constant swb_tags. */
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
    
    /** The Constant video_homeShow. */
    public static final org.semanticwb.platform.SemanticProperty video_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#homeShow");
    
    /** The Constant video_videoWebPage. */
    public static final org.semanticwb.platform.SemanticProperty video_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#videoWebPage");
    
    /** The Constant video_publishDate. */
    public static final org.semanticwb.platform.SemanticProperty video_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#publishDate");
    
    /** The Constant video_source. */
    public static final org.semanticwb.platform.SemanticProperty video_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#source");
    
    /** The Constant video_duration. */
    public static final org.semanticwb.platform.SemanticProperty video_duration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#duration");
    
    /** The Constant video_VideoContent. */
    public static final org.semanticwb.platform.SemanticClass video_VideoContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");

    /**
     * Instantiates a new video content base.
     */
    public VideoContentBase()
    {
    }

    /**
     * Instantiates a new video content base.
     * 
     * @param base the base
     */
    public VideoContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode()
    {
        return getSemanticObject().getProperty(video_code);
    }

    /**
     * Sets the code.
     * 
     * @param value the new code
     */
    public void setCode(String value)
    {
        getSemanticObject().setProperty(video_code, value);
    }

    /**
     * Gets the original title.
     * 
     * @return the original title
     */
    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(video_originalTitle);
    }

    /**
     * Sets the original title.
     * 
     * @param value the new original title
     */
    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(video_originalTitle, value);
    }

    /**
     * Gets the tags.
     * 
     * @return the tags
     */
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    /**
     * Sets the tags.
     * 
     * @param value the new tags
     */
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    /**
     * Checks if is home show.
     * 
     * @return true, if is home show
     */
    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(video_homeShow);
    }

    /**
     * Sets the home show.
     * 
     * @param value the new home show
     */
    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(video_homeShow, value);
    }

    /**
     * Gets the video web page.
     * 
     * @return the video web page
     */
    public String getVideoWebPage()
    {
        return getSemanticObject().getProperty(video_videoWebPage);
    }

    /**
     * Sets the video web page.
     * 
     * @param value the new video web page
     */
    public void setVideoWebPage(String value)
    {
        getSemanticObject().setProperty(video_videoWebPage, value);
    }

    /**
     * Gets the publish date.
     * 
     * @return the publish date
     */
    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(video_publishDate);
    }

    /**
     * Sets the publish date.
     * 
     * @param value the new publish date
     */
    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(video_publishDate, value);
    }

    /**
     * Gets the source.
     * 
     * @return the source
     */
    public String getSource()
    {
        return getSemanticObject().getProperty(video_source);
    }

    /**
     * Sets the source.
     * 
     * @param value the new source
     */
    public void setSource(String value)
    {
        getSemanticObject().setProperty(video_source, value);
    }

    /**
     * Gets the duration.
     * 
     * @return the duration
     */
    public int getDuration()
    {
        return getSemanticObject().getIntProperty(video_duration);
    }

    /**
     * Sets the duration.
     * 
     * @param value the new duration
     */
    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(video_duration, value);
    }
}
