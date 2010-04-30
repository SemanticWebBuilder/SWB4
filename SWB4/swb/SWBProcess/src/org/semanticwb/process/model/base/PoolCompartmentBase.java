package org.semanticwb.process.model.base;


public abstract class PoolCompartmentBase extends org.semanticwb.process.model.BPMNCompartment implements org.semanticwb.process.model.LaneCompartmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_PoolCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PoolCompartment");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PoolCompartment");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment>(it, true);
        }

        public static org.semanticwb.process.model.PoolCompartment createPoolCompartment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.PoolCompartment.ClassMgr.createPoolCompartment(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.PoolCompartment getPoolCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PoolCompartment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.PoolCompartment createPoolCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PoolCompartment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePoolCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPoolCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPoolCompartment(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartmentByLaneCompartment(org.semanticwb.process.model.LaneCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartmentByLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public PoolCompartmentBase(org.semanticwb.platform.SemanticObject base)
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
