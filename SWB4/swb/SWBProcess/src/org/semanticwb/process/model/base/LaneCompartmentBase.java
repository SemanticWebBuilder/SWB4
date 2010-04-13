package org.semanticwb.process.model.base;


public abstract class LaneCompartmentBase extends org.semanticwb.process.model.BPMNCompartment implements org.semanticwb.process.model.Shapeable
{
    public static final org.semanticwb.platform.SemanticClass swp_LaneCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneCompartment");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSubLane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasSubLane");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneCompartment");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment>(it, true);
        }

        public static org.semanticwb.process.model.LaneCompartment createLaneCompartment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.LaneCompartment.ClassMgr.createLaneCompartment(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.LaneCompartment getLaneCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LaneCompartment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.LaneCompartment createLaneCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LaneCompartment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeLaneCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasLaneCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLaneCompartment(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentBySubLane(org.semanticwb.process.model.LaneCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSubLane, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentBySubLane(org.semanticwb.process.model.LaneCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSubLane,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentByBPMNShape(org.semanticwb.process.model.BPMNShape value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasBPMNShape, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartmentByBPMNShape(org.semanticwb.process.model.BPMNShape value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasBPMNShape,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public LaneCompartmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> listSubLanes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment>(getSemanticObject().listObjectProperties(swp_hasSubLane));
    }

    public boolean hasSubLane(org.semanticwb.process.model.LaneCompartment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSubLane,value.getSemanticObject());
        }
        return ret;
    }

    public void addSubLane(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().addObjectProperty(swp_hasSubLane, value.getSemanticObject());
    }

    public void removeAllSubLane()
    {
        getSemanticObject().removeProperty(swp_hasSubLane);
    }

    public void removeSubLane(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSubLane,value.getSemanticObject());
    }

    public org.semanticwb.process.model.LaneCompartment getSubLane()
    {
         org.semanticwb.process.model.LaneCompartment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSubLane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LaneCompartment)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape> listBPMNShapes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape>(getSemanticObject().listObjectProperties(swp_hasBPMNShape));
    }

    public boolean hasBPMNShape(org.semanticwb.process.model.BPMNShape value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasBPMNShape,value.getSemanticObject());
        }
        return ret;
    }

    public void addBPMNShape(org.semanticwb.process.model.BPMNShape value)
    {
        getSemanticObject().addObjectProperty(swp_hasBPMNShape, value.getSemanticObject());
    }

    public void removeAllBPMNShape()
    {
        getSemanticObject().removeProperty(swp_hasBPMNShape);
    }

    public void removeBPMNShape(org.semanticwb.process.model.BPMNShape value)
    {
        getSemanticObject().removeObjectProperty(swp_hasBPMNShape,value.getSemanticObject());
    }

    public org.semanticwb.process.model.BPMNShape getBPMNShape()
    {
         org.semanticwb.process.model.BPMNShape ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasBPMNShape);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNShape)obj.createGenericInstance();
         }
         return ret;
    }
}
