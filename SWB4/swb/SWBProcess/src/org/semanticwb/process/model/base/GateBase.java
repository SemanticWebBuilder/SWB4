package org.semanticwb.process.model.base;


public abstract class GateBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Assignable
{
       public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
       public static final org.semanticwb.platform.SemanticProperty swp_outgoingSequenceFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outgoingSequenceFlowRef");
       public static final org.semanticwb.platform.SemanticClass swp_Gate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gate");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gate");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Gate> listGates(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Gate> listGates()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate>(it, true);
       }

       public static org.semanticwb.process.model.Gate createGate(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Gate.ClassMgr.createGate(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Gate getGate(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Gate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Gate createGate(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Gate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeGate(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasGate(String id, org.semanticwb.model.SWBModel model)
       {
           return (getGate(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByOutgoingSequenceFlowRef(org.semanticwb.process.model.SequenceFlow outgoingsequenceflowref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_outgoingSequenceFlowRef, outgoingsequenceflowref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gate> listGateByOutgoingSequenceFlowRef(org.semanticwb.process.model.SequenceFlow outgoingsequenceflowref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> it=new org.semanticwb.model.GenericIterator(outgoingsequenceflowref.getSemanticObject().getModel().listSubjects(swp_outgoingSequenceFlowRef,outgoingsequenceflowref.getSemanticObject()));
       return it;
   }
    }

    public GateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> listAssignments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment>(getSemanticObject().listObjectProperties(swp_hasAssignment));
    }

    public boolean hasAssignment(org.semanticwb.process.model.Assignment assignment)
    {
        if(assignment==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasAssignment,assignment.getSemanticObject());
    }

    public void addAssignment(org.semanticwb.process.model.Assignment value)
    {
        getSemanticObject().addObjectProperty(swp_hasAssignment, value.getSemanticObject());
    }

    public void removeAllAssignment()
    {
        getSemanticObject().removeProperty(swp_hasAssignment);
    }

    public void removeAssignment(org.semanticwb.process.model.Assignment assignment)
    {
        getSemanticObject().removeObjectProperty(swp_hasAssignment,assignment.getSemanticObject());
    }


    public org.semanticwb.process.model.Assignment getAssignment()
    {
         org.semanticwb.process.model.Assignment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasAssignment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Assignment)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutgoingSequenceFlowRef(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().setObjectProperty(swp_outgoingSequenceFlowRef, value.getSemanticObject());
    }

    public void removeOutgoingSequenceFlowRef()
    {
        getSemanticObject().removeProperty(swp_outgoingSequenceFlowRef);
    }


    public org.semanticwb.process.model.SequenceFlow getOutgoingSequenceFlowRef()
    {
         org.semanticwb.process.model.SequenceFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outgoingSequenceFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SequenceFlow)obj.createGenericInstance();
         }
         return ret;
    }
}
