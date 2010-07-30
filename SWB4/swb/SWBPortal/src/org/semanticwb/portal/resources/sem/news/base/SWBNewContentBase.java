package org.semanticwb.portal.resources.sem.news.base;


public abstract class SWBNewContentBase extends org.semanticwb.portal.resources.sem.HTMLContent implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swbnews_sendTwitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sendTwitter");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticProperty swbnews_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#originalTitle");
    public static final org.semanticwb.platform.SemanticProperty swbnews_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#image");
    public static final org.semanticwb.platform.SemanticProperty swbnews_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#homeShow");
    public static final org.semanticwb.platform.SemanticProperty swbnews_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#publishDate");
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    public static final org.semanticwb.platform.SemanticProperty swbnews_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#country");
    public static final org.semanticwb.platform.SemanticProperty swbnews_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#source");
    public static final org.semanticwb.platform.SemanticProperty swbnews_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#author");
    public static final org.semanticwb.platform.SemanticProperty swbnews_sourceURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#sourceURL");
    public static final org.semanticwb.platform.SemanticClass swbnews_SWBNewContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNewContent");

    public SWBNewContentBase()
    {
    }

    public SWBNewContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

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

    public boolean isSendTwitter()
    {
        return getSemanticObject().getBooleanProperty(swbnews_sendTwitter);
    }

    public void setSendTwitter(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_sendTwitter, value);
    }

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

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

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

    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(swbnews_originalTitle);
    }

    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(swbnews_originalTitle, value);
    }

    public String getImage()
    {
        return getSemanticObject().getProperty(swbnews_image);
    }

    public void setImage(String value)
    {
        getSemanticObject().setProperty(swbnews_image, value);
    }

    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(swbnews_homeShow);
    }

    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_homeShow, value);
    }

    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(swbnews_publishDate);
    }

    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbnews_publishDate, value);
    }

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

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swbnews_country);
    }

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

    public String getSource()
    {
        return getSemanticObject().getProperty(swbnews_source);
    }

    public void setSource(String value)
    {
        getSemanticObject().setProperty(swbnews_source, value);
    }

    public String getAuthor()
    {
        return getSemanticObject().getProperty(swbnews_author);
    }

    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(swbnews_author, value);
    }

    public String getSourceURL()
    {
        return getSemanticObject().getProperty(swbnews_sourceURL);
    }

    public void setSourceURL(String value)
    {
        getSemanticObject().setProperty(swbnews_sourceURL, value);
    }
}
