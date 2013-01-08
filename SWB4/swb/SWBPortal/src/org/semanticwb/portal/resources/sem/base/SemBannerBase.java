package org.semanticwb.portal.resources.sem.base;


public abstract class SemBannerBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbres_banrOpenNewWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrOpenNewWindow");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrLongDescr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrLongDescr");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrExternalUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrExternalUrl");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrImgHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrImgHeight");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrImgWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrImgWidth");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrCode");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrInternalUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrInternalUrl");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrAction");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrAlterText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrAlterText");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrTitle");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrOrderingIndex=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrOrderingIndex");
    public static final org.semanticwb.platform.SemanticProperty swbres_banrImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#banrImage");
    public static final org.semanticwb.platform.SemanticClass swbres_SemBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#semBanner");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#semBanner");

    public SemBannerBase()
    {
    }

   /**
   * Constructs a SemBannerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SemBanner
   */
    public SemBannerBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the BanrOpenNewWindow property
* @return boolean with the BanrOpenNewWindow
*/
    public boolean isBanrOpenNewWindow()
    {
        return getSemanticObject().getBooleanProperty(swbres_banrOpenNewWindow);
    }

/**
* Sets the BanrOpenNewWindow property
* @param value long with the BanrOpenNewWindow
*/
    public void setBanrOpenNewWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_banrOpenNewWindow, value);
    }

/**
* Gets the BanrLongDescr property
* @return String with the BanrLongDescr
*/
    public String getBanrLongDescr()
    {
        return getSemanticObject().getProperty(swbres_banrLongDescr);
    }

/**
* Sets the BanrLongDescr property
* @param value long with the BanrLongDescr
*/
    public void setBanrLongDescr(String value)
    {
        getSemanticObject().setProperty(swbres_banrLongDescr, value);
    }

/**
* Gets the BanrExternalUrl property
* @return String with the BanrExternalUrl
*/
    public String getBanrExternalUrl()
    {
        return getSemanticObject().getProperty(swbres_banrExternalUrl);
    }

/**
* Sets the BanrExternalUrl property
* @param value long with the BanrExternalUrl
*/
    public void setBanrExternalUrl(String value)
    {
        getSemanticObject().setProperty(swbres_banrExternalUrl, value);
    }

/**
* Gets the BanrImgHeight property
* @return int with the BanrImgHeight
*/
    public int getBanrImgHeight()
    {
        return getSemanticObject().getIntProperty(swbres_banrImgHeight);
    }

/**
* Sets the BanrImgHeight property
* @param value long with the BanrImgHeight
*/
    public void setBanrImgHeight(int value)
    {
        getSemanticObject().setIntProperty(swbres_banrImgHeight, value);
    }

/**
* Gets the BanrImgWidth property
* @return int with the BanrImgWidth
*/
    public int getBanrImgWidth()
    {
        return getSemanticObject().getIntProperty(swbres_banrImgWidth);
    }

/**
* Sets the BanrImgWidth property
* @param value long with the BanrImgWidth
*/
    public void setBanrImgWidth(int value)
    {
        getSemanticObject().setIntProperty(swbres_banrImgWidth, value);
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
* Gets the BanrCode property
* @return String with the BanrCode
*/
    public String getBanrCode()
    {
        return getSemanticObject().getProperty(swbres_banrCode);
    }

/**
* Sets the BanrCode property
* @param value long with the BanrCode
*/
    public void setBanrCode(String value)
    {
        getSemanticObject().setProperty(swbres_banrCode, value);
    }

/**
* Gets the BanrInternalUrl property
* @return String with the BanrInternalUrl
*/
    public String getBanrInternalUrl()
    {
        return getSemanticObject().getProperty(swbres_banrInternalUrl);
    }

/**
* Sets the BanrInternalUrl property
* @param value long with the BanrInternalUrl
*/
    public void setBanrInternalUrl(String value)
    {
        getSemanticObject().setProperty(swbres_banrInternalUrl, value);
    }

/**
* Gets the BanrAction property
* @return String with the BanrAction
*/
    public String getBanrAction()
    {
        return getSemanticObject().getProperty(swbres_banrAction);
    }

/**
* Sets the BanrAction property
* @param value long with the BanrAction
*/
    public void setBanrAction(String value)
    {
        getSemanticObject().setProperty(swbres_banrAction, value);
    }

/**
* Gets the BanrAlterText property
* @return String with the BanrAlterText
*/
    public String getBanrAlterText()
    {
        return getSemanticObject().getProperty(swbres_banrAlterText);
    }

/**
* Sets the BanrAlterText property
* @param value long with the BanrAlterText
*/
    public void setBanrAlterText(String value)
    {
        getSemanticObject().setProperty(swbres_banrAlterText, value);
    }

/**
* Gets the BanrTitle property
* @return String with the BanrTitle
*/
    public String getBanrTitle()
    {
        return getSemanticObject().getProperty(swbres_banrTitle);
    }

/**
* Sets the BanrTitle property
* @param value long with the BanrTitle
*/
    public void setBanrTitle(String value)
    {
        getSemanticObject().setProperty(swbres_banrTitle, value);
    }

/**
* Gets the BanrOrderingIndex property
* @return int with the BanrOrderingIndex
*/
    public int getBanrOrderingIndex()
    {
        return getSemanticObject().getIntProperty(swbres_banrOrderingIndex);
    }

/**
* Sets the BanrOrderingIndex property
* @param value long with the BanrOrderingIndex
*/
    public void setBanrOrderingIndex(int value)
    {
        getSemanticObject().setIntProperty(swbres_banrOrderingIndex, value);
    }

/**
* Gets the BanrImage property
* @return String with the BanrImage
*/
    public String getBanrImage()
    {
        return getSemanticObject().getProperty(swbres_banrImage);
    }

/**
* Sets the BanrImage property
* @param value long with the BanrImage
*/
    public void setBanrImage(String value)
    {
        getSemanticObject().setProperty(swbres_banrImage, value);
    }
}
