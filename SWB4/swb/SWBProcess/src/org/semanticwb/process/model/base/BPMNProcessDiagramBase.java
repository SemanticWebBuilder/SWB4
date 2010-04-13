package org.semanticwb.process.model.base;


public abstract class BPMNProcessDiagramBase extends org.semanticwb.process.model.BPMNDiagram implements org.semanticwb.process.model.LaneCompartmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNProcessDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNProcessDiagram");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNProcessDiagram");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagrams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagrams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram>(it, true);
        }

        public static org.semanticwb.process.model.BPMNProcessDiagram createBPMNProcessDiagram(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.createBPMNProcessDiagram(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNProcessDiagram getBPMNProcessDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNProcessDiagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNProcessDiagram createBPMNProcessDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNProcessDiagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNProcessDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNProcessDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNProcessDiagram(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagramByLaneCompartment(org.semanticwb.process.model.LaneCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagramByLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNProcessDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNProcessDiagramBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment>(getSemanticObject().listObjectProperties(swp_hasLaneCompartment));
    }

    public boolean hasLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasLaneCompartment,value.getSemanticObject());
        }
        return ret;
    }

    public void addLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().addObjectProperty(swp_hasLaneCompartment, value.getSemanticObject());
    }

    public void removeAllLaneCompartment()
    {
        getSemanticObject().removeProperty(swp_hasLaneCompartment);
    }

    public void removeLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().removeObjectProperty(swp_hasLaneCompartment,value.getSemanticObject());
    }

    public org.semanticwb.process.model.LaneCompartment getLaneCompartment()
    {
         org.semanticwb.process.model.LaneCompartment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLaneCompartment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LaneCompartment)obj.createGenericInstance();
         }
         return ret;
    }
}
