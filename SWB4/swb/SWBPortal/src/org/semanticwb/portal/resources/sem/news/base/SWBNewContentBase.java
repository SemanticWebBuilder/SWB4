package org.semanticwb.portal.resources.sem.news.base;


public abstract class SWBNewContentBase extends org.semanticwb.portal.resources.sem.HTMLContent implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    public static final org.semanticwb.platform.SemanticProperty swbnews_sourceURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sourceURL");
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swbnews_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#publishDate");
    public static final org.semanticwb.platform.SemanticProperty swbnews_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#homeShow");
    public static final org.semanticwb.platform.SemanticProperty swbnews_publishTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#publishTime");
    public static final org.semanticwb.platform.SemanticProperty swbnews_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#image");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    public static final org.semanticwb.platform.SemanticProperty swbnews_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#country");
    public static final org.semanticwb.platform.SemanticProperty swbres_contentPaginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#contentPaginated");
    public static final org.semanticwb.platform.SemanticProperty swbres_nextLinkText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#nextLinkText");
    public static final org.semanticwb.platform.SemanticProperty swbnews_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#author");
    public static final org.semanticwb.platform.SemanticProperty swbnews_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#originalTitle");
    public static final org.semanticwb.platform.SemanticProperty swbres_formerLinkText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#formerLinkText");
    public static final org.semanticwb.platform.SemanticProperty swbres_deleteStyles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#deleteStyles");
    public static final org.semanticwb.platform.SemanticProperty swbres_fontStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#fontStyle");
    public static final org.semanticwb.platform.SemanticProperty swbnews_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#source");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticProperty swbnews_sendTwitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sendTwitter");
    public static final org.semanticwb.platform.SemanticClass swbnews_SWBNewContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");

    public SWBNewContentBase()
    {
    }

   /**
   * Constructs a SWBNewContentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBNewContent
   */
    public SWBNewContentBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the SourceURL property
* @return String with the SourceURL
*/
    public String getSourceURL()
    {
        return getSemanticObject().getProperty(swbnews_sourceURL);
    }

/**
* Sets the SourceURL property
* @param value long with the SourceURL
*/
    public void setSourceURL(String value)
    {
        getSemanticObject().setProperty(swbnews_sourceURL, value);
    }
   /**
   * Sets the value for the property ActualVersion
   * @param value ActualVersion to set
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
   /**
   * Remove the value for ActualVersion property
   */

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

   /**
   * Gets the ActualVersion
   * @return a org.semanticwb.model.VersionInfo
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
* Gets the PublishDate property
* @return java.util.Date with the PublishDate
*/
    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(swbnews_publishDate);
    }

/**
* Sets the PublishDate property
* @param value long with the PublishDate
*/
    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbnews_publishDate, value);
    }

/**
* Gets the HomeShow property
* @return boolean with the HomeShow
*/
    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(swbnews_homeShow);
    }

/**
* Sets the HomeShow property
* @param value long with the HomeShow
*/
    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_homeShow, value);
    }

/**
* Gets the PublishTime property
* @return java.util.Date with the PublishTime
*/
    public java.util.Date getPublishTime()
    {
        return getSemanticObject().getDateProperty(swbnews_publishTime);
    }

/**
* Sets the PublishTime property
* @param value long with the PublishTime
*/
    public void setPublishTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbnews_publishTime, value);
    }

/**
* Gets the Image property
* @return String with the Image
*/
    public String getImage()
    {
        return getSemanticObject().getProperty(swbnews_image);
    }

/**
* Sets the Image property
* @param value long with the Image
*/
    public void setImage(String value)
    {
        getSemanticObject().setProperty(swbnews_image, value);
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
   * Sets the value for the property Country
   * @param value Country to set
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
   * Remove the value for Country property
   */

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swbnews_country);
    }

   /**
   * Gets the Country
   * @return a org.semanticwb.model.Country
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
* Gets the ContentPaginated property
* @return boolean with the ContentPaginated
*/
    public boolean isContentPaginated()
    {
        return getSemanticObject().getBooleanProperty(swbres_contentPaginated);
    }

/**
* Sets the ContentPaginated property
* @param value long with the ContentPaginated
*/
    public void setContentPaginated(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_contentPaginated, value);
    }

/**
* Gets the NextLinkText property
* @return String with the NextLinkText
*/
    public String getNextLinkText()
    {
        return getSemanticObject().getProperty(swbres_nextLinkText);
    }

/**
* Sets the NextLinkText property
* @param value long with the NextLinkText
*/
    public void setNextLinkText(String value)
    {
        getSemanticObject().setProperty(swbres_nextLinkText, value);
    }

/**
* Gets the Author property
* @return String with the Author
*/
    public String getAuthor()
    {
        return getSemanticObject().getProperty(swbnews_author);
    }

/**
* Sets the Author property
* @param value long with the Author
*/
    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(swbnews_author, value);
    }

/**
* Gets the OriginalTitle property
* @return String with the OriginalTitle
*/
    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(swbnews_originalTitle);
    }

/**
* Sets the OriginalTitle property
* @param value long with the OriginalTitle
*/
    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(swbnews_originalTitle, value);
    }

/**
* Gets the FormerLinkText property
* @return String with the FormerLinkText
*/
    public String getFormerLinkText()
    {
        return getSemanticObject().getProperty(swbres_formerLinkText);
    }

/**
* Sets the FormerLinkText property
* @param value long with the FormerLinkText
*/
    public void setFormerLinkText(String value)
    {
        getSemanticObject().setProperty(swbres_formerLinkText, value);
    }

/**
* Gets the DeleteStyles property
* @return boolean with the DeleteStyles
*/
    public boolean isDeleteStyles()
    {
        return getSemanticObject().getBooleanProperty(swbres_deleteStyles);
    }

/**
* Sets the DeleteStyles property
* @param value long with the DeleteStyles
*/
    public void setDeleteStyles(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_deleteStyles, value);
    }

/**
* Gets the FontStyle property
* @return String with the FontStyle
*/
    public String getFontStyle()
    {
        return getSemanticObject().getProperty(swbres_fontStyle);
    }

/**
* Sets the FontStyle property
* @param value long with the FontStyle
*/
    public void setFontStyle(String value)
    {
        getSemanticObject().setProperty(swbres_fontStyle, value);
    }

/**
* Gets the Source property
* @return String with the Source
*/
    public String getSource()
    {
        return getSemanticObject().getProperty(swbnews_source);
    }

/**
* Sets the Source property
* @param value long with the Source
*/
    public void setSource(String value)
    {
        getSemanticObject().setProperty(swbnews_source, value);
    }
   /**
   * Sets the value for the property LastVersion
   * @param value LastVersion to set
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
   /**
   * Remove the value for LastVersion property
   */

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

   /**
   * Gets the LastVersion
   * @return a org.semanticwb.model.VersionInfo
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
* Gets the SendTwitter property
* @return boolean with the SendTwitter
*/
    public boolean isSendTwitter()
    {
        return getSemanticObject().getBooleanProperty(swbnews_sendTwitter);
    }

/**
* Sets the SendTwitter property
* @param value long with the SendTwitter
*/
    public void setSendTwitter(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_sendTwitter, value);
    }
}
