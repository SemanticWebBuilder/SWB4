package org.semanticwb.process.model.base;


public abstract class ChoreographyCompartmentBase extends org.semanticwb.process.model.BPMNCompartment implements org.semanticwb.process.model.Shapeable
{
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyCompartment");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyCompartment");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment>(it, true);
        }

        public static org.semanticwb.process.model.ChoreographyCompartment createChoreographyCompartment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.createChoreographyCompartment(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ChoreographyCompartment getChoreographyCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ChoreographyCompartment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ChoreographyCompartment createChoreographyCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ChoreographyCompartment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeChoreographyCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasChoreographyCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChoreographyCompartment(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartmentByBPMNShape(org.semanticwb.process.model.BPMNShape value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasBPMNShape, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartmentByBPMNShape(org.semanticwb.process.model.BPMNShape value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasBPMNShape,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ChoreographyCompartmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
