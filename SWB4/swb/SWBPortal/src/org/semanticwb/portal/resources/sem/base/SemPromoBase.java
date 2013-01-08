package org.semanticwb.portal.resources.sem.base;


public abstract class SemPromoBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbres_promDefaultTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promDefaultTemplate");
    public static final org.semanticwb.platform.SemanticProperty swbres_promTextStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promTextStyle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promTitleStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promTitleStyle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promSubtitleStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promSubtitleStyle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promText");
    public static final org.semanticwb.platform.SemanticProperty swbres_promLinkText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promLinkText");
    public static final org.semanticwb.platform.SemanticProperty swbres_promTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promTitle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promFooter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promFooter");
    public static final org.semanticwb.platform.SemanticProperty swbres_promImgPosition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promImgPosition");
    public static final org.semanticwb.platform.SemanticProperty swbres_promImgHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promImgHeight");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swbres_promBulletHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promBulletHeight");
    public static final org.semanticwb.platform.SemanticProperty swbres_promCssClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promCssClass");
    public static final org.semanticwb.platform.SemanticProperty swbres_promFooterStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promFooterStyle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promLink");
    public static final org.semanticwb.platform.SemanticProperty swbres_promImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promImage");
    public static final org.semanticwb.platform.SemanticProperty swbres_promBulletWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promBulletWidth");
    public static final org.semanticwb.platform.SemanticProperty swbres_promLinkStyle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promLinkStyle");
    public static final org.semanticwb.platform.SemanticProperty swbres_promChangeTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promChangeTemplate");
    public static final org.semanticwb.platform.SemanticProperty swbres_promInternalUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promInternalUrl");
    public static final org.semanticwb.platform.SemanticProperty swbres_promTextColor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promTextColor");
    public static final org.semanticwb.platform.SemanticProperty swbres_promUnderlineLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promUnderlineLink");
    public static final org.semanticwb.platform.SemanticProperty swbres_promOpenNewWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promOpenNewWindow");
    public static final org.semanticwb.platform.SemanticProperty swbres_promImgWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promImgWidth");
    public static final org.semanticwb.platform.SemanticProperty swbres_promSubtitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/resource/ontology#promSubtitle");
    public static final org.semanticwb.platform.SemanticClass swbres_SemPromo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#semPromo");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#semPromo");

    public SemPromoBase()
    {
    }

   /**
   * Constructs a SemPromoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SemPromo
   */
    public SemPromoBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the PromDefaultTemplate property
* @return boolean with the PromDefaultTemplate
*/
    public boolean isPromDefaultTemplate()
    {
        return getSemanticObject().getBooleanProperty(swbres_promDefaultTemplate);
    }

/**
* Sets the PromDefaultTemplate property
* @param value long with the PromDefaultTemplate
*/
    public void setPromDefaultTemplate(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_promDefaultTemplate, value);
    }

/**
* Gets the PromTextStyle property
* @return String with the PromTextStyle
*/
    public String getPromTextStyle()
    {
        return getSemanticObject().getProperty(swbres_promTextStyle);
    }

/**
* Sets the PromTextStyle property
* @param value long with the PromTextStyle
*/
    public void setPromTextStyle(String value)
    {
        getSemanticObject().setProperty(swbres_promTextStyle, value);
    }

/**
* Gets the PromTitleStyle property
* @return String with the PromTitleStyle
*/
    public String getPromTitleStyle()
    {
        return getSemanticObject().getProperty(swbres_promTitleStyle);
    }

/**
* Sets the PromTitleStyle property
* @param value long with the PromTitleStyle
*/
    public void setPromTitleStyle(String value)
    {
        getSemanticObject().setProperty(swbres_promTitleStyle, value);
    }

/**
* Gets the PromSubtitleStyle property
* @return String with the PromSubtitleStyle
*/
    public String getPromSubtitleStyle()
    {
        return getSemanticObject().getProperty(swbres_promSubtitleStyle);
    }

/**
* Sets the PromSubtitleStyle property
* @param value long with the PromSubtitleStyle
*/
    public void setPromSubtitleStyle(String value)
    {
        getSemanticObject().setProperty(swbres_promSubtitleStyle, value);
    }

/**
* Gets the PromText property
* @return String with the PromText
*/
    public String getPromText()
    {
        return getSemanticObject().getProperty(swbres_promText);
    }

/**
* Sets the PromText property
* @param value long with the PromText
*/
    public void setPromText(String value)
    {
        getSemanticObject().setProperty(swbres_promText, value);
    }

/**
* Gets the PromLinkText property
* @return String with the PromLinkText
*/
    public String getPromLinkText()
    {
        return getSemanticObject().getProperty(swbres_promLinkText);
    }

/**
* Sets the PromLinkText property
* @param value long with the PromLinkText
*/
    public void setPromLinkText(String value)
    {
        getSemanticObject().setProperty(swbres_promLinkText, value);
    }

/**
* Gets the PromTitle property
* @return String with the PromTitle
*/
    public String getPromTitle()
    {
        return getSemanticObject().getProperty(swbres_promTitle);
    }

/**
* Sets the PromTitle property
* @param value long with the PromTitle
*/
    public void setPromTitle(String value)
    {
        getSemanticObject().setProperty(swbres_promTitle, value);
    }

/**
* Gets the PromFooter property
* @return String with the PromFooter
*/
    public String getPromFooter()
    {
        return getSemanticObject().getProperty(swbres_promFooter);
    }

/**
* Sets the PromFooter property
* @param value long with the PromFooter
*/
    public void setPromFooter(String value)
    {
        getSemanticObject().setProperty(swbres_promFooter, value);
    }

/**
* Gets the PromImgPosition property
* @return String with the PromImgPosition
*/
    public String getPromImgPosition()
    {
        return getSemanticObject().getProperty(swbres_promImgPosition);
    }

/**
* Sets the PromImgPosition property
* @param value long with the PromImgPosition
*/
    public void setPromImgPosition(String value)
    {
        getSemanticObject().setProperty(swbres_promImgPosition, value);
    }

/**
* Gets the PromImgHeight property
* @return int with the PromImgHeight
*/
    public int getPromImgHeight()
    {
        return getSemanticObject().getIntProperty(swbres_promImgHeight);
    }

/**
* Sets the PromImgHeight property
* @param value long with the PromImgHeight
*/
    public void setPromImgHeight(int value)
    {
        getSemanticObject().setIntProperty(swbres_promImgHeight, value);
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
* Gets the PromBulletHeight property
* @return int with the PromBulletHeight
*/
    public int getPromBulletHeight()
    {
        return getSemanticObject().getIntProperty(swbres_promBulletHeight);
    }

/**
* Sets the PromBulletHeight property
* @param value long with the PromBulletHeight
*/
    public void setPromBulletHeight(int value)
    {
        getSemanticObject().setIntProperty(swbres_promBulletHeight, value);
    }

/**
* Gets the PromCssClass property
* @return String with the PromCssClass
*/
    public String getPromCssClass()
    {
        return getSemanticObject().getProperty(swbres_promCssClass);
    }

/**
* Sets the PromCssClass property
* @param value long with the PromCssClass
*/
    public void setPromCssClass(String value)
    {
        getSemanticObject().setProperty(swbres_promCssClass, value);
    }

/**
* Gets the PromFooterStyle property
* @return String with the PromFooterStyle
*/
    public String getPromFooterStyle()
    {
        return getSemanticObject().getProperty(swbres_promFooterStyle);
    }

/**
* Sets the PromFooterStyle property
* @param value long with the PromFooterStyle
*/
    public void setPromFooterStyle(String value)
    {
        getSemanticObject().setProperty(swbres_promFooterStyle, value);
    }

/**
* Gets the PromLink property
* @return String with the PromLink
*/
    public String getPromLink()
    {
        return getSemanticObject().getProperty(swbres_promLink);
    }

/**
* Sets the PromLink property
* @param value long with the PromLink
*/
    public void setPromLink(String value)
    {
        getSemanticObject().setProperty(swbres_promLink, value);
    }

/**
* Gets the PromImage property
* @return String with the PromImage
*/
    public String getPromImage()
    {
        return getSemanticObject().getProperty(swbres_promImage);
    }

/**
* Sets the PromImage property
* @param value long with the PromImage
*/
    public void setPromImage(String value)
    {
        getSemanticObject().setProperty(swbres_promImage, value);
    }

/**
* Gets the PromBulletWidth property
* @return int with the PromBulletWidth
*/
    public int getPromBulletWidth()
    {
        return getSemanticObject().getIntProperty(swbres_promBulletWidth);
    }

/**
* Sets the PromBulletWidth property
* @param value long with the PromBulletWidth
*/
    public void setPromBulletWidth(int value)
    {
        getSemanticObject().setIntProperty(swbres_promBulletWidth, value);
    }

/**
* Gets the PromLinkStyle property
* @return String with the PromLinkStyle
*/
    public String getPromLinkStyle()
    {
        return getSemanticObject().getProperty(swbres_promLinkStyle);
    }

/**
* Sets the PromLinkStyle property
* @param value long with the PromLinkStyle
*/
    public void setPromLinkStyle(String value)
    {
        getSemanticObject().setProperty(swbres_promLinkStyle, value);
    }

/**
* Gets the PromChangeTemplate property
* @return String with the PromChangeTemplate
*/
    public String getPromChangeTemplate()
    {
        return getSemanticObject().getProperty(swbres_promChangeTemplate);
    }

/**
* Sets the PromChangeTemplate property
* @param value long with the PromChangeTemplate
*/
    public void setPromChangeTemplate(String value)
    {
        getSemanticObject().setProperty(swbres_promChangeTemplate, value);
    }

/**
* Gets the PromInternalUrl property
* @return String with the PromInternalUrl
*/
    public String getPromInternalUrl()
    {
        return getSemanticObject().getProperty(swbres_promInternalUrl);
    }

/**
* Sets the PromInternalUrl property
* @param value long with the PromInternalUrl
*/
    public void setPromInternalUrl(String value)
    {
        getSemanticObject().setProperty(swbres_promInternalUrl, value);
    }

/**
* Gets the PromTextColor property
* @return String with the PromTextColor
*/
    public String getPromTextColor()
    {
        return getSemanticObject().getProperty(swbres_promTextColor);
    }

/**
* Sets the PromTextColor property
* @param value long with the PromTextColor
*/
    public void setPromTextColor(String value)
    {
        getSemanticObject().setProperty(swbres_promTextColor, value);
    }

/**
* Gets the PromUnderlineLink property
* @return boolean with the PromUnderlineLink
*/
    public boolean isPromUnderlineLink()
    {
        return getSemanticObject().getBooleanProperty(swbres_promUnderlineLink);
    }

/**
* Sets the PromUnderlineLink property
* @param value long with the PromUnderlineLink
*/
    public void setPromUnderlineLink(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_promUnderlineLink, value);
    }

/**
* Gets the PromOpenNewWindow property
* @return boolean with the PromOpenNewWindow
*/
    public boolean isPromOpenNewWindow()
    {
        return getSemanticObject().getBooleanProperty(swbres_promOpenNewWindow);
    }

/**
* Sets the PromOpenNewWindow property
* @param value long with the PromOpenNewWindow
*/
    public void setPromOpenNewWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbres_promOpenNewWindow, value);
    }

/**
* Gets the PromImgWidth property
* @return int with the PromImgWidth
*/
    public int getPromImgWidth()
    {
        return getSemanticObject().getIntProperty(swbres_promImgWidth);
    }

/**
* Sets the PromImgWidth property
* @param value long with the PromImgWidth
*/
    public void setPromImgWidth(int value)
    {
        getSemanticObject().setIntProperty(swbres_promImgWidth, value);
    }

/**
* Gets the PromSubtitle property
* @return String with the PromSubtitle
*/
    public String getPromSubtitle()
    {
        return getSemanticObject().getProperty(swbres_promSubtitle);
    }

/**
* Sets the PromSubtitle property
* @param value long with the PromSubtitle
*/
    public void setPromSubtitle(String value)
    {
        getSemanticObject().setProperty(swbres_promSubtitle, value);
    }
}
