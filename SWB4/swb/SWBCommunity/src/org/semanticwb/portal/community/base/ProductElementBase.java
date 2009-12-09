package org.semanticwb.portal.community.base;


public abstract class ProductElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Viewable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Searchable
{
       public static final org.semanticwb.platform.SemanticProperty swbcomm_smallPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#smallPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_prodDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#prodDescription");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_promotion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#promotion");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_productWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#productWebPage");
       public static final org.semanticwb.platform.SemanticClass swbcomm_ProductElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
    public static class ClassMgr
    {

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
   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByWebPage(org.semanticwb.model.WebPage productwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_productWebPage, productwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductElement> listProductElementByWebPage(org.semanticwb.model.WebPage productwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> it=new org.semanticwb.model.GenericIterator(productwebpage.getSemanticObject().getModel().listSubjects(swbcomm_productWebPage,productwebpage.getSemanticObject()));
       return it;
   }
    }

    public ProductElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getSmallPhoto()
    {
        return getSemanticObject().getProperty(swbcomm_smallPhoto);
    }

    public void setSmallPhoto(String value)
    {
        getSemanticObject().setProperty(swbcomm_smallPhoto, value);
    }

    public String getProdDescription()
    {
        return getSemanticObject().getProperty(swbcomm_prodDescription);
    }

    public void setProdDescription(String value)
    {
        getSemanticObject().setProperty(swbcomm_prodDescription, value);
    }

    public boolean isPromotion()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_promotion);
    }

    public void setPromotion(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_promotion, value);
    }

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_productWebPage, value.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_productWebPage);
    }


    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_productWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
