package org.semanticwb.process.model.base;


public abstract class EventDetailBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_EventDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.EventDetail> listEventDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.EventDetail> listEventDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail>(it, true);
       }

       public static org.semanticwb.process.model.EventDetail createEventDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.EventDetail.ClassMgr.createEventDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.EventDetail getEventDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EventDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.EventDetail createEventDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EventDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeEventDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasEventDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getEventDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.EventDetail> listEventDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EventDetail> listEventDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public EventDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
