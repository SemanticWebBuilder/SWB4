package org.semanticwb.portal.community.base;


public class ProductElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_smallPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#smallPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_prodDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#prodDescription");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_promotion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#promotion");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_productWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#productWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_ProductElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");

       public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement>(it, true);
       }

       public static org.semanticwb.portal.community.ProductElement createProductElement(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.ProductElement.ClassMgr.createProductElement(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.ProductElement getProductElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.ProductElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.ProductElement createProductElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.ProductElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProductElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProductElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProductElement(id, model)!=null);
       }
    }

    public ProductElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getSmallPhoto()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_smallPhoto);
    }

    public void setSmallPhoto(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_smallPhoto, value);
    }

    public String getProdDescription()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_prodDescription);
    }

    public void setProdDescription(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_prodDescription, value);
    }

    public boolean isPromotion()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_promotion);
    }

    public void setPromotion(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_promotion, value);
    }

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_productWebPage, value.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_productWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByWebPage(org.semanticwb.model.WebPage productwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_productWebPage, productwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByWebPage(org.semanticwb.model.WebPage productwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(productwebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_productWebPage,productwebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_productWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
