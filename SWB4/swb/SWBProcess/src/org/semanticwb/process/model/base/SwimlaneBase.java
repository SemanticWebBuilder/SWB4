package org.semanticwb.process.model.base;


public abstract class SwimlaneBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Swimlane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Swimlane");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Swimlane");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Swimlane> listSwimlanes(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Swimlane>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Swimlane> listSwimlanes()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Swimlane>(it, true);
       }

       public static org.semanticwb.process.model.Swimlane createSwimlane(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Swimlane.ClassMgr.createSwimlane(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Swimlane getSwimlane(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Swimlane)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Swimlane createSwimlane(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Swimlane)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSwimlane(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSwimlane(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSwimlane(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Swimlane> listSwimlaneByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Swimlane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Swimlane> listSwimlaneByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Swimlane> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public SwimlaneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
