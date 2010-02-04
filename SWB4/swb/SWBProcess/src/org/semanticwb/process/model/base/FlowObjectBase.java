package org.semanticwb.process.model.base;


public abstract class FlowObjectBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObject");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject>(it, true);
       }

       public static org.semanticwb.process.model.FlowObject getFlowObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.FlowObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.FlowObject createFlowObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.FlowObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeFlowObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasFlowObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getFlowObject(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjectByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjectByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjectByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObject> listFlowObjectByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObject> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public FlowObjectBase(org.semanticwb.platform.SemanticObject base)
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
}
