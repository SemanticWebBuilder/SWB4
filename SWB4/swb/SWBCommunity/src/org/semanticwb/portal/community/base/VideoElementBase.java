package org.semanticwb.portal.community.base;


public class VideoElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoType");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoDuration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoDuration");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoCode");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoPreview=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoPreview");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_videoSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoSize");
    public static final org.semanticwb.platform.SemanticClass swbcomm_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");

    public VideoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElements(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElements()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement>(it, true);
    }

    public static org.semanticwb.portal.community.VideoElement createVideoElement(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.VideoElement.createVideoElement(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.VideoElement getVideoElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.VideoElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.VideoElement createVideoElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.VideoElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeVideoElement(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasVideoElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (getVideoElement(id, model)!=null);
    }

    public String getType()
    {
        return getSemanticObject().getProperty(swbcomm_videoType);
    }

    public void setType(String value)
    {
        getSemanticObject().setProperty(swbcomm_videoType, value);
    }

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_videoWebPage, value.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_videoWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage videowebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_videoWebPage, videowebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage videowebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> it=new org.semanticwb.model.GenericIterator(videowebpage.getSemanticObject().getModel().listSubjects(swbcomm_videoWebPage,videowebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_videoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getDuration()
    {
        return getSemanticObject().getIntProperty(swbcomm_videoDuration);
    }

    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_videoDuration, value);
    }

    public String getCode()
    {
        return getSemanticObject().getProperty(swbcomm_videoCode);
    }

    public void setCode(String value)
    {
        getSemanticObject().setProperty(swbcomm_videoCode, value);
    }

    public String getPreview()
    {
        return getSemanticObject().getProperty(swbcomm_videoPreview);
    }

    public void setPreview(String value)
    {
        getSemanticObject().setProperty(swbcomm_videoPreview, value);
    }

    public int getSize()
    {
        return getSemanticObject().getIntProperty(swbcomm_videoSize);
    }

    public void setSize(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_videoSize, value);
    }
}
