package org.semanticwb.model.base;


public class TextBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");


    public static org.semanticwb.model.Text createText(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Text)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_Text), swbxf_Text);
    }

    public TextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
