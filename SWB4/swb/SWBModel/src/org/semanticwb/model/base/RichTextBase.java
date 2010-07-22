package org.semanticwb.model.base;


public abstract class RichTextBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_RichText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }

        public static org.semanticwb.model.RichText getRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.RichText createRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeRichText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRichText(id, model)!=null);
        }
    }

    public RichTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
