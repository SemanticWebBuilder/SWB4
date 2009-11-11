package org.semanticwb.portal.community.base;


public class VideoElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Interactiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoType");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoDuration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoDuration");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoCode");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoPreview=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoPreview");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_videoSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#videoSize");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");

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
           return org.semanticwb.portal.community.VideoElement.ClassMgr.createVideoElement(String.valueOf(id), model);
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
    }

    public VideoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getType()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_videoType);
    }

    public void setType(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_videoType, value);
    }

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_videoWebPage, value.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_videoWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage videowebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_videoWebPage, videowebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.VideoElement> listVideoElementByWebPage(org.semanticwb.model.WebPage videowebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> it=new org.semanticwb.model.GenericIterator(videowebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_videoWebPage,videowebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_videoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getDuration()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_videoDuration);
    }

    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_videoDuration, value);
    }

    public String getCode()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_videoCode);
    }

    public void setCode(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_videoCode, value);
    }

    public String getPreview()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_videoPreview);
    }

    public void setPreview(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_videoPreview, value);
    }

    public int getSize()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_videoSize);
    }

    public void setSize(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_videoSize, value);
    }
}
