package org.semanticwb.process.model.base;


public abstract class BPMNChoreographyDiagramBase extends org.semanticwb.process.model.BPMNDiagram implements org.semanticwb.process.model.ChoreographyCompartmentable
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNChoreographyDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNChoreographyDiagram");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNChoreographyDiagram");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagrams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagrams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram>(it, true);
        }

        public static org.semanticwb.process.model.BPMNChoreographyDiagram createBPMNChoreographyDiagram(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.createBPMNChoreographyDiagram(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNChoreographyDiagram getBPMNChoreographyDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNChoreographyDiagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNChoreographyDiagram createBPMNChoreographyDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNChoreographyDiagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNChoreographyDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNChoreographyDiagram(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNChoreographyDiagram(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagramByChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagramByChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyCompartment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagramByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNChoreographyDiagram> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNChoreographyDiagramBase(org.semanticwb.platform.SemanticObject base)
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
}
