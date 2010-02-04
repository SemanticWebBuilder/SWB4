package org.semanticwb.process.model.base;


public abstract class DefaultSequenceFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_DefaultSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DefaultSequenceFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DefaultSequenceFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow>(it, true);
       }

       public static org.semanticwb.process.model.DefaultSequenceFlow createDefaultSequenceFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.DefaultSequenceFlow.ClassMgr.createDefaultSequenceFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.DefaultSequenceFlow getDefaultSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DefaultSequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.DefaultSequenceFlow createDefaultSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DefaultSequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDefaultSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDefaultSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDefaultSequenceFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DefaultSequenceFlow> listDefaultSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultSequenceFlow> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public DefaultSequenceFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
