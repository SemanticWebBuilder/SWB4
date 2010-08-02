package org.semanticwb.portal.resources.sem.videolibrary.base;


public abstract class VideoContentBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.model.Tagable
{
    public static final org.semanticwb.platform.SemanticProperty video_code=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#code");
    public static final org.semanticwb.platform.SemanticProperty video_originalTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#originalTitle");
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
    public static final org.semanticwb.platform.SemanticProperty video_homeShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#homeShow");
    public static final org.semanticwb.platform.SemanticProperty video_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#videoWebPage");
    public static final org.semanticwb.platform.SemanticProperty video_publishDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#publishDate");
    public static final org.semanticwb.platform.SemanticProperty video_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#source");
    public static final org.semanticwb.platform.SemanticProperty video_duration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#duration");
    public static final org.semanticwb.platform.SemanticClass video_VideoContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#VideoContent");

    public VideoContentBase()
    {
    }

    public VideoContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getCode()
    {
        return getSemanticObject().getProperty(video_code);
    }

    public void setCode(String value)
    {
        getSemanticObject().setProperty(video_code, value);
    }

    public String getOriginalTitle()
    {
        return getSemanticObject().getProperty(video_originalTitle);
    }

    public void setOriginalTitle(String value)
    {
        getSemanticObject().setProperty(video_originalTitle, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public boolean isHomeShow()
    {
        return getSemanticObject().getBooleanProperty(video_homeShow);
    }

    public void setHomeShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(video_homeShow, value);
    }

    public String getVideoWebPage()
    {
        return getSemanticObject().getProperty(video_videoWebPage);
    }

    public void setVideoWebPage(String value)
    {
        getSemanticObject().setProperty(video_videoWebPage, value);
    }

    public java.util.Date getPublishDate()
    {
        return getSemanticObject().getDateProperty(video_publishDate);
    }

    public void setPublishDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(video_publishDate, value);
    }

    public String getSource()
    {
        return getSemanticObject().getProperty(video_source);
    }

    public void setSource(String value)
    {
        getSemanticObject().setProperty(video_source, value);
    }

    public int getDuration()
    {
        return getSemanticObject().getIntProperty(video_duration);
    }

    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(video_duration, value);
    }
}
