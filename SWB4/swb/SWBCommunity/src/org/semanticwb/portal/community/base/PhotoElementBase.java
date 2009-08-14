package org.semanticwb.portal.community.base;


public class PhotoElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Viewable,org.semanticwb.model.Traceable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#width");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_imageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#imageURL");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbHeight");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#height");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_photoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photoWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbWidth");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");

    public PhotoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
        return org.semanticwb.portal.community.PhotoElement.createPhotoElement(String.valueOf(id), model);
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

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swbcomm_width);
    }

    public void setWidth(int width)
    {
        getSemanticObject().setIntProperty(swbcomm_width, width);
    }

    public String getImageURL()
    {
        return getSemanticObject().getProperty(swbcomm_imageURL);
    }

    public void setImageURL(String imageURL)
    {
        getSemanticObject().setProperty(swbcomm_imageURL, imageURL);
    }

    public int getThumbHeight()
    {
        return getSemanticObject().getIntProperty(swbcomm_thumbHeight);
    }

    public void setThumbHeight(int thumbHeight)
    {
        getSemanticObject().setIntProperty(swbcomm_thumbHeight, thumbHeight);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swbcomm_height);
    }

    public void setHeight(int height)
    {
        getSemanticObject().setIntProperty(swbcomm_height, height);
    }

    public void setPhotoWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swbcomm_photoWebPage, webpage.getSemanticObject());
    }

    public void removePhotoWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_photoWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_photoWebPage, photowebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(photowebpage.getSemanticObject().getModel().listSubjects(swbcomm_photoWebPage,photowebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getPhotoWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_photoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getThumbWidth()
    {
        return getSemanticObject().getIntProperty(swbcomm_thumbWidth);
    }

    public void setThumbWidth(int thumbWidth)
    {
        getSemanticObject().setIntProperty(swbcomm_thumbWidth, thumbWidth);
    }
}
