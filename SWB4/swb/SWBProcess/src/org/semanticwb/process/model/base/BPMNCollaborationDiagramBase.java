package org.semanticwb.process.model.base;


public abstract class BPMNCollaborationDiagramBase extends org.semanticwb.process.model.BPMNDiagram implements org.semanticwb.process.model.ChoreographyCompartmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_PoolCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PoolCompartment");
    public static final org.semanticwb.platform.SemanticProperty swp_hasPoolCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasPoolCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNCollaborationDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCollaborationDiagram");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCollaborationDiagram");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagrams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagrams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram>(it, true);
        }

        public static org.semanticwb.process.model.BPMNCollaborationDiagram createBPMNCollaborationDiagram(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.createBPMNCollaborationDiagram(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNCollaborationDiagram getBPMNCollaborationDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNCollaborationDiagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNCollaborationDiagram createBPMNCollaborationDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNCollaborationDiagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNCollaborationDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNCollaborationDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNCollaborationDiagram(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyCompartment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByPoolCompartment(org.semanticwb.process.model.PoolCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasPoolCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagramByPoolCompartment(org.semanticwb.process.model.PoolCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCollaborationDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasPoolCompartment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNCollaborationDiagramBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment value)
    {
        getSemanticObject().setObjectProperty(swp_choreographyCompartment, value.getSemanticObject());
    }

    public void removeChoreographyCompartment()
    {
        getSemanticObject().removeProperty(swp_choreographyCompartment);
    }

    public org.semanticwb.process.model.ChoreographyCompartment getChoreographyCompartment()
    {
         org.semanticwb.process.model.ChoreographyCompartment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_choreographyCompartment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ChoreographyCompartment)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PoolCompartment>(getSemanticObject().listObjectProperties(swp_hasPoolCompartment));
    }

    public boolean hasPoolCompartment(org.semanticwb.process.model.PoolCompartment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasPoolCompartment,value.getSemanticObject());
        }
        return ret;
    }

    public void addPoolCompartment(org.semanticwb.process.model.PoolCompartment value)
    {
        getSemanticObject().addObjectProperty(swp_hasPoolCompartment, value.getSemanticObject());
    }

    public void removeAllPoolCompartment()
    {
        getSemanticObject().removeProperty(swp_hasPoolCompartment);
    }

    public void removePoolCompartment(org.semanticwb.process.model.PoolCompartment value)
    {
        getSemanticObject().removeObjectProperty(swp_hasPoolCompartment,value.getSemanticObject());
    }

    public org.semanticwb.process.model.PoolCompartment getPoolCompartment()
    {
         org.semanticwb.process.model.PoolCompartment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasPoolCompartment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.PoolCompartment)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
