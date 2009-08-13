package org.semanticwb.portal.community.base;


public class ProductResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ProductElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasProduct=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasProduct");
    public static final org.semanticwb.platform.SemanticClass swbcomm_ProductResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductResource");

    public ProductResourceBase()
    {
    }

    public ProductResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ProductResource");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement> listProducts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductElement>(getSemanticObject().listObjectProperties(swbcomm_hasProduct));
    }

    public boolean hasProduct(org.semanticwb.portal.community.ProductElement productelement)
    {
        if(productelement==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasProduct,productelement.getSemanticObject());
    }

    public void addProduct(org.semanticwb.portal.community.ProductElement productelement)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasProduct, productelement.getSemanticObject());
    }

    public void removeAllProduct()
    {
        getSemanticObject().removeProperty(swbcomm_hasProduct);
    }

    public void removeProduct(org.semanticwb.portal.community.ProductElement productelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasProduct,productelement.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductResource> listProductResourceByHasProduct(org.semanticwb.portal.community.ProductElement hasproduct,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasProduct, hasproduct.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ProductResource> listProductResourceByHasProduct(org.semanticwb.portal.community.ProductElement hasproduct)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ProductResource> it=new org.semanticwb.model.GenericIterator(hasproduct.getSemanticObject().getModel().listSubjects(swbcomm_hasProduct,hasproduct.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.ProductElement getProduct()
    {
         org.semanticwb.portal.community.ProductElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasProduct);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.ProductElement)obj.createGenericInstance();
         }
         return ret;
    }
}
