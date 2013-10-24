package org.semanticwb.portal.resources.sem.base;


public abstract class HTMLContentBase extends org.semanticwb.portal.resources.sem.Content implements org.semanticwb.model.ResourceVersionable,org.semanticwb.model.Versionable
{
    public static final org.semanticwb.platform.SemanticProperty swbres_contentPaginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#contentPaginated");
    public static final org.semanticwb.platform.SemanticProperty swbres_nextLinkText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#nextLinkText");
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swbres_formerLinkText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#formerLinkText");
    public static final org.semanticwb.platform.SemanticProperty swbres_deleteStyles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#deleteStyles");
    public static final org.semanticwb.platform.SemanticProperty swbres_fontStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#fontStyle");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticClass swbres_HTMLContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    public HTMLContentBase()
    {
    }

   /**
   * Constructs a HTMLContentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the HTMLContent
   */
    public HTMLContentBase(org.semanticwb.platform.SemanticObject base)
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
}
