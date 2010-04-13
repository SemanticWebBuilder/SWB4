package org.semanticwb.process.model.base;


public abstract class BPMNLabelBase extends org.semanticwb.process.model.BPMNNode 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNLabel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNLabel");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNLabel");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNLabel> listBPMNLabels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNLabel>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNLabel> listBPMNLabels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNLabel>(it, true);
        }

        public static org.semanticwb.process.model.BPMNLabel createBPMNLabel(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNLabel.ClassMgr.createBPMNLabel(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNLabel getBPMNLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNLabel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNLabel createBPMNLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNLabel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNLabel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNLabel(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNLabel> listBPMNLabelByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNLabel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNLabel> listBPMNLabelByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNLabel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNLabelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
