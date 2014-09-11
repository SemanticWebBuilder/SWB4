package org.semanticwb.portal.resources.sem.videolibrary.base;


public abstract class VideoContentBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.model.Tagable
{
    public static final org.semanticwb.platform.SemanticProperty video_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#videoWebPage");
    public static final org.semanticwb.platform.SemanticProperty video_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#originalTitle");
    public static final org.semanticwb.platform.SemanticProperty video_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#publishDate");
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
    public static final org.semanticwb.platform.SemanticProperty video_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#homeShow");
    public static final org.semanticwb.platform.SemanticProperty video_publishTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#publishTime");
    public static final org.semanticwb.platform.SemanticProperty video_code=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#code");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty video_duration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#duration");
    public static final org.semanticwb.platform.SemanticProperty video_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#source");
    public static final org.semanticwb.platform.SemanticClass video_VideoContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");

    public VideoContentBase()
    {
    }

   /**
   * Constructs a VideoContentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the VideoContent
   */
    public VideoContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the VideoWebPage property
* @return String with the VideoWebPage
*/
    public String getVideoWebPage()
    {
        return getSemanticObject().getProperty(video_videoWebPage);
    }

/**
* Sets the VideoWebPage property
* @param value long with the VideoWebPage
*/
    public void setVideoWebPage(String value)
    {
        getSemanticObject().setProperty(video_videoWebPage, value);
    }

/**
* Gets the OriginalTitle property
* @return String with the OriginalTitle
*/
    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(video_originalTitle);
    }

/**
* Sets the OriginalTitle property
* @param value long with the OriginalTitle
*/
    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(video_originalTitle, value);
    }

/**
* Gets the PublishDate property
* @return java.util.Date with the PublishDate
*/
    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(video_publishDate);
    }

/**
* Sets the PublishDate property
* @param value long with the PublishDate
*/
    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(video_publishDate, value);
    }

/**
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
    }

/**
* Gets the HomeShow property
* @return boolean with the HomeShow
*/
    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(video_homeShow);
    }

/**
* Sets the HomeShow property
* @param value long with the HomeShow
*/
    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(video_homeShow, value);
    }

/**
* Gets the PublishTime property
* @return java.util.Date with the PublishTime
*/
    public java.util.Date getPublishTime()
    {
        return getSemanticObject().getDateProperty(video_publishTime);
    }

/**
* Sets the PublishTime property
* @param value long with the PublishTime
*/
    public void setPublishTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(video_publishTime, value);
    }

/**
* Gets the Code property
* @return String with the Code
*/
    public String getCode()
    {
        return getSemanticObject().getProperty(video_code);
    }

/**
* Sets the Code property
* @param value long with the Code
*/
    public void setCode(String value)
    {
        getSemanticObject().setProperty(video_code, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Duration property
* @return String with the Duration
*/
    public String getDuration()
    {
        return getSemanticObject().getProperty(video_duration);
    }

/**
* Sets the Duration property
* @param value long with the Duration
*/
    public void setDuration(String value)
    {
        getSemanticObject().setProperty(video_duration, value);
    }

/**
* Gets the Source property
* @return String with the Source
*/
    public String getSource()
    {
        return getSemanticObject().getProperty(video_source);
    }

/**
* Sets the Source property
* @param value long with the Source
*/
    public void setSource(String value)
    {
        getSemanticObject().setProperty(video_source, value);
    }
}
