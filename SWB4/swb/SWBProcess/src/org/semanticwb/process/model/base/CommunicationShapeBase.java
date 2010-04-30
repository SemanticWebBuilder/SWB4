package org.semanticwb.process.model.base;


public abstract class CommunicationShapeBase extends org.semanticwb.process.model.ConversationCommunicationShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_CommunicationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CommunicationShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CommunicationShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CommunicationShape> listCommunicationShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CommunicationShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CommunicationShape> listCommunicationShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CommunicationShape>(it, true);
        }

        public static org.semanticwb.process.model.CommunicationShape createCommunicationShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CommunicationShape.ClassMgr.createCommunicationShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CommunicationShape getCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CommunicationShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CommunicationShape createCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CommunicationShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCommunicationShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CommunicationShape> listCommunicationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CommunicationShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CommunicationShape> listCommunicationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CommunicationShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CommunicationShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
