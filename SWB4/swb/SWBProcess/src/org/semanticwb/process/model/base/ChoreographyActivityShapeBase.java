package org.semanticwb.process.model.base;


public abstract class ChoreographyActivityShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyActivityShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyActivityShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyActivityShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyActivityShape> listChoreographyActivityShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyActivityShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyActivityShape> listChoreographyActivityShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyActivityShape>(it, true);
        }

        public static org.semanticwb.process.model.ChoreographyActivityShape createChoreographyActivityShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.createChoreographyActivityShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ChoreographyActivityShape getChoreographyActivityShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ChoreographyActivityShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ChoreographyActivityShape createChoreographyActivityShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ChoreographyActivityShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeChoreographyActivityShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasChoreographyActivityShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChoreographyActivityShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyActivityShape> listChoreographyActivityShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyActivityShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ChoreographyActivityShape> listChoreographyActivityShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ChoreographyActivityShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ChoreographyActivityShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
