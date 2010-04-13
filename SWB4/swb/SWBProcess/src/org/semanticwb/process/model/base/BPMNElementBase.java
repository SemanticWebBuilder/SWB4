package org.semanticwb.process.model.base;


public abstract class BPMNElementBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement>(it, true);
        }

        public static org.semanticwb.process.model.BPMNElement getBPMNElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNElement createBPMNElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNElement(id, model)!=null);
        }
    }

    public BPMNElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
