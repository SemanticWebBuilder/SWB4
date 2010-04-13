package org.semanticwb.process.model.base;


public abstract class TextAnnotationShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_TextAnnotationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotationShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotationShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotationShape> listTextAnnotationShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotationShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotationShape> listTextAnnotationShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotationShape>(it, true);
        }

        public static org.semanticwb.process.model.TextAnnotationShape createTextAnnotationShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.createTextAnnotationShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.TextAnnotationShape getTextAnnotationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TextAnnotationShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.TextAnnotationShape createTextAnnotationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TextAnnotationShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeTextAnnotationShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTextAnnotationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextAnnotationShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotationShape> listTextAnnotationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotationShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotationShape> listTextAnnotationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotationShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public TextAnnotationShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
