package org.semanticwb.portal.community.base;


public class ProductElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_smallPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#smallPhoto");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_prodDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#prodDescription");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_promotion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#promotion");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_productWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#productWebPage");
    public static final org.semanticwb.platform.SemanticClass swbcomm_ProductElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");

    public ProductElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
        return org.semanticwb.portal.community.ProductElement.createProductElement(String.valueOf(id), model);
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
