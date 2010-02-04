package org.semanticwb.process.model.base;


public abstract class LinkDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_LinkDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LinkDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LinkDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.LinkDetail> listLinkDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.LinkDetail> listLinkDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkDetail>(it, true);
       }

       public static org.semanticwb.process.model.LinkDetail createLinkDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.LinkDetail.ClassMgr.createLinkDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.LinkDetail getLinkDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.LinkDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.LinkDetail createLinkDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.LinkDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeLinkDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasLinkDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getLinkDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.LinkDetail> listLinkDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.LinkDetail> listLinkDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public LinkDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
