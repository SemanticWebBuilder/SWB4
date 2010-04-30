package org.semanticwb.process.model.base;


public abstract class TextAnnotationBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticProperty swp_annotationText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#annotationText");
    public static final org.semanticwb.platform.SemanticClass swp_TextAnnotation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation>(it, true);
        }

        public static org.semanticwb.process.model.TextAnnotation createTextAnnotation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TextAnnotation.ClassMgr.createTextAnnotation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.TextAnnotation getTextAnnotation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TextAnnotation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.TextAnnotation createTextAnnotation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TextAnnotation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeTextAnnotation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTextAnnotation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextAnnotation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TextAnnotation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public TextAnnotationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getAnnotationText()
    {
        return getSemanticObject().getProperty(swp_annotationText);
    }

    public void setAnnotationText(String value)
    {
        getSemanticObject().setProperty(swp_annotationText, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
