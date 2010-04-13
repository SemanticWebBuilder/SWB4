package org.semanticwb.process.model.base;


public abstract class BPMNCompartmentBase extends org.semanticwb.process.model.BPMNNode 
{
    public static final org.semanticwb.platform.SemanticProperty swp_visible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#visible");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCompartment");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCompartment");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCompartment> listBPMNCompartments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCompartment>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCompartment> listBPMNCompartments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCompartment>(it, true);
        }

        public static org.semanticwb.process.model.BPMNCompartment createBPMNCompartment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNCompartment.ClassMgr.createBPMNCompartment(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNCompartment getBPMNCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNCompartment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNCompartment createBPMNCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNCompartment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNCompartment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNCompartment(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCompartment> listBPMNCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCompartment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNCompartment> listBPMNCompartmentByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNCompartment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNCompartmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isVisible()
    {
        return getSemanticObject().getBooleanProperty(swp_visible);
    }

    public void setVisible(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_visible, value);
    }
}
