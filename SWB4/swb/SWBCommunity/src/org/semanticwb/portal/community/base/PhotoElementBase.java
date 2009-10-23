package org.semanticwb.portal.community.base;


public class PhotoElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#width");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_photoThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photoThumbnail");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_imageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#imageURL");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbHeight");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#height");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_photoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photoWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbWidth");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");

       public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(it, true);
       }

       public static org.semanticwb.portal.community.PhotoElement createPhotoElement(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.PhotoElement.ClassMgr.createPhotoElement(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.PhotoElement getPhotoElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.PhotoElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.PhotoElement createPhotoElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.PhotoElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removePhotoElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasPhotoElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getPhotoElement(id, model)!=null);
       }
    }

    public PhotoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_width);
    }

    public void setWidth(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_width, value);
    }

    public String getPhotoThumbnail()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_photoThumbnail);
    }

    public void setPhotoThumbnail(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_photoThumbnail, value);
    }

    public String getImageURL()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_imageURL);
    }

    public void setImageURL(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_imageURL, value);
    }

    public int getThumbHeight()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_thumbHeight);
    }

    public void setThumbHeight(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_thumbHeight, value);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_height);
    }

    public void setHeight(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_height, value);
    }

    public void setPhotoWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_photoWebPage, value.getSemanticObject());
    }

    public void removePhotoWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_photoWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_photoWebPage, photowebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(photowebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_photoWebPage,photowebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getPhotoWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_photoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getThumbWidth()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_thumbWidth);
    }

    public void setThumbWidth(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_thumbWidth, value);
    }
}
