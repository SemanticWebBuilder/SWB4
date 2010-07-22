package org.semanticwb.model.base;


public abstract class TextBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_textRegExp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textRegExp");
    public static final org.semanticwb.platform.SemanticClass swbxf_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.Text> listTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Text>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.Text> listTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Text>(it, true);
        }

        public static org.semanticwb.model.Text getText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Text)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.Text createText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Text)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getText(id, model)!=null);
        }
    }

    public TextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getRegExp()
    {
        return getSemanticObject().getProperty(swbxf_textRegExp);
    }

    public void setRegExp(String value)
    {
        getSemanticObject().setProperty(swbxf_textRegExp, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
