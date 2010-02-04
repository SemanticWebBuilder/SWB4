package org.semanticwb.process.model.base;


public abstract class NormalSequenceFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_NormalSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalSequenceFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalSequenceFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow>(it, true);
       }

       public static org.semanticwb.process.model.NormalSequenceFlow createNormalSequenceFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.NormalSequenceFlow.ClassMgr.createNormalSequenceFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.NormalSequenceFlow getNormalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalSequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.NormalSequenceFlow createNormalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalSequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeNormalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasNormalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getNormalSequenceFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalSequenceFlow> listNormalSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalSequenceFlow> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public NormalSequenceFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
